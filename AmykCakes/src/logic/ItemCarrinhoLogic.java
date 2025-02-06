package logic;

import dao.ItemCarrinhoDAO;
import exceptions.ItemCarrinhoException;
import model.ItemCarrinho;

public class ItemCarrinhoLogic {
    private ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();

    public void validarCamposItemCarrinho(ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
        if (itemCarrinho == null) {
            throw new ItemCarrinhoException("O item não pode ser nulo.");
        }
        
        if (itemCarrinho.getQuantidade() <= 0) {
            throw new ItemCarrinhoException("A quantidade do item não pode ser negativa, nem igual a 0.");
        }
        
        if (itemCarrinho.getValorUnitario() < 0) {
            throw new ItemCarrinhoException("O valor unitário do item não pode ser negativo.");
        }
    }

    // Usado no controller para validar antes de criar
    public void validarCriarItemCarrinho(ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
        validarCamposItemCarrinho(itemCarrinho);
        itemCarrinhoDAO.create(itemCarrinho);
    }

    public void validarAtualizarItemCarrinho(int idPedido, int idProduto, int idPersonalizacao, int quantidade, double valorUnitario) throws ItemCarrinhoException {
        if (quantidade <= 0) {
            throw new ItemCarrinhoException("A quantidade do item não pode ser negativa, nem igual a 0.");
        }
        
        if (valorUnitario < 0) {
            throw new ItemCarrinhoException("O valor unitário do item não pode ser negativo.");
        }
        
        itemCarrinhoDAO.update(idPedido, idProduto, idPersonalizacao, quantidade, valorUnitario);
    }
}
