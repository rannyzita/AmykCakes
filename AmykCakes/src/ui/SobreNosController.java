package ui;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SobreNosController {
	
	@FXML
    private Label labelTelaInicial;
    @FXML
    private Label labelPersonalizarPedido;
    @FXML
    private Label labelMinhasEncomendas;
    @FXML
    private Label labelSobreNos;
    @FXML
    private Pane mainPane;
    
    @FXML
    private VBox equipeContainer; // Certifique-se de que o FXML contém esse ID!

    @FXML
    public void initialize() {
    	labelTelaInicial.setOnMouseClicked(event -> System.out.println("Tela Inicial clicada!"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
        carregarEquipe();
    }
    
    public void trocarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            mainPane.getChildren().setAll(novaTela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void carregarEquipe() {
        try (Connection conn = DbConnection.getConexao()) {
            String sql = "SELECT nome, foto, descricao, cargo FROM equipe"; 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome");
                byte[] imagemBytes = rs.getBytes("foto");
                String descricao = rs.getString("descricao");
                String cargo = rs.getString("cargo");

                HBox membroBox = new HBox(10);
                membroBox.setStyle("-fx-background-color: #A384B4; -fx-padding: 8px;");

                ImageView foto = new ImageView();
                foto.setFitHeight(120);
                foto.setFitWidth(120);
                foto.setPreserveRatio(true);

                if (imagemBytes != null && imagemBytes.length > 0) {
                    Image imagem = new Image(new ByteArrayInputStream(imagemBytes));
                    foto.setImage(imagem);
                } else {
                    System.out.println("Imagem não encontrada para: " + nome);
                }

                Label nomeLabel = new Label("Nome: " + nome);
                nomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

                Label descricaoLabel = new Label("Descrição: " + descricao);
                descricaoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

                Label cargoLabel = new Label("Cargo: " + cargo);
                cargoLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

                VBox detalhesBox = new VBox(5);
                detalhesBox.getChildren().addAll(nomeLabel, descricaoLabel, cargoLabel);

                membroBox.getChildren().addAll(foto, detalhesBox);
                equipeContainer.getChildren().add(membroBox);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar equipe: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

}
