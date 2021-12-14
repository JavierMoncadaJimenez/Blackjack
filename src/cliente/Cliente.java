package cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Cliente {

	public static void main(String[] args) {
		try(Socket s = new Socket("localhost", 8000);
				BufferedReader dis = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
			System.out.println(dis.readLine());
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
