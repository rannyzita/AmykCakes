package exceptions;

public class IngredienteException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IngredienteException(String mensagem) {
		super(mensagem);
	}
}