package dao;

import java.sql.*;
import java.time.LocalDateTime;

import model.Pedido;
import connection.DbConnection;
import exceptions.PedidoException;
import logic.PedidoLogic;

public class PedidoDAO extends BaseDAO<Pedido> {
    private PedidoLogic pedidoLogic;

    public PedidoDAO() {
        this.pedidoLogic = new PedidoLogic(this);  // Passando a instância de PedidoDAO
    }

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

    public void create(double valorTotal, int idProduto, int idPersonalizado) throws PedidoException {
        pedidoLogic.validarCamposPedido(valorTotal);
        
        LocalDateTime dataPedido = LocalDateTime.now();
        LocalDateTime dataEntregaPrevista = dataPedido.plusDays(15);
        
        String sql = "INSERT INTO " + getTableName() + " (dataPedido, dataEntregaPrevista, valorTotal, idProduto, idPersonalizacao) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) { // Adicionando RETURN_GENERATED_KEYS aqui
                
            ps.setTimestamp(1, Timestamp.valueOf(dataPedido));
            ps.setTimestamp(2, Timestamp.valueOf(dataEntregaPrevista));
            ps.setDouble(3, valorTotal);
            ps.setDouble(4, idProduto);
            ps.setDouble(5, idPersonalizado);
            
            int affectedRows = ps.executeUpdate();  // Execute a atualização
            
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int pedidoId = rs.getInt(1);
      
                        System.out.println("Pedido criado com sucesso! ID do Pedido: " + pedidoId);
                        // Agora, você pode usar esse ID para associar o pedido com outras tabelas, se necessário
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PedidoException("Erro ao criar o pedido.");
        }
    }


    public Pedido findById(int id) {
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
