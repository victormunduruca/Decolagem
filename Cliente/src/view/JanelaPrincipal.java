package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Companhia;
import model.Trecho;

//import org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel;

public class JanelaPrincipal extends JFrame { 

	public interface Listener { // TODO
		void onCompra();
		void onReserva();
		void onRecarregar();
	}

	private JTable tabelaPassagens, tabelaCarrinho;

	private Vector<String> colunas = new Vector<String>();
    private Vector<Vector> passagens = new Vector<Vector>();
    private Vector<Vector> carrinho = new Vector<Vector>();

	private DefaultTableModel modelPassagens, modelCarrinho;
	
	private Listener listener;

	public JanelaPrincipal(Listener listener) {
		super("Decolar @ Passagens Aéreas");
		this.listener = listener;
		iniciar();
	}
	//XXX
	JLabel lblNewLabel;
	private void iniciar() {
		
		carregar();
		
//		setLookAndFeel();

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		toolbar.add(new JButton("Comprar"));
		toolbar.add(new JButton("Reservar"));

		getContentPane().add(toolbar, BorderLayout.NORTH);
		
		JButton btnRecarregar = new JButton("Recarregar");
		btnRecarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.onRecarregar();
			}
		});
		toolbar.add(btnRecarregar);
		
		

		JPanel painel = new JPanel();
		painel.setLayout(new GridLayout(1, 2));

		modelPassagens = new DefaultTableModel(passagens, colunas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelCarrinho = new DefaultTableModel(carrinho, colunas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tabelaPassagens = new JTable(modelPassagens);
		//tabelaPassagens.setRowSelectionAllowed(true);
		//tabelaPassagens.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		//tabelaPassagens.setRowSelectionAllowed(true);

		tabelaCarrinho = new JTable(modelCarrinho);

		JScrollPane scrollPassagens = new JScrollPane(tabelaPassagens);
		scrollPassagens.setBorder(BorderFactory.createTitledBorder("Passagens"));
		painel.add(scrollPassagens);

		JScrollPane scrollCarrinho = new JScrollPane(tabelaCarrinho);
		scrollCarrinho.setBorder(BorderFactory.createTitledBorder("Carrinho de compras"));
		painel.add(scrollCarrinho);

		getContentPane().add(painel, BorderLayout.CENTER);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(550, 550);
		setVisible(true);
		
		lblNewLabel = new JLabel("New label");
		toolbar.add(lblNewLabel);
		
		setListeners();
	}
	public void atualizaTrechos(Companhia companhia) {
		//TODO apagar conte�do pr�vio das tabelas
		System.out.println("Vai atualizar o trecho da companhia: " +companhia.getNomeCompanhia());
		for(Trecho trecho: companhia.getTrechos()) {
			passagens.add(toRow(companhia.getNomeCompanhia(), trecho.getInicio(), trecho.getFim()));
		}
		modelPassagens.fireTableDataChanged();
	}
	//XXX
	public void setTexto(String texto) {
		System.out.println("Setou o texto: " +texto);
		lblNewLabel.setText(texto);;
	}
	private void setListeners() {
		tabelaPassagens.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = tabelaPassagens.getSelectedRow();
					
					System.out.print("Companhia: " + tabelaPassagens.getModel().getValueAt(index, 0));
					System.out.print("|Orig: " + tabelaPassagens.getModel().getValueAt(index, 1));
					System.out.println("|Dest: " + tabelaPassagens.getModel().getValueAt(index, 2));
					
					carrinho.add(toRow(
						"" + tabelaPassagens.getModel().getValueAt(index, 0),
						"" + tabelaPassagens.getModel().getValueAt(index, 1),
						"" + tabelaPassagens.getModel().getValueAt(index, 2)
						)
					);
					modelCarrinho.fireTableDataChanged(); // Notificar a mudanca da tabela 
				}
			}
		});
	}

//	// Fixar bug: https://github.com/Insubstantial/insubstantial/issues/56#issuecomment-7175469
//	private void setLookAndFeel() {
//        try {
//
//            JFrame.setDefaultLookAndFeelDecorated(true);
//            UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel());
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
//
//        try {
//            JFrame.setDefaultLookAndFeelDecorated(true);
//            UIManager.setLookAndFeel(new SubstanceNebulaBrickWallLookAndFeel());
//        } catch (Exception e) {
//            e.getStackTrace();
//        }
// }
	
	private Vector<String> toRow(String c, String o, String d) {
		System.out.println(c+o+d);
	    Vector<String> v = new Vector<String>();
	    v.addElement(c);
	    v.addElement(o);
	    v.addElement(d);
	    return v;
	}
	
	public void carregar() {
		// Header
	    colunas.addElement("Companhia");
	    colunas.addElement("Origem");
	    colunas.addElement("Destino");
		
//		// Passagens
;
//	    
//	    Vector<String> p2 = new Vector<String>();
//	    p2.addElement("Rosa");
//	    p2.addElement("São Paulo");
//	    p2.addElement("Acre");
//	    passagens.add(p2);
//	    
//	    Vector<String> p3 = new Vector<String>();
//	    p3.addElement("Verde");
//	    p3.addElement("Ceará");
//	    p3.addElement("Espírito Santo");
//	    passagens.add(p3);
//	    
//	    Vector<String> p4 = new Vector<String>();
//	    p4.addElement("Amarelo");
//	    p4.addElement("Ceará");
//	    p4.addElement("Brasília Santo");
//	    passagens.add(p4);
	}

	
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				JanelaPrincipal janela = new JanelaPrincipal(new Listener() {
//					@Override
//					public void onReserva() { }
//					
//					@Override
//					public void onCompra() { }
//				});
//			}
//		});
//	}
}
