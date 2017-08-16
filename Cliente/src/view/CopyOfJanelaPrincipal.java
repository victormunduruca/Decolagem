package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTable;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CopyOfJanelaPrincipal {

	private JFrame frame;
	
	private Object columnNames[] = { "Companhia", "Origem", "Destino"};

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
		
		//String[] data = {""+10,""+20,""+30,""+40,""+50,""+60,""+71,""+80,""+90,""+91};
		// JList<String> myList = new JList<String>(data);
		
		//JScrollPane scrollPane = new JScrollPane();
		
		Object rowData[][] = { 
				{ "Row1-Column1", "Row1-Column2", "Row1-Column3"},
                { "Row2-Column1", "Row2-Column2", "Row2-Column3"} 
		};
		
		TableModel model = new DefaultTableModel(rowData, columnNames);
		JTable table = new JTable(model);
		//scrollPane.add(new JButton("TEXT"));
		
		table.setBounds(144, 94, 174, 307);
	//	scrollPane.setViewportView(myList);
		frame.getContentPane().add(table);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(484, 94, 174, 307);
		frame.getContentPane().add(scrollPane_1);
		
	}
}
