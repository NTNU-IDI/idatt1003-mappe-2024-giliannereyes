package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class UserInterface {
  private final Fridge fridge;
  private final Cookbook cookbook;
  private final Scanner scanner;

  public UserInterface() {
    this.fridge = new Fridge();
    this.cookbook = new Cookbook();
    this.scanner = new Scanner(System.in);
  }

  // public void init() {}

  public void start() {
    boolean running = true;
    while (running) {
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

      int choice = scanner.nextInt();
      scanner.nextLine(); // Clears the scanner (leftover newline) after nextInt()

      switch (choice) {
        case 1:
          addIngredient();
          break;
        case 2:
          removeIngredient();
          break;
        case 3:
          searchIngredient();
          break;
        case 4:
          fridge.showAllIngredients();
          break;
        case 5:
          fridge.showAllExpiredIngredients();
          break;
        case 6:
          calculateTotalValue();
          break;
        case 7:
          addRecipe();
          break;
        case 8:
          cookbook.showAvailableRecipes(fridge);
          break;
        case 9:
          checkIfRecipeAvailable();
          break;
        case 0:
          running = false;
          break;
        default:
          System.out.println("Invalid input. Try again.");
      }

    }
  }

  private void addIngredient() {
    System.out.println("Enter ingredient name:");
    String name = scanner.nextLine();

    System.out.println("Enter quantity:");
    int quantity = scanner.nextInt();
    scanner.nextLine(); // Clears the scanner (leftover newline)

    System.out.println("Enter unit:");
    String unit = scanner.nextLine();

    System.out.println("Enter price per unit: ");
    double pricePerUnit = scanner.nextDouble();
    scanner.nextLine(); // Clears the scanner (leftover newline)

    System.out.println("Enter expiry date: (dd/MM/yyyy)");
    String date = scanner.next();
    LocalDate expiryDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

    scanner.nextLine(); // Clears the scanner

    // New instance of Ingredient class
    Ingredient newIngredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
    fridge.addIngredient(newIngredient);
    System.out.println("Ingredient added to fridge!");
  }

  private void removeIngredient() {
    System.out.println("Enter ingredient name:");
    String name = scanner.nextLine();

    System.out.println("Enter quantity to remove:");
    double quantity = scanner.nextDouble();
    scanner.nextLine(); // Clears the scanner (leftover newline)

    fridge.removeIngredient(name, quantity);
    System.out.println("Removed " + quantity + " of " + name + "!");

  }

  private void searchIngredient() {
    System.out.println("Enter ingredient name:");
    String name = scanner.nextLine();
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

  private void calculateTotalValue() {
    double totalValue = fridge.calculateTotalValue();
    System.out.printf("Total value of ingredients: %.2f kr\n", totalValue);
  }

  private void addRecipe() {
    System.out.println("Enter recipe name:");
    String name = scanner.nextLine();

    System.out.println("Enter description: ");
    String description = scanner.nextLine();

    Recipe recipe = new Recipe(name, description);

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

    this.cookbook.addRecipe(recipe);
    System.out.println("Recipe added to cookbook.");
  }

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

}
