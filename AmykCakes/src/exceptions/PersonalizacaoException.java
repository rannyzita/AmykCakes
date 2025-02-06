package exceptions;

public class PersonalizacaoException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PersonalizacaoException(String mensagem) {
        super(mensagem);
    }
}
