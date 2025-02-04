package main;

import dao.CarrinhoDAO;
import model.Carrinho;

public class Main {
    public static void main(String[] args) {
        CarrinhoDAO carrinhoDAO = new CarrinhoDAO();

        carrinhoDAO.delete(5);
        carrinhoDAO.delete(6);
    }
}
