package logic;

import java.io.File;
import java.io.IOException;
import dao.ProdutoDAO;
import exceptions.ProdutoException;
import model.Produto;

public class ProdutoLogic {
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void validarCamposProduto(Produto produto) throws ProdutoException {
        if (produto == null) {
            throw new ProdutoException("O produto não pode ser nulo.");
        }

        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new ProdutoException("O nome do produto é obrigatório.");
        }

        if (produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()) {
            throw new ProdutoException("A descrição do produto é obrigatória.");
        }

        if (produto.getPreco() <= 0) {
            throw new ProdutoException("O preço do produto não pode ser negativo, nem igual a 0.");
        }

        if (produto.getEstoque() < 0) {
            throw new ProdutoException("O estoque não pode ser menor que zero.");
        }
    }

    public void validarCriarProduto(Produto produto, File imagem) throws ProdutoException, IOException {
        validarCamposProduto(produto);
        produtoDAO.create(produto, imagem);
    }

    public void validarAtualizarProduto(Produto produto, File imagem) throws ProdutoException, IOException {
        validarCamposProduto(produto);
        produtoDAO.update(produto, imagem);
    }

    public Produto validarBuscarProduto(int id) throws ProdutoException {
        Produto produto = produtoDAO.findById(id);
        if (produto == null) {
            throw new ProdutoException("Produto com ID " + id + " não encontrado.");
        }
        return produto;
    }
}
