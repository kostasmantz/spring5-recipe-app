package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByIngredientId(String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(String ingredientId, String recipeId);
}
