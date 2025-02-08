package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Pedido;
import model.Personalizacao;
import model.Produto;
import connection.DbConnection;
import exceptions.PedidoException;
import logic.PedidoLogic;

public class PedidoDAO extends BaseDAO<Pedido> {
    private PedidoLogic pedidoLogic;

    public PedidoDAO() {
        this.pedidoLogic = new PedidoLogic();  // Passando a instância de PedidoDAO
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

    public int create(double valorTotal, int idProduto, int idPersonalizado) throws PedidoException {
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
                        
                        return pedidoId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PedidoException("Erro ao criar o pedido.");
        }
		return idPersonalizado;
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
    
    public List<Pedido> findAllProdutos() {
        String sql = "SELECT p.*, pr.* FROM pedido p " +
                     "INNER JOIN produto pr ON p.id = pr.id " +
                     "WHERE p.id IS NOT NULL";
        List<Pedido> lista = new ArrayList<>();

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = fromResultSet(rs);
                
                // Criando e associando o produto ao pedido
                Produto produto = new Produto();
                produto.setId(rs.getInt("pr.id"));
                produto.setNome(rs.getString("pr.nome"));
                produto.setDescricao(rs.getString("pr.descricao"));
                produto.setPreco(rs.getDouble("pr.preco"));
                produto.setEstoque(rs.getInt("pr.estoque"));
                
                pedido.setIdProduto(produto);  // Atribuindo o produto completo ao pedido
                
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }


    public List<Pedido> findAllPersonalizacoes() {
        String sql = "SELECT p.*, pr.* FROM pedido p " +
                     "INNER JOIN personalizacao pr ON p.id = pr.id " +
                     "WHERE p.id IS NOT NULL";
        List<Pedido> lista = new ArrayList<>();

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedido pedido = fromResultSet(rs);
                
                // Criando e associando a personalização ao pedido
                Personalizacao personalizacao = new Personalizacao();
                personalizacao.setId(rs.getInt("pr.id"));
                personalizacao.setNome(rs.getString("pr.nome"));
                personalizacao.setTipoCobertura(rs.getString("pr.tipoCobertura"));
                personalizacao.setMassaPedido(rs.getString("pr.massaPedido"));
                personalizacao.setTamanhoPedido(rs.getString("pr.tamanhoPedido"));
                personalizacao.setObservacoes(rs.getString("pr.observacoes"));
                personalizacao.setQuantidade(rs.getInt("pr.quantidade"));
                
                pedido.setPersonalizacao(personalizacao);  // Atribuindo a personalização completa ao pedido
                
                lista.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }



}
