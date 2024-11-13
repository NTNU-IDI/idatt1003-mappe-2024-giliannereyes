import edu.ntnu.iir.bidata.manager.MealPlanner;
import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.storage.Fridge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
   * <p> Tests verifying if the ingredients required for a recipe are available in
   * the fridge with a recipe that has all ingredients available. </p>
   *
   * Expected outcome: The verification method should return {@code true}.
   */
  @Test
  public void verifyAvailableIngredientsForRecipe() {
    assertTrue(mealPlanner.verifyIngredientsForRecipe("Fried Eggs"));
  }

  /**
   * <p> Test getting the suggested recipes - recipes that has all ingredients
   * available in the fridge.</p>
   *
   * <p> Expected outcome: A list containing only the recipe 'Fried Eggs'
   * should be returned as it is the only recipe with available ingredients.</p>
   */
  @Test
  public void getSuggestedDishes() {
    Recipe friedEggRecipe = new Recipe("Fried eggs", "simple recipe", "fry the eggs");
    friedEggRecipe.addIngredient(new Ingredient("egg", 100, Unit.GRAM));

    List<Recipe> expectedList = List.of(friedEggRecipe);
    List<Recipe> actualList = mealPlanner.findSuggestedRecipes();

    assertEquals(expectedList.size(), actualList.size());
    assertEquals(expectedList.toString(), actualList.toString());
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * <p> Test verifying if the ingredients required for a recipe are available
   * in the fridge with recipes that do not have all ingredients available.</p>
   *
   * <p> Expected outcome: In both cases, the verification method should return {@code false}.
   * The recipe 'Scrambled eggs' is missing 'milk' because it is expired in the fridge,
   * while the recipe 'Carbonara' is missing 'pasta' because there are none in the fridge.</p>
   */
  @Test
  public void verifyUnavailableIngredientsForRecipe() {
    assertFalse(mealPlanner.verifyIngredientsForRecipe("Scrambled Eggs"));
    assertFalse(mealPlanner.verifyIngredientsForRecipe("Carbonara"));
  }

  // --------------------------- HELPER METHODS ----------------------------------
  /**
   * Creates three new ingredients: fresh eggs and Parmesan, and expired milk,
   * and returns them.
   *
   * @return A list of the ingredients created.
   */
  private List<Ingredient> createIngredients() {
    Ingredient egg = new Ingredient("egg", 200, 1, Unit.KILOGRAM, LocalDate.of(2025,1,2));
    Ingredient milk =  new Ingredient("milk", 2, 30, Unit.LITRE, LocalDate.of(2023,1,10));
    Ingredient pasta = new Ingredient("parmesan", 200, 0.5, Unit.GRAM, LocalDate.of(2025,1,10));

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
