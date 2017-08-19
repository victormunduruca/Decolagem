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
	
	public boolean iniciar(int porta, String nomeServico) {
	    try {
	
	    	System.setProperty("java.rmi.server.hostname", "192.168.1.8");
			LocateRegistry.createRegistry(porta);
			Naming.bind(nomeServico, this);
	        System.out.println(" --- Servidor Iniciado --- ");
	        System.out.println("Porta "+porta +" nomeServico " +nomeServico);
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
