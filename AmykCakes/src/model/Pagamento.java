package model;
import java.time.LocalDateTime;

public class Pagamento {
	private int idPagamento;
	private Pedido Pedido_idPedido;
	private double valor;
	private String formaPagamento;
	private LocalDateTime data;
	
	public Pagamento() {
        this.data = LocalDateTime.now(); 
    }
	
	public int getIdPagamento() {
		return idPagamento;
	}
	
	public void setIdPagamento(int idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	public Pedido getPedido_idPedido() {
		return Pedido_idPedido;
	}
	
	public void setPedido_idPedido(Pedido pedido_idPedido) {
		Pedido_idPedido = pedido_idPedido;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}
	
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public LocalDateTime getData() {
		return data;
	}
	
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
}
