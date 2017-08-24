import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import controller.Controller;
import network.IRemoto;
import network.Servidor;


public class Main { 

	private static Servidor servidor = null;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws IOException, NotBoundException {
		String[] ips = new String[4];
		System.out.println("Insira a porta para iniciar o servidor");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int porta = Integer.parseInt(br.readLine());

		System.out.println("Insira o ID para iniciar o servidor");
		int id = Integer.parseInt(br.readLine());
		
		System.out.print("Insira o ip do servido conveniado");
		String ip1 = br.readLine();
		System.out.println("Insira o id desse servidor");
		int id1 = Integer.parseInt(br.readLine());
		
		System.out.print("Insira o ip do servido conveniado");
		String ip2 = br.readLine();
		System.out.println("Insira o id desse servidor");
		int id2 = Integer.parseInt(br.readLine());
		
		
		ips[id] = "127.0.0.1";
		ips[id1] = ip1;
		ips[id2] = ip2;
		
		Controller.getInstance().setId(id);
		Controller.getInstance().configuraServidor();
		
		try {
			servidor = new Servidor(ips);
			servidor.iniciar(porta, id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
