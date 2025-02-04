package model;

public class RestricoesAlimentares extends BaseEntity{
	private Personalizacao Personalizacao_idPedidoPersonalizado;
	private String restricao;
	
	public Personalizacao getPersonalizacao_idPedidoPersonalizado() {
		return Personalizacao_idPedidoPersonalizado;
	}
	
	public void setPersonalizacao_idPedidoPersonalizado(Personalizacao personalizacao_idPedidoPersonalizado) {
		Personalizacao_idPedidoPersonalizado = personalizacao_idPedidoPersonalizado;
	}
	
	public String getRestricao() {
		return restricao;
	}
	
	public void setRestricao(String restricao) {
		this.restricao = restricao;
	}
	
}
