package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import logic.PersonalizacaoLogic;
import model.Pedido;

import java.io.IOException;
import java.util.List;
import dao.PedidoDAO;
import dao.PersonalizacaoDAO;
import dao.ProdutoDAO;

public class MinhasEncomendasController {
	
    @FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane;
    @FXML private VBox vboxEncomendas;
    @FXML private VBox encomendasContainer;

    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
        carregarEncomendas();
    }

    public void trocarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            
            Object controller = loader.getController();
            if (controller instanceof MinhasEncomendasController) {
                ((MinhasEncomendasController) controller).setMainPane(mainPane);
            } else if (controller instanceof TelaInicialController) {
                ((TelaInicialController) controller).setMainPane(mainPane);
            }
            
            mainPane.getChildren().setAll(novaTela);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarEncomendas() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        PersonalizacaoLogic personalizacaoLogic = new PersonalizacaoLogic();
        PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO(personalizacaoLogic);
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // Limpa a interface antes de carregar os novos pedidos
        encomendasContainer.getChildren().clear();

        // Buscar os pedidos de produto e de personalização separadamente
        List<Pedido> pedidosProdutos = pedidoDAO.findAllProdutos();
        List<Pedido> pedidosPersonalizacoes = pedidoDAO.findAllPersonalizacoes();

        // Exibir os produtos encomendados
        if (!pedidosProdutos.isEmpty()) {
            Label tituloProdutos = new Label("=== Produtos Encomendados ===");
            tituloProdutos.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            encomendasContainer.getChildren().add(tituloProdutos);

            for (Pedido pedido : pedidosProdutos) {
                HBox pedidoBox = new HBox(10);
                pedidoBox.setStyle("-fx-background-color: #A384B4; -fx-padding: 8px; -fx-border-radius: 5px;");
                pedidoBox.setAlignment(Pos.CENTER_LEFT);

                VBox detalhesBox = new VBox(5);
                Label nomeLabel = new Label("🔹 Produto: " + pedido.getNomeProduto());
                Label descricaoLabel = new Label("Descrição: " + pedido.getDescricaoProduto());
                Label precoLabel = new Label("Preço: R$ " + pedido.getPrecoProduto());
                Label dataPedidoLabel = new Label("Data do Pedido: " + pedido.getDataPedido());
                Label entregaPrevistaLabel = new Label("Entrega Prevista: " + pedido.getDataEntregaPrevista());

                detalhesBox.getChildren().addAll(nomeLabel, descricaoLabel, precoLabel, dataPedidoLabel, entregaPrevistaLabel);

                pedidoBox.getChildren().add(detalhesBox);
                encomendasContainer.getChildren().add(pedidoBox);
            }
        } else {
            Label semProdutos = new Label("Nenhum produto encomendado.");
            encomendasContainer.getChildren().add(semProdutos);
        }

        // Exibir as personalizações encomendadas
        if (!pedidosPersonalizacoes.isEmpty()) {
            Label tituloPersonalizacoes = new Label("=== Personalizações Encomendadas ===");
            tituloPersonalizacoes.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            encomendasContainer.getChildren().add(tituloPersonalizacoes);

            for (Pedido pedido : pedidosPersonalizacoes) {
                HBox pedidoBox = new HBox(10);
                pedidoBox.setStyle("-fx-background-color: #A384B4; -fx-padding: 8px; -fx-border-radius: 5px;");
                pedidoBox.setAlignment(Pos.CENTER_LEFT);

                VBox detalhesBox = new VBox(5);
                Label nomeLabel = new Label("🔹 Personalização: " + pedido.getNomePersonalizacao());
                Label coberturaLabel = new Label("Cobertura: " + pedido.getTipoCoberturaPersonalizacao());
                Label tamanhoLabel = new Label("Tamanho: " + pedido.getTamanhoPedidoPersonalizacao());
                Label massaLabel = new Label("Massa: " + pedido.getMassaPedidoPersonalizacao());
                Label observacoesLabel = new Label("Observações: " + pedido.getObservacoesPersonalizacao());
                Label quantidadeLabel = new Label("Quantidade: " + pedido.getQuantidadePersonalizacao());
                Label dataPedidoLabel = new Label("Data do Pedido: " + pedido.getDataPedido());
                Label entregaPrevistaLabel = new Label("Entrega Prevista: " + pedido.getDataEntregaPrevista());

                detalhesBox.getChildren().addAll(nomeLabel, coberturaLabel, tamanhoLabel, massaLabel, observacoesLabel, quantidadeLabel, dataPedidoLabel, entregaPrevistaLabel);

                pedidoBox.getChildren().add(detalhesBox);
                encomendasContainer.getChildren().add(pedidoBox);
            }
        } else {
            Label semPersonalizacoes = new Label("Nenhuma personalização encomendada.");
            encomendasContainer.getChildren().add(semPersonalizacoes);
        }
    }


    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
    
}
