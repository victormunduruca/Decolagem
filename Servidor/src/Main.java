import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import network.Servidor;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws NotBoundException 
	 */
	public static void main(String[] args) throws IOException, NotBoundException {
		
		System.out.println("Insira a porta");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int porta = Integer.parseInt(br.readLine());
		System.out.println("Insira o id");
		int id = Integer.parseInt(br.readLine());;
		Servidor servidor = null;
		try {
			servidor = new Servidor(id);
			servidor.iniciar(porta, id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		while(true) {
			System.out.println("aperte um local, dois envia, 3 ");
			switch (Integer.parseInt(br.readLine())) {
			case 1:
				servidor.getRelogioLamport().eventoLocal();
				//servidor.testeServidores(1+"");
				break;
			case 2:
				servidor.getRelogioLamport().eventoLocal();
				System.out.println("Lamport antes de enviar a msg" +servidor.getRelogioLamport().getRelogio());
				servidor.testeServidoresLamport();
				break;
			case 3:
				System.out.println("RelogioLamport = " +servidor.getRelogioLamport().getRelogio());
				break;
			default:
				break;
			}
		}
	}

}
