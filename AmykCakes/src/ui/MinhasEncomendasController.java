package ui;

import dao.PersonalizacaoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Personalizacao;

import java.io.IOException;
import java.util.List;

public class MinhasEncomendasController {
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
    private TableView<Personalizacao> tableEncomendas;
    @FXML
    private TableColumn<Personalizacao, Double> colValorUnitario;
    @FXML
    private TableColumn<Personalizacao, Double> colSubTotal;

    @FXML
    public void initialize() {
        configurarTabela();
        carregarEncomendas();
        
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
    }

    private void configurarTabela() {
        colValorUnitario.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colSubTotal.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
    }

    private void carregarEncomendas() {
        PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO();
        List<Personalizacao> lista = personalizacaoDAO.findAll();
        
        // Debug: Verificar se a lista tem itens
        System.out.println("Itens recuperados do banco: " + lista.size());
        for (Personalizacao p : lista) {
            System.out.println("Item: " + p);
        }

        ObservableList<Personalizacao> encomendas = FXCollections.observableArrayList(lista);
        tableEncomendas.setItems(encomendas);
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

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }

    public void setStage(Stage primaryStage) {
        // Implementação futura caso necessário
    }
}
