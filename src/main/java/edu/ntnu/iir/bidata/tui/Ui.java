package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.manager.IngredientManager;
import edu.ntnu.iir.bidata.manager.MealPlanner;
import edu.ntnu.iir.bidata.manager.RecipeManager;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.storage.Fridge;

import java.util.Scanner;

public class Ui {
  private Fridge fridge;
  private Cookbook cookbook;
  private MealPlanner mealPlanner;
  private InputHandler inputHandler;
  private IngredientManager ingredientManager;
  private RecipeManager recipeManager;
  private Scanner scanner;

  /**
   * Constructs a new instance of the Ui class.
   *
   */
  public Ui() {
    init();
  }

  /**
   * Initializes new Fridge, Cookbook, MealPlanner, IngredientManager instances.
   */
  private void init() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook();
    this.mealPlanner = new MealPlanner(fridge, cookbook);
    this.inputHandler = new InputHandler();
    this.ingredientManager = new IngredientManager(fridge, inputHandler);
    this.recipeManager = new RecipeManager(inputHandler, cookbook, mealPlanner);
    this.scanner = new Scanner(System.in);
  }

  public void start() {
      mainMenu();
  }

  private void mainMenu() {
    boolean exitMainMenu = false;
    while (!exitMainMenu) {
      System.out.print("""
        \n---------- MAIN MENU ----------
        [1] Manage ingredients - Add, search or remove ingredients.
        [2] View ingredients - Check expiry dates and get a sorted list.
        [3] Recipes - Create, check availability and get suggestions.
        [4] Exit - Close the application.
        """);

      int choice = inputHandler.readInt("\nPlease choose an option: ");

      switch (choice) {
        case 1 -> manageIngredientsMenu();
        case 2 -> viewIngredientsMenu();
        case 3 -> recipeMenu();
        case 4 -> {
          System.out.println("\nClosing the application...");
          exitMainMenu = true;
        }
      }
    }
  }

  private void manageIngredientsMenu() {
    while (true) {
      System.out.print("""
        \n---------- MANAGE INGREDIENTS ----------
        [1] Add a new ingredient to the fridge.
        [2] Search for an ingredient.
        [3] Remove a quantity of an ingredient from the fridge.
        [4] Return to main menu.
        """);

      int choice = inputHandler.readInt("\nPlease choose an option: ");

      if (choice == 4) {
        break;
      }

      switch (choice) {
        case 1 -> ingredientManager.createIngredient();
        case 2 -> ingredientManager.searchForIngredient();
        case 3 -> ingredientManager.decreaseIngredientQuantity();
        default -> System.out.println("Invalid input. Try again.");
      }

      System.out.println("\nPress enter to go back to menu 'Manage Ingredients'.");
      scanner.nextLine();
    }
  }

  private void viewIngredientsMenu() {
    while (true) {
      System.out.print("""
        \n---------- VIEW INGREDIENTS ----------
        [1] Check ingredients near expiry.
        [2] View alphabetically sorted ingredients.
        [3] Return to main menu.
        """);

      int choice = inputHandler.readInt("\nPlease choose an option: ");

      if (choice == 3) {
        break;
      }

      switch (choice) {
        case 1 -> ingredientManager.checkExpiringIngredients();
        case 2 -> ingredientManager.showSortedIngredients();
        default -> System.out.println("Invalid input. Try again.");
      }

      System.out.println("\nPress enter to go back to menu 'View Ingredients'.");
      scanner.nextLine();
    }
  }

  private void recipeMenu() {
    while (true) {
      System.out.print("""
        \n---------- MANAGE RECIPES ----------
        [1] Create a recipe.
        [2] Check ingredient availability for a recipe.
        [3] Get suggestions for dishes that can be made.
        [4] Return to main menu.
        """);

      int choice = inputHandler.readInt("\nPlease choose an option: ");

      if (choice == 4) {
        break;
      }

      switch (choice) {
        case 1 -> recipeManager.createRecipe();
        case 2 -> recipeManager.checkRecipeIngredients();
        case 3 -> recipeManager.getSuggestedDishes();
        default -> System.out.println("Invalid input. Try again.");
      }
    }

    System.out.println("\nPress enter to go back to menu 'Recipe'.");
    scanner.nextLine();
  }

}
