package logic;

import dao.ItemCarrinhoDAO;
import exceptions.ItemCarrinhoException;

public class ItemCarrinhoLogic {
    private ItemCarrinhoDAO itemCarrinhoDAO;

    public ItemCarrinhoLogic(ItemCarrinhoDAO itemCarrinhoDAO) {
        this.itemCarrinhoDAO = itemCarrinhoDAO;
    }

    public void validarCamposItemCarrinho(int quantidade, double valorUnitario) throws ItemCarrinhoException {
        if (quantidade <= 0) {
            throw new ItemCarrinhoException("A quantidade do item não pode ser negativa, nem igual a 0.");
        }
        if (valorUnitario < 0) {
            throw new ItemCarrinhoException("O valor unitário do item não pode ser negativo.");
        }
    }

    public void validarAtualizarItemCarrinho(int idPedido, int idProduto, int idPersonalizacao, int quantidade, double valorUnitario) throws ItemCarrinhoException {
        validarCamposItemCarrinho(quantidade, valorUnitario);
        itemCarrinhoDAO.update(idPedido, idProduto, idPersonalizacao, quantidade, valorUnitario);
    }
}
