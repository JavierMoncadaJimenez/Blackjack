package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Servidor {
	private static List<Juego> listaJuegos;
	
	public static void main(String[] args) {
		try (ServerSocket ss = new ServerSocket(8000)) {
			listaJuegos = new ArrayList<Juego>();
			listaJuegos.add(new Juego());
			
			while (true) {
				try {
					Socket s = ss.accept();
					mostarListaJuegos(s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void mostarListaJuegos (Socket cliente) {
		try {
			DataOutputStream dos = new DataOutputStream(cliente.getOutputStream());
			for (Juego juego : listaJuegos) {
				dos.writeChars(juego.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
