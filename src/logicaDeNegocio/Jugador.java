package logicaDeNegocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Jugador {
	private ObjectOutputStream salidaCliente;
	private BufferedReader entradaCliente;
	private Socket cliente;
	private boolean plantado;
	
	public Jugador(Socket cliente) throws IOException {
		this.cliente = cliente;
		salidaCliente = new ObjectOutputStream (this.cliente.getOutputStream());
		entradaCliente = new BufferedReader(new InputStreamReader(this.cliente.getInputStream()));
		plantado = false;
	}

	public ObjectOutputStream getSalidaCliente() {
		return salidaCliente;
	}

	public void setSalidaCliente(ObjectOutputStream salidaCliente) {
		this.salidaCliente = salidaCliente;
	}

	public Socket getCliente() {
		return cliente;
	}

	public void setCliente(Socket cliente) {
		this.cliente = cliente;
	}

	public boolean isPlantado() {
		return plantado;
	}

	public void setPlantado(boolean plantado) {
		this.plantado = plantado;
	}

	public BufferedReader getEntradaCliente() {
		return entradaCliente;
	}

	public void setEntradaCliente(BufferedReader entradaCliente) {
		this.entradaCliente = entradaCliente;
	}
}
