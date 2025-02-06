package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TelaInicialController {

    @FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane;
    @FXML private ImageView imgBolo1;
    @FXML private ImageView imgBolo2;
    @FXML private ImageView imgBolo3;
    @FXML private ImageView imgBolo4;
    @FXML private ImageView imgBolo5;
    @FXML private ImageView imgBolo6;
    @FXML private Button btnBolo1;
    @FXML private Button btnBolo2;
    @FXML private Button btnBolo3;
    @FXML private Button btnBolo4;
    @FXML private Button btnBolo5;
    @FXML private Button btnBolo6;

    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> System.out.println("Tela Inicial clicada!"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));

        imgBolo1.setOnMouseClicked(event -> onComprarBoloClick());
        imgBolo2.setOnMouseClicked(event -> onComprarBoloClick());
        imgBolo3.setOnMouseClicked(event -> onComprarBoloClick());
        imgBolo4.setOnMouseClicked(event -> onComprarBoloClick());
        imgBolo5.setOnMouseClicked(event -> onComprarBoloClick());
        imgBolo6.setOnMouseClicked(event -> onComprarBoloClick());
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

    @FXML
    public void onComprarBoloClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ComprarSobremesa.fxml"));
            Pane novaTela = loader.load();
            mainPane.getChildren().setAll(novaTela);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}