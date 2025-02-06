package logic;
import java.io.File;
import java.io.IOException;

import dao.EquipeDAO;
import exceptions.EquipeException;
import model.Equipe;

public class EquipeLogic {
    private EquipeDAO equipeDAO = new EquipeDAO();

    // Método para validar se os campos da equipe são válidos
    public void validarCamposEquipe(Equipe equipe) throws EquipeException {
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

    // Valida se a equipe existe, mas sem acessar diretamente a DAO.
    public void validarBuscarEquipe(int id) throws EquipeException {
        if (id <= 0) {
            throw new EquipeException("ID inválido.");
        }
    }

    public void validarCriarEquipe(Equipe equipe, File imagem) throws EquipeException, IOException {
        validarCamposEquipe(equipe);
        equipeDAO.create(equipe, imagem);
    }
    
    public void validarAtualizarEquipe(Equipe equipe, File imagem) throws EquipeException, IOException {
        validarCamposEquipe(equipe);
        
        // Chame a DAO para verificar se a equipe existe, mas sem lógica de verificação dentro do EquipeLogic
        if (!equipeDAO.idExists(equipe.getId())) {
            throw new EquipeException("Equipe com ID " + equipe.getId() + " não encontrada.");
        }
        
        equipeDAO.update(equipe, imagem);
    }

    public Equipe validarBuscarEquipePorId(int id) throws EquipeException {
        validarBuscarEquipe(id);
        return equipeDAO.findById(id);
    }
}
