package logicaDeNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mesa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Carta> crupier;
	private List<List<Carta>> listaCartasJugadores;
	
	public Mesa() {
		crupier = new ArrayList<Carta> ();
		listaCartasJugadores = new ArrayList<List<Carta>>();
	}
	
	public void unirJugador () {
		listaCartasJugadores.add(new ArrayList<Carta>());
	}
	
	public void darCartaJugador(int numJugador, Carta carta) { 
		listaCartasJugadores.get(numJugador - 1).add(carta);
	}
	
	public void darCartaCrupier (Carta carta) {
		crupier.add(carta);
	}
	
	public String toString () {
		String estado = "Crupier: ";
		
		for (Carta cartaCrupie : crupier) {
			estado += cartaCrupie.toString() + " ";
		}
		
		estado += "\r\nJugadores: ";
		int numJugador = 1;
		
		for (List<Carta> jugador : listaCartasJugadores) {
			estado += ",Jugador " + numJugador + ": ";
			
			for (Carta cartaJugador : jugador) {
				estado += cartaJugador.toString() + " ";
			}
			
			numJugador++;
		}
		
		return estado;
	}
	
	public static void main(String[] args) {
		Baraja b = new Baraja();
		b.barajar();
		
		Mesa m = new Mesa();
		m.unirJugador();
		m.unirJugador();
		m.darCartaCrupier(b.robarCarta());
		m.darCartaJugador(1, b.robarCarta());
		m.darCartaJugador(1, b.robarCarta());
		m.darCartaJugador(2, b.robarCarta());
		
		System.out.println(m.toString());
	}
}
