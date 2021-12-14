package servidor;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import logicaDeNegocio.Baraja;
import logicaDeNegocio.Mesa;

public class Juego extends Thread {
	private static int MAX_JUGADORES = 2;
	
	private int idMesa;
	private List<Socket> clientes;
	private Baraja baraja;
	private Mesa mesa;
	
	public Juego(int idMesa) {
		baraja = new Baraja();
		baraja.barajar();
		clientes = new ArrayList<Socket>();
		mesa = new Mesa();
		this.idMesa = idMesa;
	}
	
	public void run() {
		
		while (clientes.size() != 0) {
			
		}
		
	}
	
	public void unirJugador (Socket cliente ) {
		clientes.add(cliente);
	}
	
	public String toString () {
		return "Mesa " + idMesa + " con " + clientes.size() + " jugadores unidos";
	}
	
	
}
