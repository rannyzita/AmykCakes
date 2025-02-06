package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DbConnection;
import exceptions.ItemCarrinhoException;
import logic.ItemCarrinhoLogic;
import model.ItemCarrinho;
import model.Pedido;
import model.Personalizacao;
import model.Produto;

public class ItemCarrinhoDAO {
    private boolean existsById(int id) {
        String sql = "SELECT 1 FROM ItemCarrinho WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void getCarrinhoPersonalizacao(String field) {
  
        // Query com INNER JOIN entre ItemCarrinho e Personalizacao
        String sql = "SELECT ic.id, ic.Pedido_idPedido, ic.quantidade, ic.valorUnitario, ic.Personalizacao_id " +
                     "FROM ItemCarrinho ic " +
                     "INNER JOIN Personalizacao p ON ic.Personalizacao_id = p.id";

        try (Connection conn = DbConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Obtendo os valores de todos os atributos de ItemCarrinho
                int id = rs.getInt("id");
                int pedidoId = rs.getInt("Pedido_idPedido");
                int quantidade = rs.getInt("quantidade");
                double valorUnitario = rs.getDouble("valorUnitario");
                int personalizacaoId = rs.getInt("Personalizacao_id");

                // Exibindo os valores (pode ser adaptado para armazenar em um objeto e retornar)
                System.out.println("ID: " + id);
                System.out.println("Pedido ID: " + pedidoId);
                System.out.println("Quantidade: " + quantidade);
                System.out.println("Valor Unitário: " + valorUnitario);
                System.out.println("Personalização ID: " + personalizacaoId);
                System.out.println("----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    
    public void create(ItemCarrinho itemCarrinho) {
    	
    	if (itemCarrinho.getPedido_idPedido() == null || itemCarrinho.getPedido_idPedido().getId() == 0) {
    	    throw new IllegalArgumentException("Pedido_idPedido não pode ser nulo ou zero.");
    	}
=======
    public void create(ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
    	ItemCarrinhoLogic iCarrinho = new ItemCarrinhoLogic();
    	iCarrinho.validarItemCarrinho(itemCarrinho); 
    	
        if (!pedidoExists(itemCarrinho.getPedido_idPedido().getId())) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }
>>>>>>> branch 'master' of https://github.com/rannyzita/AmykCakes

    	if (itemCarrinho.getPersonalizacao_id() == null) {
    	    System.out.println("Aviso: Nenhuma personalização associada. Continuando com Personalizacao_id como NULL.");
    	}

    	if (itemCarrinho.getProduto_idProduto() == null) {
    	    System.out.println("Aviso: Nenhum produto associado. Continuando com Produto_id como NULL.");
    	}



        String sql = "INSERT INTO ItemCarrinho (quantidade, Pedido_idPedido, Produto_idProduto, valorUnitario, Personalizacao_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemCarrinho.getQuantidade());
            ps.setInt(2, itemCarrinho.getPedido_idPedido().getId());

            ps.setDouble(4, itemCarrinho.getValorUnitario());

            // Verifica se existe personalização antes de setar
            if (itemCarrinho.getPersonalizacao_id() != null ) {
                ps.setInt(5, itemCarrinho.getPersonalizacao_id().getId());
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            
            if (itemCarrinho.getProduto_idProduto() != null ) {
                ps.setInt(3, itemCarrinho.getProduto_idProduto().getId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();

            // Buscar o último ID inserido
            String getLastIdSQL = "SELECT MAX(id) FROM ItemCarrinho";
            try (PreparedStatement psLastId = connection.prepareStatement(getLastIdSQL);
                 ResultSet rs = psLastId.executeQuery()) {
                if (rs.next()) {
                    itemCarrinho.setId(rs.getInt(1)); // Define o ID gerado no objeto
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void delete(int id) {
        if (!existsById(id)) {
            System.out.println("Erro: ItemCarrinho não existe.");
            return;
        }

        String sql = "DELETE FROM ItemCarrinho WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    public void update(ItemCarrinho itemCarrinho) {
        if (!existsById(itemCarrinho.getId())) {
            System.out.println("Erro: ItemCarrinho não existe.");
=======
    
    public void update(int idPedido, int idProduto, int quantidade, double valorUnitario, double subTotal, ItemCarrinhoLogic iCarrinho, ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
    	iCarrinho.validarItemCarrinho(itemCarrinho); 
    	
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
>>>>>>> branch 'master' of https://github.com/rannyzita/AmykCakes
            return;
        }

        String sql = "UPDATE ItemCarrinho SET quantidade = ?, valorUnitario = ?, subTotal = ?, Pedido_idPedido = ?, Produto_idProduto = ?, Personalizacao_id = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, itemCarrinho.getQuantidade());
            ps.setDouble(2, itemCarrinho.getValorUnitario());
            ps.setInt(4, itemCarrinho.getPedido_idPedido().getId());
            ps.setInt(5, itemCarrinho.getProduto_idProduto().getId());
            ps.setInt(6, itemCarrinho.getPersonalizacao_id().getId());
            ps.setInt(7, itemCarrinho.getId());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ItemCarrinho findById(int id) {
        String sql = "SELECT * FROM ItemCarrinho WHERE id = ?";
        ItemCarrinho itemCarrinho = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    itemCarrinho = new ItemCarrinho();
                    itemCarrinho.setId(rs.getInt("id"));
                    itemCarrinho.setQuantidade(rs.getInt("quantidade"));
                    itemCarrinho.setValorUnitario(rs.getDouble("valorUnitario"));
     
                    
                    Pedido pedido = new Pedido();
                    pedido.setId(rs.getInt("Pedido_idPedido"));
                    itemCarrinho.setPedido_idPedido(pedido);
                    
                    Produto produto = new Produto();
                    produto.setId(rs.getInt("Produto_idProduto"));
                    itemCarrinho.setProduto_idProduto(produto);
                    
                    Personalizacao personalizacao = new Personalizacao();
                    personalizacao.setId(rs.getInt("Personalizacao_id"));
                    itemCarrinho.setPersonalizacao_id(personalizacao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemCarrinho;
    }

    public List<ItemCarrinho> findAll() {
        String sql = "SELECT * FROM ItemCarrinho";
        List<ItemCarrinho> itemCarrinhos = new ArrayList<>();

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ItemCarrinho itemCarrinho = new ItemCarrinho();
                itemCarrinho.setId(rs.getInt("id"));
                itemCarrinho.setQuantidade(rs.getInt("quantidade"));
                itemCarrinho.setValorUnitario(rs.getDouble("valorUnitario"));
                
                Pedido pedido = new Pedido();
                pedido.setId(rs.getInt("Pedido_idPedido"));
                itemCarrinho.setPedido_idPedido(pedido);
                
                Produto produto = new Produto();
                produto.setId(rs.getInt("Produto_idProduto"));
                itemCarrinho.setProduto_idProduto(produto);
                
                Personalizacao personalizacao = new Personalizacao();
                personalizacao.setId(rs.getInt("Personalizacao_id"));
                itemCarrinho.setPersonalizacao_id(personalizacao);

                itemCarrinhos.add(itemCarrinho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemCarrinhos;
    }
}

