package edu.ntnu.iir.bidata.manager;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.storage.Fridge;
import edu.ntnu.iir.bidata.storage.Cookbook;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * The Manager class is responsible for managing operations related to
 * ingredients, recipes and user interaction in the context of fridge and cookbook
 * managements.
 */
public class Manager {
  private final Fridge fridge;
  private final Cookbook cookbook;
  private final MealPlanner mealPlanner;

  /**
   * Constructs a new Manager instance.
   *
   * @param fridge The fridge that stores ingredients.
   * @param cookbook The cookbook that contains recipes.
   * @param mealPlanner The meal planner for suggesting and verifying recipes.
   */
  public Manager(Fridge fridge, Cookbook cookbook, MealPlanner mealPlanner) {
    this.fridge = fridge;
    this.cookbook = cookbook;
    this.mealPlanner = mealPlanner;
  }

  /**
   * Adds a new ingredient to the fridge.
   *
   * @param name The name of the ingredient.
   * @param quantity The quantity of the ingredient to add.
   * @param unit The unit of measurement.
   * @param pricePerUnit The price per unit.
   * @param expiryDate The ingredient's expiry date.
   *
   * @return A Result object containing a success message if the ingredient was added,
   *         or an error message if it failed.
   */
  public Result<Void> addIngredientToFridge(String name, double quantity, double pricePerUnit, Unit unit, LocalDate expiryDate) {
    try {
      Ingredient newIngredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
      fridge.addIngredient(newIngredient);
      return new Result<>(true, String.format("Ingredient '%s' was added to the fridge.", name));
    } catch (IllegalArgumentException e) {
      return new Result<>(false, String.format("Failed to add ingredient '%s' to the fridge: %s", name, e.getMessage()));
    }
  }

  /**
   * Searches for an ingredient in the fridge by its name.
   *
   * @param name The name of the ingredient.
   *
   * @return A Result object containing the found ingredient if present and a success message,
   *         or a message if not found.
   */
  public Result<Ingredient> searchForIngredient(String name) {
    Optional<Ingredient> ingredient = fridge.findIngredientByName(name);
    return ingredient.map(value ->
        new Result<>(true, value, String.format("The ingredient %s was found.", name)))
        .orElseGet(() -> new Result<>(false, String.format("The ingredient '%s' was not found.", name)));
  }

  /**
   * Decreases the quantity of a specified ingredient in the fridge.
   *
   * @param name The name of the ingredient.
   * @param quantity The quantity to decrease the ingredient with.
   * @param unit The unit of measurement of the quantity to decrease.
   *
   * @return A Result object containing a success message if the quantity was decreased,
   *         or an error message if it failed.
   */
  public Result<Void> decreaseIngredientQuantity(String name, Unit unit, double quantity) {
    try {
      fridge.decreaseIngredientQuantity(name, quantity, unit);
      return new Result<>(true, String.format("%.2f %s of %s was successfully removed from the fridge!\n", quantity, unit.getSymbol(), name));
    } catch (IllegalArgumentException e) {
      return new Result<>(false, String.format("Failed to remove %.2f %s of %s due to: %s", quantity, unit.getSymbol(), name, e.getMessage()));
    }
  }

  /**
   * Checks for expiring ingredients before a specified date.
   *
   * @param date The latest date the ingredients to retrieve can expire.
   *
   * @return A Result object containing a list of ingredients that expire before the specified date,
   *         or a message if none are expiring before the date.
   */
  public Result<List<Ingredient>> checkExpiringIngredients(LocalDate date) {
    List<Ingredient> expiringIngredients = fridge.findIngredientsBeforeDate(date);
    if (!expiringIngredients.isEmpty()) {
      return new Result<>(true, expiringIngredients, ("Ingredients that expire before: " + date));
    } else {
      return new Result<>(false, ("There are no ingredients expiring before: " + date));
    }
  }

  /**
   * Retrieves all the ingredients in the fridge, sorted alphabetically.
   *
   * @return A Result object containing a list of all ingredients in the fridge,
   *         sorted alphabetically. Or a message if there are no ingredients.
   */
  public Result<List<Ingredient>> getSortedIngredients() {
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();
    if (!sortedIngredients.isEmpty()) {
      return new Result<>(true, sortedIngredients, "Ingredients sorted alphabetically.");
    } else {
      return new Result<>(false, "There are no ingredients in the fridge.");
    }
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe The recipe to add to the cookbook.
   *
   * @return A Result object containing a success message if the recipe was added,
   *         or an error message if it failed.
   */
  public Result<Void> addRecipeToCookbook(Recipe recipe) {
    try {
      cookbook.addRecipe(recipe);
      return new Result<>(true, String.format("Recipe '%s' added to the cookbook!", recipe.getName()));
    } catch (IllegalArgumentException e) {
      return new Result<>(false, String.format("Failed to add recipe '%s' to cookbook: %s", recipe.getName(), e.getMessage()));
    }
  }

  /**
   * Creates a Recipe object using the provided name, description and instructions.
   *
   * @param name The name of the recipe.
   * @param description The description of the recipe.
   * @param instruction The instructions for following the recipe.
   *
   * @return A Result object containing the created Recipe if successful,
   *         or an error message if it failed.
   */
  public Result<Recipe> createRecipe(String name, String description, String instruction) {
    try {
      Recipe recipe = new Recipe(name, description, instruction);
      return new Result<>(true, recipe, String.format("Recipe '%s' was successfully created.", recipe.getName()));
    } catch (IllegalArgumentException e) {
      return new Result<>(false, String.format("Failed to create recipe '%s' due to: %s", name, e.getMessage()));
    }
  }

  /**
   * Creates an ingredient and adds it to a recipe.
   *
   * @param recipe The recipe to which the ingredient will be added.
   * @param name The name of the ingredient.
   * @param quantity The quantity of the ingredient the recipe requires.
   * @param unit The unit of measurement of the ingredient.
   *
   * @return A Result object containing a success message if the ingredient was added,
   *         or an error message if it failed.
   */
  public Result<Void> addIngredientToRecipe(Recipe recipe, String name, double quantity, Unit unit) {
    try {
      Ingredient ingredient = new Ingredient(name, quantity, unit);
      recipe.addIngredient(ingredient);
      return new Result<>(true, String.format("Ingredient '%s' was added to the recipe.", ingredient.getName()));
    } catch (IllegalArgumentException e) {
      return new Result<>(false, String.format("Failed to add ingredient '%s' to the recipe.", name));
    }
  }

  /**
   * Checks if the fridge has all ingredients necessary for a recipe.
   *
   * @param recipeName The name of the recipe to check.
   *
   * @return A Result object containing a message that all ingredients required
   *         are available, or a message that there are insufficient ingredients.
   */
  public Result<Void> checkRecipeIngredients(String recipeName) {
    boolean recipeAvailable = mealPlanner.verifyIngredientsForRecipe(recipeName);

    if (recipeAvailable) {
      return new Result<>(true, String.format("You have all the ingredients to make '%s'!", recipeName));
    } else {
      return new Result<>(false, String.format("You are missing ingredients to make %s.", recipeName));
    }
  }

  /**
   * Retrieves all recipes that has all required ingredients available in the fridge.
   *
   * @return A Result object containing a list of recipes that can be followed,
   *         or a message if there are none.
   */
  public Result<List<Recipe>> getSuggestedRecipes() {
    List<Recipe> recipes = mealPlanner.findSuggestedRecipes();

    if (!recipes.isEmpty()) {
      return new Result<>(true, recipes, "You have all the ingredients to make these recipes.");
    } else {
      return new Result<>(false, "You do not have sufficient ingredients for any recipe in the cookbook.");
    }
  }
}
