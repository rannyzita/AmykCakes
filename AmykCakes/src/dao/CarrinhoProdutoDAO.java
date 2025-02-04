package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import connection.DbConnection;
import model.Carrinho;

public class CarrinhoProdutoDAO {
	
	public void create(Carrinho carrinho) {
        String sql = "INSERT INTO CARRINHO (quantidade, Carrinho_id, Produto_idProduto) VALUES (?, ?, ?)";  
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setDouble(1, carrinho.getValorTotal());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void findById() {
		String sql = "";
	}
	
	public void update() {
		String sql = "";
	}
	
	public void delete() {
		String sql = "";
	}
	
	public void findAll() {
		String sql = "";
	}
}
