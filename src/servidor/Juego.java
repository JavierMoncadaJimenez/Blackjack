package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import logicaDeNegocio.Baraja;
import logicaDeNegocio.Carta;
import logicaDeNegocio.Mesa;

public class Juego extends Thread {
	private static int MAX_JUGADORES = 2;
	
	private int idMesa;
	private List<Socket> clientes;
	private List<ObjectOutputStream> salidaClientes;
	private Baraja baraja;
	private Mesa mesa;
	private int jugadoresPlantados;
	private int jugadorActual;
	
	public Juego(int idMesa) {
		baraja = new Baraja();
		jugadorActual = 0;
		baraja.barajar();
		clientes = new ArrayList<Socket>();
		salidaClientes = new ArrayList<ObjectOutputStream>();
		mesa = new Mesa();
		this.idMesa = idMesa;
		jugadoresPlantados = 0;
	}
	
	public void run() {
		while (clientes.size() != 0) {
			Socket clienteActual = clientes.get(jugadorActual);
			
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader( clienteActual.getInputStream()));
				ObjectOutputStream bw = salidaClientes.get(jugadorActual);
				
				bw.writeObject("Es tu turno");
				bw.flush();
				
				bw.reset();
				bw.writeObject(mesa);
				bw.flush();

				String comando = br.readLine();
				
				procesarComando(clienteActual, bw, comando);
				
				System.out.println(comando);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			actualizarTurno ();
			
			if(jugadoresPlantados == clientes.size()) {
				terminarRonda();
			}
		}
		
	}

	private void procesarComando(Socket clienteActual, ObjectOutputStream bw, String comando) throws IOException {
		if (comando.equals("cartas")) {
			darCarta(bw);
		}
		
		if(comando.equals("platarse")) {
			jugadoresPlantados ++;
		}
		
		if (comando.equals("salir")) {
			salirCliente(clienteActual, bw);
		}
	}

	private void salirCliente(Socket clienteActual, ObjectOutputStream bw) throws IOException {
		clientes.remove(clienteActual);
		salidaClientes.remove(bw);
		bw.writeObject("Has salido correctamente");
		bw.flush();
	}

	private void darCarta(ObjectOutputStream bw) throws IOException {
		Carta carta = baraja.robarCarta();
		mesa.darCartaJugador(jugadorActual, carta);
		bw.writeObject(carta);
		bw.flush();
		
		if (baraja.estaVacia()) {
			baraja = new Baraja();
			baraja.barajar();
		}
	}
	
	private void terminarRonda() {
		// TODO Hacer que la ronda acabe sacando cartas al crupier
		
	}

	private void actualizarTurno() {
		jugadorActual ++;
		
		if (jugadorActual >= clientes.size()) {
			jugadorActual = 0;
		}
		
	}

	public void unirJugador (Socket cliente, ObjectOutputStream oos ) {
		clientes.add(cliente);
		salidaClientes.add(oos);
		mesa.unirJugador();
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
