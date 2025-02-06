package exceptions;

public class PedidoException extends Exception {
	private static final long serialVersionUID = 1L; // Evita o warning
	
	public PedidoException(String mensagem) {
			super(mensagem);
	}
}
