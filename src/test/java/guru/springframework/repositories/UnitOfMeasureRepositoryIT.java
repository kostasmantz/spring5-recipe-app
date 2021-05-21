package guru.springframework.repositories;

import guru.springframework.bootstrap.DefaultRecipeDataBootstrap;
import guru.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    RecipeRepository recipeRepository;
    @Autowired
    NotesRepository notesRepository;
    @Autowired
    IngredientRepository ingredientRepository;

    @Before
    public void setUp() throws Exception {
        DefaultRecipeDataBootstrap bootstrap = new DefaultRecipeDataBootstrap(categoryRepository, recipeRepository, unitOfMeasureRepository, notesRepository, ingredientRepository);
        bootstrap.onApplicationEvent(null);
    }

    @Test
    public void findByDescription_ShouldReturnFoundItem() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Cup");

        assertEquals("Cup", unitOfMeasure.get().getDescription());
    }
}