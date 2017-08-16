 package network;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements IRemoto {

	public Servidor() throws RemoteException {
		super();
	}

	public String teste(String str) throws RemoteException {
        System.err.println("Requisicao: " + str);
        return "Ola";
	}
	
	public boolean iniciar() {
	    try {
	
	        Naming.rebind("//localhost/MyServer", new Servidor());
	        System.out.println(" --- Servidor Iniciado --- ");
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	
	    }
	    return true;
	}
}
