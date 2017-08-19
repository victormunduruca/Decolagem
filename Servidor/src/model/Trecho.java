package model;

import java.io.Serializable;

public class Trecho implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String inicio;
	private String fim;

	
	public Trecho(String inicio, String fim) {
		this.inicio = inicio;
		this.fim = fim;
		this.toString();
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
		return inicio +"->"+fim;
	}
}
