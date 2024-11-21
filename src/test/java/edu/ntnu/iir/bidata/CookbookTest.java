package edu.ntnu.iir.bidata;

import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.storage.Cookbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Cookbook} class.
 *
 * <p> The following tests are included: </p>
 *
 * <b> Positive tests: </b>
 *
 * <ul>
 *   <li>Adds a valid recipe to a cookbook.</li>
 * </ul>
 *
 * <b> Negative tests: </b>
 *
 * <ul>
 *   <li>Adds a {@code null} recipe to a cookbook.</li>
 * </ul>
 */
public class CookbookTest {
  Cookbook cookbook;

  /**
   * Creates an instance of Cookbook before each test method.
   */
  @BeforeEach
  public void setUp() {
    cookbook = new Cookbook();
  }
  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test adding a valid recipe to the cookbook.
   *
   * <p> Expected outcome: The recipe should be added to the cookbook.</p>
   */
  @Test
  public void addValidRecipe() {
    // Test adding a recipe
    Recipe recipe = new Recipe("Pasta", "Delicious Pasta", "Cook for 10 min");
    cookbook.addRecipe(recipe);

    // Verify that the recipe was added
    List<Recipe> recipes = cookbook.getRecipes();
    assertEquals(recipes.size(), 1);
    assertTrue(recipes.contains(recipe));
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------
  @Test
  public void addNullRecipe() {
    // Test adding a null recipe
    assertThrows(IllegalArgumentException.class, () ->
        cookbook.addRecipe(null));

    // Verify that the recipe was not added
    List<Recipe> recipes = cookbook.getRecipes();
    assertEquals(recipes.size(), 0);
    assertFalse(recipes.contains(null));
  }
}
