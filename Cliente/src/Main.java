import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import network.IRemoto;

public class Main {

	private static IRemoto lookUp;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		lookUp = (IRemoto) Naming.lookup("//localhost/MyServer");
		String ret = lookUp.teste("Oi"); // Enviando um "Oi"
		System.out.println("Resposta: " + ret); // Espero receber um "Ola"
	}

}
