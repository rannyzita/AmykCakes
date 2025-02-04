package model;
import java.time.LocalDateTime;

public class Pedido extends BaseEntity{
	private LocalDateTime dataPedido;
	private LocalDateTime dataEntregaPrevista;
	private double valorTotal;
	
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
