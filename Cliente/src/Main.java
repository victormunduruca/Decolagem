
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import model.Trecho;
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
				janela.setTexto(lookUp.teste("olar"));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		lookUp = (IRemoto) Naming.lookup("rmi://192.168.1.8:1099/DecolagemService");
		Main main = new Main();
	}
}
