package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * UserInterface represents a text-based user interface.
 * <br><br>
 * UserInterface supports the following functionalities:
 * <ul>
 *   <lI>Add or remove an ingredient from the fridge </lI>
 *   <li>Search for a specific ingredient in the fridge </li>
 *   <li>Display all ingredients or only expired ingredients in the fridge </li>
 *   <li>Calculate the total value of ingredients</li>
 *   <li>Add a new recipe to the cookbook</li>
 *   <li>Show all recipes that can be made with the available ingredients</li>
 *   <li>Check if a specific recipe can be made</li>
 * </ul>
 */
public class UserInterface {
  private final Fridge fridge;
  private final Cookbook cookbook;
  private final Scanner scanner;

  /**
   * Constructs a new UserInterface instance.
   * Initializes a {@link Fridge}, a {@link Cookbook} and a {@link Scanner}.
   */
  public UserInterface() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook();
    this.scanner = new Scanner(System.in);
  }

  // public void init() {}

  /**
   * Starts the main loop of the user interface to provide a menu-based interaction.
   * <br>
   * The menu offers the following operations:
   * <ul>
   *   <li>Add or remove an ingredient from the fridge</li>
   *   <li>Search for a specific ingredient in the fridge</li>
   *   <li>Display all ingredients or only expired ingredients in the fridge</li>
   *   <li>Calculate the total value of ingredients</li>
   *   <li>Add a new recipe to the cookbook</li>
   *   <li>Show all recipes that can be made with the available ingredients</li>
   *   <li>Check if a specific recipe can be made</li>
   *   <li>Exit the application</li>
   * </ul>
   */
  public void start() {
    boolean running = true;
    while (running) {
      displayMenu();

      int choice = readInt();
      System.out.println();

      switch (choice) {
        case 1 -> {
          System.out.println("\nYou chose to add a new ingredient.\n");
          addIngredient();
        }
        case 2 -> {
          System.out.println("\nYou chose to remove an amount of an ingredient.\n");
          removeIngredient();
        }
        case 3 -> {
          System.out.println("\nYou chose to search for an ingredient.\n");
          searchForIngredient();
        }
        case 4 -> {
          System.out.println("\nYou chose to view all ingredients.\n");
          showIngredients(fridge.getAllIngredients());
        }
        case 5 -> {
          System.out.println("\nYou chose to view all expired ingredients.\n");
          showIngredients(fridge.getExpiredIngredients());
        }
        case 6 -> {
          System.out.println("\nYou chose to calculate the total value of all ingredients.\n");
          calculateTotalValue();
        }
        case 7 -> {
          System.out.println("\nYou chose to add a new recipe.\n");
          addRecipe();
        }
        case 8 -> {
          System.out.println("\nYou chose to view all recipes that can be made.\n");
          cookbook.showAvailableRecipes(fridge);
        }
        case 9 -> {
          System.out.println("\nYou chose to check if a recipe can be made.\n");
          checkIfRecipeCanBeMade();
        }
        case 0 -> {
          System.out.println("\nYou chose to exit the application.\n");
          running = false;
          continue;
        }
        default ->
            System.out.println("\nInvalid input. Try again.\n");
      }

      System.out.println("\nPress 'enter' to go back to menu.\n");
      scanner.nextLine();
    }
  }

  /**
   * Displays the main menu options for user interaction in the console.
   */
  private void displayMenu() {
    System.out.println("\n------------Menu------------");
    System.out.println("Choose an option and enter the number:");
    System.out.println("[1] Add a new ingredient");
    System.out.println("[2] Remove an amount of an ingredient");
    System.out.println("[3] Search for an ingredient");
    System.out.println("[4] View all ingredients");
    System.out.println("[5] View all expired ingredients");
    System.out.println("[6] Calculate the total value of all ingredients");
    System.out.println("[7] Add a recipe");
    System.out.println("[8] View recipes that can be made");
    System.out.println("[9] Check if a recipe can be made");
    System.out.println("[0] Exit");
  }

  /**
   * Adds a new ingredient to the fridge.
   * <p>
   *   Prompts the user to enter each of the ingredient's attributes.
   *   Informs the user if the ingredient has been added.
   * </p>
   */
  private void addIngredient() {
    // Reads ingredient name from user
    System.out.println("Enter ingredient name:");
    String name = readString();

    // Reads unit of measurement from user
    System.out.println("Enter the ingredient's unit:");
    Unit unit = readUnit();

    // Reads ingredient quantity from user
    System.out.println("Enter quantity:");
    double quantity = readDouble();

    // Reads price per unit from user
    System.out.println("Enter price per " + unit + ":");
    double pricePerUnit = readDouble();

    // Reads date from user
    System.out.println("Enter expiry date in this format: dd/MM/yyyy");
    LocalDate expiryDate = readDate();

    // Attempts to create a new instance of Ingredient class
    try {
      Ingredient newIngredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
      fridge.addIngredient(newIngredient);
      System.out.println("\nIngredient added to fridge!");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      System.out.println("\nIngredient could not be added.");
    }

  }

  /**
   * Removes a specific quantity of an ingredient in the fridge.
   * <p>
   *   Prompts the user to enter the ingredient's name and quantity to remove.
   *   Informs the user if the specified quantity has been removed.
   * </p>
   */
  private void removeIngredient() {
    // Reads ingredient name from user
    System.out.println("Enter ingredient name:");
    String name = readString();

    try {
      Ingredient ingredient = fridge.getIngredient(name); // Retrieves the ingredient
      System.out.println("\nInformation about the ingredient: ");
      ingredient.showDetails(); // Displays information about the ingredient

      // Reads unit of measurement from user
      System.out.println("\nEnter the unit of the quantity to remove from the list:");
      Unit unit = readUnit();

      // Reads ingredient quantity to remove from user
      System.out.println("Enter quantity to remove:");
      double quantity = readDouble();

      try {
        // Removes a quantity of the ingredient
        fridge.removeIngredient(name, quantity, unit);
        System.out.println("\nRemoved " + quantity + " " + unit.getSymbol() + " of " + name + "!");
        System.out.println("\nUpdated information about the ingredient: ");
        ingredient.showDetails(); // Displays the updated information about the ingredient

      } catch (IllegalArgumentException e) {  // Handles cases where quantity is invalid
        System.out.println(e.getMessage());
        System.out.println("\nIngredient could not be removed.");
      }

    } catch (IllegalArgumentException e) {  // Handles cases where ingredient is not found
      System.out.println(e.getMessage());
      System.out.println("\nIngredient could not be removed.");
    }

  }

  /**
   * Searches for an ingredient in the fridge by name and
   * displays the ingredient's details if found.
   * <p>
   *   Prompts the user to enter the ingredient's name. Displays the details of the ingredient
   *   on the console if it is found. Otherwise informs the user that ingredient is not found.
   * </p>
   */
  private void searchForIngredient() {
    System.out.println("Enter ingredient name:");
    String name = readString();

    try {
      Ingredient ingredient = fridge.getIngredient(name);
      System.out.println("\nIngredient " + ingredient.getName() + " found!");
      System.out.println("\nDetails of ingredient " + ingredient.getName() + ":");
      ingredient.showDetails();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * <p> Shows details of each ingredient from a provided list. The ingredient
   * information is printed to the console.</p>
   *
   * @param ingredientList The list of ingredients to show.
   */
  private void showIngredients(List<Ingredient> ingredientList) {
    try {
      // Shows ingredient information for each ingredient
      for (Ingredient ingredient : ingredientList) {
        System.out.println(ingredient);
      }

      // Total value of ingredients in the list
      double totalValue = fridge.calculateValue(ingredientList);
      System.out.printf("\nTotal value of ingredients: %.2f kr.\n", totalValue);

    } catch (NoSuchElementException e) {  // Handles empty list of ingredients
      System.out.println(e.getMessage());
    }
  }

  /**
   * Calculates the total value of all ingredients in the fridge.
   * Displays the total value, rounded with 2 decimals, on the console.
   */
  private void calculateTotalValue() {
    double totalValue = fridge.calculateValue(fridge.getAllIngredients());
    System.out.printf("Total value of ingredients: %.2f kr\n", totalValue);
  }

  /**
   * Adds a new recipe to the cookbook.
   * <p>
   *   Prompts the user to enter each of the recipe's attribute.
   *   Informs the user if the recipe has been added to the cookbook.
   * </p>
   */
  private void addRecipe() {
    System.out.println("Enter recipe name:");
    String name = readString();

    System.out.println("Enter description: ");
    String description = readString();

    Recipe recipe = new Recipe(name, description);

    System.out.println("\nPlease add the ingredients to the recipe.");
    addRecipeIngredients(recipe);

    System.out.println("\nPlease add the instructions to the recipe.");
    System.out.println("Note: The instructions will be added in order, starting from 1.");
    addRecipeInstructions(recipe);

    this.cookbook.addRecipe(recipe);
    System.out.println("\nRecipe added to cookbook.");
  }

  /**
   * Adds ingredients to a specific recipe.
   * <p>
   *   Continuously prompts the user to create an ingredient
   *   and enter the ingredient's relevant attributes for the recipe until user enters "No".
   *   User is asked to continue or not after each instruction is added.
   * </p>
   *
   * @param recipe The recipe in which ingredients are added to.
   */
  private void addRecipeIngredients(Recipe recipe) {
    boolean addingIngredients = true;

    // Continuously prompts user to add ingredients until user exits
    while (addingIngredients) {
      // Reads ingredient name from user
      System.out.println("\nEnter the ingredient name: ");
      String ingredientName = readString();

      // Reads ingredient unit from user
      System.out.println("Enter the ingredient's unit from the following list:");
      Unit unit = readUnit();

      // Reads ingredient quantity from user
      System.out.println("Enter quantity needed for this recipe: ");
      double quantity = readDouble();

      // Create a new Ingredient instance
      Ingredient recipeIngredient = new Ingredient(
          ingredientName,
          quantity,
          0.0,  // Dummy value for irrelevant field
          unit,
          LocalDate.now() // Dummy value for irrelevant field
      );

      // Adds ingredient to recipe
      recipe.addIngredient(recipeIngredient);
      System.out.println("Ingredient added to recipe!");

      // Asks user if more ingredients should be added to the recipe
      System.out.println("\nWould you like to add more ingredients to recipe?");
      System.out.println("Enter 'YES' or 'NO'");
      String continueAdding = readString();

      // If user is done adding ingredients to the recipe
      if (continueAdding.equalsIgnoreCase("NO")) {
        addingIngredients = false;
      }

    }
  }

  /**
   * Adds instructions to a specific recipe.
   * <p>
   *   Continuously prompts the user to create an instruction
   *   for the recipe until user enters "No". User is asked to continue or not after
   *   each instruction is added.
   * </p>
   *
   * @param recipe The recipe in which instructions are added to.
   */
  private void addRecipeInstructions(Recipe recipe) {
    boolean addingInstructions = true;

    while (addingInstructions) {
      System.out.println("\nEnter the instruction: ");
      String instruction = readString();

      recipe.addInstruction(instruction);
      System.out.println("Instruction added to recipe!");

      System.out.println("\nWould you like to add more instructions?");
      System.out.println("Enter 'YES' or 'NO'");
      String continueAdding = readString();

      // If user is done adding instructions to the recipe
      if (continueAdding.equalsIgnoreCase("NO")) {
        addingInstructions = false;
      }
    }
  }

  /**
   * Checks if the fridge has the required ingredients to make a recipe.
   * <p>
   *   Prompts the user to enter the recipe's name. Informs the user
   *   if there are enough ingredients to make the recipe, or if there are missing ingredients.
   *   Informs the user if recipe is not in the cookbook.
   * </p>
   */
  private void checkIfRecipeCanBeMade() {
    System.out.println("Enter recipe name:");
    String recipeName = readString();

    try {
      boolean recipeCanBeMade = cookbook.canMakeRecipe(fridge, recipeName);

      if (recipeCanBeMade ) {
        System.out.println("You have all the ingredients to make " + recipeName + "!");
      } else {
        System.out.println("You are missing ingredients to make " + recipeName + "!");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Reads the next line of text from the console.
   * <p>
   *   Continuously prompts the user to enter a valid string.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @return A string entered by the user.
   */
  private String readString() {
    while (true) {
      String input = scanner.nextLine().trim();
      if (input.isEmpty() || input.isBlank()) {
        System.out.println("Invalid input. Enter a new one.");
      } else {
        return input;
      }
    }
  }

  /**
   * Reads an integer from the console.
   * <p>
   *   Continuously prompts the user to enter a valid integer.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @return A valid integer entered by the user.
   *
   * @throws NumberFormatException if the input is not a valid integer.
   */
  private int readInt() {
    while (true) {
      try {
        return Integer.parseInt(scanner.nextLine());
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Enter a valid whole number.");
      }
    }
  }

  /**
   * Reads a double from the console.
   * <p>
   *   Continuously prompts the user to enter a valid double.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @return A valid double entered by the user.
   *
   * @throws NumberFormatException if the input is not a valid double.
   */
  private double readDouble() {
    while (true) {
      try {
        return Double.parseDouble(scanner.nextLine().replace(",", "."));
      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Enter a valid number.");
      }
    }
  }

  /**
   * Reads a date from the console in the format "dd/MM/yyyy".
   * <p>
   *   Continuously prompts the user to enter a valid date.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @return A valid {@link LocalDate} entered by the user.
   *
   * @throws NumberFormatException if the input is not a valid LocalDate.
   */
  private LocalDate readDate() {
    while (true) {
      try {
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Enter a valid date.");
      }
    }
  }

  /**
   * Reads a unit of measurement from the console.
   * <p>
   *   A list of valid units are displayed on the console.
   *   Continuously prompts the user to enter a valid unit. Displays
   *   an error message if input is invalid and retries.
   * </p>
   *
   * @return A valid {@link Unit} entered by the user.
   *
   * @throws IllegalArgumentException if the input is an invalid unit.
   */
  private Unit readUnit() {
    // Displays a list of valid units on the console
    for (Unit unit : Unit.values()) {
      System.out.println("- " + unit.getSymbol());
    }

    // Prompts the user to enter a unit until it can be converted to a Unit instance
    while (true) {
      try {
        return Unit.getUnitBySymbol(scanner.nextLine());
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid unit. Enter a valid unit.");
      }
    }
  }

}
