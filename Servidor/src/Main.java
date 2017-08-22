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
		
		System.out.println("Insira a porta para iniciar o servidor");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int porta = Integer.parseInt(br.readLine());

		System.out.println("Insira o ID para iniciar o servidor");
		int id = Integer.parseInt(br.readLine());
		
		Controller.getInstance().setId(id);
		Controller.getInstance().configuraServidor();
		
		try {
			servidor = new Servidor();
			servidor.iniciar(porta, id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		// Apenas um teste de comunicacao servidor para servidor
		/*while (true) {
			System.out.println("Entre com o id do servidor: ");
			String nome = br.readLine();
			
			IRemoto lookUp = (IRemoto) Naming.lookup("localhost/Decolagem" + nome);
			String ret = lookUp.teste("Oi"); // Enviando um "Oi"
			System.out.println("Resp: " + ret); 
		}*/
		
		while(true) {
			System.out.println("aperte um local, dois envia, 3 ");
			switch (Integer.parseInt(br.readLine())) {
			case 1:
				System.out.println("Tamanho da lista de espera de: "+servidor.getTrechos().get(0).getInicio()+" = "+servidor.getTrechos().get(0).getTamanhoListaEspera());
				break;
			case 2: 
				IRemoto lookUp = (IRemoto) Naming.lookup("localhost/Decolagem" + 1);
				System.out.println("Tamanho da lista de espera " +lookUp.getTrechos().get(0).getInicio()+lookUp.getTrechos().get(0).getCompanhia()+"no servidor um: " +lookUp.getTrechos().get(0).getTamanhoListaEspera());
			default:
				break;
			}
		}
	}

}
