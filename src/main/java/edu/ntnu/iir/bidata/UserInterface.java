package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
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
   * Starts the main loop of the UserInterface to provide a menu-based interaction.
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
      switch (choice) {
        case 1 -> addIngredient();
        case 2 -> removeIngredient();
        case 3 -> searchIngredient();
        case 4 -> fridge.showAllIngredients();
        case 5 -> fridge.showAllExpiredIngredients();
        case 6 -> calculateTotalValue();
        case 7 -> addRecipe();
        case 8 -> cookbook.showAvailableRecipes(fridge);
        case 9 -> checkIfRecipeAvailable();
        case 0 -> running = false;
        default -> System.out.println("Invalid input. Try again.");
      }
    }
  }

  /**
   * Displays the main menu options for user interaction in the console.
   */
  private void displayMenu() {
    System.out.println("------Menu------");
    System.out.println("Choose an option:");
    System.out.println("[1] Add a new ingredient");
    System.out.println("[2] Remove an amount of ingredient");
    System.out.println("[3] Search for an ingredient");
    System.out.println("[4] Show all ingredients");
    System.out.println("[5] Show all expired ingredients");
    System.out.println("[6] Calculate the total value of ingredients");
    System.out.println("[7] Add a recipe");
    System.out.println("[8] Show available recipes");
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

    System.out.println("Enter expiry date: (dd/MM/yyyy)");
    LocalDate expiryDate = readDate();

    // New instance of Ingredient class
    Ingredient newIngredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
    fridge.addIngredient(newIngredient);
    System.out.println("Ingredient added to fridge!");
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

    fridge.removeIngredient(name, quantity);
    System.out.println("Removed " + quantity + " of " + name + "!");
  }

  /**
   * Searches for an ingredient in the fridge by name and
   * displays the ingredient's details if found.
   * <p>
   *   Prompts the user to enter the ingredient's name. Displays the details of the ingredient
   *   on the console if it is found. Otherwise informs the user that ingredient is not found.
   * </p>
   */
  private void searchIngredient() {
    System.out.println("Enter ingredient name:");
    String name = readString();
    Optional<Ingredient> optionalIngredient = fridge.searchIngredient(name);

    optionalIngredient.ifPresentOrElse(
        ingredient -> {
          System.out.println("Ingredient " + ingredient.getName() + " found!");
          System.out.println("Details of ingredient " + ingredient.getName() + ":");
          ingredient.showDetails();
        },
        () -> System.out.println("Ingredient not found!")
    );
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

    addRecipeIngredients(recipe);

    this.cookbook.addRecipe(recipe);
    System.out.println("Recipe added to cookbook.");
  }

  /**
   * Adds ingredients to a specific recipe.
   * <p>
   *   Continuously prompts the user to create an ingredient
   *   and enter the ingredient's relevant attributes for the recipe until user enters "done".
   * </p>
   *
   * @param recipe The recipe in which ingredients are added to.
   */
  private void addRecipeIngredients(Recipe recipe) {
    boolean addingIngredients = true;
    while (addingIngredients) {
      System.out.println("Add an ingredient to the recipe. Type 'done' when finished.");
      System.out.println("Ingredient name: ");
      String ingredientName = scanner.nextLine();

      if (ingredientName.equals("done")) {
        addingIngredients = false;
        continue;
      }

      System.out.println("Enter quantity needed for this recipe: ");
      double quantity = scanner.nextDouble();
      scanner.nextLine(); // Clears the scanner (leftover newline)

      System.out.println("Enter unit for this ingredient: ");
      String unit = scanner.nextLine();

      // Create a new Ingredient instance
      Ingredient recipeIngredient = new Ingredient(
          ingredientName,
          quantity,
          0.0,  // Dummy value for irrelevant field
          unit,
          LocalDate.now() // Dummy value for irrelevant field
      );

      recipe.addIngredient(recipeIngredient);
    }
  }

  /**
   * Checks if a recipe is available.
   * <p>
   *   Prompts the user to enter the recipe's name. Informs the user
   *   if there are enough ingredients to make the recipe, or if there are missing ingredients.
   *   Informs the user if recipe is not in the cookbook.
   * </p>
   */
  private void checkIfRecipeAvailable() {
    System.out.println("Enter recipe name:");
    String name = scanner.nextLine();
    Optional<Recipe> optionalRecipe = cookbook.getRecipeByName(name);

    optionalRecipe.ifPresentOrElse(
        recipe -> {
          if (recipe.available(fridge)) {
            System.out.println("You have all the ingredients to make " + name + "!");
          } else {
            System.out.println("You are missing ingredients to make " + name + "!");
          }
        },
        () -> System.out.println("Recipe '" + name + "' not found!")
    );
  }

  /**
   * Reads the next line of text from the console.
   *
   * @return A string entered by the user.
   */
  private String readString() {
    return scanner.nextLine();
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
        System.out.println("Invalid input. Try again.");
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
        System.out.println("Invalid input. Try again.");
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
        System.out.println("Invalid date format. Try again.");
      }
    }
  }

}
