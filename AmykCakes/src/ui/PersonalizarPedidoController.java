package ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import model.Personalizacao;

import logic.PersonalizacaoLogic;

import dao.ItemCarrinhoDAO;
import dao.PedidoDAO;
import dao.PersonalizacaoDAO;
import exceptions.ItemCarrinhoException;
import exceptions.PedidoException;
import exceptions.PersonalizacaoException;
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
    	// personalizacao
    	// item carrinho 
    	// produto
    	
    	// 1 - personalizacao (ok)
    	
    	// 2 - cria o pedido (ok)
    	
    	// 3 - cria o item carrinho passando as foreign keys 
    	
        // Inicializando DAOs
        ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        PersonalizacaoLogic personalizacaoLogic = new PersonalizacaoLogic();
        PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO(personalizacaoLogic);

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

        // Definição dos preços e tipos
        if (tipoCobertura.matches("chocolate|morango|chantilly|brigadeiro|geleia|foundant|açucar glaceado|açúcar glaceado")) {
            precoCobertura = 20.0;
        } else {
            tipoCobertura = "chocolate"; // Valor padrão
            precoCobertura = 20.0;
        }

        if (tipoMassa.matches("branca|morango|chocolate|cenoura|fubá|nozes|pão de ló|pao de lo")) {
            precoMassa = 30.0;
        } else {
            tipoMassa = "branca"; // Valor padrão
            precoMassa = 30.0;
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
        
        int quantidade_ = Integer.parseInt(quant);
        
     // Cálculo do valor total
        double valorTotal = (precoMassa + precoCobertura + precoTam) * quantidade_;
        
     // Criando a Personalização
        Personalizacao personalizacao = new Personalizacao();
        
        personalizacao.setNome(nome);
        personalizacao.setTipoCobertura(tipoCobertura);
        personalizacao.setMassaPedido(tipoMassa);
        personalizacao.setTamanhoPedido(tam);
        personalizacao.setObservacoes(obs);
        personalizacao.setQuantidade(quantidade_);

        // Salvando a personalização no banco
        
        int idPersonalizacao;
        try {
            idPersonalizacao = personalizacaoDAO.create(personalizacao);
            
            // Só continua se a personalização foi criada com sucesso
            if (idPersonalizacao > 0) {
                Pedido pedido = new Pedido();
                pedido.setValorTotal(valorTotal);
                int idProduto = 0;
                int idPedido = pedidoDAO.create(valorTotal, idProduto, idPersonalizacao);

                // Só continua se o pedido foi criado com sucesso
                if (idPedido > 0) {
                    itemCarrinhoDAO.createPersonalizacao(quantidade_, idPedido, valorTotal, idPersonalizacao);
                    System.out.println("Item salvo no carrinho com sucesso!");
                } else {
                    System.out.println("Erro ao criar o pedido.");
                }
            } else {
                System.out.println("Erro ao criar a personalização.");
            }
        } catch (PersonalizacaoException | PedidoException | ItemCarrinhoException e) {
            e.printStackTrace();
        }

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