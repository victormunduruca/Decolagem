package network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import model.Companhia;
import model.Trecho;

public interface IRemoto extends Remote {
	
    public String teste(String str) throws RemoteException;
    
	
    
    
    
    // -- API utilizada na comunicacao entre os servidores distribuidos
    
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public List<Trecho> requisitarTrechos() throws RemoteException;
    
    /**
     * Requisita acesso a compra de passagens.
     * 
     * @param usuario
     * @param clockLamport estampa utilizada para sincronizar os eventos
     * @return
     * @throws RemoteException
     */
    public boolean requisitarAcesso(String usuario, int clockLamport) throws RemoteException;
    
    /**
     * 
     * @param usuario
     * @param passagens
     * @return
     * @throws RemoteException
     */
    public List<Trecho> requisitarCompra(String usuario, List<Trecho> passagens) throws RemoteException;
    
    /**
     * 
     * @param usuario
     * @param passagens
     * @return
     * @throws RemoteException
     */
    public List<Trecho> requisitarReservar(String usuario, List<Trecho> passagens) throws RemoteException;
    
    
    // -- API utilizada na comunicacao entre os clientes e dos servidores  
    
    /**
     * 
     * @return
     * @throws RemoteException
     */
    public List<Trecho> getTrechos() throws RemoteException;
    
    /**
     * 
     * @param usuario
     * @param passagens
     * @return
     * @throws RemoteException
     */
    public List<Trecho> comprar(String usuario, List<Trecho> passagens) throws RemoteException;
    
    /**
     * 
     * @param usuario
     * @param passagens
     * @return
     * @throws RemoteException
     */
    public List<Trecho> reservar(String usuario, List<Trecho> passagens) throws RemoteException;
}