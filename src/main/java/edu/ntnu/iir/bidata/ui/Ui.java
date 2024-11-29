package edu.ntnu.iir.bidata.ui;

import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.utils.FoodManager;
import edu.ntnu.iir.bidata.utils.Result;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents the user interface of the application, allowing the user to
 * manage ingredients in the fridge and recipes in the cookbook.
 *
 * <p>Provides methods to display the main menu, handle user input, and execute
 * operations based on the user's choice.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.0
 */
public class Ui {
  // ingredientHeader was generated by ChatGPT.
  private final String ingredientHeader = String.format(
      "+--------------------+-----------------+-----------------+"
          + "--------------------+--------------------+-----------------+%n"
          + "| %-18s | %-15s | %-15s | %-18s | %-18s | %-15s |%n"
          + "+--------------------+-----------------+-----------------+"
          + "--------------------+--------------------+-----------------+%n",
      "Name", "Quantity", "Unit", "Expiry Date ", "Price/Unit", "Total Price"
  );
  private final LinkedHashMap<Integer, Map<String, Runnable>> menu = new LinkedHashMap<>();
  private InputHandler inputHandler;
  private FoodManager foodManager;

  {
    menu.put(1, Map.of("Add ingredient", this::handleAddIngredientToFridge));
    menu.put(2, Map.of("Remove ingredient", this::handleRemoveIngredientFromFridge));
    menu.put(3, Map.of("Find ingredient", this::handleFindIngredient));
    menu.put(4, Map.of("Show expiring ingredients", this::handleExpiringIngredients));
    menu.put(5, Map.of("Show value of ingredients in fridge", this::displayFridgeValue));
    menu.put(6, Map.of("Show all ingredients", this::handleDisplaySortedIngredients));
    menu.put(7, Map.of("Add recipe", this::handleAddRecipeToCookbook));
    menu.put(8, Map.of("Show suggested recipes", this::handleDisplaySuggestedRecipes));
    menu.put(9, Map.of("Check recipe availability", this::handleRecipeAvailability));
  }

  /**
   * Starts the application by displaying the main menu and handling user input
   * until the user chooses to exit the application.
   */
  public void start() {
    init();
    boolean exit = false;
    while (!exit) {
      displayMenu();
      int choice = inputHandler.readInt("\nPlease choose an option: ");
      if (choice == menu.size() + 1) {
        System.out.println("Exiting the application...");
        inputHandler.close();
        exit = true;
      } else {
        handleMenuChoice(choice);
      }
    }
  }

  /**
   * Initializes the application by creating an input handler and a food manager instance,
   * and populating the fridge and cookbook with initial data.
   */
  private void init() {
    try {
      inputHandler = new InputHandler();
      foodManager = new FoodManager();
      foodManager.populateFridgeAndCookbook();
    } catch (Exception e) {
      System.out.println("Failed to initialize the application: " + e.getMessage());
    }
  }

  /**
   * Displays the main menu with options for managing ingredients and recipes.
   * <strong>This method was generated by ChatGPT.</strong>
   */
  private void displayMenu() {
    Iterator<Map.Entry<Integer, Map<String, Runnable>>> iterator = menu.entrySet().iterator();
    System.out.println("\n------------------------------- MENU ---------------------------------");

    while (iterator.hasNext()) {
      Map.Entry<Integer, Map<String, Runnable>> currentEntry = iterator.next();
      Integer currentNumber = currentEntry.getKey();
      String currentOptionName = currentEntry.getValue().keySet().iterator().next();

      if (iterator.hasNext()) {
        Map.Entry<Integer, Map<String, Runnable>> nextEntry = iterator.next();
        Integer nextNumber = nextEntry.getKey();
        String nextOptionName = nextEntry.getValue().keySet().iterator().next();
        System.out.printf("[%d] %-40s [%d] %-40s\n",
            currentNumber, currentOptionName, nextNumber, nextOptionName);
      } else {
        System.out.printf("[%d] %-40s\n", currentNumber, currentOptionName);
      }
    }

    System.out.printf("[%d] %-40s\n", menu.size() + 1, "Exit");
    System.out.println("______________________________________________________________________");
  }

  /**
   * Handles the user's choice from the menu by
   * executing the corresponding operation.
   *
   * @param choice is the user's choice from the menu.
   */
  private void handleMenuChoice(int choice) {
    boolean isValidChoice = choice >= 1 && choice <= menu.size();
    if (!isValidChoice) {
      System.out.println("Invalid option! Enter a valid number from 1-9.");
    } else {
      menu.get(choice).values().iterator().next().run();
      inputHandler.readEnter("\nPress enter to go back to menu...");
    }
  }

  /**
   * Prompts the user for name, quantity, unit, price per unit, and expiry date of an ingredient,
   * and attempts to add it to the fridge. Displays the result of the operation.
   */
  public void handleAddIngredientToFridge() {
    System.out.println("\nYou are now adding an ingredient to the fridge.");
    String name = inputHandler.readString("\nEnter ingredient name: ");
    double quantity = inputHandler.readDouble("\nEnter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("\nEnter the number to the ingredient's unit: ");
    double pricePerUnit = inputHandler.readDouble("\nEnter ingredient's price per unit: ");
    LocalDate expiryDate = inputHandler.readDate(
        "\nEnter the expiry date in this format 'yyyy/MM/dd': "
    );
    displayResult(foodManager.addIngredientToFridge(
        name, quantity, pricePerUnit, unit, expiryDate)
    );
  }

  /**
   * Prompts the user for name, quantity, and unit of an ingredient to remove from the fridge,
   * and attempts to remove it. Displays the result of the operation.
   */
  private void handleRemoveIngredientFromFridge() {
    System.out.println("\nYou are now removing an ingredient from the fridge.");
    String name = inputHandler.readString("\nEnter ingredient name: ");
    Unit unit = inputHandler.readUnit("\nEnter the number of the unit to remove: ");
    double quantity = inputHandler.readDouble("\nEnter quantity to remove: ");

    displayResult(foodManager.removeIngredientFromFridge(name, unit, quantity));
  }

  /**
   * Prompts the user for an ingredient name to search for in the fridge.
   * Displays the result of the search operation.
   */
  private void handleFindIngredient() {
    System.out.println("\nYou chose to search for an ingredient.");
    String name = inputHandler.readString("\nEnter ingredient name: ");
    displayResult(foodManager.findIngredient(name), ingredientHeader);
  }

  /**
   * Prompts the user for a date and displays all ingredients expiring before that date.
   * Also displays the total value of all expiring ingredients.
   */
  private void handleExpiringIngredients() {
    System.out.println("\nYou chose to view expiring ingredients.");
    System.out.println(
        "Ingredients expiring before the date (e.g. '2024/10/28') you enter will be shown."
    );
    LocalDate expiryDate = inputHandler.readDate("\nEnter the date in this format 'yyyy/MM/dd': ");
    displayResult(foodManager.getIngredientsExpiringBefore(expiryDate), ingredientHeader);
    displayResult(foodManager.calculateExpiringValueByDate(expiryDate));
  }

  /**
   * Displays the total value of ingredients in the fridge.
   */
  public void displayFridgeValue() {
    displayResult(foodManager.calculateFridgeValue());
  }

  /**
   * Displays all ingredients in the fridge sorted alphabetically by name.
   * Also displays the total value of ingredients in the fridge.
   */
  private void handleDisplaySortedIngredients() {
    System.out.println();
    displayResult(foodManager.findSortedIngredients(), ingredientHeader);
    displayFridgeValue();
  }

  /**
   * Prompts the user for a recipe name, description, instruction and ingredients,
   * and attempts to add the recipe to the cookbook. Displays the result of the operation.
   */
  private void handleAddRecipeToCookbook() {
    System.out.println("\nYou chose to add a recipe to the cookbook.");
    String name = inputHandler.readString("\nEnter recipe name: ");
    String description = inputHandler.readString("\nEnter recipe description: ");
    String instruction = inputHandler.readString("\nEnter recipe instruction: ");

    Result<Recipe> result = foodManager.createRecipe(name, description, instruction);

    if (result.isSuccess()) {
      result.getData().ifPresent(recipe -> {
        handleRecipeIngredientAddition(recipe);
        displayResult(foodManager.addRecipe(recipe));
      });
    } else {
      displayResult(result);
    }
  }

  /**
   * Prompts the user to add ingredients to a recipe continuously until the user chooses to stop.
   *
   * @param recipe is the recipe to add ingredients to.
   */
  private void handleRecipeIngredientAddition(Recipe recipe) {
    System.out.printf("""
        \nYou are now adding ingredients to the recipe '%s'%n.
        You will be asked to add ingredients to the recipe until you choose to stop.%n
        """,
        recipe.getIngredients()
    );
    boolean addingIngredients = true;
    while (addingIngredients) {
      addIngredientToRecipe(recipe);
      addingIngredients = inputHandler.readYes("Would you like to continue adding ingredients?");
    }
  }

  /**
   * Prompts the user for an ingredient name, quantity, and unit to add to a recipe.
   * Displays the result of the operation.
   *
   * @param recipe is the recipe to add the ingredient to.
   */
  private void addIngredientToRecipe(Recipe recipe) {
    String name = inputHandler.readString("\nEnter the ingredient's name: ");
    double quantity = inputHandler.readDouble("\nEnter the ingredient's quantity: ");
    Unit unit = inputHandler.readUnit("\nEnter the number of the ingredient's unit: ");
    displayResult(foodManager.addIngredientToRecipe(recipe, name, quantity, unit));
  }

  /**
   * Displays the suggested recipes based on the ingredients in the fridge.
   */
  public void handleDisplaySuggestedRecipes() {
    displayResult(foodManager.findSuggestedRecipes());
  }

  /**
   * Prompts the user for a recipe name and checks if the recipe is available.
   * Displays the result of the operation.
   */
  public void handleRecipeAvailability() {
    System.out.println("\nYou chose to check if a recipe is available.");
    String name = inputHandler.readString("\nEnter the recipe's name: ");
    displayResult(foodManager.verifyRecipeAvailability(name));
  }

  /**
   * Displays the result in the console in a string representation.
   *
   * @param result is the result object that contains the operation's result.
   * @param <T>    is the type of the data in the result.
   */
  private <T> void displayResult(Result<T> result) {
    displayResult(result, "");
  }

  /**
   * Displays the result in the console in a string representation, including a header.
   *
   * @param result is the result object that contains the operation's result.
   * @param header is the header to display.
   * @param <T>    is the type of the data in the result.
   */
  private <T> void displayResult(Result<T> result, String header) {
    System.out.println("\n" + result.getMessage() + "\n");
    if (result.isSuccess() && !header.isBlank()) {
      System.out.print(header);
    }
    result.getData().ifPresent(this::displayData);
  }

  /**
   * Displays the data in the console. If the data is an iterable,
   * it will display each element on a new line.
   * <strong>This method was generated by ChatGPT.</strong>
   *
   * @param data is the data to display.
   * @param <T>  is the type of the data.
   */
  private <T> void displayData(T data) {
    if (data instanceof Iterable<?> iterable) {
      iterable.forEach(System.out::println);
    } else {
      System.out.println(data);
    }
  }
}

