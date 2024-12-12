package edu.ntnu.idi.idatt.service;

import edu.ntnu.idi.idatt.domain.Cookbook;
import edu.ntnu.idi.idatt.domain.Fridge;
import edu.ntnu.idi.idatt.domain.Ingredient;
import edu.ntnu.idi.idatt.domain.MealPlanner;
import edu.ntnu.idi.idatt.domain.Recipe;
import edu.ntnu.idi.idatt.domain.Unit;
import edu.ntnu.idi.idatt.utils.Result;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

/**
 * Manages the fridge, cookbook and meal planning functionalities.
 *
 * <p>Orchestrates interactions between the domain classes and the user interface,
 * by delegating user input to the appropriate model classes and
 * returning the results to the UI. </p>
 *
 * <p>It also contains pre-population data for {@link Fridge} and {@link Cookbook}.</p>
 *
 * @author Gilianne Reyes
 * @version 1.3
 * @since 1.1
 */
public class FoodManagementService {
  private final Fridge fridge;
  private final Cookbook cookbook;
  private final MealPlanner mealPlanner;

  /**
   * Constructs a FoodManagementService instance with a {@link Fridge}, {@link Cookbook}
   * and {@link MealPlanner}.
   */
  public FoodManagementService() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook();
    this.mealPlanner = new MealPlanner(fridge, cookbook);
  }

  /**
   * Adds a new ingredient to the fridge.
   *
   * @param name is the name of the ingredient.
   * @param quantity is the quantity of the ingredient.
   * @param unit is the unit of measurement.
   * @param pricePerUnit is the price per unit.
   * @param expiryDate is the ingredient's expiry date.
   *
   * @return A {@link Result} object containing a success message if the ingredient was added,
   *        or a failure message if it failed.
   */
  public Result<Void> addIngredientToFridge(
      String name, double quantity, double pricePerUnit, Unit unit, LocalDate expiryDate
  ) {
    return handleOperation(() -> {
      Ingredient newIngredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
      fridge.addIngredient(newIngredient);
      return Result.success(String.format("Ingredient '%s' was added to the fridge!", name));
    });
  }

  /**
   * Searches for ingredients in the fridge by name.
   *
   * @param name is the name of the ingredient.
   *
   * @return {@link Result} object containing a list of ingredients if found,
   *        or a failure message if none were found.
   */
  public Result<List<Ingredient>> findIngredient(String name) {
    return handleOperation(() -> {
      List<Ingredient> ingredients = fridge.findIngredientsByName(name);
      if (!ingredients.isEmpty()) {
        return Result.success(
            String.format("Ingredient(s) with the name '%s' found in the fridge.", name),
            ingredients
        );
      } else {
        return Result.failure(String.format("Ingredient with the name '%s' not found.", name));
      }
    });
  }

  /**
   * Decreases the quantity of a specified ingredient in the fridge.
   *
   * @param name is the name of the ingredient.
   * @param quantity is the quantity to decrease the ingredient with.
   * @param unit is the unit of measurement of the quantity to decrease.
   * @param expiryDate is the expiry date of the ingredient.
   *
   * @return A {@link Result} object containing a success message if the ingredient was removed,
   *       or a failure message if it failed.
   */
  public Result<Void> removeIngredientFromFridge(
      String name, Unit unit, double quantity, LocalDate expiryDate
  ) {
    return handleOperation(() -> {
      fridge.removeIngredient(name, quantity, unit, expiryDate);
      return Result.success(String.format(
          "%.2f %s of '%s' was removed from the fridge!", quantity, unit.getSymbol(), name)
      );
    });
  }

  /**
   * Searches for ingredients in the fridge that are expiring before a specified date.
   *
   * @param date is the cut-off date to check for expiring ingredients.
   *
   * @return A {@link Result} object containing a list of expiring ingredients if found,
   *       or a failure message if there are none found.
   */
  public Result<List<Ingredient>> findIngredientsExpiringBefore(LocalDate date) {
    return handleOperation(() -> {
      List<Ingredient> expiringIngredients = fridge.findExpiringIngredientsBeforeDate(date);
      if (!expiringIngredients.isEmpty()) {
        return Result.success(
            String.format("Ingredients that expire before: %s", date),
            expiringIngredients
        );
      } else {
        return Result.failure(
            String.format("There are no ingredients that expire before %s.", date.toString())
        );
      }
    });
  }

  /**
   * Retrieves all the ingredients in the fridge, sorted alphabetically.
   *
   * @return A {@link Result} object containing a list of sorted ingredients if found,
   *      or a failure message if there are none.
   */
  public Result<List<Ingredient>> findSortedIngredients() {
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();
    if (!sortedIngredients.isEmpty()) {
      return Result.success(
          "All ingredients in the fridge, sorted alphabetically.", sortedIngredients
      );
    } else {
      return Result.failure("There are no ingredients in the fridge.");
    }
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe is the recipe to add to the cookbook.
   *
   * @return A {@link Result} object containing a success message if the recipe was added,
   *      or a failure message if it failed.
   */
  public Result<Void> addRecipe(Recipe recipe) {
    return handleOperation(() -> {
      cookbook.addRecipe(recipe);
      return Result.success("The recipe was added to the cookbook!");
    });
  }

  /**
   * Creates a Recipe object using the provided name, description and instructions.
   *
   * @param name is the name of the recipe.
   * @param description is the description of the recipe.
   * @param instruction is the instruction for following the recipe.
   *
   * @return A {@link Result} object containing the created recipe if successful,
   *        or a failure message if it failed.
   */
  public Result<Recipe> createRecipe(String name, String description, String instruction) {
    return handleOperation(() -> {
      Recipe recipe = new Recipe(name, description, instruction);
      return Result.success(
          String.format("Recipe '%s' was successfully created.", recipe.getName()),
          recipe
      );
    });
  }

  /**
   * Creates an ingredient and adds it to a recipe.
   *
   * @param recipe is the recipe to which the ingredient will be added.
   * @param name is the name of the ingredient.
   * @param quantity is the quantity of the ingredient the recipe requires.
   * @param unit is the unit of measurement of the ingredient.
   *
   * @return A {@link Result} object containing a success message if the ingredient was added,
   *       or a failure message if it failed.
   */
  public Result<Void> addIngredientToRecipe(
      Recipe recipe, String name, double quantity, Unit unit
  ) {
    return handleOperation(() -> {
      Ingredient ingredient = new Ingredient(name, quantity, unit);
      recipe.addIngredient(ingredient);
      return Result.success(
          String.format("Ingredient '%s' was added to the recipe.", ingredient.getName())
      );
    });
  }

  /**
   * Retrieves recipes that has all required ingredients available in the fridge.
   *
   * @return A {@link Result} object containing a list of recipes if found,
   *      or a failure message if there are none.
   */
  public Result<List<Recipe>> findSuggestedRecipes() {
    return handleOperation(() -> {
      List<Recipe> recipes = mealPlanner.findRecipesWithAvailableIngredients();
      if (!recipes.isEmpty()) {
        return Result.success(
            "These recipes can be prepared with ingredients in the fridge.", recipes
        );
      } else {
        return Result.failure("No recipes can be prepared with the ingredients in the fridge.");
      }
    });
  }

  /**
   * Checks if the fridge has all ingredients necessary for a recipe.
   *
   * @param recipeName The name of the recipe to check.
   *
   * @return A {@link Result} object containing a success message if all ingredients are available,
   *     or a failure message if there are missing ingredients.
   */
  public Result<Void> verifyRecipeAvailability(String recipeName) {
    return handleOperation(() -> {
      boolean recipeAvailable = mealPlanner.areIngredientsAvailableForRecipe(recipeName);
      if (recipeAvailable) {
        return Result.success(
            String.format("You have all the ingredients to make %s!", recipeName)
        );
      } else {
        return Result.failure(String.format("You are missing ingredients to make %s!", recipeName));
      }
    });
  }

  /**
   * Retrieves all recipes in the cookbook.
   *
   * @return A {@link Result} object containing a list of sorted recipes if found,
   *      or a failure message if there are none.
   */
  public Result<List<Recipe>> findAllRecipes() {
    return handleOperation(() -> {
      List<Recipe> recipes = cookbook.getRecipes();
      if (!recipes.isEmpty()) {
        return Result.success(
            "All recipes in the cookbook.", recipes
        );
      } else {
        return Result.failure("There are no recipes in the cookbook.");
      }
    });
  }

  /**
   * Calculates the total price of ingredients in the fridge that is
   * expiring before a specified date, and returns the total price.
   *
   * @param date is the date to check if the ingredient is expired.
   *
   * @return A {@link Result} object containing the total price of the ingredients if successful,
   *     or a failure message if it failed.
   */
  public Result<String> calculateExpiringValueByDate(LocalDate date) {
    return handleOperation(() -> {
      double value = fridge.calculateExpiringValueByDate(date);
      String valueString = String.format("%.2f kr", value);
      return Result.success(
          "The total value of ingredients expiring before " + date.toString(),
          valueString
      );
    });
  }

  /**
   * Calculates the price of all ingredients in the fridge.
   *
   * @return A {@link Result} object containing the total price of the ingredients if successful,
   *      or a failure message if it failed.
   */
  public Result<String> calculateFridgeValue() {
    return handleOperation(() -> {
      double value = fridge.calculateTotalValue();
      if (value == 0) {
        return Result.failure("There value of the fridge is 0.0 kr, as there are no ingredients.");
      }
      String valueString = String.format("%.2f kr", value);
      return Result.success("The total value of ingredients in the fridge is:", valueString);
    });
  }

  /**
   * Handles a given operation, catches any exceptions and
   * returns a {@link Result} object with the outcome.
   *
   * @param operation is the operation to perform.
   * @param <T> is the type of the result.
   *
   * @return A {@link Result} object containing the outcome of the operation.
   */
  private <T> Result<T> handleOperation(Supplier<Result<T>> operation) {
    try {
      return operation.get();
    } catch (IllegalArgumentException e) {
      return Result.failure("Operation failed.", e.getMessage());
    } catch (Exception e) {
      return Result.failure("Unexpected error occurred.", e.getMessage());
    }
  }

  /**
   * Populates the fridge and cookbook with ingredients and recipes.
   */
  public void populateFridgeAndCookbook() {
    preAddIngredientsToFridge();
    preAddRecipesToCookbook();
  }

  /**
   * Adds pre-defined ingredients to the fridge.
   */
  private void preAddIngredientsToFridge() {
    List<Ingredient> ingredients = List.of(
        new Ingredient("Milk", 1, 30, Unit.LITRE, LocalDate.now().plusDays(5)),
        new Ingredient("Egg", 6, 3, Unit.PIECE, LocalDate.now().plusDays(10)),
        new Ingredient("Flour", 2, 15.5, Unit.KILOGRAM, LocalDate.now().minusDays(30)),
        new Ingredient("Sugar", 1, 50, Unit.KILOGRAM, LocalDate.now().plusDays(60))
    );
    ingredients.forEach(fridge::addIngredient);
  }

  /**
   * Adds pre-defined recipes to the cookbook.
   */
  private void preAddRecipesToCookbook() {
    HashMap<Recipe, List<Ingredient>> recipeIngredientsMap = new HashMap<>();
    recipeIngredientsMap.put(
        new Recipe("Pancakes", "Delicious pancakes", "Mix ingredients and fry"),
        List.of(
            new Ingredient("Milk", 0.5, Unit.LITRE),
            new Ingredient("Egg", 2.0, Unit.PIECE),
            new Ingredient("Flour", 0.5, Unit.KILOGRAM)
        ));

    recipeIngredientsMap.put(
        new Recipe("Omelette", "Simple omelette", "Whisk eggs and fry"),
        List.of(
            new Ingredient("Egg", 2.0, Unit.PIECE),
            new Ingredient("Milk", 0.5, Unit.LITRE)
        ));

    recipeIngredientsMap.forEach((recipe, ingredients) -> {
      cookbook.addRecipe(recipe);
      ingredients.forEach(recipe::addIngredient);
    });
  }
}
