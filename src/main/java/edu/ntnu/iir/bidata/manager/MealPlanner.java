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


  public boolean verifyIngredientsForRecipe(String recipeName) {
    Optional<Recipe> optionalRecipe = getRecipeByName(recipeName);

    // Checks if the recipe exists
    if (optionalRecipe.isPresent()) {
      Recipe recipe = optionalRecipe.get();
      List<Ingredient> recipeIngredients = recipe.getIngredients();

      // Checks if there is enough of each required ingredient in the fridge
      return recipeIngredients.stream()
          .allMatch(ingredient -> {
              Optional<Ingredient> optFridgeIngredient = fridge.findIngredientByName(ingredient.getName());

              if (optFridgeIngredient.isPresent()) {
                Ingredient fridgeIngredient = optFridgeIngredient.get();

                return fridgeIngredient.getQuantity() >= ingredient.getQuantity()
                    && !fridgeIngredient.isExpired();

              } else {
                return false;
              }

          });

    // Throws an exception if the recipe does not exist
    } else {
      throw new NoSuchElementException("Recipe does not exist.");
    }
  }

  /**
   * Retrieves a list of recipes that has all required ingredients
   * available in the fridge.
   *
   * @return A list of recipes.
   */
  public List<Recipe> getSuggestedRecipes() {
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
  public Optional<Recipe> getRecipeByName(String recipeName) {
    return cookbook.getRecipes().stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(recipeName))
        .findFirst();
  }

}
