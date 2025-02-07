package logic;

import exceptions.ProdutoException;

public class ProdutoLogic {

    public void validarCamposProduto(String nome, String descricao, double preco, int quantidade) throws ProdutoException {

        if (nome == null || nome.trim().isEmpty()) {
            throw new ProdutoException("O nome do produto é obrigatório.");
        }

        if (descricao == null || descricao.trim().isEmpty()) {
            throw new ProdutoException("A descrição do produto é obrigatória.");
        }

        if (preco <= 0) {
            throw new ProdutoException("O preço do produto não pode ser negativo, nem igual a 0.");
        }

        if (quantidade < 0) {
            throw new ProdutoException("O estoque não pode ser menor que zero.");
        }
    }
}
