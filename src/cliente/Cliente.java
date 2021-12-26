package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import logicaDeNegocio.Mesa;

public class Cliente {

	public static void main(String[] args) {
		try (Socket s = new Socket("localhost", 8000);
				ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				BufferedReader consola = new BufferedReader(new InputStreamReader(System.in))) {
			String listaMesas = (String) dis.readObject();

			System.out.println(listaMesas);

			System.out.println("Selecione una mesa:");
			bw.write(consola.readLine());
			bw.newLine();
			bw.flush();
			Mesa mesa = null;
			boolean parar = false;
			while (!parar) {

				String servidor = (String) dis.readObject();

				System.out.println(servidor);

				System.out.println("Estado de la mesa:");
				mesa = (Mesa) dis.readObject();
				System.out.println(mesa);

				String comando = consola.readLine();
				bw.write(comando);
				bw.newLine();
				bw.flush();

				if (comando.equals("cartas")) {
					System.out.println(dis.readObject().toString());
				}

				if (comando.equals("salir")) {
					parar = true;
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
