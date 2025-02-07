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
    ItemCarrinhoLogic iCarrinho = new ItemCarrinhoLogic();
    ItemCarrinho itemCarrinho = new ItemCarrinho();

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
    
    public List<Integer> getIdForeignKeyPersonalizacao() {
        String sql = "SELECT p.id, p.Pedido_idPedido " +
                     "FROM Personalizacao p " +
                     "INNER JOIN Pedido ped ON p.Pedido_idPedido = ped.id " +
                     "ORDER BY p.id DESC LIMIT 1"; // Pega o último inserido

        List<Integer> ids = new ArrayList<>();

        try (Connection conn = DbConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                ids.add(rs.getInt("id")); // ID de Personalizacao
                ids.add(rs.getInt("Pedido_idPedido")); // ID de Pedido
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids; // Retorna a lista com os IDs
    }

    public void create(int quantidade, int Pedido_idPedido, int Produto_idProduto,
    		double valorUnitario, int Personalizacao_id) throws ItemCarrinhoException {
        iCarrinho.validarCamposItemCarrinho(quantidade, valorUnitario); 

        if (Personalizacao_id == 0) {
            System.out.println("Aviso: Nenhuma personalização associada. Continuando com Personalizacao_id como NULL.");
        }

        if (Produto_idProduto == 0) {
            System.out.println("Aviso: Nenhum produto associado. Continuando com Produto_id como NULL.");
        }

        String sql = "INSERT INTO ItemCarrinho (quantidade, Pedido_idPedido, Produto_idProduto, valorUnitario, Personalizacao_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, Pedido_idPedido);
            ps.setDouble(4, valorUnitario);

            // Verifica se existe personalização antes de setar
            if (Personalizacao_id != 0) {
                ps.setInt(5, Personalizacao_id);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            
            if (Produto_idProduto != 0) {
                ps.setInt(3, Produto_idProduto);
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

    public void update(int idPedido, int idProduto, int idPersonalizacao, int quantidade, double valorUnitario) throws ItemCarrinhoException {
        iCarrinho.validarAtualizarItemCarrinho(idPedido, idProduto, idPersonalizacao, quantidade, valorUnitario);

        if (idProduto == 0) {
            System.out.println("Atenção, está atualizando o pedido de personalização.");
        } else if (idPersonalizacao == 0) {
            System.out.println("Atenção, está atualizando o pedido do produto pronto.");
        }

        String sql = "UPDATE ItemCarrinho SET quantidade = ?, valorUnitario = ?, Pedido_idPedido = ?, Produto_idProduto = ?, Personalizacao_id = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setDouble(2, valorUnitario);
            ps.setInt(3, idPedido);
            ps.setInt(4, idProduto);
            ps.setInt(5, idPersonalizacao);
            ps.setInt(6, idPedido); // Ajuste no ID

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
