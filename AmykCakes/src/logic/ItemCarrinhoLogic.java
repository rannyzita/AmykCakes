package logic;

import dao.ItemCarrinhoDAO;

import exceptions.ItemCarrinhoException;
import model.ItemCarrinho;

public class ItemCarrinhoLogic {
    private ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
    
    public void validarItemCarrinho(ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
        if (itemCarrinho == null) {
            throw new ItemCarrinhoException("O item não pode ser nulo.");
        }
        
        if (itemCarrinho.getQuantidade() <= 0) {
            throw new ItemCarrinhoException("A quantidade do item não pode ser negativa, nem igual a 0.");
        }
        
        if (itemCarrinho.getValorUnitario() < 0) {
            throw new ItemCarrinhoException("O valor unitário do item não pode ser negativo.");
        }
        
        if (itemCarrinho.getPedido_idPedido() == null || itemCarrinho.getPedido_idPedido().getId() <= 0) {
            throw new ItemCarrinhoException("Pedido inválido.");
        }
        
        if (itemCarrinho.getProduto_idProduto() == null || itemCarrinho.getProduto_idProduto().getId() <= 0) {
            throw new ItemCarrinhoException("Produto inválido.");
        }
    }

    public void validarCriarItemCarrinho(ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
        validarItemCarrinho(itemCarrinho);
        itemCarrinhoDAO.create(itemCarrinho);
    }


    public void validarAtualizarItemCarrinho(int idPedido, int idProduto, int quantidade, double valorUnitario, double subTotal, ItemCarrinhoLogic ItemCarrinhoLogic, ItemCarrinho ItemCarrinho) throws ItemCarrinhoException {
        if (quantidade <= 0) {
            throw new ItemCarrinhoException("A quantidade do item não pode ser negativa, nem igual a 0.");
        }
        
        if (valorUnitario < 0) {
            throw new ItemCarrinhoException("O valor unitário do item não pode ser negativo.");
        }
        
        itemCarrinhoDAO.update(idPedido, idProduto, quantidade, valorUnitario, subTotal, ItemCarrinhoLogic, ItemCarrinho);
    }

    public void validarDeletarItemCarrinho(int idPedido, int idProduto) throws ItemCarrinhoException {
        
        //itemCarrinhoDAO.delete(idPedido, idProduto);
    }
}