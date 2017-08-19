package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import model.Trecho;
import network.IRemoto;

public class CopyOfJanelaPrincipal {

	private JFrame frame;
	private IRemoto lookUp;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) { 
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CopyOfJanelaPrincipal window = new CopyOfJanelaPrincipal();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CopyOfJanelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//try {
		//	lookUp = (IRemoto) Naming.lookup("rmi://192.168.1.8:1099/DecolagemService");
		//} catch (MalformedURLException | RemoteException | NotBoundException e1) {
		//	// TODO Auto-generated catch block
		//	e1.printStackTrace();
		//}
		frame = new JFrame();
		frame.setBounds(100, 100, 884, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTrechos = new JLabel("Trechos ");
		lblTrechos.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblTrechos.setBounds(186, 38, 90, 31);
		frame.getContentPane().add(lblTrechos);
		
		JLabel lblCarrinho = new JLabel("Carrinho");
		lblCarrinho.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblCarrinho.setBounds(526, 38, 90, 31);
		frame.getContentPane().add(lblCarrinho);
		
		JButton btnEscolher = new JButton("Escolher ->");
		btnEscolher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEscolher.setBounds(344, 221, 121, 23);
		frame.getContentPane().add(btnEscolher);
		
		JButton btnNewButton = new JButton("Comprar");
		btnNewButton.setBounds(748, 188, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnReservar = new JButton("Reservar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnReservar.setBounds(748, 222, 89, 23);
		frame.getContentPane().add(btnReservar);
		
		JButton btnDesistir = new JButton("Desistir");
		btnDesistir.setBounds(748, 256, 89, 23);
		frame.getContentPane().add(btnDesistir);
		ArrayList<String> data = new ArrayList<String>();
		data.add("1");
		data.add("2");
		data.add("3");
		
//		System.out.println("Datas: " +datas);
	//	JList<String> myList = new JList<String>((String[]) data);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(144, 94, 174, 307);
		//scrollPane.setViewportView(datas);
		frame.getContentPane().add(scrollPane);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(484, 94, 174, 307);
		frame.getContentPane().add(scrollPane_1);
		
		JButton btnRecarregar = new JButton("Recarregar ");
		btnRecarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				scrollPane.setViewportView();
			}
		});
		btnRecarregar.setBounds(748, 290, 89, 23);
		frame.getContentPane().add(btnRecarregar);
		
	}
//	public String[] trechosToArray(ArrayList<Trecho> trechos) {
//		
//	}
}
