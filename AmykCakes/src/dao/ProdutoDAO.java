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

public class ProdutoDAO extends BaseDAO<Produto> {

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

    public void create(String nome, String descricao, File imagem, int pedido_id,
                       double preco, int estoque) throws ProdutoException, IOException {
        
        // A validação é feita antes de chamar o método do DAO
        if (nome == null || nome.trim().isEmpty()) {
            throw new ProdutoException("O nome do produto é obrigatório.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new ProdutoException("A descrição do produto é obrigatória.");
        }
        if (preco <= 0) {
            throw new ProdutoException("O preço do produto não pode ser negativo, nem igual a 0.");
        }
        if (estoque < 0) {
            throw new ProdutoException("O estoque não pode ser menor que zero.");
        }

        String sql = "INSERT INTO " + getTableName() + " (nome, descricao, preco, foto, estoque, Pedido_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
             FileInputStream fis = (imagem != null) ? new FileInputStream(imagem) : null) {
        
            ps.setString(1, nome);
            ps.setString(2, descricao);
            ps.setDouble(3, preco);
        
            if (fis != null) {
                ps.setBinaryStream(4, fis, (int) imagem.length());
            } else {
                ps.setNull(4, java.sql.Types.BLOB);
            }
        
            ps.setInt(5, estoque);
            ps.setInt(6, pedido_id);
        
            ps.executeUpdate();
        
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int produtoId = rs.getInt(1);
                    System.out.println("Produto criado com sucesso! ID: " + produtoId);
                }
            }
        
        } catch (SQLException e) {
            System.err.println("Erro ao inserir produto no banco: " + e.getMessage());
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

    public void update(String nome, String descricao, File imagem, int pedido_id,
                       double preco, int estoque, int produtoId) throws ProdutoException, IOException {
        
        // Validação feita antes da atualização
        if (nome == null || nome.trim().isEmpty()) {
            throw new ProdutoException("O nome do produto é obrigatório.");
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new ProdutoException("A descrição do produto é obrigatória.");
        }
        if (preco <= 0) {
            throw new ProdutoException("O preço do produto não pode ser negativo, nem igual a 0.");
        }
        if (estoque < 0) {
            throw new ProdutoException("O estoque não pode ser menor que zero.");
        }

        if (!super.idExists(produtoId)) {
            System.out.println("Erro: Produto com ID " + produtoId + " não encontrado.");
            return;
        }

        String sql = "UPDATE " + getTableName() + " SET nome = ?, descricao = ?, preco = ?, foto = ?, estoque = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(imagem)) {

            ps.setString(1, nome);
            ps.setString(2, descricao);
            ps.setDouble(3, preco);
            ps.setBinaryStream(4, fis);
            ps.setInt(5, estoque);
            ps.setInt(6, produtoId);

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
