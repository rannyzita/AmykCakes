package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class ComprarSobremesaController {
	
	@FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane; // O local onde as telas serão trocadas
    @FXML private Label txtNome;
    @FXML private Label txtCobertura;
    @FXML private Label txtDescricao;
    @FXML private Label txtEstoque;
    @FXML private RadioButton txtPagamento;
    @FXML private Label txtPreco;
    @FXML private Label txtNomeH1;
    
    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
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
}