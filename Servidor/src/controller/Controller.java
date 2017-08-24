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
	
	private static Controller self = new Controller(); // Instancia do controller
	
	private static Companhia companhia = new Companhia(); // Instancia representando a companhia
	
	private int id; // id do Servidor
	private int statusRegiaCritica = RegiaoCritica.RELEASED; // Status da regiao critica 
	
	private int clockLamport = 0; // Relogio de Lamport, iniciando em 0
	
	private int contadorDeRequisicoesEmFila = 0; // Requisicoes em fila, utilizado como controle no algoritmo de exclusao mutua

	private int totalServidores = 3; // Indica o número total de servidores 

	private Controller() { }
	/**
	 * Atualiza o model para atribuir passagens a um determinado usuario
	 * @param usuario
	 * @param trechos
	 * @return
	 */
	public synchronized List<Trecho> comprar(String usuario, List<Trecho> trechos) {
		List<Trecho> indisponiveis = new ArrayList<Trecho>(); //Cria uma lista para armazenar os trechos indisponiveis 
		Trecho trecho;
		for (Trecho t : trechos) { //Percorre os trechos escolhidos pelo usuario
			trecho = companhia.getTrechos().get(t.getInicio() + t.getFim()); //O recupera da lista de trechos do servidor
			try {
				if (trecho != null && getId() == Integer.valueOf(t.getCompanhia())) { //Se for um trecho valido e que seja da companhia deste servidor
					if (trecho.getStatus().equals(Trecho.Status.DISPONIVEL)) { //Se for disponivel muda para comprador
						trecho.setStatus(Trecho.Status.COMPRADO);
						trecho.setNomeComprador(usuario); //atribui trecho a comprador
					} else {
						indisponiveis.add(trecho); //caso nao esteja disponivel pra compra, adiciona em uma lista de trechos indisponiveis 
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return indisponiveis; //retorna os trechos indisponiveis, caso existam
	}
	/**
	 * Método que realiza açoes de reserva na companhia
	 * @param usuario
	 * @param trechos
	 */
	public synchronized void reservar(String usuario, List<Trecho> trechos) {
		Trecho trecho;
		for (Trecho t : trechos) {
			trecho = companhia.getTrechos().get(t.getInicio() + t.getFim()); //Recupera o trecho da lista de trechos do servidor
			try {
				if (trecho != null && getId() == Integer.valueOf(t.getCompanhia())) {  //Se for um trecho valido e desse servidor
					if (!trecho.getStatus().equals(Trecho.Status.DISPONIVEL)) { //Se nao estiver disponivel, sera possivel realizar a reserva
						trecho.addListaEspera(usuario); //Adiciona o usuario na lista de espera de trecho
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Metodo que retorna o contador de requisicoes em fila
	 * @return
	 */
	public synchronized int getContadorDeRequisicoesEmFila() {
		return contadorDeRequisicoesEmFila;
	}
	/**
	 * Metodo que incrmenta o contador de requisicoes em fila
	 */
	public synchronized void incremContadorDeRequisicoesEmFila() {
		this.contadorDeRequisicoesEmFila++;
	}
	/**
	 * Metodo que zera o contador de requisicoes em fila
	 */
	public synchronized void apagaContadorDeRequisicoesEmFila() {
		this.contadorDeRequisicoesEmFila = 0;
	}
	/**
	 * Metodo que retorna o total de servidores
	 * @return
	 */
	public synchronized int getTotalServidores() {
		return totalServidores;
	}
	/**
	 * Metodo que atribui um valor para o total de servidores 
	 * @param totalServidores
	 */
	public synchronized void setTotalServidores(int totalServidores) {
		this.totalServidores = totalServidores;
	}
	/**
	 * Retorna o relogio de lamport
	 * @return
	 */
	public synchronized int getClockLamport() {
		return clockLamport;
	}
	/**
	 * Atualiza o relogio de lamport
	 * @param clockDeRequisicao
	 */
	public synchronized void atualizaClockLamport(int clockDeRequisicao) {
		this.clockLamport = Math.max(this.clockLamport, this.clockLamport) + 1;
	}
	
	/**
	 * Meotodo que incrementa o relogio de Lamport
	 */
	public synchronized void incrementaClockLamport() {
		this.clockLamport++;
	}
	/**
	 * Meotod que retorna a instancia unica do controller
	 * @return
	 */
	public synchronized static Controller getInstance() {
		return self;
	}
	/**
	 * Retorna o status da regiao critica 
	 * @return
	 */
	public synchronized int getStatusRegiaCritica() {
		return statusRegiaCritica;
	}
	/**
	 * Metodo que atribui um valor para o status da regiao critica
	 * @param statusRegiaCritica
	 */
	public synchronized void setStatusRegiaCritica(int statusRegiaCritica) {
		this.statusRegiaCritica = statusRegiaCritica;
	}
	/**
	 * Metodo que retorna a companhia
	 * @return
	 */
	public synchronized Companhia getCompanhia() {
		return companhia;
	}
	/**
	 * Metorna o id do servidor
	 * @return
	 */
	public synchronized int getId() {
		return id;
	}
	/**
	 * Atribui um valor para o id do servidor
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Metodo que le as configuracoes do servidor, presente em um arquivo  de texto
	 * @return
	 * @throws IOException
	 */
	private String leituraConfiguracao() throws IOException {
		File file = new File("configuracao"+id+".txt"); //Existem 3 arquivos de texto: configuracao1.txt, configuracao2.txt, configuracao3.txt
		FileReader fileReader = new FileReader(file); //Realiza a leitura do arquivo
		BufferedReader leitura = new BufferedReader(fileReader);
		StringBuilder texto = new StringBuilder();
		while(leitura.ready()) {
			texto.append(leitura.readLine());
		}
		leitura.close();
		fileReader.close();
		return texto.toString(); //Retorna a string codificada
	}
	/**
	 * Metodo que recupera as informçoes do arquivo de texto no formato IddaCompanhia#127.0.0.1;127.0.0.1#Trecho-Trecho;Trecho-Trecho;Trecho-Trecho;Trecho-Trecho;Trecho-Trecho
	 * @throws IOException
	 */
	public void configuraServidor() throws IOException {
		String textoLido = leituraConfiguracao(); //Recupera as informacoes do arquivo
		String[] partes = textoLido.split("#"); // Divide as informacoes princpais
		companhia.setNomeCompanhia(partes[0]); //Seta o nome da companhia
		String[] ips = partes[1].split(";"); // Setar ips
		String[] trechos = partes[2].split(";");//Divide os trechos
		setarTrechos(partes[0], trechos); //Seta os trechos
	}
	/**
	 * Configura os trechos, com base no arquivo de texto
	 * @param cia
	 * @param trechos
	 */
	private void setarTrechos(String cia, String[] trechos) {
		for(int i = 0; i < trechos.length; i++) { 
			String[] infoTrechos = trechos[i].split("-");
			companhia.addNovoTrecho(cia, infoTrechos[0], infoTrechos[1]);
		}
	}
}