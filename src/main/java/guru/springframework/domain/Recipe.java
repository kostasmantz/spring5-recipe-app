package guru.springframework.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    @DBRef
    private Set<Ingredient> ingredients;
    private Byte[] image;
    private Difficulty difficulty;
    @DBRef
    private Notes notes;
    @DBRef
    private Set<Category> categories;

    public void addCategory(Category category) {
        if (category == null) {
            return;
        } else if (categories == null) {
            categories = new HashSet<>();
        }
        categories.add(category);
    }

    public void addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return;
        } else if (ingredients == null) {
            ingredients = new HashSet<>();
        }

        ingredients.add(ingredient);
    }
}
