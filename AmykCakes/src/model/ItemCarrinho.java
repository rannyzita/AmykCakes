package model;

public class ItemCarrinho {
	private Pedido Pedido_idPedido;
	private Produto Produto_idProduto;
	private int quantidade;
	private double valorUnitario;
	private double subTotal;
	
	public Pedido getPedido_idPedido() {
		return Pedido_idPedido;
	}
	
	public void setPedido_idPedido(Pedido pedido_idPedido) {
		Pedido_idPedido = pedido_idPedido;
	}
	
	public Produto getProduto_idProduto() {
		return Produto_idProduto;
	}
	
	public void setProduto_idProduto(Produto produto_idProduto) {
		Produto_idProduto = produto_idProduto;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	public double getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public double getSubTotal() {
		return subTotal;
	}
	
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
