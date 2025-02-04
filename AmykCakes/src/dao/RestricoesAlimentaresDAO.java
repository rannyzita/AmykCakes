package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.DbConnection;
import model.RestricoesAlimentares;
import model.Pedido;
import model.Personalizacao;
import model.Produto;

public class RestricoesAlimentaresDAO extends BaseDAO<RestricoesAlimentares>{
	
	@Override
	protected String getTableName() {
		return "RESTRICOESALIMENTARES";
	}

	@Override
	protected RestricoesAlimentares fromResultSet(ResultSet rs) throws SQLException {
	    RestricoesAlimentares restricao = new RestricoesAlimentares();
	    
	    // Definindo os atributos da restrição alimentar
	    restricao.setId(rs.getInt("id"));
	    restricao.setRestricao(rs.getString("restricao"));

	    // Pegando o ID da Personalizacao da consulta
	    int personalizacaoId = rs.getInt("Personalizacao_idPedidoPersonalizado");

	    // Se o ID da Personalizacao não for nulo, criamos a associação
	    if (!rs.wasNull()) {
	        Personalizacao personalizacao = new Personalizacao();
	        personalizacao.setId(personalizacaoId);
	        restricao.setPersonalizacao_idPedidoPersonalizado(personalizacao);
	    } else {
	        restricao.setPersonalizacao_idPedidoPersonalizado(null);
	    }

	    return restricao;
	}

    // Método para verificar se o id de Personalizacao existe
    private boolean personalizacaoExists(int idPersonalizacao) {
        String sql = "SELECT 1 FROM Personalizacao WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPersonalizacao);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Se encontrar algum resultado, Personalizacao existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para criar um novo registro de RestricoesAlimentares
    public void create(RestricoesAlimentares restricoesAlimentares) {
        // Verifica se a Personalizacao existe
        if (!personalizacaoExists(restricoesAlimentares.getPersonalizacao_idPedidoPersonalizado().getId())) {
            System.out.println("Erro: Personalizacao não existe.");
            return;
        }

        String sql = "INSERT INTO RestricoesAlimentares (id, Personalizacao_idPedidoPersonalizado, restricao) VALUES (?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
        	
            ps.setInt(1, restricoesAlimentares.getId());  // id já vem de BaseEntity
            ps.setInt(2, restricoesAlimentares.getPersonalizacao_idPedidoPersonalizado().getId());
            ps.setString(3, restricoesAlimentares.getRestricao());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar RestricoesAlimentares por ID
    public RestricoesAlimentares findById(int id) {
        String sql = "SELECT * FROM RestricoesAlimentares WHERE id = ?";
        RestricoesAlimentares restricoesAlimentares = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    restricoesAlimentares = new RestricoesAlimentares();
                    restricoesAlimentares.setId(rs.getInt("id"));
                    Personalizacao personalizacao = new Personalizacao();
                    personalizacao.setId(rs.getInt("Personalizacao_idPedidoPersonalizado"));
                    restricoesAlimentares.setPersonalizacao_idPedidoPersonalizado(personalizacao);
                    restricoesAlimentares.setRestricao(rs.getString("restricao"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restricoesAlimentares;
    }

    // Método para atualizar uma RestricoesAlimentares existente
    public void update(RestricoesAlimentares restricoesAlimentares) {
        // Verifica se a Personalizacao existe
        if (!personalizacaoExists(restricoesAlimentares.getPersonalizacao_idPedidoPersonalizado().getId())) {
            System.out.println("Erro: Personalizacao não existe.");
            return;
        }

        String sql = "UPDATE RestricoesAlimentares SET Personalizacao_idPedidoPersonalizado = ?, restricao = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, restricoesAlimentares.getPersonalizacao_idPedidoPersonalizado().getId());
            ps.setString(2, restricoesAlimentares.getRestricao());
            ps.setInt(3, restricoesAlimentares.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
