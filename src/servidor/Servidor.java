package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import excepciones.MesaLLenaExcepcion;
import logicaDeNegocio.Jugador;

public class Servidor {
	private static List<Juego> listaJuegos;
	
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try (ServerSocket ss = new ServerSocket(8000)) {
			listaJuegos = new ArrayList<Juego>();
			listaJuegos.add(new Juego(1));
			
			while (true) {
				Jugador jugador= null ;
				try {
					Socket s = ss.accept();
					System.out.println("Se conecta un cliente");
					jugador = new Jugador(s);
					BufferedReader br = jugador.getEntradaCliente();
					String nombre = br.readLine();
					jugador.setNombre(nombre);
					selecionMesa(pool, jugador);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void selecionMesa(ExecutorService pool, Jugador jugador) {
		try {
			mostrarListaMesas(jugador);
			BufferedReader br = jugador.getEntradaCliente();
			String numeroS = br.readLine();
			int idMesa = Integer.parseInt(numeroS) - 1;
			
			if (listaJuegos.get(idMesa).mesaLlena()) {
				throw new MesaLLenaExcepcion();
			}
			
			if (listaJuegos.get(idMesa).mesaVacia()) {
				listaJuegos.get(idMesa).unirJugador(jugador);
				pool.execute(listaJuegos.get(idMesa));
			} else {
				listaJuegos.get(idMesa).unirJugador(jugador);
			}
			
			if (listaJuegos.get(idMesa).mesaLlena()) {
				listaJuegos.add(new Juego(listaJuegos.size() + 1));
			}
			
			jugador.getSalidaCliente().writeObject("unido");
			
		} catch (NumberFormatException | IndexOutOfBoundsException | MesaLLenaExcepcion e) {
			selecionMesa(pool, jugador);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private static void mostrarListaMesas(Jugador jugador) {
		try {
			
			String listaMesas = "";
			
			for(Juego juego : listaJuegos) {
				listaMesas += juego.toString();
				listaMesas += "\r\n";
			}
			
			jugador.getSalidaCliente().writeObject(listaMesas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
