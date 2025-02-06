package logic;

import dao.CarrinhoProdutoDAO;

import model.CarrinhoProduto;

public class CarrinhoProdutoLogic {
    private CarrinhoProdutoDAO carrinhoProdutoDAO = new CarrinhoProdutoDAO();

    public void validarCarrinhoProduto(CarrinhoProduto carrinhoProduto)  {
        if (carrinhoProduto == null) {
            throw new IllegalArgumentException("CarrinhoProduto não pode ser nulo.");
        }

        if (carrinhoProduto.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade do produto não pode ser negativa, nem igual a 0.");
        }

        if (carrinhoProduto.getCarrinho_id() == null) {
            throw new IllegalArgumentException("O carrinho não pode ser nulo.");
        }

        if (carrinhoProduto.getProduto_idProduto() == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo.");
        }
    }

    public void validarCriarCarrinhoProduto(CarrinhoProduto carrinhoProduto) {
        validarCarrinhoProduto(carrinhoProduto);
        
        carrinhoProdutoDAO.create(carrinhoProduto);
        System.out.println("Produto adicionado ao carrinho com sucesso.");
    }

    public void validarRemoverCarrinhoProduto(int idProduto, int idCarrinho) {

        carrinhoProdutoDAO.delete(idProduto, idCarrinho);
        System.out.println("Produto removido do carrinho com sucesso.");
    }

    public void validarAtualizarQuantidadeCarrinhoProduto(int idProduto, int idCarrinho, int novaQuantidade, CarrinhoProduto carrinhoProduto, CarrinhoProduto CarrinhoProduto, CarrinhoProdutoLogic CarrinhoProdutoLogic) {
        if (novaQuantidade <= 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa, nem igual que 0.");
        }

        if (!carrinhoProdutoDAO.produtoExists(idProduto)) {
            throw new IllegalArgumentException("O produto não existe.");
        }

        if (!carrinhoProdutoDAO.carrinhoExists(idCarrinho)) {
            throw new IllegalArgumentException("O carrinho não existe.");
        }

        carrinhoProdutoDAO.update(idProduto, idCarrinho, novaQuantidade, CarrinhoProduto, CarrinhoProdutoLogic);
        System.out.println("Quantidade do produto atualizada com sucesso.");
    }

    public CarrinhoProduto validarBuscarCarrinhoProduto(int idProduto, int idCarrinho){
        CarrinhoProduto carrinhoProduto = carrinhoProdutoDAO.findById(idProduto, idCarrinho);
        
        if (carrinhoProduto == null) {
            throw new IllegalArgumentException("CarrinhoProduto não encontrado para o Produto " + idProduto + " no Carrinho " + idCarrinho);
        }
        
        return carrinhoProduto;
    }
}
