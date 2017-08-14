import java.rmi.RemoteException;

import network.Servidor;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Servidor servidor;
		try {
			servidor = new Servidor();
			servidor.iniciar();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
