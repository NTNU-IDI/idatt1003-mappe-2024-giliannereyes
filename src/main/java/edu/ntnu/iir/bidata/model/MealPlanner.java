package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.util.List;

/**
 * Represents a meal planning system that checks if recipes
 * in the {@link Cookbook} can be prepared based on the availability
 * of ingredients in the {@link Fridge}.
 *
 * <p>Provides methods to check availability of recipes and to retrieve
 * recipes that have all ingredients required available.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.1
 */
public class MealPlanner {
  private Fridge fridge;
  private Cookbook cookbook;

  /**
   * Constructs a MealPlanner instance with a fridge and cookbook.
   *
   * @param fridge is the fridge to retrieve ingredients from.
   * @param cookbook is the cookbook to retrieve recipes from.
   *
   * @throws IllegalArgumentException if the fridge or cookbook is null.
   */
  public MealPlanner(Fridge fridge, Cookbook cookbook) {
    setFridge(fridge);
    setCookbook(cookbook);
  }

  /**
   * Verifies if all the ingredients required for a specified recipe
   * are available in the fridge.
   *
   * @param recipeName is the name of the recipe to check for.
   *
   * @return {@code true} if all ingredients required are available. Otherwise, {@code false}.
   *
   * @throws IllegalArgumentException if the recipe name is null or empty.
   */
  public boolean ingredientsAreAvailableForRecipe(String recipeName) {
    Validation.validateNonEmptyString(recipeName, "Recipe name");
    return cookbook.findRecipeByName(recipeName).map(recipe ->
        recipe.getIngredients().stream()
        .allMatch(this::isIngredientAvailable))
        .orElse(false);
  }

  /**
   * Retrieves a list of recipes that has all required ingredients
   * available in the fridge.
   *
   * @return A list of recipes.
   */
  public List<Recipe> findRecipesWithAvailableIngredients() {
    return cookbook.getRecipes().stream()
        .filter(recipe -> ingredientsAreAvailableForRecipe(recipe.getName()))
        .toList();
  }

  /**
   * Checks if an ingredient is available in the fridge with sufficient quantity.
   *
   * @param requiredIngredient is the ingredient required.
   *
   * @return {@code true} if the ingredient is available. Otherwise, {@code false}.
   */
  private boolean isIngredientAvailable(Ingredient requiredIngredient) {
    return fridge.findIngredientByName(requiredIngredient.getName())
        .filter(fridgeIngredient -> !fridgeIngredient.isExpired())
        .filter(fridgeIngredient ->
            fridgeIngredient.getUnit().isCompatibleWith(requiredIngredient.getUnit()))
        .map(fridgeIngredient ->
            isQuantitySufficient(fridgeIngredient, requiredIngredient))
        .orElse(false);
  }

  /**
   * Checks if the quantity of the available ingredient meets or exceeds the required quantity.
   *
   * @param availableIngredient is the ingredient in the fridge.
   * @param requiredIngredient is the ingredient required.
   *
   * @return {@code true} if the available quantity is sufficient. Otherwise, {@code false}.
   */
  private boolean isQuantitySufficient(
      Ingredient availableIngredient, Ingredient requiredIngredient
  ) {
    double availableQuantity = availableIngredient.getUnit()
        .convertToBaseUnitValue(availableIngredient.getQuantity());
    double requiredQuantity = requiredIngredient.getUnit()
        .convertToBaseUnitValue(requiredIngredient.getQuantity());
    return availableQuantity >= requiredQuantity;
  }

  /**
   * Sets the fridge.
   *
   * @param fridge is the fridge to retrieve ingredients from.
   *
   * @throws IllegalArgumentException if the fridge is null.
   */
  private void setFridge(Fridge fridge) {
    Validation.validateNonNull(fridge, "Fridge");
    this.fridge = fridge;
  }

  /**
   * Sets the cookbook.
   *
   * @param cookbook is the cookbook to retrieve recipes from.
   *
   * @throws IllegalArgumentException if the cookbook is null.
   */
  private void setCookbook(Cookbook cookbook) {
    Validation.validateNonNull(cookbook, "Cookbook");
    this.cookbook = cookbook;
  }
}
