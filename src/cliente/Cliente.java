package cliente;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import logicaDeNegocio.Carta;
import logicaDeNegocio.Mesa;

public class Cliente {

	public static void main(String[] args) {
		try (Socket s = new Socket("localhost", 8000);
				ObjectInputStream dis = new ObjectInputStream(s.getInputStream());
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				BufferedReader consola = new BufferedReader(new InputStreamReader(System.in))) {

			procesarNombre(bw, consola);

			selecionarMesa(dis, bw, consola);
			
			jugar(dis, bw, consola);

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void jugar(ObjectInputStream dis, BufferedWriter bw, BufferedReader consola)
			throws IOException, ClassNotFoundException {
		Mesa mesa = null;
		boolean parar = false;
		while (!parar) {
			String servidor = (String) dis.readObject();
			System.out.println(servidor);
			if (servidor.equals("Es tu turno")) {
				
				mesa = (Mesa) dis.readObject();
				System.out.println(mesa);
				System.out.println();
				
				mostarComandosDisponibles();
				
				String comando = consola.readLine().toLowerCase();
				
				while (!comandoValido(comando)) {
					System.out.println("Comando incorrecto");
					mostarComandosDisponibles();
					
					comando = consola.readLine().toLowerCase();
				}
				
				bw.write(comando);
				bw.newLine();
				bw.flush();

				if (comando.equals("cartas")) {
					Carta carta = (Carta) dis.readObject();
					System.out.println("Tu carta es: " + carta.toString());
					String puntos = (String) dis.readObject();

					System.out.println("Tus puntos son: " + puntos);
				}

				if (comando.equals("salir")) {
					parar = true;
				}
				
			} else if (servidor.equals("La ronda ha terminado")) {
				mesa = (Mesa) dis.readObject();
				System.out.println(mesa);
				System.out.println(dis.readObject().toString());
			}
			
			System.out.println();

		}
	}

	private static boolean comandoValido(String comando) {
		if (comando.equalsIgnoreCase("cartas")) {
			return true;
		}
		
		if (comando.equalsIgnoreCase("plantarse")) {
			return true;
		}
		
		if (comando.equalsIgnoreCase("salir")) {
			return true;
		}
		
		return false;
	}

	private static void selecionarMesa(ObjectInputStream dis, BufferedWriter bw, BufferedReader consola)
			throws IOException, ClassNotFoundException {
		String listaMesas = (String) dis.readObject();
		
		while (!listaMesas.equals("unido")) {
			System.out.println(listaMesas);

			System.out.println("Selecione una mesa:");
			bw.write(consola.readLine());
			bw.newLine();
			bw.flush();
			
			listaMesas = (String) dis.readObject();
			
			if (!listaMesas.equals("unido")) {
				System.out.println("Mesa no valida, selecione otra");
			}
		}
		
		System.out.println("Te has undio correctamente");
	}

	private static void procesarNombre(BufferedWriter bw, BufferedReader consola) throws IOException {
		System.out.println("Escribe tu nombre de jugador");
		bw.write(consola.readLine());
		bw.newLine();
		bw.flush();
	}
	
	private static void mostarComandosDisponibles () {
		System.out.println("Comandos disponibles:");
		System.out.println("1. Cartas");
		System.out.println("2. Plantarse");
		System.out.println("3. Salir");
		System.out.println("Escriba el comando deseado sin el numero (Ej: Cartas)");
	}

}
