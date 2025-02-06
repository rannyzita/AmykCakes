package logic;

import dao.RestricoesAlimentaresDAO;

import exceptions.RestricoesAlimentaresException;
import model.RestricoesAlimentares;

public class RestricoesAlimentaresLogic {
	 private RestricoesAlimentaresDAO restricoesAlimentaresDAO = new RestricoesAlimentaresDAO();
	 
	 public void validarRestricoesAlimentares(RestricoesAlimentares restricaoAlimentar) throws RestricoesAlimentaresException{
		 if(restricaoAlimentar == null) {
			 throw new RestricoesAlimentaresException("A restrição não pode ser nula.");
		 }
		 
		 if (restricaoAlimentar.getRestricao() == null || restricaoAlimentar.getRestricao().trim().isEmpty()) {
	            throw new RestricoesAlimentaresException("A descrição da restrição alimentar é obrigatória.");
		 }
		 
		 if (restricaoAlimentar.getPersonalizacao_idPedidoPersonalizado() == null || restricaoAlimentar.getPersonalizacao_idPedidoPersonalizado().getId() <= 0) {
	            throw new RestricoesAlimentaresException("A personalização associada deve ser válida.");
         }
	 }
	 
	public void validarCriarRestricao(RestricoesAlimentares restricaoAlimentar) throws RestricoesAlimentaresException {
		validarRestricoesAlimentares(restricaoAlimentar);
	    restricoesAlimentaresDAO.create(restricaoAlimentar);
	}

    public void validarAtualizarRestricao(RestricoesAlimentares restricaoAlimentar, RestricoesAlimentaresLogic RestricoesAlimentaresLogic) throws RestricoesAlimentaresException {
    	validarRestricoesAlimentares(restricaoAlimentar);
        restricoesAlimentaresDAO.update(restricaoAlimentar, RestricoesAlimentaresLogic);
    }

    public RestricoesAlimentares validarBuscarRestricao(int id) throws RestricoesAlimentaresException {
        RestricoesAlimentares restricao = restricoesAlimentaresDAO.findById(id);
        if (restricao == null) {
            throw new RestricoesAlimentaresException("Restrição alimentar com ID " + id + " não encontrada.");
        }
        return restricao;
    }
}
