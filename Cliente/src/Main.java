
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Trecho;
import network.IRemoto;

public class Main {

	private static IRemoto lookUp;

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		
		lookUp = (IRemoto) Naming.lookup("rmi://192.168.1.8:1099/DecolagemService");
		String ret = lookUp.teste("Oi"); // Enviando um "Oi"
		System.out.println("Resposta: " + ret); // Espero receber um "Ola"
		ArrayList<Trecho> str = lookUp.getTrechos();
		for(Trecho trecho: str) {
			System.out.println("Trecho: " +trecho);
		}
	}

}
