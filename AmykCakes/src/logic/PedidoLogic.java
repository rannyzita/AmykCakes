package logic;

import dao.PedidoDAO;
import exceptions.PedidoException;
import model.Pedido;

public class PedidoLogic {
    
    public void validarCamposPedido(double valorTotal) throws PedidoException {
        if (valorTotal <= 0){
            throw new PedidoException("O valor total não pode ser negativo, nem igual a 0.");
        }   
    }
}
