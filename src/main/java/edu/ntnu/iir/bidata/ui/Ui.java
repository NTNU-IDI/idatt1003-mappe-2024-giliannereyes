package edu.ntnu.iir.bidata.ui;

import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.utils.Manager;
import edu.ntnu.iir.bidata.utils.Result;
import java.time.LocalDate;

/**
 * The Ui class provides a text-based user interface for managing ingredients
 * and recipes. It handles user actions and interacts with the {@link Manager} to
 * handle operations related to ingredient and recipe management.
 */
public class Ui {
  private final InputHandler inputHandler;
  private final Manager manager;

  private final String INGREDIENT_HEADER = String.format(
      "%-15s %-10s %-10s %-15s %-15s %-10s%n%n",
      "Name", "Quantity", "Unit", "Expiry Date", "Price/Unit", "Total Price"
  );

  /**
   * Creates a new Ui instance.
   *
   * @param inputHandler The input handler for reading user input.
   * @param manager      The manager to handle business logic.
   */
  public Ui(InputHandler inputHandler, Manager manager) {
    this.inputHandler = inputHandler;
    this.manager = manager;
  }

  /**
   * Initializes the application by adding some ingredients and recipes to the fridge and cookbook.
   */
  private void init() {
    try {
      preAddIngredientsToFridge();
      preAddRecipesToCookbook();
    } catch (Exception e) {
      System.out.println("Failed to initialize the application: " + e.getMessage());
    }
  }

  /**
   * Pre-adds ingredients to fridge when application is initialized.
   */
  private void preAddIngredientsToFridge() {
    manager.addIngredientToFridge(
        "Milk", 1, 30, Unit.LITRE, LocalDate.now().plusDays(5));
    manager.addIngredientToFridge(
        "Egg", 6, 3, Unit.PIECE, LocalDate.now().plusDays(10));
    manager.addIngredientToFridge(
        "Flour", 2, 15.5, Unit.KILOGRAM, LocalDate.now().minusDays(30));
    manager.addIngredientToFridge(
        "Sugar", 1, 50, Unit.KILOGRAM, LocalDate.now().plusDays(60));
  }

  /**
   * Pre-adds recipes to cookbook when application is initialized.
   */
  private void preAddRecipesToCookbook() {
    manager.addRecipeToCookbook(
        new Recipe("Pancakes", "Delicious pancakes", "Mix ingredients and fry"));
    manager.addRecipeToCookbook(
        new Recipe("Omelette", "Simple omelette", "Whisk eggs and fry"));
    manager.addIngredientToRecipe(
        manager.getRecipeByName("Pancakes"), "Milk", 0.5, Unit.LITRE);
    manager.addIngredientToRecipe(
        manager.getRecipeByName("Pancakes"), "Egg", 2.0, Unit.PIECE);
    manager.addIngredientToRecipe(
        manager.getRecipeByName("Pancakes"), "Flour", 0.5, Unit.KILOGRAM);
    manager.addIngredientToRecipe(
        manager.getRecipeByName("Omelette"), "Egg", 2.0, Unit.PIECE);
    manager.addIngredientToRecipe(
        manager.getRecipeByName("Omelette"), "Milk", 0.5, Unit.LITRE);
  }

  /**
   * Starts the main menu loop, allowing the user to choose options until they choose to exit.
   */
  public void start() {
    init();
    boolean exit = false;
    while (!exit) {
      displayMenu();
      int choice = inputHandler.readInt("\nPlease choose an option: \n");
      exit = choice == 9;
      handleMenuChoice(choice);
    }
  }

  /**
   * Displays the main menu with options for managing ingredients and recipes.
   */
  private void displayMenu() {
    System.out.printf("""
            ------------------------------- MENU ---------------------------------
            ______________________________________________________________________
            %-40s %-40s%n%-40s %-40s%n%-40s %-40s%n%-40s %-40s%n%-40s
            ______________________________________________________________________
            """,
        "[1] Add Ingredient", "[2] Search Ingredient",
        "[3] Remove Ingredient", "[4] View Expiring Ingredients",
        "[5] View Sorted Ingredients", "[6] Add Recipe",
        "[7] Check Recipe Ingredients", "[8] Get Recipe Suggestions",
        "[9] Exit"
    );
  }

  /**
   * Handles the user's menu choice by executing the corresponding action.
   *
   * @param choice The user's choice.
   */
  private void handleMenuChoice(int choice) {
    switch (choice) {
      case 1 -> promptAddIngredient();
      case 2 -> promptSearchIngredient();
      case 3 -> promptDecreaseIngredientQuantity();
      case 4 -> promptCheckExpiringIngredients();
      case 5 -> displaySortedIngredients();
      case 6 -> promptAddRecipe();
      case 7 -> promptRecipeIngredientsCheck();
      case 8 -> displaySuggestedRecipes();
      case 9 -> System.out.println("Closing the application...");
      default -> System.out.println("Invalid option! Enter a valid number from 1-9.");
    }

    inputHandler.readEnter();
  }

  /**
   * Prompts the user to add a new ingredient to fridge and calls the manager to execute the operation.
   */
  public void promptAddIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");
    double pricePerUnit = inputHandler.readDouble("Enter ingredient's price per unit: ");
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");

    displayResult(manager.addIngredientToFridge(name, quantity, pricePerUnit, unit, expiryDate));
    // promptRetryOperationIfFailed(this::promptAddIngredient, result.isSuccess());
  }

  /**
   * Prompts the user to search for an ingredient.
   */
  private void promptSearchIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");
    displayResult(manager.searchForIngredient(name), INGREDIENT_HEADER);
    // promptRetryOperationIfFailed(this::promptSearchIngredient, result.isSuccess());
  }

  /**
   * Prompts the user for name and quantity of the ingredient to remove from fridge.
   */
  private void promptDecreaseIngredientQuantity() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Unit unit = inputHandler.readUnit("Enter the unit of the quantity to remove: ");
    double quantity = inputHandler.readDouble("Enter quantity to remove: ");

    displayResult(manager.decreaseIngredientQuantity(name, unit, quantity));
  }

  /**
   * Prompts the user for an expiry date to check expiring ingredients.
   */
  private void promptCheckExpiringIngredients() {
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");
    displayResult(manager.checkExpiringIngredients(expiryDate), INGREDIENT_HEADER);
  }

  /**
   * Displays all ingredients in fridge, sorted alphabetically.
   */
  private void displaySortedIngredients() {
    displayResult(manager.getSortedIngredients(), INGREDIENT_HEADER);
  }

  /**
   * Prompts the user for name, description, instruction and ingredients of a recipe to add.
   */
  private void promptAddRecipe() {
    String name = inputHandler.readString("Enter recipe name: ");
    String description = inputHandler.readString("Enter recipe description: ");
    String instruction = inputHandler.readString("Enter recipe instruction: ");

    Result<Recipe> result = manager.createRecipe(name, description, instruction);
    displayResult(result);

    if (result.isSuccess()) {
      result.getData().ifPresent(recipe -> {
        addIngredientsToRecipe(recipe);
        System.out.println(manager.addRecipeToCookbook(recipe).getMessage());
      });
    }
  }

  /**
   * Loop that prompts user to add ingredients until they exit.
   *
   * @param recipe The recipe to add ingredients to.
   */
  private void addIngredientsToRecipe(Recipe recipe) {
    System.out.println("You are now adding ingredients to recipe: " + recipe.getName());

    boolean addingIngredients = true;
    while (addingIngredients) {
      promptAddIngredient(recipe);
      addingIngredients = inputHandler.readYes("Would you like to continue adding ingredients?");
    }
  }

  /**
   * Prompts the user for an ingredient to add to recipe.
   *
   * @param recipe The recipe to add the ingredient to.
   */
  private void promptAddIngredient(Recipe recipe) {
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");

    displayResult(manager.addIngredientToRecipe(recipe, name, quantity, unit));
  }

  /**
   * Displays recipes that has all required ingredients available in the fridge.
   */
  public void displaySuggestedRecipes() {
    displayResult(manager.getSuggestedRecipes());
  }

  /**
   * Prompts user for a recipe to check if the required ingredients are in the fridge.
   */
  public void promptRecipeIngredientsCheck() {
    String name = inputHandler.readString("Enter recipe name: ");
    displayResult(manager.checkRecipeIngredients(name));
  }

  /**
   * Displays the result in the console in a string representation.
   *
   * @param result The result object that contains the operation's result.
   * @param <T>    A generic datatype.
   */
  private <T> void displayResult(Result<T> result) {
    displayResult(result, "");
  }

  /**
   * Displays the result in the console in a string representation, including a header.
   *
   * @param result The result object that contains the operation's result.
   * @param header The header required for the result.
   * @param <T>    A generic datatype.
   */
  private <T> void displayResult(Result<T> result, String header) {
    System.out.println("\n" + result.getMessage());

    if (result.isSuccess() && !header.isBlank()) {
      System.out.print(header);
    }

    System.out.println(result.getFormattedResult());
  }
}

