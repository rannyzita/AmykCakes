package logic;

import dao.CarrinhoDAO;

import model.Carrinho;

public class CarrinhoLogic {
    private CarrinhoDAO carrinhoDAO = new CarrinhoDAO();
    
    public void validarCarrinho(Carrinho carrinho) {
        if (carrinho == null) {
            throw new IllegalArgumentException("O carrinho não pode ser nulo.");
        }

        if (carrinho.getValorTotal() < 0) {
            throw new IllegalArgumentException("O valor total do carrinho não pode ser negativo.");
        }
    }

    public void validarCriarCarrinho(Carrinho carrinho){
        validarCarrinho(carrinho);
        carrinhoDAO.create(carrinho); 
    }

    public Carrinho validarBuscarCarrinho(int id){
        Carrinho carrinho = carrinhoDAO.findById(id); 
        if (carrinho == null) {
            throw new IllegalArgumentException("Carrinho com ID " + id + " não encontrado.");
        }
        return carrinho;
    }

    public void validarAtualizarCarrinho(Carrinho carrinho, CarrinhoLogic CarrinhoLogic ) {
        validarCarrinho(carrinho);
        carrinhoDAO.update(carrinho, CarrinhoLogic); 
    }
}