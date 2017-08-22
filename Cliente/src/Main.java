
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.JOptionPane;
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
		public void onCompra(String usuario, List<Trecho> passagens) { 
			System.out.println("Comprando " + passagens.size() + " passagens...");
			try {
				List<Trecho> passagensIndisponiveis = lookUp.comprar(usuario, passagens);
				janela.atualizaTrechos(lookUp.getTrechos());
				if (passagensIndisponiveis.size() > 0) {
					janela.atualizaCarrinho(passagensIndisponiveis);
					JOptionPane.showMessageDialog(janela, "Algumas passagens não estão disponíveis para compra.\n " +
							"Verifique seu carrinho e se desejar, faça uma reserva.");
				}
				System.out.println(passagensIndisponiveis.size() + " passagens indisponiveis!");
			} catch (RemoteException e) {
				System.err.println("Erro ao comprar as passagens.");
				JOptionPane.showMessageDialog(janela, "Erro ao comprar as passagens.");
				//e.printStackTrace();
			}
		}

		@Override
		public void onReserva(String usuario, List<Trecho> trechos) {
			System.out.println("Reservando " + trechos.size() + " passagens...");
			try {
				lookUp.reservar(usuario, trechos);
				janela.atualizaTrechos(lookUp.getTrechos());
			} catch (RemoteException e) {
				System.err.println("Erro ao fazer a reserva das passagens.");
				JOptionPane.showMessageDialog(janela, "Erro ao fazer a reserva das passagens.");
				//e.printStackTrace();
			}
		}

		@Override
		public void onRecarregar() { 
			try {
				janela.atualizaTrechos(lookUp.getTrechos());
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws NotBoundException, NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Insira o ID do servico: "); 
		String id = br.readLine();
		
		lookUp = (IRemoto) Naming.lookup("localhost/Decolagem" + id);
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
