package dao;

import connection.DbConnection;
import model.BaseEntity;
import model.RestricoesAlimentares;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T extends BaseEntity> {

    protected abstract String getTableName();
    protected abstract T fromResultSet(ResultSet rs) throws SQLException; // método abstrato para converter resultSet

    // Método auxiliar para verificar se o ID já existe na tabela
    protected boolean idExists(int id) {
        String sql = "SELECT 1 FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Se houver algum resultado, significa que o id existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete (Excluir um registro)
    public void delete(int id) {
        if (!idExists(id)) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }

        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update (Atualizar um registro)
    public void update(T entity, String updateFields) {
        if (!idExists(entity.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }

        String sql = "UPDATE " + getTableName() + " SET " + updateFields + " WHERE id = ?";
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // FindAll (Buscar todos os registros)
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getTableName(); // Pega todos os campos da tabela
        List<T> lista = new ArrayList<>();

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(fromResultSet(rs)); // Chama o método abstrato para converter resultSet no objeto correto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
