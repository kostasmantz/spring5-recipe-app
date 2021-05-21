package guru.springframework.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"recipes"})
@Document
public class Category {

    @Id
    private String id;
    private String description;
    @DBRef
    private Set<Recipe> recipes;

    public void addRecipe(Recipe recipe) {
        if (recipe == null) {
            return;
        } else if (recipes == null) {
            recipes = new HashSet<>();
        }
        recipes.add(recipe);
    }
}
