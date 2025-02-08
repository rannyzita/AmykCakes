package dao;

import java.sql.*;

import model.Personalizacao;
import connection.DbConnection;
import exceptions.PersonalizacaoException;
import logic.PersonalizacaoLogic;

public class PersonalizacaoDAO extends BaseDAO<Personalizacao> {
    private PersonalizacaoLogic personalizacaoLogic;

    public PersonalizacaoDAO(PersonalizacaoLogic personalizacaoLogic) {
        this.personalizacaoLogic = personalizacaoLogic;
    }

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
            
        return personalizacao;
    }
    
    public int create(Personalizacao personalizacao) throws PersonalizacaoException {
        int personalizacaoId = 0;
    	// Validando os campos da personalização usando o objeto
        personalizacaoLogic.validarCamposPersonalizacao(
            personalizacao.getNome(),
            personalizacao.getTipoCobertura(),
            personalizacao.getTamanhoPedido(),
            personalizacao.getQuantidade()
        );

        // Preparando a consulta SQL com os dados do objeto
        String sql = "INSERT INTO " + getTableName() + 
                     " (nome, tipoCobertura, tamanhoPedido, massaPedido, observacoes, quantidade) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Setando os valores na query a partir do objeto `personalizacao`
            ps.setString(1, personalizacao.getNome());
            ps.setString(2, personalizacao.getTipoCobertura());
            ps.setString(3, personalizacao.getTamanhoPedido());
            ps.setString(4, personalizacao.getMassaPedido());
            ps.setString(5, personalizacao.getObservacoes());
            ps.setInt(6, personalizacao.getQuantidade());
            
            // Executando a atualização no banco de dados
            int affectedRows = ps.executeUpdate();  
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        personalizacaoId = rs.getInt(1);
      
                        return personalizacaoId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersonalizacaoException("Erro ao criar personalização.");
        }
		return personalizacaoId;
    }

    public Personalizacao findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return fromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
