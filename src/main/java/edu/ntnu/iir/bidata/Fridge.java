package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a fridge that stores ingredients.
 *
 * The fridge supports the following functionality:
 * <ul>
 *   <li></li>
 * </ul>
 */
public class Fridge {
  private List<Ingredient> inventory; // List to hold the ingredients

  /**
   * Constructor of the Fridge class that initializes the inventory list.
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
   * @return The ingredient.
   */
  public Ingredient searchIngredient(String ingredientName) {
    for (Ingredient ingredient : inventory) {
      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        return ingredient;
      }
    }
    return null;
  }

  /**
   * Displays all the ingredients in the inventory.
   */
  public void showAllIngredients() {
    // Loop through each ingredient
    inventory.forEach(ingredient -> {
      ingredient.showIngredientDetails(); // Call method to display ingredient details
      System.out.println();
    });
  }

  /**
   * Displays all expired ingredients from the inventory and their total value.
   * <p>
   *   This method retrieves the current date and filters the inventory to find
   *   the expired ingredients. For each expired ingredient, it prints out its details to the console.
   *   Then the total value of the expired ingredients is printed out.
   * </p>
   */
  public void showAllExpiredIngredients() {
    LocalDate currentDate = LocalDate.now();  // Get the current date

    double totalValue = inventory.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(currentDate)) // Filters expired ingredients
        .peek(Ingredient::showIngredientDetails)  // Displays each expired ingredient's details
        .mapToDouble(Ingredient::getPrice)  // Maps each Ingredient-instance to its price (double value)
        .sum(); // Calculates the sum of the prices of expired ingredients

    System.out.println("The total value of expired ingredients is " + totalValue + " kr.");
  }

  /**
   * Calculates the total value of all ingredients in the inventory.
   *
   * @return The total value of the ingredients.
   */
  public double calculateTotalValue() {
    return inventory.stream()
        .mapToDouble(Ingredient::getPrice) // Map each ingredient instance to its price
        .sum(); // Sum the prices
  }


  /**
   * Checks if the fridge has enough of the specified ingredient.
   *
   * <p>
   *   This method filters the inventory to find ingredients that mach the provided name.
   *   It then checks if at least one of those matching ingredients has the required quantity
   *   and is not expired.
   * </p>
   *
   * @param name The name of the ingredient to check for.
   * @param quantity The required amount of the ingredient.
   *
   * @return {@code true} if fridge has enough of the ingredient, and it is not expired.
   *         {@code false} if there is not enough of the ingredient, or it has expired.
   */
  public boolean hasEnoughIngredient(String name, double quantity) {
    return inventory.stream()
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name))  // Filter by ingredient name
        .anyMatch(ingredient -> ingredient.getQuantity() >= quantity  // Check for sufficient quantity
                  && ingredient.getExpiryDate().isAfter(LocalDate.now()));  // Check if not expired
  }

}
