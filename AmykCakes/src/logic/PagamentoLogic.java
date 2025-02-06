package logic;

import dao.PagamentoDAO;

import exceptions.PagamentoException;
import model.Pagamento;

public class PagamentoLogic {
	private PagamentoDAO pagamentoDAO = new PagamentoDAO();
	
	public void validarCamposPagamento(Pagamento pagamento) throws PagamentoException{
		if (pagamento == null) {
			throw new PagamentoException("O pagamento não pode ser nulo.");
		}
		
        if (pagamento.getValor() <= 0) {
            throw new PagamentoException("O valor do pagamento não pode ser negativo, nem igual a 0.");
        }
        
        if (pagamento.getFormaPagamento() == null || pagamento.getFormaPagamento().trim().isEmpty()) {
            throw new PagamentoException("A forma de pagamento não pode estar vazia.");
        }
        
        
        if (pagamento.getPedido_idPedido() == null || pagamento.getPedido_idPedido().getId() <= 0) {
            throw new PagamentoException("Pedido inválido.");
        }
	}
	
	// controller javafx, na tela de seleção do pedido
	// na tela inicial
	public void validarCriarPagamento(Pagamento pagamento) throws PagamentoException {
        validarCamposPagamento(pagamento);
        pagamentoDAO.create(pagamento);
    }
	
	// usaria no controller do javafx
    public void validarAtualizarPagamento(Pagamento pagamento) throws PagamentoException {
        validarCamposPagamento(pagamento);
    }

}