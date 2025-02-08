package logic;

import exceptions.IngredienteException;
import model.Ingrediente;

public class IngredienteLogic {

    public IngredienteLogic() {
    }

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

}
