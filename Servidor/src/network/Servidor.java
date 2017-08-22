package network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import model.Companhia;
import model.Trecho;
import controller.Controller;
import controller.Controller.RegiaoCritica;

public class Servidor extends UnicastRemoteObject implements IRemoto {
	
	private static final long serialVersionUID = 1L;

	public Servidor() throws RemoteException {
		super();
	} 
	
	@Override
	public String teste(String str) throws RemoteException {
        System.err.println("Servidor.teste() nova requisicao: " + str);
        return "Ola";
	}
	
	public boolean iniciar(int porta, int id) {
	    try {   	
			LocateRegistry.createRegistry(porta);
			Naming.rebind("localhost/Decolagem" + id, (IRemoto) this);
	        System.out.println(" --- Servidor Iniciado --- ");
	        System.out.println("Porta " + porta + " nomeServico " + id);
	    } catch (Exception e) { 
	        e.printStackTrace();
	        return false;
	    }
	    return true;
	}
			
	@Override
	public List<Trecho> requisitarTrechos() throws RemoteException {
 		List<Trecho> trechos = new ArrayList<Trecho>();
 		trechos.addAll(Controller.getInstance().getCompanhia().getTrechos().values());
		return trechos;
	}
	
 	@Override
    public List<Trecho> getTrechos() throws RemoteException {
 		List<Trecho> trechos = new ArrayList<Trecho>();
 		trechos.addAll(Controller.getInstance().getCompanhia().getTrechos().values());
 		
		for (int i = 1; i <= Controller.getInstance().getTotalServidores(); i++) {
			if (i == Controller.getInstance().getId()) continue;
			IRemoto remoto;
			try {
				remoto = (IRemoto) Naming.lookup("localhost/Decolagem" + i);
				trechos.addAll(remoto.requisitarTrechos());
			} catch (MalformedURLException | NotBoundException e) {
				System.err.println("Erro ao solicitar entrada na sessao critica. Servidor" + i + " nao disponivel!");
			}
		}
 		
		return trechos;
	}

	@Override
	public boolean requisitarAcesso(String usuario, int clockLamport)
			throws RemoteException {
		// Ref.: Sistemas Distribuidos: Conceitos e Projetos. PDF pag 647
		System.out.println("Servidor.requisitarAcesso(): Requisicao de acesso a secao critica por \"" + usuario + "\"");
		
		Controller.getInstance().atualizaClockLamport(clockLamport); 
		
		// Caso 1: A secao critica nao esta em uso
		// Caso 2: A secao critica esta em uso, entao add a requisicao a um FIFO para retarda-la
		// Caso 3: A secao critica nao esta em uso, porem dois processos solicitam sua posse. 
		//	Utilizar, entao, o clockLamport para desempate.
		
		// Se este servidor estiver na regicao critica OU 
		// (se este servidor pretender entrar na regiao critica E este clock lamport < clock lamport da requisicao) 
		if (Controller.getInstance().getStatusRegiaCritica() == RegiaoCritica.HELD ||
		   (Controller.getInstance().getStatusRegiaCritica() == RegiaoCritica.WANTED 
				&& Controller.getInstance().getClockLamport() < clockLamport) ) {
			// Enfileira a requisicao
			Controller.getInstance().incremContadorDeRequisicoesEmFila();
			while (Controller.getInstance().getContadorDeRequisicoesEmFila() != 0); // Espera bloqueada ate a saida da secao critica deste servida
			
		} // else responde imediatamente
		
		return true;
	}

	@Override
	public List<Trecho> comprar(String usuario, List<Trecho> passagens)
			throws RemoteException {
		List<Trecho> trechosIndisponiveis = new ArrayList<Trecho>();
		
		Controller.getInstance().incrementaClockLamport(); 
		
		// Ref.: Sistemas Distribuidos: Conceitos e Projetos. PDF pag 647
		// Envia solicitacao para entrada na secao critica para todos os servidores
		Controller.getInstance().setStatusRegiaCritica(RegiaoCritica.WANTED); // Deseja entrar na secao critica
		IRemoto remoto;
		for (int i = 1; i <= Controller.getInstance().getTotalServidores(); i++) {
			if (i == Controller.getInstance().getId()) continue;
			try {
				remoto = (IRemoto) Naming.lookup("localhost/Decolagem" + i);
				remoto.requisitarAcesso(usuario, Controller.getInstance().getClockLamport());
			} catch (MalformedURLException | NotBoundException e) {
				System.err.println("Erro ao solicitar entrada na sessao critica. Servidor" + i + " nao disponivel!");
			}
		}
		
		Controller.getInstance().setStatusRegiaCritica(RegiaoCritica.HELD); // Entrou na secao critica

		// Compra as passagens nesse servidor
		trechosIndisponiveis.addAll(Controller.getInstance().comprar(usuario, passagens));
		
		Controller.getInstance().setStatusRegiaCritica(RegiaoCritica.RELEASED); // Sai da secao critica
		
		// Responde a todas as requisicoes enfileiradas de entrada na secao critica deste servidor
		Controller.getInstance().apagaContadorDeRequisicoesEmFila();
		
		// Compra as passagens nos demais servidores
		for (int i = 1; i <= Controller.getInstance().getTotalServidores(); i++) { // FIXME Recuperar o total de servidores do controlador
			if (i == Controller.getInstance().getId()) continue;
			try {
				remoto = (IRemoto) Naming.lookup("localhost/Decolagem" + i);
				trechosIndisponiveis.addAll(remoto.requisitarCompra(usuario, passagens)); // Compra as demais passagens e retorna os trechos indisponiveis
			} catch (MalformedURLException | NotBoundException e) {
				System.err.println("Erro ao comprar passagens nos demais servidores. Servidor" + i + " nao disponivel!");
			}
		}
		
		// Retorna para o cliente todas as passagens indisponiveis no processo  de compra
		return trechosIndisponiveis;
	}
	
	@Override
	public List<Trecho> requisitarCompra(String usuario, List<Trecho> passagens)
			throws RemoteException {
		List<Trecho> trechosIndisponiveis = new ArrayList<Trecho>();
		
		Controller.getInstance().incrementaClockLamport(); 
		
		// Ref.: Sistemas Distribuidos: Conceitos e Projetos. PDF pag 647
		// Envia solicitacao para entrada na secao critica para todos os servidores
		Controller.getInstance().setStatusRegiaCritica(RegiaoCritica.WANTED); // Deseja entrar na secao critica
		IRemoto remoto;
		for (int i = 1; i <= Controller.getInstance().getTotalServidores(); i++) {
			if (i == Controller.getInstance().getId()) continue;
			try {
				remoto = (IRemoto) Naming.lookup("localhost/Decolagem" + i);
				remoto.requisitarAcesso(usuario, Controller.getInstance().getClockLamport());
			} catch (MalformedURLException | NotBoundException e) {
				System.err.println("Erro ao solicitar entrada na sessao critica. Servidor" + i + " nao disponivel!");
			}
		}
		
		Controller.getInstance().setStatusRegiaCritica(RegiaoCritica.HELD); // Entrou na secao critica

		// Compra as passagens nesse servidor
		trechosIndisponiveis.addAll(Controller.getInstance().comprar(usuario, passagens));
		
		Controller.getInstance().setStatusRegiaCritica(RegiaoCritica.RELEASED); // Sai da secao critica
		
		// Responde a todas as requisicoes enfileiradas de entrada na secao critica deste servidor
		Controller.getInstance().apagaContadorDeRequisicoesEmFila();
		
		// Retorna para o cliente todas as passagens indisponiveis no processo  de compra
		return trechosIndisponiveis;
	}
	

	@Override 
	public List<Trecho> requisitarReservar(String usuario,
			List<Trecho> passagens) throws RemoteException {
		Controller.getInstance().reservar(usuario, passagens);
		return null;
	}

	@Override
	public List<Trecho> reservar(String usuario, List<Trecho> passagens)
			throws RemoteException {
		System.out.println("CHAMOU O RESERVAR E CHEGOU NO SERVER");
		Controller.getInstance().reservar(usuario, passagens);
		
		IRemoto remoto;
		for (int i = 1; i <= Controller.getInstance().getTotalServidores(); i++) {
			if (i == Controller.getInstance().getId()) continue;
			try {
				remoto = (IRemoto) Naming.lookup("localhost/Decolagem" + i);
				remoto.requisitarReservar(usuario, passagens);
			} catch (MalformedURLException | NotBoundException e) {
				System.err.println("Erro ao solicitar a reserva de passagem. Servidor" + i + " nao disponivel!");
			}
		}
		return null;
	}
}
