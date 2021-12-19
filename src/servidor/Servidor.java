package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {
	private static List<Juego> listaJuegos;
	
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		try (ServerSocket ss = new ServerSocket(8000)) {
			listaJuegos = new ArrayList<Juego>();
			listaJuegos.add(new Juego(1));
			
			while (true) {
				try {
					Socket s = ss.accept();
					System.out.println("Se conecta un cliente");
					mostrarListaMesas(s);
					BufferedReader br = new BufferedReader(new InputStreamReader( s.getInputStream()));
					int idMesa = Integer.parseInt(br.readLine()) - 1;
					
					if (listaJuegos.get(idMesa).mesaVacia()) {
						listaJuegos.get(idMesa).unirJugador(s);
						pool.execute(listaJuegos.get(idMesa));
					} else {
						listaJuegos.get(idMesa).unirJugador(s);
					}
					
					if (listaJuegos.get(idMesa).mesaLlena()) {
						listaJuegos.add(new Juego(listaJuegos.size() + 1));
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarListaMesas(Socket s) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			for(Juego juego : listaJuegos) {
				bw.write(juego.toString());
				bw.newLine();
				bw.flush();
			}
			
			bw.write("listadoCompleto");
			bw.newLine();
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void eliminarMesa(int idMesa) {
		listaJuegos.remove(idMesa - 1);
		
		if (listaJuegos.isEmpty()) {
			listaJuegos.add(new Juego(1));
		}
		
		actualizarIndicesMesas (idMesa);
	}

	private static void actualizarIndicesMesas(int idMesa) {
		// TODO tiene que descontar uno a todas las mesas que esten por encima del id del paramentro
		
	}
	
}
