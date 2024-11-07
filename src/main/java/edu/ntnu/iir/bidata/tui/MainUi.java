package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.manager.Manager;
import edu.ntnu.iir.bidata.manager.MealPlanner;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.storage.Fridge;

/**
 * The MainUi class is responsible for managing the main user interaction
 * of the application. It serves as an entry point, and delegates further user interaction
 * handling to {@link IngredientUi} and {@link RecipeUi}.
 * It provides various menus for managing ingredients and recipes.
 */
public class MainUi {
  private InputHandler inputHandler;
  private IngredientUi ingredientUi;
  private RecipeUi recipeUi;

  /**
   * Constructs a new instance of the Ui class.
   * Initializes the required dependencies for managing the user interaction.
   */
  public MainUi() {
    init();
  }

  /**
   * Initializes new Fridge, Cookbook, MealPlanner, Manager, InputHandler, IngredientUi,
   * RecipeUi instances that are required for the application to function.
   */
  private void init() {
    Fridge fridge = new Fridge();
    Cookbook cookbook = new Cookbook();
    MealPlanner mealPlanner = new MealPlanner(fridge, cookbook);
    Manager manager = new Manager(fridge, cookbook, mealPlanner);
    this.inputHandler = new InputHandler();
    this.ingredientUi = new IngredientUi(manager, inputHandler);
    this.recipeUi = new RecipeUi(manager, inputHandler);
  }

  /**
   * Starts the main application by displaying the main menu.
   */
  public void start() {
      mainMenu();
  }

  /**
   * Displays the main menu and prompts the user to choose an option.
   * Leads the user to another menu or exits the application, depending on the choice.
   */
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

      int choice = inputHandler.readInt("\nPlease choose an option: \n");

      switch (choice) {
        case 1 -> manageIngredientsMenu();
        case 2 -> viewIngredientsMenu();
        case 3 -> recipeMenu();
        case 4 -> {
          System.out.println("\nClosing the application...");
          exitMainMenu = true;
          inputHandler.close();
        }
      }
    }
  }

  /**
   * Displays a menu for managing ingredients and prompts the user to choose
   * an option. Calls on the appropriate methods in the IngredientUi class
   * for further user interaction, depending on the choice.
   */
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
        case 1 -> ingredientUi.promptAddIngredient();
        case 2 -> ingredientUi.promptSearchIngredient();
        case 3 -> ingredientUi.promptDecreaseIngredientQuantity();
        default -> System.out.println("Invalid input. Try again.");
      }

      inputHandler.waitForKeyPress();
    }
  }

  /**
   * Displays a menu for viewing ingredients and prompts the user to choose
   * an option. Calls on the appropriate methods in the IngredientUi class
   * for further user interaction, depending on the choice.
   */
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
        case 1 -> ingredientUi.promptCheckExpiringIngredients();
        case 2 -> ingredientUi.displaySortedIngredients();
        default -> System.out.println("Invalid input. Try again.");
      }

      inputHandler.waitForKeyPress();
    }
  }

  /**
   * Displays a menu for managing and viewing recipes, and prompts the user to choose
   * an option. Calls on the appropriate methods in the RecipeUi class
   * for further user interaction, depending on the choice.
   */
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
        case 1 -> recipeUi.promptAddRecipe();
        case 2 -> recipeUi.promptRecipeIngredientsCheck();
        case 3 -> recipeUi.displaySuggestedRecipes();
        default -> System.out.println("Invalid input. Try again.");
      }

      inputHandler.waitForKeyPress();
    }
  }

}
