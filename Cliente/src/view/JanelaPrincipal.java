package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Trecho;
import model.Trecho.Status;

//import org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel;

public class JanelaPrincipal extends JFrame { 

	private static final long serialVersionUID = 1L;

	public interface Listener {
		void onCompra(String usuario, List<Trecho> passagens);
		void onReserva(String usuario, List<Trecho> trechos);
		void onRecarregar(); 
	}

	private JTable tabelaPassagens, tabelaCarrinho;

	private Vector<String> colunas = new Vector<String>();
    private Vector<Vector> passagens = new Vector<Vector>();
    private Vector<Vector> carrinho = new Vector<Vector>();

	private DefaultTableModel modelPassagens, modelCarrinho;
	private TextField nomeUsuario;
	private JButton btnRecarregar, btnComprar, btnReservar;
	
	private Listener listener;

	public JanelaPrincipal(Listener listener) {
		super("Decolar @ Passagens Aéreas");
		this.listener = listener;
		iniciar();
	}

	private void iniciar() {
		
		carregar();
		
		//setLookAndFeel();

		JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));

		getContentPane().add(toolbar, BorderLayout.NORTH);
		
		nomeUsuario = new TextField("User" + new Random().nextInt(100));
		nomeUsuario.setColumns(15);
		toolbar.add(nomeUsuario);
		
		btnComprar = new JButton("Comprar");
		toolbar.add(btnComprar);
		
		btnReservar = new JButton("Reservar");
		toolbar.add(btnReservar);
		
		btnRecarregar = new JButton("Atualizar");
		toolbar.add(btnRecarregar);

		JPanel painel = new JPanel();
		painel.setLayout(new GridLayout(1, 2));

		modelPassagens = new DefaultTableModel(passagens, colunas) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		modelCarrinho = new DefaultTableModel(carrinho, colunas) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
		
		setListeners();
	}
	public void atualizaTrechos(List<Trecho> trechos) {
		modelCarrinho.getDataVector().removeAllElements();
		modelPassagens.getDataVector().removeAllElements();
		
		String status = "";
		for (Trecho trecho: trechos) {
			System.out.println("Lista de espera de " +trecho.getCompanhia()+trecho.getInicio()+ " tamanho:" + trecho.getListaEspera().size() + " " + trecho.getStatus());
//			if (trecho.getStatus().equals(Status.COMPRADO)) {
//				status = (trecho.getNomeComprador() != null && trecho.getNomeComprador().equals(nomeUsuario.getText())) 
//						? Status.COMPRADO : Status.INDISPONIVEL;	
//			} else {
//				System.out.println("Lista de espera de " +trecho.getCompanhia()+trecho.getInicio()+ " tamanho>>>:" + trecho.getListaEspera().size());
//				if (trecho.getPosicao(nomeUsuario.getText()) != -1) {
//					System.err.println("Entrou em setar nome");
//					status = Status.ESPERA + "(" + trecho.getPosicao(nomeUsuario.getText()) + ")";
//				} else {
//					status = trecho.getStatus();
//				}
//			}
			if (trecho.getStatus().equals(Status.COMPRADO)) {
				System.out.println("Entrou no if");
				//System.out.println("Lista de espera de " +trecho.getCompanhia()+trecho.getInicio()+ " tamanho>>>:" + trecho.getListaEspera().size());
				if(trecho.getNomeComprador() != null && trecho.getNomeComprador().equals(nomeUsuario.getText())) { 
					status = Status.COMPRADO;
				} else if (trecho.getPosicao(nomeUsuario.getText()) != -1) {
					System.err.println("Entrou em setar nome");
					status = Status.ESPERA + "(" + trecho.getPosicao(nomeUsuario.getText()) + ")";
				}
			} else { 
				status = trecho.getStatus();
			}
			passagens.add(toRow(trecho.getCompanhia(), 
					trecho.getInicio(), trecho.getFim(), status));
		}
		modelCarrinho.fireTableDataChanged();
		modelPassagens.fireTableDataChanged();
	}
	
	public void atualizaCarrinho(List<Trecho> trechos) {
		modelCarrinho.getDataVector().removeAllElements();
		//System.out.println("Vai atualizar o trecho da companhia: " +companhia.getNomeCompanhia());
		
		String status;
		for (Trecho trecho: trechos) {
			if (trecho.getStatus().equals(Status.COMPRADO)) {
				status = (trecho.getNomeComprador() != null && trecho.getNomeComprador().equals(nomeUsuario.getText())) 
						? Status.COMPRADO : Status.INDISPONIVEL;	
			} else {
				status = trecho.getStatus();
			}
			carrinho.add(toRow(trecho.getCompanhia(), 
					trecho.getInicio(), trecho.getFim(), status));
		}
		modelCarrinho.fireTableDataChanged();
	}

	private void setListeners() {
		btnRecarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listener.onRecarregar();
			}
		});
		
		btnComprar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Trecho> trechos = new ArrayList<Trecho>();
				int total = tabelaCarrinho.getModel().getRowCount();
				for (int i = 0; i < total; i++) {
					trechos.add(new Trecho(
						"" + tabelaCarrinho.getModel().getValueAt(i, 0),
						"" + tabelaCarrinho.getModel().getValueAt(i, 1),
						"" + tabelaCarrinho.getModel().getValueAt(i, 2)
					));
				}
				listener.onCompra(nomeUsuario.getText().toString(), trechos);
			}
		});
		
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Trecho> trechos = new ArrayList<Trecho>();
				int total = tabelaCarrinho.getModel().getRowCount();
				for (int i = 0; i < total; i++) {
					trechos.add(new Trecho(
						"" + tabelaCarrinho.getModel().getValueAt(i, 0),
						"" + tabelaCarrinho.getModel().getValueAt(i, 1),
						"" + tabelaCarrinho.getModel().getValueAt(i, 2)
					));
				}
				listener.onReserva(nomeUsuario.getText().toString(), trechos);
			}
		});
		
		tabelaPassagens.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = tabelaPassagens.getSelectedRow();
					
					// if (!tabelaPassagens.getModel().getValueAt(index, 3).toString().equals(Status.DISPONIVEL)) return;
					
					carrinho.add(toRow(
						"" + tabelaPassagens.getModel().getValueAt(index, 0),
						"" + tabelaPassagens.getModel().getValueAt(index, 1),
						"" + tabelaPassagens.getModel().getValueAt(index, 2),
						"" + tabelaPassagens.getModel().getValueAt(index, 3)
						)
					);
					modelPassagens.removeRow(index);
					
					// Notificar a mudanca da tabela
					modelPassagens.fireTableDataChanged();
					modelCarrinho.fireTableDataChanged(); 
				}
			}
		});
		
		tabelaCarrinho.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int index = tabelaCarrinho.getSelectedRow();
					
					passagens.add(toRow(
						"" + tabelaCarrinho.getModel().getValueAt(index, 0),
						"" + tabelaCarrinho.getModel().getValueAt(index, 1),
						"" + tabelaCarrinho.getModel().getValueAt(index, 2),
						"" + tabelaPassagens.getModel().getValueAt(index, 3)
						)
					);
					modelCarrinho.removeRow(index);
					
					// Notificar a mudanca da tabela
					modelPassagens.fireTableDataChanged();
					modelCarrinho.fireTableDataChanged(); 
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
	
	private Vector<String> toRow(String c, String o, String d, String s) {
	    Vector<String> v = new Vector<String>();
	    v.addElement(c);
	    v.addElement(o);
	    v.addElement(d);
	    v.addElement(s);
	    return v;
	}
	
	public void carregar() {
		// Header
	    colunas.addElement("Companhia");
	    colunas.addElement("Origem");
	    colunas.addElement("Destino");
	    colunas.addElement("Status");
	}
}
