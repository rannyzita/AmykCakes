package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaInicialController {

	//Declação das funções do menu
	
    @FXML
    private Label labelTelaInicial;
    @FXML
    private Label labelPersonalizarPedido;
    @FXML
    private Label labelMinhasEncomendas;
    @FXML
    private Label labelSobreNos;
    @FXML
    private Pane mainPane; // local onde as telas serão trocadas
    
    //Declaração das imagens de bolos da tela 
    @FXML 
    protected ImageView imgBolo1;
    @FXML 
    protected ImageView imgBolo2;
    @FXML 
    protected ImageView imgBolo3;
    @FXML 
    protected ImageView imgBolo4;
    @FXML 
    protected ImageView imgBolo5;
    @FXML 
    protected ImageView imgBolo6;
    
    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> System.out.println("Tela Inicial clicada!"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
        
        //Adiciona eventos de clique para cada bolo
        imgBolo1.setOnMouseClicked(event -> onComprarBoloClick("Bolo de Chocolate"));
        imgBolo2.setOnMouseClicked(event -> onComprarBoloClick("Bolo de Morango"));
        imgBolo3.setOnMouseClicked(event -> onComprarBoloClick("Bolo de Cenoura"));
        imgBolo4.setOnMouseClicked(event -> onComprarBoloClick("Bolo de Limão"));
        imgBolo5.setOnMouseClicked(event -> onComprarBoloClick("Bolo de Nozes"));
        imgBolo6.setOnMouseClicked(event -> onComprarBoloClick("Bolo de Maracujá"));
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
    
    @FXML
    public void onComprarBoloClick(String nomeBolo) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AmykCakes/src/ui/ComprarSobremesa.fxml"));
            Parent root = loader.load();
            
            ComprarSobremesaController compraSobremesaController = loader.getController();
            //(SETAR O NOME) compraSobremesaController.setProduto(nomeBolo);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
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