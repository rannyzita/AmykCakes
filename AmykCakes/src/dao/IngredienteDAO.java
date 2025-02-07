package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void create(Personalizacao personalizacao) {
        String sql = "INSERT INTO " + getTableName() + " (nome, tipoCobertura, tamanhoPedido, massaPedido, observacoes, Pedido_idPedido, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, personalizacao.getNome());
            ps.setString(2, personalizacao.getTipoCobertura());
            ps.setString(3, personalizacao.getTamanhoPedido());
            ps.setString(4, personalizacao.getMassaPedido());
            ps.setString(5, personalizacao.getObservacoes());

            // Verifica se o Pedido_idPedido é nulo ou se não for zero
            if (personalizacao.getPedido_idPedido() != null) {
                ps.setInt(6, personalizacao.);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);  // Usando setNull para lidar com valores nulos
            }

            ps.setInt(7, personalizacao.getQuantidade());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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