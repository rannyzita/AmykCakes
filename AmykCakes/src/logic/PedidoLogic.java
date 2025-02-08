package logic;

import exceptions.PedidoException;

public class PedidoLogic {

    public PedidoLogic() {
    }

    public void validarCamposPedido(double valorTotal) throws PedidoException {
        if (valorTotal <= 0) {
            throw new PedidoException("O valor total não pode ser negativo, nem igual a 0.");
        }
    }

}
