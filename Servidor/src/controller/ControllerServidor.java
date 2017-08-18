package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.Companhia;
import model.Trecho;

public class ControllerServidor {
private static Companhia companhia;
	
	
	public ControllerServidor() {
		companhia = new Companhia();
	}
	public String leituraConfiguracao() throws IOException {
		File file = new File("configuracao.txt");
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
		setarTrechos(trechos);
	}
	public void setarTrechos(String[] trechos) {
		for(int i = 0; i < trechos.length; i++) {
			String[] infoTrechos = trechos[i].split("-");
			companhia.addNovoTrecho(infoTrechos[0], infoTrechos[1], Integer.valueOf(infoTrechos[2]));
		}
	}
	public static void main(String[] args) throws IOException {
		ControllerServidor c = new ControllerServidor();
		c.configuraServidor();
		for(Trecho t : companhia.getTrechos()) {
			System.out.println("" +t);
		}
	}
	public static Companhia getCompanhia() {
		return companhia;
	}
}
