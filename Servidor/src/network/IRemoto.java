package network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Companhia;
import model.Trecho;

public interface IRemoto extends Remote {
	
    public String teste(String str) throws RemoteException;

    public Companhia getCompanhia() throws RemoteException;
    
}