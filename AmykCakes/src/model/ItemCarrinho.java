package model;

import javafx.scene.Node;

public class ItemCarrinho extends BaseEntity {
	private Pedido Pedido_idPedido = new Pedido();
	private int quantidade;
	private double valorUnitario;
	
	public void setPedido_idPedido(int id) {
		Pedido_idPedido.setId(id);
	}

	public Pedido getPedido_idPedido() {
		return Pedido_idPedido;
	}
	
	public void setPedido_idPedido(Pedido pedido_idPedido) {
		Pedido_idPedido = pedido_idPedido;
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
