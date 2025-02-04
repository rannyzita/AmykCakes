package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DbConnection;
import model.ItemCarrinho;
import model.Pedido;
import model.Personalizacao;
import model.Produto;

public class ItemCarrinhoDAO {
	
	private boolean personalizadoExists(int Personalizacao_id) {
        String sql = "SELECT 1 FROM Personalizacao WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, Personalizacao_id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean pedidoExists(int idPedido) {
        String sql = "SELECT 1 FROM Pedido WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean produtoExists(int idProduto) {
        String sql = "SELECT 1 FROM Produto WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProduto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void create(ItemCarrinho itemCarrinho) {
        if (!pedidoExists(itemCarrinho.getPedido_idPedido().getId())) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        if (!produtoExists(itemCarrinho.getProduto_idProduto().getId())) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        String sql = "INSERT INTO ItemCarrinho (quantidade, Pedido_idPedido, Produto_idProduto, valorUnitario, subTotal, Personalizacao_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemCarrinho.getQuantidade());
            ps.setInt(2, itemCarrinho.getPedido_idPedido().getId()); 
            ps.setInt(3, itemCarrinho.getProduto_idProduto().getId()); 
            ps.setDouble(4, itemCarrinho.getValorUnitario());
            ps.setDouble(5, itemCarrinho.getSubTotal());
            ps.setInt(6, itemCarrinho.getPersonalizacao_id().getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void delete(int idPedido, int idProduto, int Personalizacao_id) {
    	if (!personalizadoExists(Personalizacao_id)) {
    		System.out.println("Erro: Pedido não existe.");
    		return;
    	}
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        if (!produtoExists(idProduto)) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        String sql = "DELETE FROM ItemCarrinho WHERE Pedido_idPedido = ? AND Produto_idProduto = ? AND Personalizacao_id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idProduto);
            ps.setInt(3, Personalizacao_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void update(int idPedido, int idProduto, int quantidade, double valorUnitario, double subTotal, int Personalizacao_id) {
    	if (!personalizadoExists(Personalizacao_id)) {
    		System.out.println("Erro: Pedido não existe.");
    		return;
    	}
    	if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        if (!produtoExists(idProduto)) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        String sql = "UPDATE ItemCarrinho SET quantidade = ?, valorUnitario = ?, subTotal = ? WHERE Pedido_idPedido = ? AND Produto_idProduto = ? AND Personalizacao_id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setDouble(2, valorUnitario); 
            ps.setDouble(3, subTotal); 
            ps.setInt(4, idPedido); 
            ps.setInt(5, idProduto); 
            ps.setInt(6, Personalizacao_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ItemCarrinho findById(int idPedido, int idProduto, int Personalizacao_id) {
        String sql = "SELECT * FROM ItemCarrinho WHERE Pedido_idPedido = ? AND Produto_idProduto = ? AND Personalizacao_id = ?";
        ItemCarrinho itemCarrinho = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idProduto);
            ps.setInt(3, Personalizacao_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    itemCarrinho = new ItemCarrinho();
                    itemCarrinho.setQuantidade(rs.getInt("quantidade"));
                    itemCarrinho.setValorUnitario(rs.getDouble("valorUnitario"));
                    itemCarrinho.setSubTotal(rs.getDouble("subTotal"));
                    
                    // Preenchendo os objetos Pedido e Produto corretamente
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
                itemCarrinho.setQuantidade(rs.getInt("quantidade"));
                itemCarrinho.setValorUnitario(rs.getDouble("valorUnitario"));
                itemCarrinho.setSubTotal(rs.getDouble("subTotal"));

                // Preenchendo os objetos Pedido e Produto corretamente
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
