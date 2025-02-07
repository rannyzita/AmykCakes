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
    private ItemCarrinhoLogic itemCarrinhoLogic;
    private ItemCarrinho itemCarrinho = new ItemCarrinho();

    public ItemCarrinhoDAO() {
        this.itemCarrinhoLogic = new ItemCarrinhoLogic(this);
    }
    
    public List<Integer> getIdForeignKeyPersonalizacao() {
        String sql = "SELECT p.id AS idPedido, p.idPersonalizacao " +
                     "FROM Pedido p " +
                     "INNER JOIN ItemCarrinho ic ON ic.Pedido_idPedido = p.id " +
                     "ORDER BY p.id DESC LIMIT 1"; // Pega o último inserido

        List<Integer> ids = new ArrayList<>();

        try (Connection conn = DbConnection.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                ids.add(rs.getInt("idPedido"));  // ID do Pedido
                ids.add(rs.getInt("idPersonalizacao")); // ID da Personalização
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ids; // Retorna a lista com os IDs encontrados
    }
    
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

    public void create(int quantidade, int Pedido_idPedido,
                       double valorUnitario) throws ItemCarrinhoException {
        itemCarrinhoLogic.validarCamposItemCarrinho(quantidade, valorUnitario);

        String sql = "INSERT INTO ItemCarrinho (quantidade, Pedido_idPedido, Produto_idProduto, valorUnitario, Personalizacao_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidade);
            ps.setInt(2, Pedido_idPedido);
            ps.setDouble(4, valorUnitario);

            ps.executeUpdate();

            // Buscar o último ID inserido
            String getLastIdSQL = "SELECT MAX(id) FROM ItemCarrinho";
            try (PreparedStatement psLastId = connection.prepareStatement(getLastIdSQL);
                 ResultSet rs = psLastId.executeQuery()) {
                if (rs.next()) {
                    itemCarrinho.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int idPedido, int idProduto, int idPersonalizacao, int quantidade, double valorUnitario) throws ItemCarrinhoException {
        itemCarrinhoLogic.validarAtualizarItemCarrinho(idPedido, idProduto, idPersonalizacao, quantidade, valorUnitario);

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
}
