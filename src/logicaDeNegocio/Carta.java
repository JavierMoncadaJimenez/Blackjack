package logicaDeNegocio;

import java.io.Serializable;

public class Carta implements Serializable {
	private int valor;
	private String palo;
	
	public Carta (int valor, String palo) {
		this.valor = valor;
		this.palo = palo;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	public String getPalo() {
		return palo;
	}
	
	public void setPalo(String palo) {
		this.palo = palo;
	}
	
	@Override
	public String toString() {
		return transformNumber(valor) + " de " + palo;
	}
	
	private String transformNumber(int n) {
		switch (n) {
		case 11:
			return "Jota";
		case 12:
			return "Reina";
		case 13:
			return "Rey";
		case 1:
			return "As";
		default:
			return n + "";
		}

	}
	
	
}
