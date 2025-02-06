package dao;

import connection.DbConnection;
import logic.HistoricoDePedidosLogic;
import java.sql.*;

public class HistoricoDePedidosDAO {
	
    private boolean pedidoExists(int idPedido) {
        String sql = "SELECT 1 FROM Pedido WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void create(int idPedido){
    	HistoricoDePedidosLogic h = new HistoricoDePedidosLogic();
    	h.validarIdPedido(idPedido); 
    	
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        String sql = "INSERT INTO HISTORICODEPEDIDOS (Pedido_idPedido) VALUES (?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);  
            ps.executeUpdate();
            System.out.println("Histórico de Pedido criado com ID do Pedido: " + idPedido);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    public void update(int idPedido, HistoricoDePedidosLogic h) {
    	h.validarIdPedido(idPedido); 
    	
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        String sql = "UPDATE HISTORICODEPEDIDOS SET Pedido_idPedido = ? WHERE Pedido_idPedido = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);  
            ps.setInt(2, idPedido);  
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public int findById(int idPedido) {
        String sql = "SELECT Pedido_idPedido FROM HISTORICODEPEDIDOS WHERE Pedido_idPedido = ?";
        int result = -1;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    result = rs.getInt("Pedido_idPedido"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    
    
    public void delete(int idPedido) {
        if (!pedidoExists(idPedido)) {
            System.out.println("Erro: Pedido não existe.");
            return;
        }

        String sql = "DELETE FROM HISTORICODEPEDIDOS WHERE Pedido_idPedido = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idPedido); 
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void findAll() {
        String sql = "SELECT * FROM HISTORICODEPEDIDOS";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println("ID do Pedido: " + rs.getInt("Pedido_idPedido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
