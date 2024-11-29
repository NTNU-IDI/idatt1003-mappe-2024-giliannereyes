package edu.ntnu.iir.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.iir.bidata.model.Cookbook;
import edu.ntnu.iir.bidata.model.Fridge;
import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.MealPlanner;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link MealPlanner} class.
 *
 * <br><b>Positive Tests:</b>
 * <ul>
 *   <li>Initializes the {@code MealPlanner} with a fridge and cookbook
 *   containing predefined ingredients and recipes.</li>
 *   <li>Verifies that the required ingredients for a recipe are available in the fridge.</li>
 *   <li>Retrieves a list of recipes for which all required
 *   ingredients are available in the fridge.</li>
 * </ul>
 *
 * <br><b>Negative Tests:</b>
 * <ul>
 *   <li>Verifies that recipes cannot be made when not all
 *   required ingredients are available in the fridge.</li>
 * </ul>
 *
 * <b>Helper Methods:</b>
 * <ul>
 *   <li>Creates predefined lists of ingredients (some fresh, some expired) for use in tests.</li>
 *   <li>Creates predefined lists of recipes with associated ingredients for use in tests.</li>
 *   <li>Provides utility methods to add ingredients to a {@link Fridge}
 *   and recipes to a {@link Cookbook}.</li>
 * </ul>
 *
 * <strong>This class-level documentation was generated by ChatGPT.</strong>
 */
public class MealPlannerTest {
  MealPlanner mealPlanner;

  /**
   * Initializes a {@link Cookbook}, {@link Fridge} and {@link MealPlanner} before
   * each test is conducted. Initializes also ingredients and recipes,
   * added to the fridge and cookbook respectively, which are used to
   * test the meal planner.
   */
  @BeforeEach
  public void setUp() {
    Cookbook cookbook = new Cookbook();
    Fridge fridge = new Fridge();
    addIngredientsToFridge(createIngredients(), fridge);
    addRecipesToCookbook(createRecipes(), cookbook);
    mealPlanner = new MealPlanner(fridge, cookbook);
  }

  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test verifying if the ingredients required for a recipe are available.
   *
   * <p>Expected outcome: The verification method should return {@code true} as
   * all ingredients required for the recipe 'Fried Eggs' are available in the fridge.</p>
   */
  @Test
  void testVerifyAvailableIngredientsForRecipe() {
    assertTrue(mealPlanner.ingredientsAreAvailableForRecipe("Fried Eggs"));
  }

  /**
   * Test retrieving a list of recipes that have all required ingredients available.
   *
   * <p>Expected outcome: The method should return a list of recipes that have all
   * required ingredients available in the fridge. In this case, the recipe 'Fried Eggs'.</p>
   */
  @Test
  void testFindRecipesWithAvailableIngredients() {
    Recipe friedEggRecipe = new Recipe("Fried eggs", "simple recipe", "fry the eggs");
    friedEggRecipe.addIngredient(new Ingredient("egg", 100, Unit.GRAM));

    List<Recipe> expectedList = List.of(friedEggRecipe);
    List<Recipe> actualList = mealPlanner.findRecipesWithAvailableIngredients();

    assertEquals(expectedList.size(), actualList.size());
    assertEquals(expectedList.toString(), actualList.toString());
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test verifying if the ingredients required for a recipe are available.
   *
   * <p>Expected outcome: The verification method should return {@code false} as
   * neither 'Carbonara' nor 'Scrambled Eggs' recipes have all required ingredients.</p>
   */
  @Test
  void testVerifyUnavailableIngredientsForRecipe() {
    assertFalse(mealPlanner.ingredientsAreAvailableForRecipe("Scrambled Eggs"));
    assertFalse(mealPlanner.ingredientsAreAvailableForRecipe("Carbonara"));
  }

  // --------------------------- HELPER METHODS ----------------------------------
  /**
   * Creates three new ingredients: fresh eggs and Parmesan, and expired milk,
   * and returns them.
   *
   * @return A list of the ingredients created.
   */
  private List<Ingredient> createIngredients() {
    Ingredient egg = new Ingredient(
        "egg", 200, 1, Unit.KILOGRAM, LocalDate.of(2025, 1, 2)
    );
    Ingredient milk =  new Ingredient(
        "milk", 2, 30, Unit.LITRE, LocalDate.of(2023, 1, 10)
    );
    Ingredient pasta = new Ingredient(
        "parmesan", 200, 0.5, Unit.GRAM, LocalDate.of(2025, 1, 10)
    );

    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(egg);
    ingredients.add(milk);
    ingredients.add(pasta);

    return ingredients;
  }

  /**
   * Creates three new recipes: carbonara, scrambled eggs and fried eggs,
   * and returns them.
   *
   * @return A list of the recipes created.
   */
  private List<Recipe> createRecipes() {
    Recipe carbonaraRecipe = new Recipe("Carbonara", "Delicious dish", "boil pasta, add sauce");
    carbonaraRecipe.addIngredient(new Ingredient("pasta", 200, Unit.GRAM));
    carbonaraRecipe.addIngredient(new Ingredient("parmesan", 50, Unit.GRAM));
    carbonaraRecipe.addIngredient(new Ingredient("egg", 100, Unit.GRAM));

    Recipe scrambledEggRecipe = new Recipe("Scrambled eggs", "scrambled", "beat eggs, etc");
    scrambledEggRecipe.addIngredient(new Ingredient("egg", 100, Unit.GRAM));
    scrambledEggRecipe.addIngredient(new Ingredient("milk", 100, Unit.GRAM));

    Recipe friedEggRecipe = new Recipe("Fried eggs", "simple recipe", "fry the eggs");
    friedEggRecipe.addIngredient(new Ingredient("egg", 100, Unit.GRAM));

    List<Recipe> recipes = new ArrayList<>();
    recipes.add(carbonaraRecipe);
    recipes.add(scrambledEggRecipe);
    recipes.add(friedEggRecipe);

    return recipes;
  }

  /**
   * Adds recipes to a cookbook.
   *
   * @param recipes A list of recipes to add to the cookbook.
   * @param cookbook The cookbook to add recipes to.
   */
  private void addRecipesToCookbook(List<Recipe> recipes, Cookbook cookbook) {
    recipes.forEach(cookbook::addRecipe);
  }

  /**
   * Adds ingredients to a fridge.
   *
   * @param ingredients A list of ingredients to add to the fridge.
   * @param fridge The fridge to add ingredients to.
   */
  private void addIngredientsToFridge(List<Ingredient> ingredients, Fridge fridge) {
    ingredients.forEach(fridge::addIngredient);
  }
}
