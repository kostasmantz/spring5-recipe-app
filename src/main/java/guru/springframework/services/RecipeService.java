package guru.springframework.services;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(String recipeId);

    RecipeCommand findCommandById(String recipeId);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(String recipeId);
}
