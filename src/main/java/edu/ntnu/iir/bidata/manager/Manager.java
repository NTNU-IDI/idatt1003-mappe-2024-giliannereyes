package edu.ntnu.iir.bidata.manager;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.storage.Fridge;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.tui.InputHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
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
  private final InputHandler inputHandler;

  /**
   * Constructs a new Manager instance.
   *
   * @param fridge The fridge that stores ingredients.
   * @param cookbook The cookbook that contains recipes.
   * @param mealPlanner The meal planner for suggesting and verifying recipes.
   * @param inputHandler The input handler to receive user input.
   */
  public Manager(Fridge fridge, Cookbook cookbook, MealPlanner mealPlanner, InputHandler inputHandler) {
    this.fridge = fridge;
    this.cookbook = cookbook;
    this.mealPlanner = mealPlanner;
    this.inputHandler = inputHandler;
  }

  /**
   * Creates an ingredient for either the fridge or a recipe,
   * depending on the type provided.
   *
   * @param type The type of ingredient to create, either "fridge" or "recipe".
   *
   * @return The created Ingredient object.
   */
  private Ingredient createIngredient(String type) {
    // Reads the common ingredient details from user
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");

    // If the ingredient is for the fridge, ask for additional details
    if ("fridge".equals(type)) {
      double pricePerUnit = inputHandler.readDouble("Enter ingredient's price per unit: ");
      LocalDate expiryDate = inputHandler.readDate("Enter ingredient's expiry date: ");
      return new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
    }

    // For recipe ingredients, set placeholders for price per unit and expiry date
    return new Ingredient(name, quantity, 0, unit, LocalDate.now());
  }

  /**
   * Adds a new ingredient to the fridge based on ingredient details
   * from the user.
   */
  public void addIngredient() {
    // Creates an ingredient for the fridge
    try {
      Ingredient ingredient = createIngredient("fridge");
      fridge.addIngredient(ingredient);
      System.out.println("New ingredient added to fridge!");
    } catch (IllegalArgumentException e) {
      System.out.println("Ingredient could not be added due to:\n" + e.getMessage());
    }
  }

  /**
   * Searches for an ingredient in the fridge by name and displays its details.
   */
  public void searchForIngredient() {
    // Prompts the user for the ingredient name to search
    String name = inputHandler.readString("Enter ingredient name: ");
    Optional<Ingredient> ingredient = fridge.findIngredientByName(name);

    // Displays the ingredient details if found, otherwise notify the user
    if (ingredient.isPresent()) {
      System.out.printf("""
          Ingredient found!
          Here are the %s's information:
          %s
          """, ingredient.get().getName(), ingredient.get());
    } else {
      System.out.println("Ingredient not found!");
    }
  }

  /**
   * Decreases the quantity of a specified ingredient in the fridge.
   */
  public void decreaseIngredientQuantity() {
    // Reads the details from the user about the ingredient to remove
    String name = inputHandler.readString("Enter ingredient name: ");
    Unit unit = inputHandler.readUnit("Enter the unit of the quantity to remove: ");
    double quantity = inputHandler.readDouble("Enter quantity to remove: ");

    // Attempt to decrease the quantity in the fridge
    try {
      fridge.decreaseIngredientQuantity(name, quantity, unit);
      System.out.printf("%.2f %s of %s was successfully removed from the fridge!\n", quantity, unit.getSymbol(), name);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Displays ingredients in the fridge that are expiring before a specified date.
   */
  public void checkExpiringIngredients() {
    // Prompts the user for an expiry date to check
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");
    List<Ingredient> expiringIngredients = fridge.findIngredientsBeforeDate(expiryDate);

    // Display the ingredients expiring before the date, or notify if none found
    if (!expiringIngredients.isEmpty()) {
      System.out.println("Here are the ingredients that expire before " + expiryDate + ":\n");
      expiringIngredients.forEach(System.out::println);
    } else {
      System.out.println("There are no ingredients that expire before " + expiryDate);
    }
  }

  /**
   * Displays all the ingredients in the fridge, sorted alphabetically.
   */
  public void showSortedIngredients() {
    // Retrieve all the ingredients sorted alphabetically
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();

    // Display all the ingredients, or notify if none found
    if (!sortedIngredients.isEmpty()) {
      System.out.println("Ingredients in the fridge sorted alphabetically: ");
      sortedIngredients.forEach(System.out::println);
    } else {
      System.out.println("There are currently no ingredients in the fridge.");
    }
  }

  /**
   * Adds a new recipe to the cookbook. Prompts the user for recipe details and ingredients.
   */
  public void addRecipe() {
    // Create a new recipe and add to cookbook
    try {
      Recipe recipe = createRecipe();
      addIngredientsToRecipe(recipe);
      cookbook.addRecipe(recipe);
      System.out.println("Recipe successfully added to cookbook!");
    } catch (IllegalArgumentException e) {
      System.out.println("Recipe could not be added due to:\n" + e.getMessage());
    }
  }

  /**
   * Creates a Recipe object by prompting the user for name, description and instructions.
   *
   * @return The created Recipe object.
   */
  private Recipe createRecipe() {
    // Prompt the user for recipe details
    String recipeName = inputHandler.readString("Enter recipe name: ");
    String description = inputHandler.readString("Enter recipe description: ");
    String instruction = inputHandler.readString("Enter recipe instruction: ");
    return new Recipe(recipeName, description, instruction);
  }

  /**
   * Adds ingredients to a specific recipe. Prompts the user to add ingredients
   * and ingredient details.
   *
   * @param recipe The Recipe object to which ingredients will be added to.
   */
  private void addIngredientsToRecipe(Recipe recipe) {
      boolean addingIngredients = true;

      while (addingIngredients) {
        try {
          // Create a new ingredient to add to the recipe
          Ingredient newIngredient = createIngredient("recipe");
          recipe.addIngredient(newIngredient);
          System.out.println("New ingredient added to the recipe!");
        } catch (IllegalArgumentException e) {
          System.out.println("Ingredient could not be added due to:\n" + e.getMessage());
        }

        // Prompt the user if they want to add more recipes
        System.out.println("Would you like to continue adding ingredients?");
        int choice = inputHandler.readInt("[1] Yes\n [2] No\n");
        addingIngredients = (choice == 1);
      }
  }

  /**
   * Checks if the fridge has all ingredients necessary for a specific recipe.
   */
  public void checkRecipeIngredients() {
    // Prompt the user for the name of the recipe to check
    String name = inputHandler.readString("Enter recipe name: ");

    try {
      // Check if the fridge contains all ingredients for the recipe
      boolean recipeAvailable = mealPlanner.verifyIngredientsForRecipe(name);

      if (recipeAvailable) {
        System.out.println("You have all the ingredients to make " + name + "!");
      } else {
        System.out.println("You do not have sufficient ingredients to make " + name + "!");
      }
    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Suggests dishes that can be made with the available ingredients in the fridge.
   */
  public void getSuggestedDishes() {
    // Find recipes that has sufficient ingredients in the fridge.
    List<Recipe> recipes = mealPlanner.findSuggestedRecipes();

    // Display all the recipes that can be used, or notify if none
    if (!recipes.isEmpty()) {
      System.out.println("Ingredients in the fridge sorted alphabetically: ");
      recipes.forEach(System.out::println);
    } else {
      System.out.println("There are not sufficient ingredients for any recipe.");
    }
  }
}
