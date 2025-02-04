package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.Personalizacao;
import dao.ItemCarrinhoDAO;
import dao.PedidoDAO;
import dao.PersonalizacaoDAO;
import model.ItemCarrinho;
import model.Pedido;

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
    private Pane mainPane;
    @FXML
    private TextField nomePedido;
    @FXML
    private TextArea cobertura;
    @FXML
    private TextArea massa;
    @FXML
    private ToggleGroup tamanho;
    @FXML
    private TextArea observacoes;
    @FXML
    private Button botaoEncomendar;
    @FXML
    private TextField quantidade;

    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> System.out.println("Personalizar Pedido clicado!"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
        
        botaoEncomendar.setOnAction(event -> processarEncomenda());
    }

    public void trocarTela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Pane novaTela = loader.load();
            
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

    private void processarEncomenda() {
        String nome = nomePedido.getText().trim().toLowerCase();
        String tipoCobertura = cobertura.getText().trim().toLowerCase();
        String tipoMassa = massa.getText().trim().toLowerCase();
        String obs = observacoes.getText().trim().toLowerCase();
        String quant = quantidade.getText().trim().toLowerCase();

        double precoMassa = 0;
        double precoCobertura = 0;
        double precoTam = 0;

        if (nome.isEmpty() || tipoCobertura.isEmpty() || tipoMassa.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos obrigatórios!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Personalizacao personalizacao = new Personalizacao();
        personalizacao.setNome(nome);

        if (tipoCobertura.matches("chocolate|chantilly|brigadeiro|geleia|foundant|açucar glaceado|açúcar glaceado")) {
            precoCobertura = 20.0;
            personalizacao.setTipoCobertura(tipoCobertura);
        } else {
            personalizacao.setTipoCobertura("chocolate"); // Valor padrão
        }

        if (tipoMassa.matches("branca|chocolate|cenoura|fubá|nozes|pão de ló|pao de lo")) {
            precoMassa = 30.0;
            personalizacao.setMassaPedido(tipoMassa);
        } else {
            personalizacao.setMassaPedido("branca"); // Valor padrão
        }

        RadioButton selecionado = (RadioButton) tamanho.getSelectedToggle();
        String tam = (selecionado != null) ? selecionado.getText().trim().toLowerCase() : "";

        if (tam.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um tamanho!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        switch (tam) {
            case "p":
                precoTam = 30.0;
                break;
            case "m":
                precoTam = 40.0;
                break;
            case "g":
                precoTam = 50.0;
                break;
            default:
                precoTam = 30.0; // Valor padrão
        }
        personalizacao.setTamanhoPedido(tam);

        if (!obs.isEmpty()) {
            personalizacao.setObservacoes(obs);
        }

        double valorTotal = precoMassa + precoCobertura + precoTam;
        double valorUnitario = precoMassa + precoCobertura + precoTam;

        Pedido pedido = new Pedido();
        pedido.setValorTotal(valorTotal);

        PedidoDAO pedidoDAO = new PedidoDAO();
        pedidoDAO.create(pedido); // Salva o pedido primeiro
        
        personalizacao.setPedido_idPedido(pedido); // Associa o pedido à personalização

        PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO();
        personalizacaoDAO.create(personalizacao); // Agora salva a personalização
        
        ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
        ItemCarrinho itemCarrinho = new ItemCarrinho();

        int quantInt = Integer.parseInt(quant);
        itemCarrinho.setQuantidade(quantInt);

        itemCarrinho.setValorUnitario(valorUnitario);

        // Chamando corretamente o método create()
        itemCarrinhoDAO.create(itemCarrinho);

        System.out.println("Item do carrinho salvo com sucesso!");
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}