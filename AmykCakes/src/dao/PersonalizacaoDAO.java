package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Personalizacao;
import model.Pedido;
import connection.DbConnection;

public class PersonalizacaoDAO extends BaseDAO<Personalizacao> {
    
    @Override
    protected String getTableName() {
        return "PERSONALIZACAO";
    }
    
    @Override
    protected Personalizacao fromResultSet(ResultSet rs) throws SQLException {
        Personalizacao personalizacao = new Personalizacao();
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
    
    public void create(Personalizacao personalizacao) {
        String sql = "INSERT INTO " + getTableName() + " (nome, tipoCobertura, tamanhoPedido, massaPedido, observacoes, Pedido_idPedido) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, personalizacao.getNome());
            ps.setString(2, personalizacao.getTipoCobertura());
            ps.setString(3, personalizacao.getTamanhoPedido());
            ps.setString(4, personalizacao.getMassaPedido());
            ps.setString(5, personalizacao.getObservacoes());
            if (personalizacao.getPedido_idPedido() != null) {
                ps.setInt(6, personalizacao.getPedido_idPedido().getId());
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
    
    public void update(Personalizacao personalizacao) {
        if (!idExists(personalizacao.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }
        
        String sql = "UPDATE " + getTableName() + " SET nome = ?, tipoCobertura = ?, tamanhoPedido = ?, massaPedido = ?, observacoes = ?, Pedido_idPedido = ? WHERE id = ?";
        
        try (PreparedStatement ps = DbConnection.getConexao().prepareStatement(sql)) {
            ps.setString(1, personalizacao.getNome());
            ps.setString(2, personalizacao.getTipoCobertura());
            ps.setString(3, personalizacao.getTamanhoPedido());
            ps.setString(4, personalizacao.getMassaPedido());
            ps.setString(5, personalizacao.getObservacoes());
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
}
