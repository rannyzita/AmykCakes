package dao;

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
import model.Equipe;

public class EquipeDAO extends BaseDAO<Equipe> {
    
	@Override
	protected String getTableName() {
		return "EQUIPE";
	}
	
	@Override
	protected Equipe fromResultSet(ResultSet rs) throws SQLException {
	    Equipe equipe = new Equipe();
	    equipe.setId(rs.getInt("id"));
	    equipe.setNome(rs.getString("nome"));
	    equipe.setDescricao(rs.getString("descricao"));
	    equipe.setCargo(rs.getString("cargo"));
	    
	    // Pegando a foto do banco de dados
	    byte[] fotoBytes = rs.getBytes("foto");
	    if (fotoBytes != null) {
	        equipe.setFoto(fotoBytes);
	    }
	    return equipe;
	}
    public void create(Equipe equipe, File imagem) {
        String sql = "INSERT INTO EQUIPE (nome, descricao, foto, cargo) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DbConnection.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(imagem)) {
            
            ps.setString(1, equipe.getNome());
            ps.setString(2, equipe.getDescricao());
            ps.setBinaryStream(3, fis);
            ps.setString(4, equipe.getCargo());
            
            ps.executeUpdate();
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public Equipe findById(int id) {
        String sql = "SELECT id, nome, descricao, foto, cargo FROM EQUIPE WHERE id = ?";
        Equipe equipe = null;
        
        try (Connection conn = DbConnection.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    equipe = new Equipe();
                    equipe.setId(rs.getInt("id"));
                    equipe.setNome(rs.getString("nome"));
                    equipe.setDescricao(rs.getString("descricao"));
                    equipe.setFoto(rs.getBytes("foto")); 
                    equipe.setCargo(rs.getString("cargo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return equipe;
    }
    
    public void update(Equipe equipe, File imagem) {
    	if (!idExists(equipe.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }
    	
        String sql = "UPDATE EQUIPE SET nome = ?, descricao = ?, foto = ?, cargo = ? WHERE id = ?";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql);
             FileInputStream fis = new FileInputStream(imagem)) {

            ps.setString(1, equipe.getNome());
            ps.setString(2, equipe.getDescricao());
            ps.setBinaryStream(3, fis);
            ps.setString(4, equipe.getCargo());
            ps.setInt(5, equipe.getId());

            int linhasAfetadas = ps.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Equipe atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma equipe foi encontrada com esse ID.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    
    public List<Equipe> findAll() {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT * FROM Equipe";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Equipe equipe = new Equipe(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getBytes("foto"),
                    rs.getString("cargo")
                );
                equipes.add(equipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return equipes;
    }
    
}
