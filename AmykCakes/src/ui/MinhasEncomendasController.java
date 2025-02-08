package ui;

import javafx.application.Platform;

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

public class MinhasEncomendasController {
	
    @FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane;
    @FXML private VBox vboxEncomendas;
    @FXML private VBox encomendasContainer;
    @FXML private VBox encomendasPersonalizadasContainer;


    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
        carregarEncomendas();
        carregarEncomendasPersonalizadas();
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

        encomendasContainer.getChildren().clear();
        List<Pedido> pedidosProdutos = pedidoDAO.findAllProdutos();

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
    }  // **FECHANDO O MÉTODO AQUI**

    public void carregarEncomendasPersonalizadas() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        PersonalizacaoLogic personalizacaoLogic = new PersonalizacaoLogic();
        PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO(personalizacaoLogic);
        
        encomendasPersonalizadasContainer.getChildren().clear();
        List<Pedido> pedidosPersonalizacoes = pedidoDAO.findAllPersonalizacoes();

        if (!pedidosPersonalizacoes.isEmpty()) {
            Label tituloPersonalizacoes = new Label("=== Produtos Encomendados ===");
            tituloPersonalizacoes.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

            encomendasPersonalizadasContainer.getChildren().add(tituloPersonalizacoes);
            double valorTotal = 0;
            for (Pedido pedido : pedidosPersonalizacoes) {
                HBox pedidoBox = new HBox(10);
                pedidoBox.setStyle("-fx-background-color: #A384B4; -fx-padding: 8px; -fx-border-radius: 5px;");
                pedidoBox.setAlignment(Pos.CENTER_LEFT);

                VBox detalhesBox = new VBox(5);
                Label nomeLabel = new Label("🔹 Produto: " + pedido.getNomePersonalizacao());
                Label coberturaLabel = new Label("Cobertura: " + pedido.getTipoCoberturaPersonalizacao());
                Label massaLabel = new Label("Massa: " + pedido.getMassaPedidoPersonalizacao());
                Label tamanhoLabel = new Label("Tamanho: " + pedido.getTamanhoPedidoPersonalizacao());
                Label quantidadeLabel = new Label("Quantidade: " + pedido.getQuantidadePersonalizacao());
                Label observacoesLabel = new Label("Observações: " + pedido.getObservacoesPersonalizacao());
                Label precoLabel = new Label("Preço: R$ " + pedido.getValorTotal());

                // Atualiza o valor total acumulado
                valorTotal = valorTotal + pedido.getValorTotal();
                
                // Adiciona todos os Labels na detalhesBox
                detalhesBox.getChildren().addAll(nomeLabel, coberturaLabel, massaLabel, tamanhoLabel, quantidadeLabel, observacoesLabel, precoLabel);

                pedidoBox.getChildren().add(detalhesBox);
                encomendasPersonalizadasContainer.getChildren().add(pedidoBox);
            }

            // Após o loop, cria o Label para o valor total e o adiciona à lista
            Label valorTotalLabel = new Label("Valor total: R$ " + valorTotal);
            encomendasPersonalizadasContainer.getChildren().add(valorTotalLabel);
        } else {
            Label semProdutos = new Label("Nenhum produto encomendado.");
            encomendasPersonalizadasContainer.getChildren().add(semProdutos);
        }
    }


    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
    
}
