package ui;

import javafx.fxml.FXML;
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

    private void trocarTela(String fxml) {
        try {
            Pane novaTela = javafx.fxml.FXMLLoader.load(getClass().getResource(fxml));
            mainPane.getChildren().setAll(novaTela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public void setStage(Stage primaryStage) {
		// TODO Auto-generated method stub
		
	}
}
