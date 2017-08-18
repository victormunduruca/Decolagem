package model;

import java.io.Serializable;

public class Trecho implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String inicio;
	private String fim;
	private int lugares;
	
	public Trecho(String inicio, String fim, int lugares) {
		this.inicio = inicio;
		this.fim = fim;
		this.lugares = lugares;
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
	public int getLugares() {
		return lugares;
	}
	public void setLugares(int lugares) {
		this.lugares = lugares;
	}
	@Override
	public String toString() {
		return inicio +"-"+fim+" Lugares: " + lugares;
	}
}
