package logicaDeNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Baraja implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final int MAX_CARTAS = 52;
	
	private List<Carta> cartas;
	
	public Baraja () {
		cartas = new ArrayList<Carta>();
		
		for (int i = 0; i < 13; i++) {
			cartas.add(new Carta(i + 1, "Picas"));
			cartas.add(new Carta(i + 1, "Corazones"));
			cartas.add(new Carta(i + 1, "Rombos"));
			cartas.add(new Carta(i + 1, "Treboles"));
		}
	}
	
	public String toString() {
		String s = "";
		
		for (Carta c: cartas) {
			s += c.toString() + "\r\n";
		}
		
		return s;
	}
	
	public Carta robarCarta () {
		Carta carta = cartas.get(0);
		cartas.remove(0);
		return carta;
	}
	
	public void barajar () {
		Random r = new Random();
		
		
		for (int i = 300; i >= 0; i--) {
			int pos1 = r.nextInt(MAX_CARTAS);
			int pos2 = r.nextInt(MAX_CARTAS);

			Collections.swap(cartas, pos1, pos2);
		}
	}
	
	public static void main(String[] args) {
		Baraja b = new Baraja();
		b.barajar();
		System.out.println(b.toString());
		
		b.robarCarta();
		System.out.println();
		System.out.println(b.toString());
	}

}
