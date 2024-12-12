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
 * @version 1.3
 * @since 1.1
 */
public class MealPlanner {
  private Fridge fridge;
  private Cookbook cookbook;

  /**
   * Constructs a MealPlanner instance with a fridge and cookbook.
   *
   * @param fridge is the {@link Fridge} to retrieve ingredients from.
   * @param cookbook is the {@link Cookbook} to retrieve recipes from.
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
  public boolean areIngredientsAvailableForRecipe(String recipeName) {
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
        .filter(recipe -> areIngredientsAvailableForRecipe(recipe.getName()))
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
    List<Ingredient> ingredientBatches = fridge.findIngredientsByName(requiredIngredient.getName());
    double totalAvailableQuantity = ingredientBatches.stream()
        .filter(batch -> !batch.isExpired())
        .filter(batch -> batch.unitIsCompatibleWith(requiredIngredient.getUnit()))
        .mapToDouble(batch -> batch.convertQuantityTo(requiredIngredient.getUnit()))
        .sum();
    return totalAvailableQuantity >= requiredIngredient.getQuantity();
  }

  /**
   * Sets the fridge.
   *
   * @param fridge is the {@link Fridge} object to retrieve ingredients from.
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
   * @param cookbook is the {@link Cookbook} to retrieve recipes from.
   *
   * @throws IllegalArgumentException if the cookbook is null.
   */
  private void setCookbook(Cookbook cookbook) {
    Validation.validateNonNull(cookbook, "Cookbook");
    this.cookbook = cookbook;
  }
}
