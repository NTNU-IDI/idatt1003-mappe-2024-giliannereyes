package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;

import java.util.List;

/**
 * The MealPlanner class is responsible for managing recipes in the cookbook
 * and checking if there are enough ingredients in the fridge to follow them.
 */
public class MealPlanner {
  private final Fridge fridge;
  private final Cookbook cookbook;

  /**
   * Constructs an instance of MealPlanner.
   *
   * @param fridge   The fridge to retrieve ingredients from.
   * @param cookbook The cookbook to retrieve recipes from.
   */
  public MealPlanner(Fridge fridge, Cookbook cookbook) {
    Validation.validateFridge(fridge);
    Validation.validateCookbook(cookbook);

    this.fridge = fridge;
    this.cookbook = cookbook;
  }

  /**
   * Verifies if all the ingredients required for a specified recipe
   * are available in the fridge.
   *
   * @param recipeName is a name of the recipe to check for.
   *
   * @return {@code true} if all ingredients required are available. Otherwise, {@code false}.
   */
  public boolean ingredientsAreAvailableForRecipe(String recipeName) {
    Validation.validateNonEmptyString(recipeName);
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
   * Checks if an ingredient required for a recipe is available, meaning there
   * is a sufficient quantity in the fridge, and that the ingredient is not expired.
   *
   * @param requiredIngredient is the ingredient required for a recipe.
   *
   * @return {@code true} if the ingredient is available. Otherwise, {@code false}.
   */
  private boolean isIngredientAvailable(Ingredient requiredIngredient) {
    return fridge.findIngredientByName(requiredIngredient.getName())
        .filter(fridgeIngredient -> !fridgeIngredient.isExpired())
        .filter(fridgeIngredient -> fridgeIngredient.getUnit().isCompatibleWith(requiredIngredient.getUnit()))
        .map(fridgeIngredient -> hasSufficientQuantity(fridgeIngredient, requiredIngredient))
        .orElse(false);
  }

  /**
   * Checks if the quantity of an ingredient in the fridge is sufficient for a recipe.
   * The quantity is considered sufficient if the quantity in the fridge is greater or equal.
   *
   * @param availableIngredient is the ingredient in the fridge.
   * @param requiredIngredient is the ingredient required.
   *
   * @return {@code true} if the available quantity meets or exceeds the required quantity.
   *         Otherwise, {@code false}.
   */
  private boolean hasSufficientQuantity(Ingredient availableIngredient, Ingredient requiredIngredient) {
    double availableQuantity = availableIngredient.getUnit().convertToBaseUnitValue(availableIngredient.getQuantity());
    double requiredQuantity = requiredIngredient.getUnit().convertToBaseUnitValue(requiredIngredient.getQuantity());
    return availableQuantity >= requiredQuantity;
  }
}
