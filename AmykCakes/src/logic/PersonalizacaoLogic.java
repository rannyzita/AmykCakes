package logic;

import dao.PersonalizacaoDAO;

import exceptions.PersonalizacaoException;
import model.Personalizacao;

public class PersonalizacaoLogic {
	private PersonalizacaoDAO personalizacaoDAO = new PersonalizacaoDAO();
	
	
	public void validarPersonalizacao(Personalizacao personalizacao) throws PersonalizacaoException {
		if (personalizacao == null) {
            throw new PersonalizacaoException("A personalização não pode ser nula.");
        }
        if (personalizacao.getNome() == null || personalizacao.getNome().trim().isEmpty()) {
            throw new PersonalizacaoException("O nome da personalização não pode estar vazio.");
        }
        if (personalizacao.getTipoCobertura() == null || personalizacao.getTipoCobertura().trim().isEmpty()) {
            throw new PersonalizacaoException("O tipo de cobertura não pode estar vazio.");
        }
        if (personalizacao.getTamanhoPedido() == null || personalizacao.getTamanhoPedido().trim().isEmpty()) {
            throw new PersonalizacaoException("O tamanho do pedido não pode estar vazio.");
        }
	}
	
	public void validarCriarPersonalizacao(Personalizacao personalizacao) throws PersonalizacaoException {
        validarPersonalizacao(personalizacao);
        personalizacaoDAO.create(personalizacao);
    }

    public Personalizacao validarBuscarPersonalizacao(int id) throws PersonalizacaoException {
        Personalizacao personalizacao = personalizacaoDAO.findById(id);
        if (personalizacao == null) {
            throw new PersonalizacaoException("Personalização com ID " + id + " não encontrada.");
        }
        return personalizacao;
    }

    public void validarAtualizarPersonalizacao(Personalizacao personalizacao, PersonalizacaoLogic PersonalizacaoLogic) throws PersonalizacaoException {
        validarPersonalizacao(personalizacao);
        personalizacaoDAO.update(personalizacao, PersonalizacaoLogic);
    }

    public void validarDeletarPersonalizacao(int id) throws PersonalizacaoException {
        Personalizacao personalizacao = validarBuscarPersonalizacao(id);
        if (personalizacao == null) {
            throw new PersonalizacaoException("Personalização com ID " + id + " não encontrada.");
        }
        personalizacaoDAO.delete(id);
    }
}