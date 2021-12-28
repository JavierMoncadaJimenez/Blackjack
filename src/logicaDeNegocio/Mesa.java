package logicaDeNegocio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		int total = 0;
		List<Carta> cartasJugador = listaCartasJugadores.get(numJugador);
		
		for (Carta carta : cartasJugador) {
			total += carta.getValor();
		}
		
		return total;
	}
	
	public void eliminarJugador (int numJugador)  {
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(crupier, listaCartasJugadores);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mesa other = (Mesa) obj;
		return Objects.equals(crupier, other.crupier)
				&& Objects.equals(listaCartasJugadores, other.listaCartasJugadores);
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
		int numJugador = 1;
		
		for (List<Carta> jugador : listaCartasJugadores) {
			estado += "\r\n\tJugador " + numJugador + ": \r\n";
			
			for (Carta cartaJugador : jugador) {
				estado += "\t\t" + cartaJugador.toString() + "\r\n";
			}
			
			numJugador++;
		}
		
		return estado;
	}
}
