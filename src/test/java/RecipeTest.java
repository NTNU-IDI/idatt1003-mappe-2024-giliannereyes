import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Recipe} class.
 *
 * <p> The following tests are included: </p>
 *
 * <b> Positive tests: </b>
 *
 * <ul>
 *   <li>Creates a Recipe instance with valid fields.</li>
 *   <li>Adds valid ingredient to a recipe.</li>
 * </ul>
 *
 * <b> Negative tests: </b>
 *
 * <ul>
 *   <li>Creates a recipe instances with invalid names.</li>
 *   <li>Creates a recipe instances with invalid descriptions.</li>
 *   <li>Creates a recipe instances with invalid instructions.</li>
 *   <lI>Adds null ingredient to a recipe.</lI>
 * </ul>
 */
public class RecipeTest {
  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test creation of an instance of the Recipe with a valid name, description
   * and instruction.
   *
   * <p> Expected outcome: The Recipe class should be created, and its fields
   * should match the values that were given.</p>
   */
  @Test
  public void createInstanceWithValidFields() {
    Recipe recipe = new Recipe("Pasta", "Delicious pasta recipe", "Cook for 10 minutes");

    assertEquals(recipe.getName(), "Pasta");
    assertEquals(recipe.getDescription(), "Delicious pasta recipe");
    assertEquals(recipe.getInstruction(), "Cook for 10 minutes");
  }

  /**
   * Test adding a valid ingredient to a recipe.
   *
   * <p> Expected outcome: The ingredient should be added to the recipe's ingredients.</p>
   */
  @Test
  public void addValidIngredient() {
    Recipe recipe = new Recipe("Pasta", "Delicious pasta recipe", "Cook for 10 minutes");

    // Test adding an ingredient to the recipe
    Ingredient ingredient =
        new Ingredient("Pasta", 200, 0.1, Unit.GRAM, LocalDate.of(2024, 11, 10));
    recipe.addIngredient(ingredient);

    // Checks if the ingredient was added
    List<Ingredient> ingredients = recipe.getIngredients();
    assertEquals(1, ingredients.size());
    assertEquals(ingredient, ingredients.get(0));
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test creation of an instance of Recipe where the names provided
   * are invalid values of {@code blank}, {@code empty} and {@code null}.
   *
   * <p> Expected outcome: An instance should not be created if the
   * name is set to {@code blank}, {@code empty} or {@code null}.
   * An {@link IllegalArgumentException} should be thrown in all 3 cases.</p>
   */
  @Test
  public void createInstanceWithInvalidName() {
    // A recipe with a null name
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe(null, "Delicious pasta recipe", "Cook for 10 minutes"));

    // A recipe with an empty name
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("", "Delicious pasta recipe", "Cook for 10 minutes"));

    // A recipe with a blank name
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("     ", "Delicious pasta recipe", "Cook for 10 minutes"));
  }

  /**
   * Test creation of an instance of Recipe where the descriptions provided
   * are invalid values of {@code blank}, {@code empty} and {@code null}.
   *
   * <p> Expected outcome: An instance should not be created if the
   * name is set to {@code blank}, {@code empty} or {@code null}.
   * An {@link IllegalArgumentException} should be thrown in all 3 cases.</p>
   */
  @Test
  public void createInstanceWithInvalidDescription() {
    // A recipe with a null description
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("Pasta", null, "Cook for 10 minutes"));

    // A recipe with an empty description
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("Pasta", "", "Cook for 10 minutes"));

    // A recipe with a blank description
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("Pasta", "   ", "Cook for 10 minutes"));
  }

  /**
   * Test creation of an instance of Recipe where the instructions provided
   * are invalid values of {@code blank}, {@code empty} and {@code null}.
   *
   * <p> Expected outcome: An instance should not be created if the
   * name is set to {@code blank}, {@code empty} or {@code null}.
   * An {@link IllegalArgumentException} should be thrown in all 3 cases.</p>
   */
  @Test
  public void createInstanceWithInvalidInstruction() {
    // A recipe with a null instruction
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("Pasta", "Delicious pasta recipe", null));

    // A recipe with an empty instruction
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("Pasta", "Delicious pasta recipe", ""));

    // A recipe with a blank instruction
    assertThrows(IllegalArgumentException.class, () ->
      new Recipe("Pasta", "Delicious pasta recipe", "    "));
  }

  /**
   * Test adding an ingredient when the ingredient is {@code null}.
   *
   * <p> Expected outcome: The ingredient should not be added to the recipe,
   * and an {@link IllegalArgumentException} should be thrown. </p>
   */
  @Test
  public void addNullIngredient() {
    Recipe recipe = new Recipe("Pasta", "Delicious pasta recipe", "Cook for 10 minutes");

    assertThrows(IllegalArgumentException.class, () ->
      recipe.addIngredient(null));
  }
}
