package exceptions;

public class ProdutoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public ProdutoException(String mensagem) {
        super(mensagem);
    }
}
