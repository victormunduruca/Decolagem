package network;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import controller.ControllerServidor;
import model.Companhia;
import model.Trecho;

public class Servidor extends UnicastRemoteObject implements IRemoto {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ControllerServidor controller;
	private RelogioLamport relogio;
	public Servidor(int id) throws RemoteException {
		super();
		relogio = new RelogioLamport(id);
		controller = new ControllerServidor(id);
		try {
			controller.configuraServidor();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String teste(String str) throws RemoteException {
		relogio.eventoLocal();
        System.err.println("Servidor.teste() nova requisicao: " + str);
        return "Ola";
	}
	
	public boolean iniciar(int porta, int id) {
	    try {
	
	    	//System.setProperty("java.rmi.server.hostname", "127.0.0.1");
//			LocateRegistry.createRegistry(porta);
//			Naming.rebind(nomeServico, new Servidor());
//	        System.out.println(" --- Servidor Iniciado --- ");
//	        System.out.println("Porta "+porta +" nomeServico " +nomeServico);
	    	
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
	public void testeServidores(String id) throws MalformedURLException, RemoteException, NotBoundException {
		IRemoto lookUp = null;
		 for(int i = 1; i <= 2; i++) {
			if(i != controller.getId()) {
				 lookUp = (IRemoto) Naming.lookup("192.168.1.8/Decolagem"+i);
				 lookUp.teste("EITAMOLEQUE");
			}
		 }
		
	}
	public void testeServidoresLamport() throws MalformedURLException, RemoteException, NotBoundException {
		IRemoto lookUp = null;
		 for(int i = 1; i <= 2; i++) {
			if(i != controller.getId()) {
				 lookUp = (IRemoto) Naming.lookup("192.168.1.8/Decolagem"+i);
				 lookUp.testeLamport(relogio.getRelogio());
			}
		 }
		
	}
	
 	@Override
	public Companhia getCompanhia() throws RemoteException {
		return controller.getCompanhia();
	}

	@Override
	public void testeLamport(float relogioMsg) throws RemoteException {
		relogio.eventoMsg(relogioMsg);
	}

	public RelogioLamport getRelogioLamport() {
		return relogio;
	}

}
