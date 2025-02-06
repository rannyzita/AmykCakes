package dao;

import model.Produto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connection.DbConnection;
import exceptions.ProdutoException;
import logic.ProdutoLogic;

public class ProdutoDAO extends BaseDAO<Produto> {

    @Override
    protected String getTableName() {
        return "PRODUTO";
    }
    
    // classe sobrecarregada para reaproveitar linhas e simplificar a 
    // implementacao das classes no geral
    @Override
    protected Produto fromResultSet(ResultSet rs) throws SQLException {
        Produto produto = new Produto();
        produto.setId(rs.getInt("id"));
        produto.setNome(rs.getString("nome"));
        produto.setDescricao(rs.getString("descricao"));
        produto.setPreco(rs.getDouble("preco"));
        produto.setFoto(rs.getBytes("foto"));
        produto.setEstoque(rs.getInt("estoque"));
        return produto;
    }

    public void create(Produto produto, File imagem) throws IOException, ProdutoException {
    	ProdutoLogic prod = new ProdutoLogic();
    	prod.validarProduto(produto);
    	
        String sql = "INSERT INTO " + getTableName() + " (nome, descricao, preco, foto, estoque) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
             FileInputStream fis = new FileInputStream(imagem)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setBinaryStream(4, fis); // Corrigido: antes estava `setBinaryStream(3, fis);`
            ps.setInt(5, produto.getEstoque());

            ps.executeUpdate();

            // Recuperando o ID gerado pelo banco
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    produto.setId(rs.getInt(1)); // Armazena o ID gerado
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Produto findById(int id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        Produto produto = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = fromResultSet(rs); // usa o método genérico la em cima
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return produto;
    }

    public void update(Produto produto, File imagem, ProdutoLogic prod) throws FileNotFoundException, IOException, ProdutoException {
    	prod.validarProduto(produto);
    	
        if (!super.idExists(produto.getId())) { // verifica se o ID existe
            System.out.println("Erro: Produto com ID " + produto.getId() + " não encontrado.");
            return;
        }

        String sql = "UPDATE " + getTableName() + " SET nome = ?, descricao = ?, preco = ?, foto = ?, estoque = ? WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(imagem)) {

            ps.setString(1, produto.getNome());
            ps.setString(2, produto.getDescricao());
            ps.setDouble(3, produto.getPreco());
            ps.setBinaryStream(4, fis);
            ps.setInt(5, produto.getEstoque());
            ps.setInt(6, produto.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Erro ao atualizar produto. Nenhuma linha foi afetada.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
