package logicaDeNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import servidor.Juego;

public class Mesa implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<Carta> crupier;
	private List<List<Carta>> listaCartasJugadores;
	private List<String> nomJugadores;
	
	public Mesa() {
		crupier = new ArrayList<Carta> ();
		listaCartasJugadores = new ArrayList<List<Carta>>();
		nomJugadores = new ArrayList<String>();
	}
	
	public void unirJugador (String nomJugador) {
		listaCartasJugadores.add(new ArrayList<Carta>());
		this.nomJugadores.add(nomJugador);
	}
	
	public void darCartaJugador(int numJugador, Carta carta) { 
		listaCartasJugadores.get(numJugador).add(carta);
	}
	
	public int puntosCrupier () {
		int total = 0;
		
		for (Carta carta : crupier) {
			total += carta.getValor();
		}
		
		return total;
	}
	
	public int getPuntosJugador (int numJugador) {
		if (listaCartasJugadores.size() == 0) {
			return 0;
		}
		
		int total = 0;
		List<Carta> cartasJugador = listaCartasJugadores.get(numJugador);
		
		for (Carta carta : cartasJugador) {
			total += carta.getValor();
		}
		
		return total;
	}
	
	public void eliminarJugador (int numJugador)  {
		listaCartasJugadores.remove(numJugador);
		nomJugadores.remove(numJugador);
	}
	
	public void reinicairMesa () {
		crupier.clear();
		for (List<Carta> cartas : listaCartasJugadores) {
			cartas.clear();
		}
	}

	public void darCartaCrupier (Carta carta) {
		crupier.add(carta);
	}
	
	public String toString () {
		String estado = "El estado de la mesa es:\r\nCrupier: ";
		
		for (Carta cartaCrupie : crupier) {
			estado += cartaCrupie.toString() + " ";
		}
		
		estado += "\r\nJugadores: ";
		int numJugador = 0;
		
		for (List<Carta> jugador : listaCartasJugadores) {
			estado += "\r\n\t " + nomJugadores.get(numJugador) + ": \r\n";
			
			for (Carta cartaJugador : jugador) {
				estado += "\t\t" + cartaJugador.toString() + "\r\n";
			}
			
			numJugador++;
		}
		
		return estado;
	}
}
