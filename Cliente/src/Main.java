
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import network.IRemoto;
import view.JanelaPrincipal;
import view.JanelaPrincipal.Listener;

public class Main{

	private static IRemoto lookUp;
	private static JanelaPrincipal janela;
	public Main() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				janela = new JanelaPrincipal(new PrincipalCallback());
			}
		});
	}
	//	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
	//		
	//		lookUp = (IRemoto) Naming.lookup("rmi://192.168.1.8:1099/DecolagemService");
	//		String ret = lookUp.teste("Oi"); // Enviando um "Oi"
	//		System.out.println("Resposta: " + ret); // Espero receber um "Ola"
	//		ArrayList<Trecho> str = lookUp.getTrechos();
	//		for(Trecho trecho: str) {
	//			System.out.println("Trecho: " +trecho);
	//		}
	//		
	//	}
	private class PrincipalCallback implements Listener{

		@Override
		public void onCompra() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onReserva() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onRecarregar() {
			// TODO Auto-generated method stub
			try {
				janela.atualizaTrechos(lookUp.getCompanhia());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) throws NotBoundException, NumberFormatException, IOException {
		System.out.println("Insira a porta");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int porta = Integer.parseInt(br.readLine());
		System.out.println("Insira o nome do serviço");
		String nome = br.readLine();
		
		lookUp = (IRemoto) Naming.lookup("rmi://192.168.1.8:"+porta+"/"+nome);
		Main main = new Main();
	}
}
