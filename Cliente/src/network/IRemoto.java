package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoto extends Remote {
	
    public String teste(String str) throws RemoteException;
    
}