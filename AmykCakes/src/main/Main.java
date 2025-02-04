package main;
import dao.HistoricoDePedidosDAO;

public class Main {
    public static void main(String[] args) {
       
        // Criando uma instância do DAO
        HistoricoDePedidosDAO historicoDePedidosDAO = new HistoricoDePedidosDAO();

        

        // Testando o método delete
        System.out.println("\n### Testando Delete ###");
        historicoDePedidosDAO.delete(1);  // Excluindo o Histórico de Pedidos com o ID fornecido
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        historicoDePedidosDAO.delete(1);
        
        // Após excluir, testando novamente o findById
        
    }
}
