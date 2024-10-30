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

public class Manager {
  private final Fridge fridge;
  private final Cookbook cookbook;
  private final MealPlanner mealPlanner;
  private final InputHandler inputHandler;

  public Manager(Fridge fridge, Cookbook cookbook, MealPlanner mealPlanner, InputHandler inputHandler) {
    this.fridge = fridge;
    this.cookbook = cookbook;
    this.mealPlanner = mealPlanner;
    this.inputHandler = inputHandler;
  }

  /**
   * JavaDocs!
   */
  public void createIngredient() {
    // Reads ingredient details from user
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");
    double pricePerUnit = inputHandler.readDouble("Enter ingredient's price per unit: ");
    LocalDate expiryDate = inputHandler.readDate("Enter ingredient's expiry date: ");

    // Attempts to create a new ingredient
    try {
      Ingredient ingredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
      fridge.addIngredient(ingredient);
      System.out.println("New ingredient added to fridge!");

      // If the ingredient details are invalid
    } catch (IllegalArgumentException e) {
      System.out.printf("""
          Ingredient could not be added due to:
          %s
          """, e.getMessage());
    }
  }

  /**
   * JavaDocs!
   */
  public void searchForIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Optional<Ingredient> ingredient = fridge.findIngredientByName(name);

    // Shows ingredient information if ingredient exists
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
   * Javadocs!
   */
  public void decreaseIngredientQuantity() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Unit unit = inputHandler.readUnit("Enter the unit of the quantity to remove: ");
    double quantity = inputHandler.readDouble("Enter quantity to remove: ");

    try {
      fridge.decreaseIngredientQuantity(name, quantity, unit);
      System.out.printf("%.2f %s of %s was successfully removed from the fridge!\n", quantity, unit, name);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  public void checkExpiringIngredients() {
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");

    try {
      List<Ingredient> ingredients = fridge.findIngredientsBeforeDate(expiryDate);
      System.out.println("Here are the ingredients that expire before " + expiryDate + ":");

      for (Ingredient ingredient : ingredients) {
        System.out.println(ingredient);
      }

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * JavaDocs!
   */
  public void showSortedIngredients() {
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();

    try {
      System.out.println("Ingredients in the fridge sorted alphabetically: ");

      for (Ingredient ingredient : sortedIngredients) {
        System.out.println(ingredient);
      }

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * JavaDocs!
   */
  public void createRecipe() {
    String recipeName = inputHandler.readString("Enter recipe name: ");
    String description = inputHandler.readString("Enter recipe description: ");
    String instruction = inputHandler.readString("Enter recipe instruction: ");

    Recipe recipe = new Recipe(recipeName, description, instruction);

    boolean addingIngredients = true;
    while (addingIngredients) {
      String ingredientName = inputHandler.readString("Enter ingredient name: ");
      double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
      Unit unit = inputHandler.readUnit("Enter ingredient unit: ");
      double pricePerUnit = inputHandler.readDouble("Enter ingredient price per unit: ");
      LocalDate expiryDate = inputHandler.readDate("Enter expiry date: ");

      Ingredient ingredient = new Ingredient(ingredientName, quantity, pricePerUnit, unit, expiryDate);
      recipe.addIngredient(ingredient);

      int choice = inputHandler.readInt("Would you like to continue adding ingredients?\n [1] Yes [2] No ");

      if (choice == 2) {
        addingIngredients = false;
      }
    }

    cookbook.addRecipe(recipe);
    System.out.println("Recipe successfully added to cookbook!");
  }

  public void checkRecipeIngredients() {
    String name = inputHandler.readString("Enter recipe name: ");

    try {
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

  public void getSuggestedDishes() {
    try {
      List<Recipe> recipes = mealPlanner.getSuggestedRecipes();

      for (Recipe recipe : recipes) {
        System.out.println(recipe);
      }

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

}
