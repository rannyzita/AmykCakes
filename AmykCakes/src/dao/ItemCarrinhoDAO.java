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

    public void create(ItemCarrinho itemCarrinho) {
        if (itemCarrinho == null) {
            System.out.println("Erro: O item do carrinho está nulo.");
            return;
        }

        if (itemCarrinho.getPedido_idPedido() == null || itemCarrinho.getPedido_idPedido().getId() == 0) {
            System.out.println("Erro: Pedido não informado ou inválido.");
            return;
        }

        if (itemCarrinho.getProduto_idProduto() == null || itemCarrinho.getProduto_idProduto().getId() == 0) {
            System.out.println("Erro: Produto não informado ou inválido.");
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

            // Verifica se existe personalização antes de setar
            if (itemCarrinho.getPersonalizacao_id() != null) {
                ps.setInt(6, itemCarrinho.getPersonalizacao_id().getId());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
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

    public void update(ItemCarrinho itemCarrinho) {
        if (!existsById(itemCarrinho.getId())) {
            System.out.println("Erro: ItemCarrinho não existe.");
            return;
        }

        String sql = "UPDATE ItemCarrinho SET quantidade = ?, valorUnitario = ?, subTotal = ?, Pedido_idPedido = ?, Produto_idProduto = ?, Personalizacao_id = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            ps.setInt(1, itemCarrinho.getQuantidade());
            ps.setDouble(2, itemCarrinho.getValorUnitario());
            ps.setDouble(3, itemCarrinho.getSubTotal());
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
                    itemCarrinho.setSubTotal(rs.getDouble("subTotal"));
                    
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
                itemCarrinho.setSubTotal(rs.getDouble("subTotal"));
                
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

