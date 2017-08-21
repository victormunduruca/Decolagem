package model;

import java.io.Serializable;

public class Trecho implements Serializable{

	private static final long serialVersionUID = 1L;
	private String inicio;
	private String fim;
	
	private String nomeCompanhia = "";
	
	private boolean comprado = false;
	private String nomeComprador = "";
	
	public Trecho(String companhia, String inicio, String fim) {
		this.nomeCompanhia = companhia;
		this.inicio = inicio;
		this.fim = fim;
	}
	
	public Trecho(String inicio, String fim) {
		this.inicio = inicio;
		this.fim = fim;
		this.toString();
	}
	
	public String geCompanhia() {
		return nomeCompanhia;
	}

	public void setCompanhia(String nomeCompanhia) {
		this.nomeCompanhia = nomeCompanhia;
	}

	public boolean isComprado() {
		return comprado;
	}
	public void setComprado(boolean comprado) {
		this.comprado = comprado;
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
