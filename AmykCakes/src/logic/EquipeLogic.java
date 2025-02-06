package logic;

import java.io.File;

import java.io.IOException;

import dao.EquipeDAO;
import exceptions.EquipeException;
import model.Equipe;

public class EquipeLogic {
    private EquipeDAO equipeDAO = new EquipeDAO();

    public void validarEquipe(Equipe equipe) throws EquipeException {
        if (equipe == null) {
            throw new EquipeException("A equipe não pode ser nula.");
        }

        if (equipe.getNome() == null || equipe.getNome().trim().isEmpty()) {
            throw new EquipeException("O nome da equipe é obrigatório.");
        }

        if (equipe.getDescricao() == null || equipe.getDescricao().trim().isEmpty()) {
            throw new EquipeException("A descrição da equipe é obrigatória.");
        }

        if (equipe.getCargo() == null || equipe.getCargo().trim().isEmpty()) {
            throw new EquipeException("O cargo da equipe é obrigatório.");
        }
    }

    public void validarCriarEquipe(Equipe equipe, File imagem) throws EquipeException, IOException {
        validarEquipe(equipe);
        equipeDAO.create(equipe, imagem);
    }

    public void validarAtualizarEquipe(Equipe equipe, File imagem, EquipeLogic EquipeLogic) throws EquipeException, IOException {
        validarEquipe(equipe);
        equipeDAO.update(equipe, imagem, EquipeLogic);
    }

    public Equipe validarBuscarEquipe(int id) throws EquipeException {
        Equipe equipe = equipeDAO.findById(id);
        if (equipe == null) {
            throw new EquipeException("Equipe com ID " + id + " não encontrada.");
        }
        return equipe;
    }
}
