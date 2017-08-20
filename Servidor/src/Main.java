import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.Scanner;

import network.Servidor;


public class Main { 

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Insira a porta");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int porta = Integer.parseInt(br.readLine());
		System.out.println("Insira o nome do servico:"); 
		String nome = br.readLine(); 
		
		Servidor servidor;
		try {
			servidor = new Servidor(); 
			servidor.iniciar(porta, nome);  
		} catch (RemoteException e) { 
			e.printStackTrace();
		}
	}

}
