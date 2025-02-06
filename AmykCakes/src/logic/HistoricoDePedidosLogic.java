package logic;

import dao.HistoricoDePedidosDAO;

public class HistoricoDePedidosLogic {
    private HistoricoDePedidosDAO historicoDAO = new HistoricoDePedidosDAO();

    public void validarIdPedido(int idPedido) {
        if (idPedido <= 0) {
            throw new IllegalArgumentException("O ID do pedido não pode ser negativo, nem igual a 0.");
        }
    }
    
    public void validarCriarHistorico(int idPedido){
        validarIdPedido(idPedido);
        historicoDAO.create(idPedido);
    }

    public int validarBuscarHistorico(int idPedido) {
        validarIdPedido(idPedido);
        int result = historicoDAO.findById(idPedido);
        if (result == -1) {
            throw new IllegalArgumentException("Histórico para o pedido com ID " + idPedido + " não encontrado.");
        }
        return result;
    }

    public void validarAtualizarHistorico(int idPedido, HistoricoDePedidosLogic HistoricoDePedidosLogic) {
        validarIdPedido(idPedido);
        historicoDAO.update(idPedido, HistoricoDePedidosLogic);
    }

    public void validarDeletarHistorico(int idPedido) {
        validarIdPedido(idPedido);
        historicoDAO.delete(idPedido);
    }

    public void listarHistoricos() {
        historicoDAO.findAll();
    }
}
