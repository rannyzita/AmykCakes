package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Personalizacao;
import model.Pedido;
import connection.DbConnection;
import exceptions.PersonalizacaoException;
import logic.PersonalizacaoLogic;

public class PersonalizacaoDAO extends BaseDAO<Personalizacao> {
    PersonalizacaoLogic personalizarLogic = new PersonalizacaoLogic();
    Personalizacao personalizacao = new Personalizacao();
    
    @Override
    protected String getTableName() {
        return "PERSONALIZACAO";
    }
    
    @Override
    protected Personalizacao fromResultSet(ResultSet rs) throws SQLException {
        
        personalizacao.setId(rs.getInt("id"));
        personalizacao.setNome(rs.getString("nome"));
        personalizacao.setTipoCobertura(rs.getString("tipoCobertura"));
        personalizacao.setTamanhoPedido(rs.getString("tamanhoPedido"));
        personalizacao.setMassaPedido(rs.getString("massaPedido"));
        personalizacao.setObservacoes(rs.getString("observacoes"));
        
        int pedidoId = rs.getInt("Pedido_idPedido");
        if (!rs.wasNull()) {
            Pedido pedido = new Pedido();
            pedido.setId(pedidoId);
            personalizacao.setPedido_idPedido(pedido);
        } else {
            personalizacao.setPedido_idPedido(null);
        }
        
        return personalizacao;
    }
    
    public void create(String nome, String tipoCobertura, String tamanhoPedido, String massaPedido,
    		String observacoes, int Pedido_idPedido, int quantidade) throws PersonalizacaoException {
    	
        personalizarLogic.validarCamposPersonalizacao(nome, tipoCobertura, tamanhoPedido, quantidade);
        
        String sql = "INSERT INTO " + getTableName() + " (nome, tipoCobertura, tamanhoPedido, massaPedido, observacoes, Pedido_idPedido, quantidade) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, tipoCobertura);
            ps.setString(3, tamanhoPedido);
            ps.setString(4, massaPedido);
            ps.setString(5, observacoes);
            ps.setInt(7, quantidade);
            
            if (Pedido_idPedido != 0) {
                ps.setInt(6, Pedido_idPedido);
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public Personalizacao findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        Personalizacao personalizacao = null;
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    personalizacao = fromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return personalizacao;
    }
    
    public void update(String nome, String tipoCobertura, String tamanhoPedido, String massaPedido,
    	String observacoes, int Pedido_idPedido, int quantidade) throws PersonalizacaoException {
        personalizarLogic.validarCamposPersonalizacao(nome, tipoCobertura, tamanhoPedido, quantidade);
        
        if (!idExists(personalizacao.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }
        
        String sql = "UPDATE " + getTableName() + " SET nome = ?, tipoCobertura = ?, tamanhoPedido = ?, massaPedido = ?, observacoes = ?, Pedido_idPedido = ? WHERE id = ?";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, tipoCobertura);
            ps.setString(3, tamanhoPedido);
            ps.setString(4, massaPedido);
            ps.setString(5, observacoes);
            if (personalizacao.getPedido_idPedido() != null) {
                ps.setInt(6, personalizacao.getPedido_idPedido().getId());
            } else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.setInt(7, personalizacao.getId());
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
    
    public List<Personalizacao> findAll() {
        List<Personalizacao> personalizacoes = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                personalizacoes.add(fromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return personalizacoes;
    }
}
