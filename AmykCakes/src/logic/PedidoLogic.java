package logic;

import dao.PedidoDAO;

import exceptions.PedidoException;
import model.Pedido;

public class PedidoLogic {
	private PedidoDAO pedidoDAO = new PedidoDAO();
	
	public void validarCamposPedido(Pedido pedido) throws PedidoException {
		if (pedido == null) {
			throw new PedidoException("O pedido não pode ser nulo.");
		}
		
		if (pedido.getValorTotal() <= 0){
			throw new PedidoException("O valor total não pode ser negativo, nem igual a 0.");
		}	
	}
	
	// usaria no controller do javafx
	public void validarCriarPedido(Pedido pedido) throws PedidoException {
        validarCamposPedido(pedido);
        pedidoDAO.create(pedido);
    }

	// usaria no controller do javafx
	public void validarAtualizarPedido(Pedido pedido, String updateFields) throws PedidoException {
        validarCamposPedido(pedido);
        pedidoDAO.update(pedido, updateFields);
    }
	
}