package main;

import dao.ItemCarrinhoDAO;

public class Main {
    public static void main(String[] args) {
    	ItemCarrinhoDAO item = new ItemCarrinhoDAO();
    	
    	System.out.println(item.getIdForeignKeyPersonalizacao());
    	
    }
}
