package dao;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Ingrediente;
import model.Produto;
import connection.DbConnection;
import exceptions.IngredienteException;
import logic.IngredienteLogic;

public class IngredienteDAO extends BaseDAO<Ingrediente> {
	IngredienteLogic i = new IngredienteLogic();
	
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
    
    public void create(Ingrediente ingrediente) throws IngredienteException {
    	i.validarCamposIngrediente(ingrediente); 
    	
        String sql = "INSERT INTO " + getTableName() + " (nomeIngrediente, quantidadeEstoque, Produto_idProduto) VALUES (?, ?, ?)";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, ingrediente.getNomeIngrediente());
            ps.setInt(2, ingrediente.getQuantidadeEstoque());
            if (ingrediente.getProduto_idProduto() != null) {
                ps.setInt(3, ingrediente.getProduto_idProduto().getId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
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
    
    public void update(Ingrediente ingrediente) throws IngredienteException {
    	i.validarAtualizarIngrediente(ingrediente);
    	
        if (!idExists(ingrediente.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }
        
        String sql = "UPDATE " + getTableName() + " SET nomeIngrediente = ?, quantidadeEstoque = ?, Produto_idProduto = ? WHERE id = ?";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, ingrediente.getNomeIngrediente());
            ps.setInt(2, ingrediente.getQuantidadeEstoque());
            if (ingrediente.getProduto_idProduto() != null) {
                ps.setInt(3, ingrediente.getProduto_idProduto().getId());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setInt(4, ingrediente.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(int id) {
    	
        if (!idExists(id)) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }
        
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
