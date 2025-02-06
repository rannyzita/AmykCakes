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
import model.Produto;

public class ItemCarrinhoDAO {

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

    public void create(ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
    	ItemCarrinhoLogic iCarrinho = new ItemCarrinhoLogic();
    	iCarrinho.validarItemCarrinho(itemCarrinho); 
    	
        if (!pedidoExists(itemCarrinho.getPedido_idPedido().getId())) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        if (!produtoExists(itemCarrinho.getProduto_idProduto().getId())) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        String sql = "INSERT INTO ItemCarrinho (quantidade, Pedido_idPedido, Produto_idProduto, valorUnitario, subTotal) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, itemCarrinho.getQuantidade());
            ps.setInt(2, itemCarrinho.getPedido_idPedido().getId()); 
            ps.setInt(3, itemCarrinho.getProduto_idProduto().getId()); 
            ps.setDouble(4, itemCarrinho.getValorUnitario());
            ps.setDouble(5, itemCarrinho.getSubTotal());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void delete(int idPedido, int idProduto) {
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        if (!produtoExists(idProduto)) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        String sql = "DELETE FROM ItemCarrinho WHERE Pedido_idPedido = ? AND Produto_idProduto = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void update(int idPedido, int idProduto, int quantidade, double valorUnitario, double subTotal, ItemCarrinhoLogic iCarrinho, ItemCarrinho itemCarrinho) throws ItemCarrinhoException {
    	iCarrinho.validarItemCarrinho(itemCarrinho); 
    	
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        if (!produtoExists(idProduto)) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        String sql = "UPDATE ItemCarrinho SET quantidade = ?, valorUnitario = ?, subTotal = ? WHERE Pedido_idPedido = ? AND Produto_idProduto = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setDouble(2, valorUnitario); 
            ps.setDouble(3, subTotal); 
            ps.setInt(4, idPedido); 
            ps.setInt(5, idProduto); 

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ItemCarrinho findById(int idPedido, int idProduto) {
        String sql = "SELECT * FROM ItemCarrinho WHERE Pedido_idPedido = ? AND Produto_idProduto = ?";
        ItemCarrinho itemCarrinho = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            ps.setInt(2, idProduto);

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

                itemCarrinhos.add(itemCarrinho);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemCarrinhos;
    }

}
