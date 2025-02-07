package logic;

import dao.PedidoDAO;
import exceptions.PedidoException;
import model.Pedido;

public class PedidoLogic {
    private PedidoDAO pedidoDAO;

    public PedidoLogic(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;  // Agora passando o PedidoDAO como dependência
    }

    public void validarCamposPedido(double valorTotal) throws PedidoException {
        if (valorTotal <= 0) {
            throw new PedidoException("O valor total não pode ser negativo, nem igual a 0.");
        }
    }

    public void validarCriarPedido(double valorTotal) throws PedidoException {
        validarCamposPedido(valorTotal);
        pedidoDAO.create(valorTotal);
    }

    public Pedido validarBuscarPedido(int id) throws PedidoException {
        Pedido pedido = pedidoDAO.findById(id);
        if (pedido == null) {
            throw new PedidoException("Pedido com ID " + id + " não encontrado.");
        }
        return pedido;
    }
}
