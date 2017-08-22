package model;

import java.io.Serializable;
import java.util.HashMap;

public class Trecho implements Serializable {
	
	public interface Status {
		String DISPONIVEL = "disponível";
		String COMPRADO = "comprado";
		String INDISPONIVEL = "indisponível";
		String ESPERA = "espera";
	}

	private static final long serialVersionUID = 1L;
	private String inicio;
	private String fim;
	
	private String nomeCompanhia = "";
	
	private String status = Status.DISPONIVEL;
	private String nomeComprador = "";
	
	private HashMap<String, Integer> listaEspera = new HashMap<String, Integer>();
	
	public Trecho(String companhia, String inicio, String fim) {
		this.nomeCompanhia = companhia;
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public void addListaEspera(String usuario) {
		listaEspera.put(usuario, listaEspera.size() + 1); // Posicao
	}
	
	public int getPosicao(String usuario) {
		return listaEspera.get(usuario) != null ? listaEspera.get(usuario) : -1;
	}
	public int getTamanhoListaEspera() {
		return listaEspera.size();
	}
	public HashMap<String, Integer> getListaEspera() {
		return listaEspera;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCompanhia() {
		return nomeCompanhia;
	}

	public void setCompanhia(String nomeCompanhia) {
		this.nomeCompanhia = nomeCompanhia;
	}

	public String getNomeComprador() {
		return nomeComprador;
	}
	public void setNomeComprador(String nomeComprador) {
		this.nomeComprador = nomeComprador;
	}
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFim() {
		return fim;
	}
	public void setFim(String fim) {
		this.fim = fim;
	}
	
	@Override
	public String toString() {
		return inicio +"-"+fim;
	}
}
