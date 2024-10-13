package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Represents a fridge that stores ingredients.
 *<br>
 * Fridge has the following functionalities:
 * <ul>
 *   <li></li>
 * </ul>
 */
public class Fridge {
  private final List<Ingredient> inventory; // List to hold the ingredients

  /**
   * Constructs a new Fridge instance that initializes the inventory list.
   */
  public Fridge() {
    inventory = new ArrayList<>();  // Initialize the inventory as an empty ArrayList
  }

  /**
   * Adds an ingredient to the inventory.
   *
   * @param ingredient The ingredient to add.
   */
  public void addIngredient(Ingredient ingredient) {
    inventory.add(ingredient);
  }

  /**
   * Removes a specified quantity of an ingredient by name.
   *
   * @param ingredientName  The name of the ingredient to remove.
   * @param quantity  The quantity to remove.
   *
   * @throws IllegalArgumentException if the ingredient is not found or if removing the quantity
   *                                  results in a negative quantity.
   */
  public void removeIngredient(String ingredientName, double quantity) {
    // Use an iterator to safely remove items from the inventory
    Iterator<Ingredient> iterator = inventory.iterator();

    while (iterator.hasNext()) {
      Ingredient ingredient = iterator.next();

      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        ingredient.removeQuantity(quantity);  // Remove the specified quantity

        // Ingredient is removed from inventory if quantity equals zero
        if (ingredient.getQuantity() == 0) {
          iterator.remove(); // Safely remove the ingredient
        }

        return; // Exit once the quantity is removed
      }
    }

    // Throws an IllegalArgumentException if ingredient is not found
    throw new IllegalArgumentException("Ingredient '" + ingredientName + "' not found.");
  }

  /**
   * Searches for an ingredient by name.
   *
   * @param ingredientName The name of the ingredient to search for.
   *
   * @return An Optional containing the ingredient if found, or an empty Optional if not found.
   */
  public Optional<Ingredient> searchIngredient(String ingredientName) {
    return inventory.stream()
        // Filters by ingredient name
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(ingredientName))
        // Returns first instance of specified ingredient
        .findFirst();
  }

  /**
   * Displays all the ingredients in the inventory.
   */
  public void showAllIngredients() {
    // Loops through each ingredient
    inventory.forEach(ingredient -> {
      ingredient.showDetails(); // Calls method to display ingredient details
      System.out.println();
    });
  }

  /**
   * Displays all expired ingredients from the inventory and their total value.
   * <p>
   *   This method retrieves the current date and filters the inventory to find
   *   the expired ingredients. For each expired ingredient,
   *   it prints out its details to the console.
   *   Then the total value of the expired ingredients is printed out.
   * </p>
   */
  public void showAllExpiredIngredients() {
    LocalDate currentDate = LocalDate.now();  // Get the current date

    double totalValue = inventory.stream()
        // Filters expired ingredients
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(currentDate))
        // Displays each expired ingredient's details
        .peek(Ingredient::showDetails)
        // Maps each Ingredient-instance to its price (double value)
        .mapToDouble(Ingredient::getPrice)
        // Calculates the sum of the prices of expired ingredients
        .sum();

    System.out.println("The total value of expired ingredients is " + totalValue + " kr.");
  }

  /**
   * Calculates the total value of all ingredients in the inventory.
   *
   * @return The total value of the ingredients.
   */
  public double calculateTotalValue() {
    return inventory.stream()
        .mapToDouble(Ingredient::getPrice) // Maps each ingredient instance to its price
        .sum(); // Calculates the sum of the prices
  }

  /**
   * Checks if the fridge has enough of the specified ingredient.
   *
   * @param name The name of the ingredient to check for.
   * @param quantity The required amount of the ingredient.
   *
   * @return {@code true} if fridge has enough of the ingredient, and it is not expired.
   *         {@code false} if there is not enough of the ingredient, or it has expired.
   */
  public boolean hasEnoughIngredient(String name, double quantity) {
    return inventory.stream()
        // Filters by ingredient name
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))
        // Checks for sufficient quantity
        .anyMatch(ingredient -> ingredient.getQuantity() >= quantity
            // Checks if ingredient is not expired
            && ingredient.getExpiryDate().isAfter(LocalDate.now()));
  }

}
