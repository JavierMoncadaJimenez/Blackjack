package excepciones;

public class MesaLLenaExcepcion extends Exception {

	private static final long serialVersionUID = 1L;
	
	public String getMessage() {
		return "La mesa seleccionada esta llena";
	}
}
