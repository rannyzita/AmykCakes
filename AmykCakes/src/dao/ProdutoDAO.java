package dao;

import model.Produto;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import connection.DbConnection;
import exceptions.ProdutoException;
import logic.ProdutoLogic;

public class ProdutoDAO extends BaseDAO<Produto> {
    ProdutoLogic produtoLogic = new ProdutoLogic();

    @Override
    protected String getTableName() {
        return "PRODUTO";
    }

    @Override
    protected Produto fromResultSet(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setFoto(rs.getBytes("foto"));
        produto.setEstoque(rs.getInt("estoque"));
        return produto;
    }

    public List<Integer> getIdForeignKeyPersonalizacao() {
        String sql = "SELECT p.id, p.Pedido_idPedido " +
                     "FROM Produto p " +
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

    public void create(Produto produto, File imagem) throws ProdutoException, IOException {
        produtoLogic.validarCamposProduto(produto);
        
        String sql = "INSERT INTO " + getTableName() + " (nome, descricao, preco, foto, estoque, Pedido_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
             FileInputStream fis = new FileInputStream(imagem)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setBinaryStream(4, fis);
            ps.setInt(5, produto.getEstoque());
            
            //ps.setInt(6, produto.getP());
            
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Produto findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        Produto produto = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = fromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public void update(Produto produto, File imagem) throws ProdutoException, IOException {
        produtoLogic.validarCamposProduto(produto);

        if (!super.idExists(produto.getId())) {
            System.out.println("Erro: Produto com ID " + produto.getId() + " não encontrado.");
            return;
        }

        String sql = "UPDATE " + getTableName() + " SET nome = ?, descricao = ?, preco = ?, foto = ?, estoque = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(imagem)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setBinaryStream(4, fis);
            ps.setInt(5, produto.getEstoque());
            ps.setInt(6, produto.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar produto. Nenhuma linha foi afetada.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
