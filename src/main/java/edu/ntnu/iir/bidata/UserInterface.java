package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
          System.out.println("You chose to add a new ingredient.\n");
          addIngredient();
        }
        case 2 -> {
          System.out.println("You chose to remove an amount of an ingredient.\n");
          removeIngredient();
        }
        case 3 -> {
          System.out.println("You chose to search for an ingredient.\n");
          searchForIngredient();
        }
        case 4 -> {
          System.out.println("You chose to view all ingredients.\n");
          fridge.showAllIngredients();
        }
        case 5 -> {
          System.out.println("You chose to view all expired ingredients.\n");
          fridge.showAllExpiredIngredients();
        }
        case 6 -> {
          System.out.println("You chose to calculate the total value of all ingredients.\n");
          calculateTotalValue();
        }
        case 7 -> {
          System.out.println("You chose to add a new recipe.\n");
          addRecipe();
        }
        case 8 -> {
          System.out.println("You chose to view all recipes that can be made.\n");
          cookbook.showAvailableRecipes(fridge);
        }
        case 9 -> {
          System.out.println("You chose to check if a recipe can be made.\n");
          checkIfRecipeCanBeMade();
        }
        case 0 -> {
          System.out.println("You chose to exit the application.\n");
          running = false;
          continue;
        }
        default ->
            System.out.println("Invalid input. Try again.");
      }

      System.out.println("\nPress 'enter' to go back to menu.");
      scanner.nextLine();
    }
  }

  /**
   * Displays the main menu options for user interaction in the console.
   */
  private void displayMenu() {
    System.out.println("\n--------Menu--------");
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
    System.out.println("Enter ingredient name:");
    String name = readString();

    System.out.println("Enter quantity:");
    int quantity = readInt();

    System.out.println("Enter unit:");
    String unit = readString();

    System.out.println("Enter price per unit: ");
    double pricePerUnit = readDouble();

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
    System.out.println("Enter ingredient name:");
    String name = readString();

    System.out.println("Enter quantity to remove:");
    double quantity = readDouble();

    // Attempts to remove a specific quantity of an ingredient
    try {
      fridge.removeIngredient(name, quantity);
      System.out.println("\nRemoved " + quantity + " of " + name + "!");
    } catch (IllegalArgumentException e) {
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
   * Calculates the total value of all ingredients in the fridge.
   * Displays the total value, rounded with 2 decimals, on the console.
   */
  private void calculateTotalValue() {
    double totalValue = fridge.calculateTotalValue();
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
    while (addingIngredients) {
      System.out.println("\nEnter the ingredient name: ");
      String ingredientName = readString();

      System.out.println("Enter quantity needed for this recipe: ");
      double quantity = readDouble();

      System.out.println("Enter unit for this ingredient: ");
      String unit = readString();

      // Create a new Ingredient instance
      Ingredient recipeIngredient = new Ingredient(
          ingredientName,
          quantity,
          0.0,  // Dummy value for irrelevant field
          unit,
          LocalDate.now() // Dummy value for irrelevant field
      );

      recipe.addIngredient(recipeIngredient);
      System.out.println("Ingredient added to recipe!");

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
   *
   * @return A string entered by the user.
   */
  private String readString() {
    return scanner.nextLine().trim();
  }

  /**
   * Reads an integer from the console.
   * <p>
   *   Continuously prompts the user to enter a valid integer.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @return A valid integer entered by the user.
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

}
