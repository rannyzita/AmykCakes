package exceptions;

public class PagamentoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PagamentoException(String mensagem) {
        super(mensagem);
    }
}