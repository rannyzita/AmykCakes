package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Produto;
import dao.ProdutoDAO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaInicialController {

    private static final Logger logger = Logger.getLogger(TelaInicialController.class.getName());

    @FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane;
    @FXML private ImageView imgBolo1, imgBolo2, imgBolo3, imgBolo4, imgBolo5, imgBolo6;
    @FXML private Label labelBolo1, labelBolo2, labelBolo3, labelBolo4, labelBolo5, labelBolo6;
    @FXML private Label labelComprar1;
    @FXML private Label labelComprar2;
    @FXML private Label labelComprar3;
    @FXML private Label labelComprar4;
    @FXML private Label labelComprar5;
    @FXML private Label labelComprar6;

    private List<Produto> produtos;

    @FXML
    public void initialize() {
        logger.info("Inicializando a Tela Inicial...");
        carregarProdutos();

        // Configuração de eventos para navegação entre telas
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml", labelTelaInicial, event));
        labelPersonalizarPedido.setOnMouseClicked(event -> trocarTela("PersonalizarPedido.fxml", labelPersonalizarPedido, event));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml", labelMinhasEncomendas, event));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml", labelSobreNos, event));
        
        // Configuração dos eventos para os botões de compra
        labelComprar1.setOnMouseClicked(event -> trocarTela("ComprarSobremesa.fxml", labelComprar1, event));
        labelComprar2.setOnMouseClicked(event -> trocarTela("ComprarSobremesa.fxml", labelComprar2, event));
        labelComprar3.setOnMouseClicked(event -> trocarTela("ComprarSobremesa.fxml", labelComprar3, event));
        labelComprar4.setOnMouseClicked(event -> trocarTela("ComprarSobremesa.fxml", labelComprar4, event));
        labelComprar5.setOnMouseClicked(event -> trocarTela("ComprarSobremesa.fxml", labelComprar5, event));
        labelComprar6.setOnMouseClicked(event -> trocarTela("ComprarSobremesa.fxml", labelComprar6, event));
    }

    private void carregarProdutos() {
        ProdutoDAO produtoDAO = new ProdutoDAO();
        logger.info("Carregando produtos...");
        produtos = produtoDAO.findAll();

        if (produtos == null || produtos.isEmpty()) {
            logger.warning("Nenhum produto encontrado no banco de dados.");
            return;
        }

        ImageView[] imagens = {imgBolo1, imgBolo2, imgBolo3, imgBolo4, imgBolo5, imgBolo6};
        Label[] labels = {labelBolo1, labelBolo2, labelBolo3, labelBolo4, labelBolo5, labelBolo6};

        for (int i = 0; i < produtos.size() && i < 6; i++) {
            Produto produto = produtos.get(i);
            labels[i].setText(produto.getNome());
            byte[] foto = produto.getFoto();
            if (foto != null && foto.length > 0) {
                try {
                    Image image = new Image(new ByteArrayInputStream(foto));
                    imagens[i].setImage(image);
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Erro ao carregar imagem", e);
                }
            }
        }
    }

    private void abrirTelaProdutoSelecionado(int id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ComprarSobremesa.fxml"));
            Parent root = loader.load();
            
            ComprarSobremesaController controller = loader.getController();
            controller.carregarProduto(id);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao abrir a tela de produto selecionado", e);
        }
    }

    public void trocarTela(String fxml, Label labelComprar, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            
            Object controller = loader.getController();
            if (controller instanceof TelaInicialController) {
                ((TelaInicialController) controller).setMainPane(mainPane);
            } else if (controller instanceof MinhasEncomendasController) {
                ((MinhasEncomendasController) controller).setMainPane(mainPane);
            } else if (controller instanceof ComprarSobremesaController) {
                ((ComprarSobremesaController) controller).setMainPane(mainPane);
            }

            // Lógica para selecionar o produto ao clicar nos labels de compra
            if (fxml.equals("ComprarSobremesa.fxml")) {
                Label clickedLabel = (Label) event.getSource();
                int id = 0;

                // Determina o ID com base no label clicado
                if (clickedLabel == labelComprar1) {
                    id = 1;
                } else if (clickedLabel == labelComprar2) {
                    id = 2;
                } else if (clickedLabel == labelComprar3) {
                    id = 3;
                } else if (clickedLabel == labelComprar4) {
                    id = 4;
                } else if (clickedLabel == labelComprar5) {
                    id = 5;
                } else if (clickedLabel == labelComprar6) {
                    id = 6;
                }

                if (id > 0) {
                    abrirTelaProdutoSelecionado(id);
                }
            }

            // Troca de tela
            mainPane.getChildren().setAll(novaTela);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erro ao trocar de tela", e);
        }
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}