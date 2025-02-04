package main;

import dao.PagamentoDAO;
import dao.PedidoDAO;
import model.Pagamento;
import model.Pedido;

public class Main {

    public static void main(String[] args) {
        
        PagamentoDAO pagamentoDAO = new PagamentoDAO();

        
        pagamentoDAO.delete(1);  
        pagamentoDAO.delete(2);  
        pagamentoDAO.delete(3);  
        pagamentoDAO.delete(4);  
        pagamentoDAO.delete(5);  
        pagamentoDAO.delete(6);  

     
    }
}
