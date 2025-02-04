package model;

public class Personalizacao extends BaseEntity{
	private String tipoCobertura;
	private String tamanhoPedido;
	private String massaPedido;
	private String observacoes;
	private String nome;
	private Pedido Pedido_idPedido;
	
	public String getTipoCobertura() {
		return tipoCobertura;
	}
	
	public void setTipoCobertura(String tipoCobertura) {
		this.tipoCobertura = tipoCobertura;
	}
	
	public String getTamanhoPedido() {
		return tamanhoPedido;
	}
	
	public void setTamanhoPedido(String tamanhoPedido) {
		this.tamanhoPedido = tamanhoPedido;
	}
	
	public String getMassaPedido() {
		return massaPedido;
	}
	
	public void setMassaPedido(String massaPedido) {
		this.massaPedido = massaPedido;
	}
	
	public String getObservacoes() {
		return observacoes;
	}
	
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Pedido getPedido_idPedido() {
		return Pedido_idPedido;
	}
	
	public void setPedido_idPedido(Pedido pedido) {
		Pedido_idPedido = pedido;
	}
	
}
