package logic;

import exceptions.PersonalizacaoException;

public class PersonalizacaoLogic {
    
    public void validarCamposPersonalizacao(String nome, String tipoCobertura, String tamanhoPedido, int quantidade) throws PersonalizacaoException {
      
        if (nome == null || nome.trim().isEmpty()) {
            throw new PersonalizacaoException("O nome da personalização não pode estar vazio.");
        }
        if (tipoCobertura == null || tipoCobertura.trim().isEmpty()) {
            throw new PersonalizacaoException("O tipo de cobertura não pode estar vazio.");
        }
        if (tamanhoPedido == null || tamanhoPedido.trim().isEmpty()) {
            throw new PersonalizacaoException("O tamanho do pedido não pode estar vazio.");
        }
        
        if (quantidade <= 0) {
            throw new PersonalizacaoException("Quantidade inválida.");
        }
    }
    
}
