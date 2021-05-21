package guru.springframework.services;

import guru.springframework.commands.IngredientCommand;
import guru.springframework.converters.IngredientToIngredientCommand;
import guru.springframework.converters.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Recipe;
import guru.springframework.repositories.IngredientRepository;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;
    private final IngredientToIngredientCommand converter;
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientServiceImpl(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, RecipeService recipeService, IngredientToIngredientCommand converter, UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
        this.converter = converter;
        this.uomConverter = uomConverter;
    }

    @Override
    public IngredientCommand findByIngredientId(String ingredientId) {
        return converter.convert(ingredientRepository.findById(ingredientId).orElse(new Ingredient()));
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Ingredient ingredient = ingredientRepository.findByIdAndRecipeId(command.getId(), command.getRecipeId()).orElse(null);

        if (ingredient == null) {
            ingredient = new Ingredient();
        }

        Recipe recipe = recipeService.findById(command.getRecipeId());
        ingredient.setRecipe(recipe);
        ingredient.setDescription(command.getDescription());
        ingredient.setAmount(command.getAmount());
        ingredient.setUom(Optional.of(command.getUom())
                .filter(uom -> !StringUtils.isEmpty(uom.getDescription()))
                .map(uomConverter::convert)
                .orElse(null));

        Ingredient savedIngredient = ingredientRepository.save(ingredient);

        recipe.addIngredient(ingredient);
        recipeRepository.save(recipe);

        return converter.convert(savedIngredient);
    }

    @Override
    public void deleteById(String ingredientId, String recipeId) {
        ingredientRepository.deleteByIdAndRecipeId(ingredientId, recipeId);
    }
}
