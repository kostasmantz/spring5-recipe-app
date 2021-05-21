package guru.springframework.controllers;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RecipeControllerTest {

    public static final String RECIPE_ID = "1";
    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void showRecipeForId() {
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();

        when(recipeService.findById(anyString())).thenReturn(recipe);

        try {
            mockMvc.perform(get("/recipe/1/show"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/show"))
                    .andExpect(model().attributeExists("recipe"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void showRecipeForIdNotFound_ShouldThrowException() {
        when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);

        try {
            mockMvc.perform(get("/recipe/1/show"))
                    .andExpect(status().isNotFound())
                    .andExpect(view().name("errors/404notfound"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void showRecipeForIdWithBadStringId_ShouldThrowException() {
        when(recipeService.findById(anyString())).thenThrow(NumberFormatException.class);

        try {
            mockMvc.perform(get("/recipe/dummy/show"))
                    .andExpect(status().isBadRequest())
                    .andExpect(view().name("errors/400badrequest"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void createRecipeForId() {
        try {
            mockMvc.perform(get("/recipe/create"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_form"))
                    .andExpect(model().attributeExists("recipe"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateRecipeForId() {
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();

        when(recipeService.findById(anyString())).thenReturn(recipe);

        try {
            mockMvc.perform(get("/recipe/1/update"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_form"))
                    .andExpect(model().attributeExists("recipe"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void deleteRecipeById() {

        try {
            mockMvc.perform(get("/recipe/1/delete"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void postNewRecipeFormWithValid_ShouldCreateRecipe() {
        RecipeCommand command = new RecipeCommand();
        command.setId(RECIPE_ID);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        try {
            mockMvc.perform(post("/recipe")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("id", "")
                                .param("description", "Test description")
                                .param("directions", "Test directions"))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/recipe/1/show"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void postNewRecipeFormWithNoDescription_ShouldFailValidationAndReturnToFormView() {
        RecipeCommand command = new RecipeCommand();
        command.setId(RECIPE_ID);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        try {
            mockMvc.perform(post("/recipe")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("id", "")
                    .param("directions", "Test directions"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("recipe/recipe_form"));
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}