package network;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import controller.ControllerServidor;
import model.Companhia;
import model.Trecho;

public class Servidor extends UnicastRemoteObject implements IRemoto {
	
	private ControllerServidor controller;

	public Servidor() throws RemoteException {
		super();
		controller = new ControllerServidor();
		try {
			controller.configuraServidor();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String teste(String str) throws RemoteException {
        System.err.println("Requisicao: " + str);
        return "Ola";
	}
	
	public boolean iniciar(int porta, String id) {
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

	@Override
	public Companhia getCompanhia() throws RemoteException {
		return controller.getCompanhia();
	}

}
