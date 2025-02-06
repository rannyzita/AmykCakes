package dao;

import model.Pagamento;

import model.Pedido;

import java.sql.*;

import connection.DbConnection;
import exceptions.PagamentoException;
import logic.PagamentoLogic;

public class PagamentoDAO extends BaseDAO<Pagamento> {
	PagamentoLogic p = new PagamentoLogic();
	
    @Override
    protected String getTableName() {
        return "Pagamento"; // Nome da tabela no banco de dados
    }

    @Override
    protected Pagamento fromResultSet(ResultSet rs) throws SQLException {
        Pagamento pagamento = new Pagamento();
        pagamento.setId(rs.getInt("id"));  
        pagamento.setPedido_idPedido(new Pedido(rs.getInt("Pedido_idPedido")));  
        pagamento.setValor(rs.getDouble("valor"));
        pagamento.setFormaPagamento(rs.getString("formaPagamento"));
        pagamento.setData(rs.getTimestamp("data").toLocalDateTime());  
        return pagamento;
    }

    
    public void create(Pagamento pagamento) throws PagamentoException {
    	p.validarCamposPagamento(pagamento); 
    	
        String sql = "INSERT INTO " + getTableName() + " (Pedido_idPedido, valor, formaPagamento, data) VALUES (?, ?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, pagamento.getPedido_idPedido().getId());
            ps.setDouble(2, pagamento.getValor());
            ps.setString(3, pagamento.getFormaPagamento());
            ps.setTimestamp(4, Timestamp.valueOf(pagamento.getData())); 

            ps.executeUpdate();

            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    pagamento.setId(rs.getInt(1)); 
                    System.out.println("Pagamento criado com sucesso! ID do Pagamento: " + pagamento.getId());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public Pagamento findById(int id) {
        if (!idExists(id)) {
            System.out.println("Erro: O ID não existe na tabela.");
            return null;
        }

        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        Pagamento pagamento = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pagamento = fromResultSet(rs); 
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pagamento;
    }

    // Método para atualizar um pagamento
    public void update(Pagamento pagamento, String updateFields, PagamentoLogic p) throws PagamentoException {
    	p.validarAtualizarPagamento(pagamento); 
    	
        // Verifica se o ID do pagamento existe na tabela
        if (!idExists(pagamento.getId())) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }

        // Gerar a string de SQL dinamicamente para atualização
        String sql = "UPDATE " + getTableName() + " SET " + updateFields + " WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            // Atualiza o campo de forma_pagamento
            ps.setString(1, pagamento.getFormaPagamento());  
            // Atualiza o campo id
            ps.setInt(2, pagamento.getId());  
            // Executa a atualização
            ps.executeUpdate();
            System.out.println("Pagamento atualizado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
