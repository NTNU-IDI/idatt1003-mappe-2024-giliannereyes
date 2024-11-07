package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.manager.Manager;
import edu.ntnu.iir.bidata.manager.Result;
import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;

import java.time.LocalDate;
import java.util.List;

/**
 * The IngredientUi class is responsible for managing user interaction
 * related to ingredients, such as prompting the user for ingredient details
 * and displaying relevant messages in the console.
 */
public class IngredientUi {
  private final Manager manager;
  private final InputHandler inputHandler;

  /**
   * Constructs a new IngredientUi instance.
   *
   * @param manager The manager to handle business logic related to recipes.
   * @param inputHandler The input handler to receive and validate user input.
   */
  public IngredientUi(Manager manager, InputHandler inputHandler) {
    this.manager = manager;
    this.inputHandler = inputHandler;
  }

  /**
   * Prompts the user for ingredient details needed to create a new ingredient.
   * Then displays a message informing the user if the ingredient was added or not.
   */
  public void promptAddIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");
    double pricePerUnit = inputHandler.readDouble("Enter ingredient's price per unit: ");
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");

    Result<Void> result = manager.addIngredientToFridge(name, quantity, pricePerUnit, unit, expiryDate);
    System.out.println(result.getMessage());
  }

  /**
   * Prompts the user for the name of an ingredient to search for. Displays
   * the details of the ingredient if it is found. Or a message informing
   * the user that it was not found.
   */
  public void promptSearchIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Result<Ingredient> result = manager.searchForIngredient(name);

    System.out.println(result.getMessage());

    if (result.isSuccess()) {
      result.getData().ifPresent(System.out::println);
    }
  }

  /**
   * Prompts the user for the name of the ingredient to decrease the quantity of, and
   * the quantity and unit of measurement to decrease with. Displays a message informing
   * the user if the ingredient was decreased or not.
   */
  public void promptDecreaseIngredientQuantity() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Unit unit = inputHandler.readUnit("Enter the unit of the quantity to remove: ");
    double quantity = inputHandler.readDouble("Enter quantity to remove: ");

    Result<Void> result = manager.decreaseIngredientQuantity(name, unit, quantity);
    System.out.println(result.getMessage());
  }

  /**
   * Prompts the user for a date to check for ingredients that expire before this date.
   * Displays each ingredient's details if there are ingredients expiring before the
   * specified date. Or displays a message informing the user that there are no ingredients.
   */
  public void promptCheckExpiringIngredients() {
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");
    Result<List<Ingredient>> result = manager.checkExpiringIngredients(expiryDate);

    System.out.println(result.getMessage());

    if (result.isSuccess()) {
      List<Ingredient> expiringIngredients = result.getData().orElseGet(List::of);
      expiringIngredients.forEach(System.out::println);
    }
  }

  /**
   * Displays each ingredient's details, sorted alphabetically by their name. If
   * there are no ingredients in the fridge, displays a message informing the user instead.
   */
  public void displaySortedIngredients() {
    Result<List<Ingredient>> result = manager.getSortedIngredients();

    System.out.println(result.getMessage());

    if (result.isSuccess()) {
      List<Ingredient> sortedIngredients = result.getData().orElseGet(List::of);
      sortedIngredients.forEach(System.out::println);
    }
  }
}
