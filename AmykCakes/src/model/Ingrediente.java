package model;

public class Ingrediente extends BaseEntity{
	private String nomeIngrediente;
	private int quantidadeEstoque;
	private Produto Produto_idProduto;
	
	public String getNomeIngrediente() {
		return nomeIngrediente;
	}
	
	public void setNomeIngrediente(String nomeIngrediente) {
		this.nomeIngrediente = nomeIngrediente;
	}
	
	public int getQuantidadeEstoque() {
		return quantidadeEstoque;
	}
	
	public void setQuantidadeEstoque(int quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}
	
	public Produto getProduto_idProduto() {
		return Produto_idProduto;
	}
	
	public void setProduto_idProduto(Produto produto_idProduto) {
		Produto_idProduto = produto_idProduto;
	}

}
