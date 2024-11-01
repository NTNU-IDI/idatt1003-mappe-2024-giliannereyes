package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.manager.Manager;
import edu.ntnu.iir.bidata.manager.MealPlanner;
import edu.ntnu.iir.bidata.storage.Cookbook;
import edu.ntnu.iir.bidata.storage.Fridge;

import java.util.Scanner;

public class Ui {
  private InputHandler inputHandler;
  private Manager manager;
  private Scanner scanner;

  /**
   * Constructs a new instance of the Ui class.
   */
  public Ui() {
    init();
  }

  /**
   * Initializes new Fridge, Cookbook, MealPlanner, Manager instances.
   */
  private void init() {
    Fridge fridge = new Fridge();
    Cookbook cookbook = new Cookbook();
    MealPlanner mealPlanner = new MealPlanner(fridge, cookbook);
    this.inputHandler = new InputHandler();
    this.manager = new Manager(fridge, cookbook, mealPlanner,inputHandler);
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
        case 1 -> manager.addIngredient();
        case 2 -> manager.searchForIngredient();
        case 3 -> manager.decreaseIngredientQuantity();
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
        case 1 -> manager.checkExpiringIngredients();
        case 2 -> manager.showSortedIngredients();
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
        case 1 -> manager.addRecipe();
        case 2 -> manager.checkRecipeIngredients();
        case 3 -> manager.getSuggestedDishes();
        default -> System.out.println("Invalid input. Try again.");
      }
    }

    System.out.println("\nPress enter to go back to menu 'Recipe'.");
    scanner.nextLine();
  }

}
