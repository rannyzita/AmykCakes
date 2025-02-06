package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connection.DbConnection;
import logic.CarrinhoProdutoLogic;
import model.CarrinhoProduto;


public class CarrinhoProdutoDAO {
	
	public boolean produtoExists(int idProduto) {
        String sql = "SELECT 1 FROM Produto WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProduto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Se encontrar algum resultado, o Produto existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public boolean carrinhoExists(int idCarrinho) {
        String sql = "SELECT 1 FROM Carrinho WHERE id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idCarrinho);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Se encontrar algum resultado, o Carrinho existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
    // Método para inserir um novo CarrinhoProduto
    public void create(CarrinhoProduto carrinhoProduto) {
    	CarrinhoProdutoLogic cProduto = new CarrinhoProdutoLogic();
    	cProduto.validarCarrinhoProduto(carrinhoProduto);   
    	
    	if (!produtoExists(carrinhoProduto.getProduto_idProduto().getId())) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        if (!carrinhoExists(carrinhoProduto.getCarrinho_id().getId())) {
            System.out.println("Erro: Carrinho não existe.");
            return;
        }
        
        String sql = "INSERT INTO CARRINHOPRODUTO (quantidade, Carrinho_id, Produto_idProduto) VALUES (?, ?, ?)";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, carrinhoProduto.getQuantidade());
            ps.setInt(2, carrinhoProduto.getCarrinho_id().getId()); // Pegando o ID do Carrinho
            ps.setInt(3, carrinhoProduto.getProduto_idProduto().getId()); // Pegando o ID do Produto

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para excluir um CarrinhoProduto com base nas chaves estrangeiras
    public void delete(int idProduto, int idCarrinho) {
    	if (!produtoExists(idProduto)) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        if (!carrinhoExists(idCarrinho)) {
            System.out.println("Erro: Carrinho não existe.");
            return;
        }
        
        String sql = "DELETE FROM CARRINHOPRODUTO WHERE Produto_idProduto = ? AND Carrinho_id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProduto);
            ps.setInt(2, idCarrinho);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para atualizar a quantidade de um CarrinhoProduto
    public void update(int idProduto, int idCarrinho, int quantidade, CarrinhoProduto carrinhoProduto, CarrinhoProdutoLogic cProduto) {
    	cProduto.validarCarrinhoProduto(carrinhoProduto);
    	
    	if (!produtoExists(idProduto)) {
            System.out.println("Erro: Produto não existe.");
            return;
        }

        if (!carrinhoExists(idCarrinho)) {
            System.out.println("Erro: Carrinho não existe.");
            return;
        }
        
        String sql = "UPDATE CARRINHOPRODUTO SET quantidade = ? WHERE Produto_idProduto = ? AND Carrinho_id = ?";

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, quantidade); // Atualizando a quantidade
            ps.setInt(2, idProduto); // Usando a chave estrangeira Produto_idProduto
            ps.setInt(3, idCarrinho); // Usando a chave estrangeira Carrinho_id

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar CarrinhoProduto por ID do Carrinho e Produto
    public CarrinhoProduto findById(int idProduto, int idCarrinho) {
        String sql = "SELECT * FROM CARRINHOPRODUTO WHERE Produto_idProduto = ? AND Carrinho_id = ?";
        CarrinhoProduto carrinhoProduto = null;

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idProduto);
            ps.setInt(2, idCarrinho);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    carrinhoProduto = new CarrinhoProduto();
                    carrinhoProduto.setQuantidade(rs.getInt("quantidade"));
                    // Aqui você pode preencher os dados relacionados a Produto e Carrinho
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carrinhoProduto;
    }

    // Método para listar todos os CarrinhoProduto (caso seja necessário)
    public List<CarrinhoProduto> findAll() {
        String sql = "SELECT * FROM CARRINHOPRODUTO";
        List<CarrinhoProduto> carrinhoProdutos = new ArrayList<>();

        try (Connection connection = DbConnection.getConexao();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CarrinhoProduto carrinhoProduto = new CarrinhoProduto();
                carrinhoProduto.setQuantidade(rs.getInt("quantidade"));
                // Adicione o preenchimento de outros campos, se necessário
                carrinhoProdutos.add(carrinhoProduto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return carrinhoProdutos;
    }
}
