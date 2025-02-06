package logic;

import dao.PedidoDAO;

import exceptions.PedidoException;
import model.Pedido;

public class PedidoLogic {
	private PedidoDAO pedidoDAO = new PedidoDAO();
	
	public void validarPedido(Pedido pedido) throws PedidoException {
		if (pedido == null) {
			throw new PedidoException("O pedido não pode ser nulo.");
		}
		
		if (pedido.getValorTotal() <= 0){
			throw new PedidoException("O valor total não pode ser negativo, nem igual a 0.");
		}	
	}
	
	public void validarCriarPedido(Pedido pedido) throws PedidoException {
        validarPedido(pedido);
        pedidoDAO.create(pedido);
    }

	
	public void validarAtualizarPedido(Pedido pedido, String updateFields) throws PedidoException {
        validarPedido(pedido);
        pedidoDAO.update(pedido, updateFields);
    }
	
	public Pedido validarBuscarPedido(int id) throws PedidoException {
        Pedido pedido = pedidoDAO.findById(id);
        if (pedido == null) {
            throw new PedidoException("Pedido com ID " + id + " não encontrado.");
        }
        return pedido;
    }

}