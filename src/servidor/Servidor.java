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
					ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
					mostrarListaMesas(s, oos);
					BufferedReader br = new BufferedReader(new InputStreamReader( s.getInputStream()));
					String numeroS = br.readLine();
					int idMesa = Integer.parseInt(numeroS) - 1;
					
					//TODO hacer que no se pueda conectar si la mesa esta llena
					
					if (listaJuegos.get(idMesa).mesaVacia()) {
						listaJuegos.get(idMesa).unirJugador(s, oos);
						pool.execute(listaJuegos.get(idMesa));
					} else {
						listaJuegos.get(idMesa).unirJugador(s, oos);
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

	private static void mostrarListaMesas(Socket s, ObjectOutputStream oos) {
		try {
			
			String listaMesas = "";
			
			for(Juego juego : listaJuegos) {
				listaMesas += juego.toString();
				listaMesas += "\r\n";
			}
			
			oos.writeObject(listaMesas);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
