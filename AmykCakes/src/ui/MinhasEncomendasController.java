package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.DbConnection;

public class MinhasEncomendasController {
	
    @FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane;
    @FXML private VBox vboxEncomendas;

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
        List<String> encomendas = obterDadosEncomendas();
        System.out.println("Encomendas carregadas: " + encomendas); // Verificar se há dados
        vboxEncomendas.getChildren().clear();

        for (String encomenda : encomendas) {
            Label label = new Label(encomenda);
            label.setStyle("-fx-font-size: 18px; -fx-padding: 10px;");
            vboxEncomendas.getChildren().add(label);
        }
    }

    private List<String> obterDadosEncomendas() {
        List<String> lista = new ArrayList<>();
        try (Connection conn = DbConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT personalizacao FROM encomendas");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("personalizacao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lista;
    }
    
    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}
