package logic;

import dao.IngredienteDAO;

import exceptions.IngredienteException;
import model.Ingrediente;

public class IngredienteLogic {
	private IngredienteDAO ingredienteDAO = new IngredienteDAO();
	
	public void validarIngrediente(Ingrediente ingrediente) throws IngredienteException{
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
	
	public void validarCriarIngrediente(Ingrediente ingrediente) throws IngredienteException {
        validarIngrediente(ingrediente);
        ingredienteDAO.create(ingrediente);
    }

	public Ingrediente validarBuscarIngrediente(int id) throws IngredienteException {
	    Ingrediente ingrediente = ingredienteDAO.findById(id);
	    if (ingrediente == null) {
	        throw new IngredienteException("Ingrediente com ID " + id + " não encontrado.");
	    }
	    return ingrediente;
	}

	public void validarAtualizarIngrediente(Ingrediente ingrediente, IngredienteLogic IngredienteLogic) throws IngredienteException {
	    validarIngrediente(ingrediente);
	    ingredienteDAO.update(ingrediente, IngredienteLogic);
	}

	public void validarDeletarIngrediente(int id) throws IngredienteException {
	    Ingrediente ingrediente = validarBuscarIngrediente(id);
	    if (ingrediente == null) {
	        throw new IngredienteException("Ingrediente com ID " + id + " não encontrado.");
	    }
	    ingredienteDAO.delete(id);
	}	
}