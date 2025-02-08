package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.Produto;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;

public class ComprarSobremesaController {
	
	@FXML private Pane mainPane;
	@FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private VBox sobremesaContainer;
    
    @FXML
    private Label txtNome;
    @FXML
    private Label txtDescricao;
    @FXML
    private Label txtEstoque;
    @FXML
    private Label txtPreco;
    @FXML
    private Label txtNomeH1;
    @FXML
    private ToggleGroup formadepag;
    @FXML
    private ImageView imagemProduto;

    private Produto produto; // Armazena o produto carregado
    
    @FXML
    public void initialize() {
        // Configuração de eventos para navegação entre telas
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));

    }
    
    public void trocarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            
            Object controller = loader.getController();
            if (controller instanceof TelaInicialController) {
                ((TelaInicialController) controller).setMainPane(mainPane);
            } else if (controller instanceof MinhasEncomendasController) {
                ((MinhasEncomendasController) controller).setMainPane(mainPane);
            } else if (controller instanceof SobreNosController) {
                ((SobreNosController) controller).setMainPane(mainPane);
            }
            
            mainPane.getChildren().setAll(novaTela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void carregarProduto(int id) {
        System.out.println("Chegou aqui");

        Produto produto = null;

        try (Connection conn = DbConnection.getConexao()) {
            // SQL para buscar o produto pelo ID
            String sql = "SELECT nome, descricao, preco, foto, estoque FROM produtos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);  // Passa o ID como parâmetro na consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Recupera os dados do produto
                String nome = rs.getString("nome");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");
                byte[] imagemBytes = rs.getBytes("foto");
                int estoque = rs.getInt("estoque");

                // Cria o produto com os dados recuperados
                produto = new Produto(id, nome, imagemBytes, descricao, preco, estoque);
            }

            // Verifica se o produto foi encontrado
            if (produto != null) {
                // Criação do layout para exibir os detalhes do produto
                HBox produtoBox = new HBox(10); // Box horizontal com espaçamento
                produtoBox.setStyle("-fx-background-color: #A384B4; -fx-padding: 8px;");

                // Exibe a imagem do produto
                ImageView foto = new ImageView();
                foto.setFitHeight(120);
                foto.setFitWidth(120);
                foto.setPreserveRatio(true);

                if (produto.getFoto() != null && produto.getFoto().length > 0) {
                    Image imagem = new Image(new ByteArrayInputStream(produto.getFoto()));
                    foto.setImage(imagem);
                } else {
                    System.out.println("Imagem não encontrada para: " + produto.getNome());
                    foto.setImage(null); // Caso não tenha imagem, define como vazio
                }

                // Labels para exibir nome, descrição, preço e estoque do produto
                Label nomeLabel = new Label("Nome: " + produto.getNome());
                nomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

                Label descricaoLabel = new Label("Descrição: " + produto.getDescricao());
                descricaoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

                Label precoLabel = new Label("Preço: R$ " + String.format("%.2f", produto.getPreco()));
                precoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

                Label estoqueLabel = new Label("Estoque: " + produto.getEstoque());
                estoqueLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

                // Caixa de detalhes do produto
                VBox detalhesBox = new VBox(5);
                detalhesBox.getChildren().addAll(nomeLabel, descricaoLabel, precoLabel, estoqueLabel);

                // Adiciona a imagem e os detalhes ao layout
                produtoBox.getChildren().addAll(foto, detalhesBox);

                // Adiciona ao container (por exemplo, um painel ou VBox que já esteja na tela)
                // Supondo que 'equipeContainer' seja o container onde você deseja exibir os dados
                
                sobremesaContainer.getChildren().add(produtoBox);

            } else {
                System.err.println("Produto não encontrado para ID: " + id);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    public void onRealizarPedido() {
        if (produto == null) {
            System.err.println("Erro: Nenhum produto carregado.");
            return;
        }

        // Verifica qual forma de pagamento foi selecionada
        RadioButton selectedRadioButton = (RadioButton) formadepag.getSelectedToggle();
        if (selectedRadioButton == null) {
            System.err.println("Erro: Nenhuma forma de pagamento selecionada.");
            return;
        }

        String formaPagamento = selectedRadioButton.getText();
        System.out.println("Pedido realizado com sucesso!");
        System.out.println("Produto: " + produto.getNome());
        System.out.println("Forma de pagamento: " + formaPagamento);
    }
    
    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}