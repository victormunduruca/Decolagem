package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Companhia implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeCompanhia;
	private ArrayList<Trecho> trechos;
	
	public Companhia() {
		trechos = new ArrayList<Trecho>();
	}
	
	public String getNomeCompanhia() {
		return nomeCompanhia;
	}
	public void setNomeCompanhia(String nomeCompanhia) {
		this.nomeCompanhia = nomeCompanhia;
	}
	public ArrayList<Trecho> getTrechos() {
		return trechos;
	}
	public void setTrechos(ArrayList<Trecho> trechos) {
		this.trechos = trechos;
	}
	public void addNovoTrecho(String inicio, String fim) {
		trechos.add(new Trecho(inicio, fim));
	}
	
}
