package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import logicaDeNegocio.Baraja;
import logicaDeNegocio.Carta;
import logicaDeNegocio.Jugador;
import logicaDeNegocio.Mesa;

public class Juego extends Thread {
	private static int MAX_JUGADORES = 2;

	private int idMesa;
	private List<Jugador> clientes;
	private Baraja baraja;
	private Mesa mesa;
	private int jugadoresPlantados;
	private int jugadorActual;

	public Juego(int idMesa) {
		baraja = new Baraja();
		jugadorActual = 0;
		clientes = new ArrayList<Jugador>();
		mesa = new Mesa();
		this.idMesa = idMesa;
		jugadoresPlantados = 0;
	}

	public void run() {
		reinicarJuego();

		while (clientes.size() != 0) {
			Jugador clienteActual = clientes.get(jugadorActual);

			if (!clienteActual.isPlantado()) {
				try {
					BufferedReader br = clienteActual.getEntradaCliente();
					ObjectOutputStream bw = clienteActual.getSalidaCliente();

					bw.writeObject("Es tu turno");
					bw.flush();

					bw.reset();
					bw.writeObject(mesa);
					bw.flush();

					String comando = br.readLine();

					procesarComando(clienteActual, comando);
				} catch (IOException | NullPointerException e) {
					clientes.remove(clientes.get(jugadorActual));
					e.printStackTrace();
				}
			}

			actualizarTurno();

			if (jugadoresPlantados == clientes.size()) {
				terminarRonda();
			}
		}

	}

	private void reinicarJuego() {
		baraja = new Baraja();
		baraja.barajar();
		mesa.reinicairMesa();
		mesa.darCartaCrupier(baraja.robarCarta());

		for (Jugador jugador : clientes) {
			jugador.setPlantado(false);
		}
		
		jugadoresPlantados = 0;
	}

	private void procesarComando(Jugador clienteActual, String comando) throws IOException {
		ObjectOutputStream bw = clienteActual.getSalidaCliente();
		if (comando.equals("cartas")) {
			darCarta(bw);
		}

		if (comando.equals("plantarse")) {
			plantarJugador();
		}

		if (comando.equals("salir")) {
			salirCliente(clienteActual);
		}
	}

	private void plantarJugador() {
		jugadoresPlantados++;
		clientes.get(jugadorActual).setPlantado(true);
	}

	private void salirCliente(Jugador clienteActual) throws IOException {
		clientes.remove(clienteActual);
		mesa.eliminarJugador(jugadorActual);
		ObjectOutputStream bw = clienteActual.getSalidaCliente();
		bw.writeObject("Has salido correctamente");
		bw.flush();
	}

	private void darCarta(ObjectOutputStream bw) throws IOException {
		Carta carta = baraja.robarCarta();
		mesa.darCartaJugador(jugadorActual, carta);
		bw.writeObject(carta);
		bw.flush();
		int puntosJugador = mesa.getPuntosJugador(jugadorActual);

		bw.writeObject(String.valueOf(puntosJugador));
		bw.flush();

		if (puntosJugador > 21) {
			plantarJugador();
		}

		comprobarBaraja();
	}

	private void comprobarBaraja() {
		if (baraja.estaVacia()) {
			baraja = new Baraja();
			baraja.barajar();
		}
	}

	private void terminarRonda() {
		boolean parar = false;
		while (!parar) {
			mesa.darCartaCrupier(baraja.robarCarta());
			comprobarBaraja();

			if (mesa.puntosCrupier() > 16) {
				parar = true;
			}
		}

		comporbarPuntos();

		reinicarJuego();

	}

	private void comporbarPuntos() {
		int puntosCrupier = mesa.puntosCrupier();

		for (int i = 0; i < clientes.size(); i++) {
			int puntosJugador = mesa.getPuntosJugador(i);
			try {
				ObjectOutputStream salida = clientes.get(i).getSalidaCliente();
				salida.writeObject("La ronda ha terminado");
				salida.reset();
				salida.writeObject(mesa);
				if ((puntosCrupier > puntosJugador || puntosJugador > 21) && puntosCrupier <= 21) {
					salida.writeObject("Has perdido");
				} else {
					salida.writeObject("Has ganado");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void actualizarTurno() {
		jugadorActual++;

		if (jugadorActual >= clientes.size()) {
			jugadorActual = 0;
		}

	}

	public void unirJugador(Jugador jugador) {
		clientes.add(jugador);
		mesa.unirJugador();
	}

	public boolean mesaLlena() {
		return clientes.size() == MAX_JUGADORES;
	}

	public boolean mesaVacia() {
		return clientes.size() == 0;
	}

	public String toString() {
		return "Mesa " + idMesa + " con " + clientes.size() + " jugadores unidos";
	}

}
