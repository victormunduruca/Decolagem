package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Companhia;
import model.Trecho;

public class Controller {
	
	public interface RegiaoCritica {
		int RELEASED = 0; // A secao critica esta desocupada
		int WANTED = 1;	  // Deseja-se entrar na secao critica
		int HELD = 2;	  // Entrou na secao critica
	}
	
	private static Controller self = new Controller();
	
	private static Companhia companhia = new Companhia();
	
	private int id;
	private int statusRegiaCritica = RegiaoCritica.RELEASED;
	
	private int clockLamport = 0;
	
	private int contadorDeRequisicoesEmFila = 0;

	private int totalServidores = 3;

	private Controller() { }
	
	public synchronized List<Trecho> comprar(String usuario, List<Trecho> trechos) {
		List<Trecho> indisponiveis = new ArrayList<Trecho>();
		Trecho trecho;
		for (Trecho t : trechos) {
			trecho = companhia.getTrechos().get(t.getInicio() + t.getFim());
			try {
				if (trecho != null && getId() == Integer.valueOf(t.getCompanhia())) {
					if (trecho.getStatus().equals(Trecho.Status.DISPONIVEL)) {
						trecho.setStatus(Trecho.Status.COMPRADO);
						trecho.setNomeComprador(usuario);
					} else {
						indisponiveis.add(trecho);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return indisponiveis;
	}
	
	public synchronized void reservar(String usuario, List<Trecho> trechos) {
		Trecho trecho;
		for (Trecho t : trechos) {
			trecho = companhia.getTrechos().get(t.getInicio() + t.getFim());
			try {
				if (trecho != null && getId() == Integer.valueOf(t.getCompanhia())) {
					if (!trecho.getStatus().equals(Trecho.Status.DISPONIVEL)) {
						trecho.addListaEspera(usuario);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized int getContadorDeRequisicoesEmFila() {
		return contadorDeRequisicoesEmFila;
	}

	public synchronized void incremContadorDeRequisicoesEmFila() {
		this.contadorDeRequisicoesEmFila++;
	}
	
	public synchronized void apagaContadorDeRequisicoesEmFila() {
		this.contadorDeRequisicoesEmFila = 0;
	}

	public synchronized int getTotalServidores() {
		return totalServidores;
	}

	public synchronized void setTotalServidores(int totalServidores) {
		this.totalServidores = totalServidores;
	}
	
	public synchronized int getClockLamport() {
		return clockLamport;
	}

	public synchronized void atualizaClockLamport(int clockDeRequisicao) {
		this.clockLamport = Math.max(this.clockLamport, this.clockLamport) + 1;
	}
	
	public synchronized void incrementaClockLamport() {
		this.clockLamport++;
	}
	
	public synchronized static Controller getInstance() {
		return self;
	}
	
	public synchronized int getStatusRegiaCritica() {
		return statusRegiaCritica;
	}

	public synchronized void setStatusRegiaCritica(int statusRegiaCritica) {
		this.statusRegiaCritica = statusRegiaCritica;
	}
	
	public synchronized Companhia getCompanhia() {
		return companhia;
	}
	
	public synchronized int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	private String leituraConfiguracao() throws IOException {
		File file = new File("configuracao"+id+".txt");
		FileReader fileReader = new FileReader(file);
		BufferedReader leitura = new BufferedReader(fileReader);
		StringBuilder texto = new StringBuilder();
		while(leitura.ready()) {
			texto.append(leitura.readLine());
		}
		leitura.close();
		fileReader.close();
		System.out.println("EU LI ISSO: " +texto.toString());
		return texto.toString();
	}
	
	public void configuraServidor() throws IOException {
		String textoLido = leituraConfiguracao();
		String[] partes = textoLido.split("#");
		companhia.setNomeCompanhia(partes[0]);
		String[] ips = partes[1].split(";"); // Setar ips
		String[] trechos = partes[2].split(";");
		setarTrechos(partes[0], trechos);
	}
	
	private void setarTrechos(String cia, String[] trechos) {
		for(int i = 0; i < trechos.length; i++) {
			String[] infoTrechos = trechos[i].split("-");
			companhia.addNovoTrecho(cia, infoTrechos[0], infoTrechos[1]);
		}
	}
}
