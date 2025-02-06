package logic;

import dao.IngredienteDAO;
import exceptions.IngredienteException;
import model.Ingrediente;

public class IngredienteLogic {
    private IngredienteDAO ingredienteDAO = new IngredienteDAO();

    public void validarCamposIngrediente(Ingrediente ingrediente) throws IngredienteException {
        if (ingrediente == null) {
            throw new IngredienteException("O ingrediente não pode ser nulo.");
        }

        if (ingrediente.getNomeIngrediente() == null || ingrediente.getNomeIngrediente().trim().isEmpty()) {
            throw new IngredienteException("O nome do ingrediente não pode estar vazio.");
        }

        if (ingrediente.getQuantidadeEstoque() <= 0) {
            throw new IngredienteException("A quantidade em estoque não pode ser negativa, nem igual a 0.");
        }
    }

    // Método que valida e cria o ingrediente
    public void validarCriarIngrediente(Ingrediente ingrediente) throws IngredienteException {
        validarCamposIngrediente(ingrediente);
        ingredienteDAO.create(ingrediente);
    }

    // Método que valida e busca o ingrediente pelo ID
    public Ingrediente validarBuscarIngrediente(int id) throws IngredienteException {
        Ingrediente ingrediente = ingredienteDAO.findById(id);
        if (ingrediente == null) {
            throw new IngredienteException("Ingrediente com ID " + id + " não encontrado.");
        }
        return ingrediente;
    }

    // Método que valida e atualiza o ingrediente
    public void validarAtualizarIngrediente(Ingrediente ingrediente) throws IngredienteException {
        validarCamposIngrediente(ingrediente);
        ingredienteDAO.update(ingrediente);
    }

    // Método que valida e exclui o ingrediente
    public void validarExcluirIngrediente(int id) throws IngredienteException {
        if (ingredienteDAO.findById(id) == null) {
            throw new IngredienteException("Ingrediente com ID " + id + " não encontrado.");
        }
        ingredienteDAO.delete(id);
    }
}
