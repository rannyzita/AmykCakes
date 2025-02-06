package exceptions;

public class EquipeException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public EquipeException(String mensagem) {
		super(mensagem);
	}
}