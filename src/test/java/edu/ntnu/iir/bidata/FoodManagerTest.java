package edu.ntnu.iir.bidata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.utils.FoodManager;
import edu.ntnu.iir.bidata.utils.Result;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link FoodManager} class.
 *
 * <br><b>Positive Tests:</b>
 * <ul>
 *   <li>Calculates the total value of the fridge,
 *   both when it is empty and after adding ingredients</li>
 *   <li>Finds sorted ingredients in the fridge after adding valid ingredients</li>
 *   <li>Adds valid ingredients to the fridge, including updating existing ones</li>
 *   <li>Finds an existing ingredient in the fridge</li>
 *   <li>Removes valid quantities of ingredients from the fridge</li>
 *   <li>Identifies ingredients expiring within a given timeframe</li>
 *   <li>Adds valid recipes to the cookbook</li>
 *   <li>Checks recipe availability with all required ingredients available</li>
 *   <li>Creates valid recipes successfully</li>
 * </ul>
 *
 * <br><b>Negative Tests:</b>
 * <ul>
 *   <li>Fails to add ingredients with invalid attributes
 *   (e.g., negative price, incompatible units)</li>
 *   <li>Fails to find non-existent ingredients or ingredients with invalid names</li>
 *   <li>Fails to remove quantities exceeding available amounts,
 *   using incompatible units, or for non-existent ingredients</li>
 *   <li>Fails to identify ingredients expiring before a given date when none exist</li>
 *   <li>Fails to add recipes with invalid attributes (e.g., duplicate names, null names)</li>
 *   <li>Fails to verify recipe availability when required ingredients are missing</li>
 * </ul>
 *
 * <strong> This class-level documentation was generated by ChatGPT. </strong>
 */
public class FoodManagerTest {
  FoodManager foodManager;
  String validName;
  double validQuantity;
  double validPrice;
  Unit validUnit;
  LocalDate validExpiryDate;

  /**
   * Set up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    foodManager = new FoodManager();
    validName = "Milk";
    validQuantity = 2.0;
    validPrice = 1.0;
    validUnit = Unit.LITRE;
    validExpiryDate = LocalDate.now().plusDays(7);
  }

  /**
   * Test calculating the total value of the fridge, when it is empty and
   * after adding an ingredient to the fridge.
   *
   * <p>Expected outcome: The result should be unsuccessful when the fridge is empty,
   * and successful after adding an ingredient.</p>
   */
  @Test
  void testCalculateValueOfFridge() {
    Result<String> result = foodManager.calculateFridgeValue();
    assertFalse(result.isSuccess());

    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    Result<String> result2 = foodManager.calculateFridgeValue();
    assertTrue(result2.isSuccess());
  }

  /**
   * Test finding all ingredients in the fridge, when it is empty and after adding an ingredient.
   *
   * <p>Expected outcome: The result should be successful after adding an ingredient.</p>
   */
  @Test
  void testFindSortedIngredients() {
    Result<List<Ingredient>> result = foodManager.findSortedIngredients();
    assertFalse(result.isSuccess());

    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    Result<List<Ingredient>> result2 = foodManager.findSortedIngredients();
    assertTrue(result2.isSuccess());
  }

  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test adding valid ingredients to the fridge -
   * a new ingredient and an additional quantity of an existing ingredient.
   *
   * <p>Expected outcome: The result should be successful in both cases.</p>
   */
  @Test
  void testAddValidIngredient() {
    Result<Void> result = foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    assertTrue(result.isSuccess());

    assertTrue(validUnit.isCompatibleWith(Unit.DECILITRE));
    Result<Void> result2 = foodManager.addIngredientToFridge(
        validName, validQuantity + 10, validPrice, Unit.DECILITRE, validExpiryDate
    );
    assertTrue(result2.isSuccess());
  }

  /**
   * Test finding an existing ingredient in the fridge.
   *
   * <p>Expected outcome: The result should be successful and the ingredient should be found.</p>
   */
  @Test
  void testFindExistingIngredient() {
    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    Result<Ingredient> result = foodManager.findIngredient(validName);
    assertTrue(result.isSuccess());
    assertTrue(result.getData().isPresent());
    assertInstanceOf(Ingredient.class, result.getData().get());
  }

  /**
   * Test removing a quantity of an ingredient from the fridge. First, a smaller amount
   * than available, and then the remaining amount.
   *
   * <p>Expected outcome: The result should be successful in both cases, and the ingredient
   * should be removed from the fridge after the second removal.</p>
   */
  @Test
  void testRemoveValidIngredient() {
    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    Result<Void> result = foodManager.removeIngredientFromFridge(
        validName, validUnit, validQuantity - 1
    );
    assertTrue(result.isSuccess());

    Result<Void> result2 = foodManager.removeIngredientFromFridge(validName, validUnit, 1);
    assertTrue(result2.isSuccess());
    assertFalse(foodManager.findIngredient(validName).getData().isPresent());
  }

  /**
   * Test getting a list of ingredients that are expiring before a certain date.
   *
   * <p>Expected outcome: The result should be successful, and the list should contain
   * the ingredient that is expiring within the specified time frame.</p>
   */
  @Test
  void testGetExpiringIngredients() {
    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, LocalDate.now().plusDays(1)
    );
    Result<List<Ingredient>> result =
        foodManager.getIngredientsExpiringBefore(LocalDate.now().plusDays(10)
    );
    assertTrue(result.isSuccess());
    assertTrue(result.getData().isPresent());
    assertEquals(1, result.getData().get().size());
  }

  /**
   * Test adding a valid recipe to the cookbook.
   *
   * <p>Expected outcome: The result should be successful.</p>
   */
  @Test
  void testAddValidRecipe() {
    Recipe recipe = new Recipe("Apple Pie", "description", "instructions");
    Result<Void> result = foodManager.addRecipe(recipe);
    assertTrue(result.isSuccess());
  }

  /**
   * Test checking if a recipe with all ingredients required available
   * is seen as available.
   *
   * <p>Expected outcome: The result should be successful in both
   * methods that check the availability.</p>
   */
  @Test
  void testRecipeAvailabilityWithAvailableRecipe() {
    Recipe recipe = new Recipe("Apple Pie", "description", "instructions");
    foodManager.addRecipe(recipe);
    foodManager.addIngredientToRecipe(recipe, validName, validQuantity, validUnit);
    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );

    Result<List<Recipe>> result = foodManager.findSuggestedRecipes();
    assertTrue(result.isSuccess());
    assertTrue(result.getData().isPresent());
    assertEquals(recipe, result.getData().get().getFirst());

    Result<Void> result2 = foodManager.verifyRecipeAvailability("Apple Pie");
    assertTrue(result2.isSuccess());
  }

  /**
   * Test creating a valid recipe.
   *
   * <p>Expected outcome: The result should be successful, and the manager
   * should return the recipe it created.</p>
   */
  @Test
  void testCreateValidRecipe() {
    Result<Recipe> result = foodManager.createRecipe("Apple Pie", "description", "instructions");
    assertTrue(result.isSuccess());
    assertTrue(result.getData().isPresent());
  }
  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test adding invalid ingredients to the fridge - an ingredient with invalid
   * attributes, and an ingredient that shares the same name as an existing ingredient,
   * but has different attributes.
   *
   * <p>Expected outcome: The result should be unsuccessful in both cases.</p>
   */
  @Test
  void testAddInvalidIngredient() {
    Result<Void> result = foodManager.addIngredientToFridge(
        validName, validQuantity, -50, validUnit, validExpiryDate
    );
    assertFalse(result.isSuccess());

    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    assertFalse(validUnit.isCompatibleWith(Unit.GRAM));
    Result<Void> result2 = foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, Unit.GRAM, validExpiryDate
    );
    assertFalse(result2.isSuccess());
  }

  /**
   * Test finding a non-existent ingredient in the fridge.
   *
   * <p>Expected outcome: The result should be unsuccessful,
   * and the ingredient should not be found.</p>
   */
  @Test
  void testFindNonExistentIngredient() {
    Result<Ingredient> result = foodManager.findIngredient("Non-existent ingredient");
    assertFalse(result.isSuccess());
    assertFalse(result.getData().isPresent());
  }

  /**
   * Test finding an ingredient with an invalid name.
   *
   * <p>Expected outcome: The result should be unsuccessful.</p>
   */
  @Test
  void testFindIngredientInvalidName() {
    Result<Ingredient> result = foodManager.findIngredient(null);
    assertFalse(result.isSuccess());
    assertFalse(result.getData().isPresent());
  }

  /**
   * Test removing an invalid quantity of an ingredient from the fridge. First, a larger amount
   * than available, and then an amount of an incompatible unit. Lastly, a non-existent ingredient.
   *
   * <p>Expected outcome: The result should be unsuccessful in all cases.</p>
   */
  @Test
  void testRemoveInvalidIngredient() {
    foodManager.addIngredientToFridge(
        validName, validQuantity, validPrice, validUnit, validExpiryDate
    );
    Result<Void> result = foodManager.removeIngredientFromFridge(
        validName, validUnit, validQuantity + 10
    );
    assertFalse(result.isSuccess());

    assertFalse(validUnit.isCompatibleWith(Unit.GRAM));
    Result<Void> result2 = foodManager.removeIngredientFromFridge(
        validName, Unit.GRAM, validQuantity
    );
    assertFalse(result2.isSuccess());

    Result<Void> result3 = foodManager.removeIngredientFromFridge(
        "Non-existent ingredient", validUnit, validQuantity
    );
    assertFalse(result3.isSuccess());
  }

  /**
   * Test getting a list of ingredients that are expiring before a certain date,
   * when there are no expiring ingredients in the fridge.
   *
   * <p>Expected outcome: The result should be unsuccessful.</p>
   */
  @Test
  void testFindNonExistentExpiringIngredients() {
    Result<List<Ingredient>> result = foodManager.getIngredientsExpiringBefore(LocalDate.now());
    assertFalse(result.isSuccess());
  }

  /**
   * Test getting a list of ingredients expiring before a certain date with an invalid date.
   *
   * <p>Expected outcome: The result should be unsuccessful.</p>
   */
  @Test
  void testFindExpiringIngredientsWithInvalidDate() {
    Result<List<Ingredient>> result = foodManager.getIngredientsExpiringBefore(null);
    assertFalse(result.isSuccess());
  }

  /**
   * Test adding an invalid recipe to the cookbook - a recipe with
   * the same name as an existing recipe.
   *
   * <p>Expected outcome: The result should be unsuccessful.</p>
   */
  @Test
  void testAddInvalidRecipe() {
    Recipe recipe = new Recipe("Apple Pie", "description", "instructions");
    foodManager.addRecipe(recipe);

    Recipe recipe2 = new Recipe("Apple Pie", "description", "instructions");
    Result<Void> result = foodManager.addRecipe(recipe2);
    assertFalse(result.isSuccess());
  }

  /**
   * Test checking if a recipe with ingredients required unavailable
   * is seen as unavailable.
   *
   * <p>Expected outcome: The result should be unsuccessful in
   * both methods that check for availability.</p>
   */
  @Test
  void testRecipeAvailabilityWithUnavailableRecipe() {
    Recipe recipe = new Recipe("Apple Pie", "description", "instructions");
    foodManager.addRecipe(recipe);
    foodManager.addIngredientToRecipe(recipe, validName, validQuantity, validUnit);

    Result<Void> result = foodManager.verifyRecipeAvailability("Apple Pie");
    assertFalse(result.isSuccess());

    Result<List<Recipe>> result2 = foodManager.findSuggestedRecipes();
    assertFalse(result2.isSuccess());
    assertTrue(result2.getData().isEmpty());
  }

  /**
   * Test adding a recipe with an invalid attribute (null name).
   *
   * <p>Expected outcome: The result should be unsuccessful.</p>
   */
  @Test
  void createInvalidRecipe() {
    Result<Recipe> result = foodManager.createRecipe(null, "description", "instructions");
    assertFalse(result.isSuccess());
    assertFalse(result.getData().isPresent());
  }
}
