package model;

public class CarrinhoProduto {
	private int quantidade;
	private Carrinho Carrinho_id;
	private Produto Produto_idProduto;
	
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Carrinho getCarrinho_id() {
		return Carrinho_id;
	}
	public void setCarrinho_id(Carrinho carrinho_id) {
		Carrinho_id = carrinho_id;
	}
	public Produto getProduto_idProduto() {
		return Produto_idProduto;
	}
	public void setProduto_idProduto(Produto produto_idProduto) {
		Produto_idProduto = produto_idProduto;
	}
	
}
