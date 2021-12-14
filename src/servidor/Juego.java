package servidor;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import logicaDeNegocio.Baraja;
import logicaDeNegocio.Mesa;

public class Juego extends Thread {
	private static int MAX_JUGADORES = 2;
	private List<Socket> clientes;
	private Baraja baraja;
	private Mesa mesa;
	
	public Juego() {
		baraja = new Baraja();
		baraja.barajar();
		clientes = new ArrayList<Socket>();
		mesa = new Mesa();
	}
	
	public void run() {
		
		while (clientes.size() != 0) {
			
		}
		
	}
	
	public void unirJugador (Socket cliente ) {
		clientes.add(cliente);
	}
	
	
}
