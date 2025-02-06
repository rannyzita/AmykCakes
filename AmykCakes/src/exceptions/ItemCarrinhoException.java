package exceptions;

public class ItemCarrinhoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ItemCarrinhoException(String mensagem) {
		super(mensagem);
	}
}