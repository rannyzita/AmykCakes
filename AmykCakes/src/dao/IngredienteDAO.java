package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Ingrediente;
import model.Personalizacao;
import model.Produto;
import connection.DbConnection;
import exceptions.IngredienteException;
import logic.IngredienteLogic;

public class IngredienteDAO extends BaseDAO<Ingrediente> {
    private IngredienteLogic ingredienteLogic;

    public IngredienteDAO(IngredienteLogic ingredienteLogic) {
        this.ingredienteLogic = ingredienteLogic;
    }

    @Override
    protected String getTableName() {
        return "INGREDIENTE";
    }

    @Override
    protected Ingrediente fromResultSet(ResultSet rs) throws SQLException {
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setId(rs.getInt("id"));
        ingrediente.setNomeIngrediente(rs.getString("nomeIngrediente"));
        ingrediente.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
        int produtoId = rs.getInt("Produto_idProduto");
        if (!rs.wasNull()) {
            Produto produto = new Produto();
            produto.setId(produtoId);
            ingrediente.setProduto_idProduto(produto);
        } else {
            ingrediente.setProduto_idProduto(null);
        }
        return ingrediente;
    }

    public int create(Ingrediente ingrediente, int idProduto) throws IngredienteException {
        String sql = "INSERT INTO " + getTableName() + " (nomeIngrediente, quantidadeEstoque, Produto_idProduto) VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, ingrediente.getNomeIngrediente());
            ps.setInt(2, ingrediente.getQuantidadeEstoque());
            ps.setInt(3, idProduto);

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);  // Retorna o ID gerado, caso precisse em algm dia ai
                    }
                }
            }

            throw new IngredienteException("Erro ao inserir ingrediente: Nenhuma linha foi afetada.");

        } catch (SQLException e) {
            e.printStackTrace();
            throw new IngredienteException("Erro ao inserir ingrediente no banco de dados.");
        }
    }


    public Ingrediente findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        Ingrediente ingrediente = null;

        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ingrediente = fromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingrediente;
    }
}