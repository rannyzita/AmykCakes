package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class TelaInicialController {

    @FXML
    private Label labelTelaInicial;
    @FXML
    private Label labelPersonalizarPedido;
    @FXML
    private Label labelMinhasEncomendas;
    @FXML
    private Label labelSobreNos;
    @FXML
    private Pane mainPane; // O local onde as telas serão trocadas

    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> System.out.println("Tela Inicial clicada!"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
    }

    public void trocarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            
            // Passa a referência do mainPane para o controlador da nova tela
            Object controller = loader.getController();
            if (controller instanceof MinhasEncomendasController) {
                ((MinhasEncomendasController) controller).setMainPane(mainPane);
            } else if (controller instanceof TelaInicialController) {
                ((TelaInicialController) controller).setMainPane(mainPane);
            }
            
            mainPane.getChildren().setAll(novaTela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public void setStage(Stage primaryStage) {
        // TODO Auto-generated method stub
    }
}