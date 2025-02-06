package logic;

import dao.PagamentoDAO;

import exceptions.PagamentoException;
import model.Pagamento;

public class PagamentoLogic {
	private PagamentoDAO pagamentoDAO = new PagamentoDAO();
	
	public void validarPagamento(Pagamento pagamento) throws PagamentoException{
		if (pagamento == null) {
			throw new PagamentoException("O pagamento não pode ser nulo.");
		}
		
        if (pagamento.getValor() <= 0) {
            throw new PagamentoException("O valor do pagamento não pode ser negativo, nem igual a 0.");
        }
        
        if (pagamento.getFormaPagamento() == null || pagamento.getFormaPagamento().trim().isEmpty()) {
            throw new PagamentoException("A forma de pagamento não pode estar vazia.");
        }
        
        if (pagamento.getData() == null) {
            throw new PagamentoException("A data do pagamento não pode ser nula.");
        }
        
        if (pagamento.getPedido_idPedido() == null || pagamento.getPedido_idPedido().getId() <= 0) {
            throw new PagamentoException("Pedido inválido.");
        }
	}
	
	public void validarCriarPagamento(Pagamento pagamento) throws PagamentoException {
        validarPagamento(pagamento);
        pagamentoDAO.create(pagamento);
    }

    public Pagamento validarBuscarPagamento(int id) throws PagamentoException {
        Pagamento pagamento = pagamentoDAO.findById(id);
        if (pagamento == null) {
            throw new PagamentoException("Pagamento com ID " + id + " não encontrado.");
        }
        return pagamento;
    }

    public void validarAtualizarPagamento(Pagamento pagamento, String updateFields) throws PagamentoException {
        validarPagamento(pagamento);
        pagamentoDAO.update(pagamento, updateFields);
    }

    public void validarDeletarPagamento(int id) throws PagamentoException {
        Pagamento pagamento = validarBuscarPagamento(id);
        if (pagamento == null) {
            throw new PagamentoException("Pagamento com ID " + id + " não encontrado.");
        }
        pagamentoDAO.delete(id);
    } 
}