package servidor;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	private static List<Juego> listaJuegos;
	
	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(8000)) {
			listaJuegos = new ArrayList<Juego>();
			listaJuegos.add(new Juego(1));
			
			while (true) {
				try {
					Socket s = ss.accept();
					System.out.println("Se conecta un cliente");
					mostrarListaMesas(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarListaMesas(Socket s) {
		try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()))) {
			for(Juego juego : listaJuegos) {
				bw.write(juego.toString());
				bw.newLine();
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
