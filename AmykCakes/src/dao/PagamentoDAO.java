package dao;

import model.Pagamento;
import model.Pedido;
import java.sql.*;

import connection.DbConnection;
import exceptions.PagamentoException;
import logic.PagamentoLogic;

public class PagamentoDAO extends BaseDAO<Pagamento> {
    private PagamentoLogic pagamentoLogic;

    public PagamentoDAO() {
        this.pagamentoLogic = new PagamentoLogic(this);
    }

    @Override
    protected String getTableName() {
        return "Pagamento";
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

    public void create(int Pedido_idPedido, double valor, String formaPagamento, Pagamento pagamento) throws PagamentoException {
        pagamentoLogic.validarCamposPagamento(formaPagamento, valor);

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

    public void update(String formaPagamento, double valor, int pagamentoId) throws PagamentoException {
        pagamentoLogic.validarCamposPagamento(formaPagamento, valor);

        if (!idExists(pagamentoId)) {
            System.out.println("Erro: O ID não existe na tabela.");
            return;
        }

        String sql = "UPDATE " + getTableName() + " SET formaPagamento = ?, valor = ?, data = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, formaPagamento);
            ps.setDouble(2, valor);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setInt(4, pagamentoId);
            ps.executeUpdate();
            System.out.println("Pagamento atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
