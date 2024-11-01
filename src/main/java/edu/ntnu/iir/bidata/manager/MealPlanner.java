package edu.ntnu.iir.bidata.manager;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.storage.Fridge;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    // Find the recipe with the specified name
    Recipe recipe = findRecipeByName(recipeName)
        .orElseThrow(() -> new NoSuchElementException("No recipe found with name " + recipeName));

    // If the recipe exists, check if its ingredients are available
    return recipe.getIngredients().stream()
        .allMatch(this::isIngredientAvailable);
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
    Optional<Ingredient> fridgeIngredientOpt = fridge.findIngredientByName(recipeIngredient.getName());

    if (fridgeIngredientOpt.isEmpty()) {
      return false;
    }

    Ingredient fridgeIngredient = fridgeIngredientOpt.get();

    if (fridgeIngredient.isExpired() || !fridgeIngredient.getUnit().isSameType(recipeIngredient.getUnit())) {
      return false;
    }

    // Compare quantities in base units
    double availableQuantity = fridgeIngredient.getUnit().convertToBaseUnit(fridgeIngredient.getQuantity());
    double requiredQuantity = recipeIngredient.getUnit().convertToBaseUnit(recipeIngredient.getQuantity());

    return availableQuantity >= requiredQuantity;
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
