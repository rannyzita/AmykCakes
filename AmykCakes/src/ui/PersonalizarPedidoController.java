package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class PersonalizarPedidoController {

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
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> System.out.println("Personalizar Pedido clicado!"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
    }

    public void trocarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            
            // Passa a referência do mainPane para o controlador da nova tela
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

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}