package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Companhia implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nomeCompanhia;
	private HashMap<String, Trecho> trechos = new HashMap<>();
	
	public Companhia() { }
	
	public String getNomeCompanhia() {
		return nomeCompanhia;
	}
	
	public void setNomeCompanhia(String nomeCompanhia) {
		this.nomeCompanhia = nomeCompanhia;
	}
	
	public HashMap<String, Trecho> getTrechos() {
		return trechos;
	}
	
	public void setTrechos(HashMap<String, Trecho> trechos) {
		this.trechos = trechos;
	}
	
	public Trecho buscarTrecho(String origem, String destino) {
		return this.trechos.get(origem + destino);
	}
	
	public void addNovoTrecho(String cia, String inicio, String fim) {
		trechos.put(inicio + fim, new Trecho(cia, inicio, fim));
	}
	
}
