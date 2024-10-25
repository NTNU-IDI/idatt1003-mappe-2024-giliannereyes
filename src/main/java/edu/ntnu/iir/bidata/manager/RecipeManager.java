package edu.ntnu.iir.bidata.manager;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.tui.InputHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public class RecipeManager {
  private final InputHandler inputHandler;
  private final Cookbook cookbook;
  private final MealPlanner mealPlanner;

  // Constructor
  public RecipeManager(InputHandler inputHandler, Cookbook cookbook, MealPlanner mealPlanner) {
    this.inputHandler = inputHandler;
    this.cookbook = cookbook;
    this.mealPlanner = mealPlanner;
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
