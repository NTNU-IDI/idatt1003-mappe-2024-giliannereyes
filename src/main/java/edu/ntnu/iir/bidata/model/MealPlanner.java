package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;

import java.util.List;
import java.util.Optional;

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
   * @param recipeName The name of the recipe to check for.
   *
   * @return {@code true} if all ingredients required are available. Otherwise, {@code false}.
   */
  public boolean verifyIngredientsForRecipe(String recipeName) {
    Validation.validateNonEmptyString(recipeName);
    Optional<Recipe> recipeOpt = findRecipeByName(recipeName);

    return recipeOpt.map(recipe ->
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
  public List<Recipe> findSuggestedRecipes() {
    return cookbook.getRecipes().stream()
        .filter(recipe -> verifyIngredientsForRecipe(recipe.getName()))
        .toList();
  }

  /**
   * Checks if an ingredient required for a recipe is available, meaning there
   * is a sufficient quantity in the fridge, and that the ingredient is not expired.
   *
   * @param recipeIngredient The ingredient from a recipe to check for.
   *
   * @return {@code true} if the ingredient is available. Otherwise, {@code false}.
   */
  private boolean isIngredientAvailable(Ingredient recipeIngredient) {
    return fridge.findIngredientByName(recipeIngredient.getName())
        .filter(fridgeIngredient -> !fridgeIngredient.isExpired())
        .filter(fridgeIngredient -> !fridgeIngredient.getUnit().notSameType(recipeIngredient.getUnit()))
        .map(fridgeIngredient -> isIngredientSufficient(fridgeIngredient, recipeIngredient))
        .orElse(false);
  }

  private boolean isIngredientSufficient(Ingredient availableIngredient, Ingredient requiredIngredient) {
    double availableQuantity = availableIngredient.getUnit().convertToBaseUnitValue(availableIngredient.getQuantity());
    double requiredQuantity = requiredIngredient.getUnit().convertToBaseUnitValue(requiredIngredient.getQuantity());
    return availableQuantity >= requiredQuantity;
  }

  /**
   * Retrieves a recipe with the specified name.
   *
   * @param recipeName The name of the recipe to retrieve.
   * @return A recipe that matches the specified name.
   */
  private Optional<Recipe> findRecipeByName(String recipeName) {
    return cookbook.getRecipes().stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(recipeName))
        .findFirst();
  }

}
