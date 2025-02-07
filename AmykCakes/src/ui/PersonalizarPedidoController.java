package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.Personalizacao;

import javafx.util.Pair;

import java.util.List;

import dao.ItemCarrinhoDAO;
import dao.PedidoDAO;
import dao.PersonalizacaoDAO;
import exceptions.ItemCarrinhoException;
import exceptions.PedidoException;
import exceptions.PersonalizacaoException;
import model.ItemCarrinho;
import model.Pedido;

public class PersonalizarPedidoController {

    @FXML private Label labelTelaInicial;
    @FXML private Label labelPersonalizarPedido;
    @FXML private Label labelMinhasEncomendas;
    @FXML private Label labelSobreNos;
    @FXML private Pane mainPane;
    @FXML private TextField nomePedido;
    @FXML private TextArea cobertura;
    @FXML private TextArea massa;
    @FXML private ToggleGroup tamanho;
    @FXML private TextArea observacoes;
    @FXML private Button botaoEncomendar;
    @FXML private TextField quantidade;
    @FXML private Button btnLimpar;

    @FXML
    public void initialize() {
        labelTelaInicial.setOnMouseClicked(event -> trocarTela("TelaInicial.fxml"));
        labelPersonalizarPedido.setOnMouseClicked(event -> System.out.println("Personalizar Pedido clicado!"));
        labelMinhasEncomendas.setOnMouseClicked(event -> trocarTela("MinhasEncomendas.fxml"));
        labelSobreNos.setOnMouseClicked(event -> trocarTela("SobreNos.fxml"));
        
        botaoEncomendar.setOnAction(event -> processarEncomenda());
        btnLimpar.setOnAction(event -> onBtnLimparClick());
    }
    
    public void onencomendarbotao() {
    	botaoEncomendar.setStyle("-fx-background-color: #725d80; -fx-text-fill: white");
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
        // Inicializando DAOs
        ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO();

        // Captura dos dados do formulário
        String nome = nomePedido.getText().trim().toLowerCase();
        String tipoCobertura = cobertura.getText().trim().toLowerCase();
        String tipoMassa = massa.getText().trim().toLowerCase();
        String obs = observacoes.getText().trim().toLowerCase();
        String quant = quantidade.getText().trim().toLowerCase();
        double precoMassa = 0;
        double precoCobertura = 0;
        double precoTam = 0;

        // Validação de campos obrigatórios
        if (nome.isEmpty() || tipoCobertura.isEmpty() || tipoMassa.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Preencha todos os campos obrigatórios!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        // Criando personalização
        Personalizacao personalizacao = new Personalizacao();
        personalizacao.setNome(nome);

        // Definição dos preços e tipos
        if (tipoCobertura.matches("chocolate|morango|chantilly|brigadeiro|geleia|foundant|açucar glaceado|açúcar glaceado")) {
            precoCobertura = 20.0;
            personalizacao.setTipoCobertura(tipoCobertura);
        } else {
            personalizacao.setTipoCobertura("chocolate"); // Valor padrão
        }

        if (tipoMassa.matches("branca|morango|chocolate|cenoura|fubá|nozes|pão de ló|pao de lo")) {
            precoMassa = 30.0;
            personalizacao.setMassaPedido(tipoMassa);
        } else {
            personalizacao.setMassaPedido("branca"); // Valor padrão
        }
        
        // Obtendo tamanho
        RadioButton selecionado = (RadioButton) tamanho.getSelectedToggle();
        String tam = (selecionado != null) ? selecionado.getText().trim().toLowerCase() : "";

        if (tam.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione um tamanho!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        switch (tam) {
            case "p": precoTam = 30.0; break;
            case "m": precoTam = 40.0; break;
            case "g": precoTam = 50.0; break;
            default: precoTam = 30.0;
        }
        personalizacao.setTamanhoPedido(tam);

        // Conversão da quantidade
        int quantidade_ = Integer.parseInt(quant);
        personalizacao.setQuantidade(quantidade_);
        if (!obs.isEmpty()) {
            personalizacao.setObservacoes(obs);
        }

        // Cálculo do valor total
        double valorTotal = (precoMassa + precoCobertura + precoTam) * quantidade_;

        // Criando pedido
        Pedido pedido = new Pedido();
        pedido.setValorTotal(valorTotal);
        try {
			pedidoDAO.create(pedido);
		} catch (PedidoException e) {
			e.printStackTrace();
		}

     // Obtendo IDs de Personalização e Pedido a partir do ItemCarrinhoDAO
        List<Integer> personalizacaoData = itemCarrinhoDAO.getIdForeignKeyPersonalizacao();
        if (personalizacaoData == null || personalizacaoData.size() < 2) {
            System.out.println("Erro ao recuperar a Personalização.");
            return;
        }

        int personalizacaoId = personalizacaoData.get(0); // ID de Personalização
        int pedidoId = personalizacaoData.get(1); // ID de Pedido

        personalizacao.setPedido_idPedido(pedidoId);
        

        personalizacao.setPedido_idPedido(pedidoId);
        try {
			personalizacaoDAO.create(personalizacao);
		} catch (PersonalizacaoException e) {
			e.printStackTrace();
		}

        // Criando item do carrinho
        ItemCarrinho itemCarrinho = new ItemCarrinho();
        itemCarrinho.setPedido_idPedido(pedidoId);
        itemCarrinho.setPersonalizacao_id(personalizacaoId);
        itemCarrinho.setQuantidade(quantidade_);
        itemCarrinho.setValorUnitario(valorTotal);

        // Salvando no banco
        try {
            itemCarrinhoDAO.create(itemCarrinho);
            System.out.println("Item salvo no carrinho com sucesso! ID da Personalização: " + personalizacaoId + ", ID do Pedido: " + pedidoId);
        } catch (ItemCarrinhoException e) {
            e.printStackTrace();
            return;
        }

        // Limpando o formulário
        onBtnLimparClick();
    }

    
    public void onBtnLimparClick() {
        if (nomePedido != null) nomePedido.clear();
        if (cobertura != null) cobertura.clear();
        if (massa != null) massa.clear();
        if (observacoes != null) observacoes.clear();
        if (quantidade != null) quantidade.clear();
    }

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}