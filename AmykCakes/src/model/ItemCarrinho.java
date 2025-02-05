package model;

import javafx.scene.Node;

public class ItemCarrinho extends BaseEntity {
	private Pedido Pedido_idPedido;
	private Produto Produto_idProduto;
	private int quantidade;
	private double valorUnitario;
	private Personalizacao Personalizacao_id;
	
	public Personalizacao getPersonalizacao_id() {
		return Personalizacao_id;
	}

	public void setPersonalizacao_id(Personalizacao personalizacao_id) {
		Personalizacao_id = personalizacao_id;
	}

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

	public Node setPedido_idPedido() {
		// TODO Auto-generated method stub
		return null;
	}
		
}
