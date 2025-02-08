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

	public int getIdProduto() {
		return idProduto.getId();
	}

	public void setIdProduto(Produto idProduto) {
		this.idProduto = idProduto;
	}
	
	public void setPersonalizacao (Personalizacao personalizacao) {
		this.idPersonalizacao = personalizacao;
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
	
	public String getNomeProduto() {
		return idProduto.getNome();
	}
	
	public String getDescricaoProduto() {
		return idProduto.getDescricao();
	}
	
	public Double getPrecoProduto() {
		return idProduto.getPreco();
	}
	
	public int getEstoqueProduto() {
		return idProduto.getEstoque();
	}
	
	public String getNomePersonalizacao() {
		return idPersonalizacao.getNome();
	}
	
	public String getTipoCoberturaPersonalizacao() {
		return idPersonalizacao.getTipoCobertura();
	}
	
	public String getTamanhoPedidoPersonalizacao() {
		return idPersonalizacao.getTamanhoPedido();
	}
	
	public String getMassaPedidoPersonalizacao() {
		return idPersonalizacao.getMassaPedido();
	}
	
	public String getObservacoesPersonalizacao() {
		return idPersonalizacao.getObservacoes();
	}
	
	public int getQuantidadePersonalizacao() {
		return idPersonalizacao.getQuantidade();
	}

	public void setPersonalizacao(int id) {
		idPersonalizacao.setId(id);
	}
		
}
