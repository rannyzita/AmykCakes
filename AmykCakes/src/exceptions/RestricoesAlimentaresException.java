package exceptions;

public class RestricoesAlimentaresException extends Exception {
	private static final long serialVersionUID = 1L;

	public RestricoesAlimentaresException(String mensagem) {
        super(mensagem);
    }
}
