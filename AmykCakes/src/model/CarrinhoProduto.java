package model;

public class CarrinhoProduto {
    private int quantidade;
    private Carrinho Carrinho_id;
    private Produto Produto_idProduto;
    
    public CarrinhoProduto () {
    	
    }
    
    public CarrinhoProduto(Carrinho Carrinho_id, Produto Produto_idProduto, int quantidade) {
        this.Carrinho_id = Carrinho_id;
        this.Produto_idProduto = Produto_idProduto;
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Carrinho getCarrinho_id() {
        return Carrinho_id;
    }

    public void setCarrinho_id(Carrinho Carrinho_id) {
        this.Carrinho_id = Carrinho_id;
    }

    public Produto getProduto_idProduto() {
        return Produto_idProduto;
    }

    public void setProduto_idProduto(Produto Produto_idProduto) {
        this.Produto_idProduto = Produto_idProduto;
    }
}
