package logic;

import dao.PagamentoDAO;
import exceptions.PagamentoException;
import model.Pagamento;

public class PagamentoLogic {
    
    public void validarCamposPagamento(String formaPagamento, double valor) throws PagamentoException {
        
        
        if (valor <= 0) {
            throw new PagamentoException("O valor do pagamento não pode ser negativo, nem igual a 0.");
        }
        
        if (formaPagamento == null || formaPagamento.trim().isEmpty()) {
            throw new PagamentoException("A forma de pagamento não pode estar vazia.");
        }
        
    }
    
}
