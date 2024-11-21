package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.manager.Manager;
import edu.ntnu.iir.bidata.manager.Result;
import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;

import java.time.LocalDate;
import java.util.Collection;
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
    displayResult(result);
    promptRetryOperationIfFailed(this::promptAddIngredient, result.isSuccess());
  }

  /**
   * Prompts the user for the name of an ingredient to search for. Displays
   * the details of the ingredient if it is found. Or a message informing
   * the user that it was not found.
   */
  public void promptSearchIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");

    Result<Ingredient> result = manager.searchForIngredient(name);
    displayResult(result, getIngredientHeader());
    promptRetryOperationIfFailed(this::promptSearchIngredient, result.isSuccess());
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
    displayResult(result);
    promptRetryOperationIfFailed(this::promptDecreaseIngredientQuantity, result.isSuccess());
  }

  /**
   * Prompts the user for a date to check for ingredients that expire before this date.
   * Displays each ingredient's details if there are ingredients expiring before the
   * specified date. Or displays a message informing the user that there are no ingredients.
   */
  public void promptCheckExpiringIngredients() {
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");

    Result<List<Ingredient>> result = manager.checkExpiringIngredients(expiryDate);
    displayResult(result, getIngredientHeader());
    promptRetryOperationIfFailed(this::promptCheckExpiringIngredients, result.isSuccess());
  }

  /**
   * Displays each ingredient's details, sorted alphabetically by their name. If
   * there are no ingredients in the fridge, displays a message informing the user instead.
   */
  public void displaySortedIngredients() {
    Result<List<Ingredient>> result = manager.getSortedIngredients();
    displayResult(result, getIngredientHeader());
    promptRetryOperationIfFailed(this::displaySortedIngredients, result.isSuccess());
  }

  /**
   * Prompts the user to choose whether to run an operation again or not
   * if the operation failed.
   *
   * @param operation The operation to run if the user chooses to retry.
   */
  private void promptRetryOperationIfFailed(Runnable operation, boolean success) {
    if (!success ) {
      boolean retry = inputHandler.readYes("Would you like to try again?");
      if (retry) {
        operation.run();
      }
    }
  }

  // Overloaded method without a header
  private <T> void displayResult(Result<T> result) {
    displayResult(result, "");
  }

  // Main method with a header
  private <T> void displayResult(Result<T> result, String header) {
    System.out.println(result.getMessage());

    if (result.isSuccess() && result.getData().isPresent()) {
      if (!header.isBlank()) {
        System.out.println(header);
      }
      T data = result.getData().get();

      if (data instanceof Collection<?> collection) {
        collection.forEach(System.out::println);
      } else {
        System.out.println(data);
      }
    }
  }

  private String getIngredientHeader() {
    return String.format(
        "%-15s %-10s %-10s %-15s %-15s %-10s%n%n",
        "Name", "Quantity", "Unit", "Expiry Date", "Price/Unit", "Total Price"
    );
  }

}
