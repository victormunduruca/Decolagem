
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.SwingUtilities;

import model.Trecho;
import network.IRemoto;
import view.JanelaPrincipal;
import view.JanelaPrincipal.Listener;

public class Main{

	private static IRemoto lookUp;
	private static JanelaPrincipal janela;
	
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
	private static class PrincipalCallback implements Listener {
		@Override
		public void onCompra(List<Trecho> passagens) { 
			System.out.println("Comprando " + passagens.size() + " passagens");
		}

		@Override
		public void onReserva() { }

		@Override
		public void onRecarregar() { 
			try {
				janela.atualizaTrechos(lookUp.getCompanhia());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws NotBoundException, NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Insira o nome do servico:"); 
		String nome = br.readLine();
		
		lookUp = (IRemoto) Naming.lookup("localhost/Decolagem" + nome);
		iniciarGui();  
	}
	
	public static void iniciarGui() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				janela = new JanelaPrincipal(new PrincipalCallback());
			}
		});
	}
}
