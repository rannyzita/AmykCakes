package dao;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.Carrinho;
import connection.DbConnection;
import logic.CarrinhoLogic;

public class CarrinhoDAO extends BaseDAO<Carrinho> {
	@Override
	protected String getTableName() {
		return "CARRINHO";
	}

	@Override
	protected Carrinho fromResultSet(ResultSet rs) throws SQLException {
		Carrinho carrinho = new Carrinho();
        carrinho.setId(rs.getInt("id"));
        carrinho.setValorTotal(rs.getDouble("valorTotal"));
        return carrinho;
	}
	
    public void create(Carrinho carrinho) {
    	CarrinhoLogic c = new CarrinhoLogic();
    	c.validarCarrinho(carrinho);   
      
    	String sql = "INSERT INTO CARRINHO (valorTotal) VALUES (?)";  
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setDouble(1, carrinho.getValorTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Carrinho findById(int id) {
    	if (!idExists(id)) {
            System.out.println("Erro: O ID não existe na tabela.");
            return null;
        }
    	
        String sql = "SELECT id, valorTotal FROM CARRINHO WHERE id = ?";
        Carrinho carrinho = null;

        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id); 
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	carrinho = fromResultSet(rs); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return carrinho;
    }

    public void update(Carrinho carrinho, CarrinhoLogic c) {
        // Verificando se o carrinho existe
    	// so que sem o id, usando a classe como arg
    	c.validarCarrinho(carrinho);   
    	
        Carrinho carrinhoExistente = findById(carrinho.getId());
        
        if (carrinhoExistente != null) {
            String sql = "UPDATE CARRINHO SET valorTotal = ? WHERE id = ?";
            
            try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
                ps.setDouble(1, carrinho.getValorTotal());
                ps.setInt(2, carrinho.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Carrinho com id " + carrinho.getId() + " não encontrado. Atualização não realizada.");
        }
    }

}
