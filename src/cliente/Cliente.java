package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		try(Socket s = new Socket("localhost", 8000);
				BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()));
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				BufferedReader consola = new BufferedReader(new InputStreamReader(System.in))) {
			String listaMesas = dis.readLine();
			while (!listaMesas.equals("listadoCompleto")) {
				System.out.println(listaMesas);
				listaMesas = dis.readLine();
				
			}
			System.out.println("Selecione una mesa:");
			bw.write(consola.readLine());
			bw.newLine();
			bw.flush();
			
			boolean parar = false;
			while (!parar) {
				String servidor = dis.readLine();
				if (servidor.equals("Es tu turno")) {
					System.out.println("es tu turno crack");
					String comando = consola.readLine();
					bw.write(comando);
					bw.newLine();
					bw.flush();
					
					System.out.println(dis.readLine());
					
					if (comando.equals("salir")) {
						parar = true;
					}
				}
				
			}
			
			
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
