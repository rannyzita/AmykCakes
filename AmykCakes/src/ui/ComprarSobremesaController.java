package ui;

import java.util.List;

import dao.ItemCarrinhoDAO;
import dao.PedidoDAO;
import dao.PersonalizacaoDAO;
import dao.ProdutoDAO;
import exceptions.ItemCarrinhoException;
import exceptions.PedidoException;
import exceptions.PersonalizacaoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import model.ItemCarrinho;
import model.Pedido;
import model.Personalizacao;
import model.Produto;

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
    
    private void processarEncomenda() {
        // Inicializando DAOs
        ItemCarrinhoDAO itemCarrinhoDAO = new ItemCarrinhoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();
        
        Produto produto = new Produto();
       
        // Obtendo tamanho
        RadioButton selecionado = (RadioButton) tamanho.getSelectedToggle();
        String tam = (selecionado != null) ? selecionado.getText().trim().toLowerCase() : "";

        
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

    public void setMainPane(Pane mainPane) {
        this.mainPane = mainPane;
    }
}