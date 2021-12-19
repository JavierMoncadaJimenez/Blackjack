package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
	private int jugadorActual;
	
	public Juego(int idMesa) {
		baraja = new Baraja();
		jugadorActual = 0;
		baraja.barajar();
		clientes = new ArrayList<Socket>();
		mesa = new Mesa();
		this.idMesa = idMesa;
	}
	
	public void run() {
		while (clientes.size() != 0) {
			Socket clienteActual = clientes.get(jugadorActual);
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader (clienteActual.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clienteActual.getOutputStream()));
				bw.write("Es tu turno");
				bw.newLine();
				bw.flush();
				
				String comando = br.readLine();
				
				if (comando.equals("cartas")) {
					bw.write(baraja.robarCarta().toString());
					bw.newLine();
					bw.flush();
				}
				
				if (comando.equals("salir")) {
					clientes.remove(clienteActual);
					bw.write("Has salido correctamente");
					bw.newLine();
					bw.flush();
				}
				
				System.out.println(comando);
			} catch (IOException e) {
				e.printStackTrace();
			}
			actualizarTurno ();
		}
		
		Servidor.eliminarMesa(idMesa);
		
	}
	
	private void actualizarTurno() {
		jugadorActual ++;
		
		if (jugadorActual >= clientes.size()) {
			jugadorActual = 0;
		}
		
	}

	public void unirJugador (Socket cliente ) {
		clientes.add(cliente);
	}
	
	public boolean mesaLlena() {
		return clientes.size() == MAX_JUGADORES;
	}
	
	public boolean mesaVacia() {
		return clientes.size() == 0;
	}
	
	public String toString () {
		return "Mesa " + idMesa + " con " + clientes.size() + " jugadores unidos";
	}
	
	
}
