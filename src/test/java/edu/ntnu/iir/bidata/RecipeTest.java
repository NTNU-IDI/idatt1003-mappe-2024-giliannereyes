package edu.ntnu.iir.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.iir.bidata.domain.Ingredient;
import edu.ntnu.iir.bidata.domain.Recipe;
import edu.ntnu.iir.bidata.domain.Unit;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Recipe} class.
 *
 * <br><b>Positive Tests:</b>
 * <ul>
 *   <li>Creates a valid recipe with a name, description, and instruction.</li>
 *   <li>Adds a valid ingredient to a recipe and ensures it is
 *   included in the recipe's list of ingredients.</li>
 * </ul>
 *
 * <br><b>Negative Tests:</b>
 * <ul>
 *   <li>Throws an exception when creating a recipe with
 *   an invalid name (null, empty, or blank).</li>
 *   <li>Throws an exception when creating a recipe with
 *   an invalid description (null, empty, or blank).</li>
 *   <li>Throws an exception when creating a recipe with
 *   an invalid instruction (null, empty, or blank).</li>
 *   <li>Throws an exception when attempting to add a {@code null} ingredient to a recipe.</li>
 * </ul>
 *
 * <strong>This class-level documentation was generated by ChatGPT.</strong>
 */
public class RecipeTest {
  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test creation of instances of a Recipe object with a
   * valid name, description and instruction.
   *
   * <p>Expected outcome: A recipe should be created with a name,
   * description and instruction equal to the provided values.</p>
   */
  @Test
  void testCreateValidRecipe() {
    Recipe recipe = new Recipe("Pasta Recipe", "Delicious pasta recipe", "Cook for 10 minutes");
    assertEquals(recipe.getName(), "Pasta Recipe");
    assertEquals(recipe.getDescription(), "Delicious pasta recipe");
    assertEquals(recipe.getInstruction(), "Cook for 10 minutes");

    Recipe recipe2 = new Recipe(
        "Pizza Recipe with a long name",
        "Delicious pizza recipe with a long description",
        "A long instruction for making pizza... etc."
    );
    assertEquals(recipe2.getName(), "Pizza Recipe with a long name");
    assertEquals(recipe2.getDescription(), "Delicious pizza recipe with a long description");
    assertEquals(recipe2.getInstruction(), "A long instruction for making pizza... etc.");
  }

  /**
   * Test adding a valid ingredient to a recipe.
   *
   * <p>Expected outcome: The ingredient should be added to the recipe's list of ingredients.</p>
   */
  @Test
  void testAddValidIngredient() {
    Recipe recipe = new Recipe("Pasta Recipe", "Delicious pasta recipe", "Cook for 10 minutes");
    Ingredient ingredient = new Ingredient(
        "Pasta", 200, 0.1, Unit.GRAM, LocalDate.of(2024, 11, 10)
    );
    recipe.addIngredient(ingredient);
    List<Ingredient> ingredients = recipe.getIngredients();
    assertEquals(1, ingredients.size());
    assertEquals(ingredient, ingredients.getFirst());
  }

  /**
   * Test adding a duplicate ingredient to a recipe, which should increase the quantity
   * instead of adding a new ingredient.
   */
  @Test
  void testAddDuplicateIngredient() {
    Recipe recipe = new Recipe("Pasta Recipe", "Delicious pasta recipe", "Cook for 10 minutes");
    Ingredient ingredient1 = new Ingredient("Pasta", 200, Unit.GRAM);
    Ingredient ingredient2 = new Ingredient("PaSTA ", 0.2, Unit.KILOGRAM);
    recipe.addIngredient(ingredient1);
    recipe.addIngredient(ingredient2);
    List<Ingredient> ingredients = recipe.getIngredients();
    assertEquals(1, ingredients.size());
    assertEquals(
        400, ingredients.getFirst().getQuantity())
    ;
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test creating a recipe with an invalid name, which is null, empty or blank.
   *
   * <p>Expected outcome: An IllegalArgumentException should be thrown in all cases.</p>
   */
  @Test
  void testCreateRecipeWithInvalidName() {
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe(null, "Delicious pasta recipe", "Cook for 10 minutes")
    );
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("", "Delicious pasta recipe", "Cook for 10 minutes")
    );
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("     ", "Delicious pasta recipe", "Cook for 10 minutes")
    );
  }

  /**
   * Test creating a recipe with an invalid description, which is null, empty or blank.
   *
   * <p>Expected outcome: An IllegalArgumentException should be thrown in all cases.</p>
   */
  @Test
  void testCreateRecipeWithInvalidDescription() {
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("Pasta Recipe", null, "Cook for 10 minutes")
    );
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("Pasta Recipe", "", "Cook for 10 minutes")
    );
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("Pasta Recipe", "   ", "Cook for 10 minutes")
    );
  }

  /**
   * Test creating a recipe with an invalid instruction, which is null, empty or blank.
   *
   * <p>Expected outcome: An IllegalArgumentException should be thrown in all cases.</p>
   */
  @Test
  void testCreateRecipeWithInvalidInstruction() {
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("Pasta Recipe", "Delicious pasta recipe", null)
    );
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("Pasta Recipe", "Delicious pasta recipe", "")
    );
    assertThrows(IllegalArgumentException.class, () ->
        new Recipe("Pasta Recipe", "Delicious pasta recipe", "    ")
    );
  }

  /**
   * Test adding a null ingredient to a recipe.
   *
   * <p>Expected outcome: An IllegalArgumentException should be thrown,
   * and the ingredient should not be added to the recipe.</p>
   */
  @Test
  void testAddNullIngredient() {
    Recipe recipe = new Recipe("Pasta Recipe", "Delicious pasta recipe", "Cook for 10 minutes");
    assertThrows(IllegalArgumentException.class, () -> recipe.addIngredient(null));
    assertTrue(recipe.getIngredients().isEmpty());
  }
}
