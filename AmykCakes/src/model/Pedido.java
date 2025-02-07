package model;
import java.time.LocalDateTime;

public class Pedido extends BaseEntity{
	private Personalizacao idPersonalizacao;
	private Produto idProduto;
	private LocalDateTime dataPedido;
	private LocalDateTime dataEntregaPrevista;
	private double valorTotal;
	
	public int getIdPersonalizacao() {
		return idPersonalizacao.getId();
	}

	public void setIdPersonalizacao(Personalizacao idPersonalizacao) {
		this.idPersonalizacao = idPersonalizacao;
	}

	public Produto getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Produto idProduto) {
		this.idProduto = idProduto;
	}
	
	public Pedido() {
        this.dataPedido = LocalDateTime.now(); 
        this.dataEntregaPrevista = LocalDateTime.now().plusDays(15);
    }

	public Pedido(int int1) {
	}

	public LocalDateTime getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}

	public LocalDateTime getDataEntregaPrevista() {
		return dataEntregaPrevista;
	}

	public void setDataEntregaPrevista(LocalDateTime dataEntregaPrevista) {
		this.dataEntregaPrevista = dataEntregaPrevista;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
}
