package edu.ntnu.iir.bidata;

import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Cookbook;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Cookbook} class.
 *
 * <p>This test class validates the functionality and robustness of the
 * {@code Cookbook} class, including creating a cookbook, adding and managing recipes,
 * and retrieving recipes by name.</p>
 *
 * <b>Positive Tests:</b>
 * <ul>
 *   <li>Creates a cookbook and verifies that it is empty upon initialization.</li>
 *   <li>Adds valid recipes to the cookbook and ensures they are correctly added to the recipe list.</li>
 *   <li>Finds an existing recipe by name, verifying case insensitivity and whitespace handling.</li>
 *   <li>Handles the case of finding a non-existing recipe by returning an empty {@code Optional}.</li>
 * </ul>
 *
 * <b>Negative Tests:</b>
 * <ul>
 *   <li>Throws an exception when attempting to add a {@code null} recipe to the cookbook.</li>
 *   <li>Throws an exception when attempting to add a recipe with the same name as an existing recipe.</li>
 * </ul>
 *
 * <p>The tests ensure that the {@code Cookbook} class adheres to expected behaviors,
 * handles invalid inputs gracefully, and enforces constraints such as unique recipe names.</p>
 *
 * <strong>This class-level documentation was generated by ChatGPT.</strong>
 */
public class CookbookTest {
  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test creating a cookbook.
   * Expected outcome: The cookbook should be empty when created.
   */
  @Test
  void testCreateCookbook() {
    Cookbook cookbook = new Cookbook();
    assertEquals(0, cookbook.getRecipes().size());
  }

  /**
   * Test adding valid recipes to the cookbook.
   * Expected outcome: The recipes should be added to the cookbook.
   */
  @Test
  void testAddValidRecipe() {
    Cookbook cookbook = new Cookbook();
    Recipe pastaRecipe = new Recipe("Pasta", "Delicious Pasta", "Cook for 10 min");
    cookbook.addRecipe(pastaRecipe);
    List<Recipe> recipes = cookbook.getRecipes();
    assertEquals(1, recipes.size());
    assertTrue(recipes.contains(pastaRecipe));

    Recipe pizzaRecipe = new Recipe("Pizza", "Delicious Pizza", "Cook for 20 min");
    cookbook.addRecipe(pizzaRecipe);
    recipes = cookbook.getRecipes();
    assertEquals(2, recipes.size());
    assertTrue(recipes.contains(pizzaRecipe));
  }

  /**
   * Test finding an existing recipe by name.
   * Expected outcome: The recipe should be found, regardless of the case of the name.
   */
  @Test
  void testFindExistingRecipeByName() {
    Cookbook cookbook = new Cookbook();
    Recipe pastaRecipe = new Recipe("Pasta", "Delicious Pasta", "Cook for 10 min");
    cookbook.addRecipe(pastaRecipe);

    assertTrue(cookbook.findRecipeByName("Pasta").isPresent());
    assertEquals(pastaRecipe, cookbook.findRecipeByName("Pasta").get());
    assertTrue(cookbook.findRecipeByName(" PaSta ").isPresent());
    assertEquals(pastaRecipe, cookbook.findRecipeByName(" PaSta ").get());
  }

  /**
   * Test finding a non-existing recipe by name.
   * Expected outcome: An empty Optional should be returned.
   */
  @Test
  void testFindNonExistingRecipeByName() {
    Cookbook cookbook = new Cookbook();
    Recipe pastaRecipe = new Recipe("Pasta", "Delicious Pasta", "Cook for 10 min");
    cookbook.addRecipe(pastaRecipe);

    assertFalse(cookbook.findRecipeByName("Pizza").isPresent());
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test adding a {@code null} recipe to the cookbook.
   * Expected outcome: An {@code IllegalArgumentException} should be thrown.
   */
  @Test
  void testAddNullRecipe() {
    Cookbook cookbook = new Cookbook();
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(null));
  }

  /**
   * Test adding a recipe with the same name as an existing recipe.
   * Expected outcome: An {@code IllegalArgumentException} should be thrown.
   */
  @Test
  void testAddDuplicateRecipe() {
    Cookbook cookbook = new Cookbook();
    Recipe pastaRecipe = new Recipe("Pasta", "Delicious Pasta", "Cook for 10 min");
    cookbook.addRecipe(pastaRecipe);

    Recipe duplicatePastaRecipe = new Recipe("Pasta", "Delicious Pasta", "Cook for 10 min");
    assertThrows(IllegalArgumentException.class, () -> cookbook.addRecipe(duplicatePastaRecipe));
  }
}
