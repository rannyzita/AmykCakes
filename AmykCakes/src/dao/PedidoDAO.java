package dao;

import model.Pedido;

import java.sql.*;

import connection.DbConnection;
import exceptions.PedidoException;
import logic.PedidoLogic;

public class PedidoDAO extends BaseDAO<Pedido> {

    @Override
    protected String getTableName() {
        return "PEDIDO"; 
    }

    @Override
    protected Pedido fromResultSet(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setId(rs.getInt("id"));
        pedido.setDataPedido(rs.getTimestamp("dataPedido").toLocalDateTime());
        pedido.setDataEntregaPrevista(rs.getTimestamp("dataEntregaPrevista").toLocalDateTime());
        pedido.setValorTotal(rs.getDouble("valorTotal"));
        return pedido;
    }

    public void create(Pedido pedido) throws PedidoException {
    	PedidoLogic ped = new PedidoLogic();
    	ped.validarPedido(pedido);
    	
        // data de entrega prevista para 15 dias após a data do pedido
        pedido.setDataEntregaPrevista(pedido.getDataPedido().plusDays(15));
        
        String sql = "INSERT INTO " + getTableName() + " (dataPedido, dataEntregaPrevista, valorTotal) VALUES (?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { 

            ps.setTimestamp(1, Timestamp.valueOf(pedido.getDataPedido()));
            ps.setTimestamp(2, Timestamp.valueOf(pedido.getDataEntregaPrevista()));
            ps.setDouble(3, pedido.getValorTotal());

            ps.executeUpdate(); 

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    
                    pedido.setId(rs.getInt(1));  
                    System.out.println("Pedido criado com sucesso! ID do Pedido: " + pedido.getId());
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Pedido findById(int id) {
        if (!idExists(id)) {
            System.out.println("Erro: O ID não existe na tabela.");
            return null;
        }

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

    public void update(Pedido pedido, String updateFields, PedidoLogic ped) throws PedidoException {
    	ped.validarPedido(pedido);
    	
        // Verifica se o ID do pedido existe na tabela
        if (!idExists(pedido.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }

        // Gera o SQL para atualizar os campos fornecidos
        String sql = "UPDATE " + getTableName() + " SET " + updateFields + " WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            
            // Define o parâmetro para o ID do Pedido
            ps.setInt(1, pedido.getId());

            // Executa o update
            ps.executeUpdate();
            System.out.println("Pedido atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
