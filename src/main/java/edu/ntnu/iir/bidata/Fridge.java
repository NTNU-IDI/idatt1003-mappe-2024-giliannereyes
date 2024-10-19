package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
  public void removeIngredient(String ingredientName, double quantity, Unit unit) {
    // Use an iterator to safely remove items from the inventory
    Iterator<Ingredient> iterator = inventory.iterator();

    while (iterator.hasNext()) {
      Ingredient ingredient = iterator.next();

      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        ingredient.removeQuantity(quantity, unit);  // Remove the specified quantity

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
   * @return An ingredient that matches the provided name.
   *
   * @throws IllegalArgumentException if there is no ingredient in the fridge
   *                                  with the provided name.
   */
  public Ingredient getIngredient(String ingredientName) {
    return inventory.stream()
        // Filters by ingredient name
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(ingredientName))
        // Returns first instance of specified ingredient
        .findFirst()
        // Throws an IllegalArgumentException if no ingredient is found
        .orElseThrow(() ->
            new IllegalArgumentException("Ingredient '" + ingredientName + "' not found."));
  }

  /*
  /**
   * Displays all the ingredients in the inventory. For each ingredient,
   * the name, quantity, price, price per unit and expiry date are displayed.

  public void showAllIngredients() {
    // Loops through each ingredient
    if (inventory.isEmpty()) {
      System.out.println("There are no ingredients in the fridge.");
    } else {
      inventory.forEach(ingredient -> {
        ingredient.showDetails(); // Calls method to display ingredient details
        System.out.println();
      });
    }
  }
  */


  /**
   * Retrieves all the ingredients in the fridge.
   *
   * @return A List containing all the ingredients.
   *
   * @throws NoSuchElementException if there are no ingredients in the fridge.
   */
  public List<Ingredient> getAllIngredients() {
    // If there are no ingredients in the fridge
    if (inventory.isEmpty()) {
      throw new NoSuchElementException("There are no ingredients in the fridge.");
    } else {
      return inventory; // Returns all ingredients in the fridge
    }
  }

  /**
   * Retrieves all the expired ingredients in the fridge.
   *
   * @return A List containing all the expired ingredients.
   *
   * @throws NoSuchElementException if there are no expired ingredients in the fridge.
   */
  public List<Ingredient> getExpiredIngredients() {
    List<Ingredient> expiredIngredients = inventory.stream()
        .filter(Ingredient::isExpired)  // Filter expired ingredients
        .toList();  // Collects expired ingredients in a list

    // If there are no expired ingredients
    if (expiredIngredients.isEmpty()) {
      throw new NoSuchElementException("There are no expired ingredients in the fridge.");
    }

    return expiredIngredients;
  }

  /*
  /**
   * Displays all expired ingredients from the inventory and their total value.
   * <p>
   *   This method retrieves the current date and filters the inventory to find
   *   the expired ingredients. For each expired ingredient,
   *   it prints out its details to the console.
   *   Then the total value of the expired ingredients is printed out.
   * </p>

  public void showAllExpiredIngredients() {
    LocalDate currentDate = LocalDate.now();  // Get the current date

    List<Ingredient> expiredIngredients = inventory.stream()
        // Filters expired ingredients
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(currentDate))
        // Collects expired ingredients in a list
        .toList();

    if (expiredIngredients.isEmpty()) {
      System.out.println("There are no expired ingredients in the fridge.");
    } else {
      double totalValue = 0;

      for (Ingredient ingredient : expiredIngredients) {
        totalValue += ingredient.getPrice();
        ingredient.showDetails();
      }

      System.out.println("Total value of all expired ingredients: " + totalValue + " kr.");
    }
  }
  */

  /**
   * Calculates the total value of all ingredients in the provided list.
   *
   * @param ingredientList The list of ingredients.
   *
   * @return The total value of the ingredients in the list.
   */
  public double calculateValue(List<Ingredient> ingredientList) {
    return ingredientList.stream()
        .mapToDouble(Ingredient::getPrice) // Maps each ingredient instance to its price
        .sum(); // Calculates the sum of the prices
  }

  /**
   * Checks if the fridge has enough of the specified ingredient.
   *
   * @param name The name of the ingredient to check for.
   * @param quantity The required amount of the ingredient.
   * @param unit The unit of the required amount.
   *
   * @return {@code true} if fridge has enough of the ingredient, and it is not expired.
   *         {@code false} if the ingredient has expired, has insufficient quantity,
   *         or is not in the fridge.
   */
  public boolean hasEnoughIngredient(String name, double quantity, Unit unit) {
    try {
      Ingredient ingredient = getIngredient(name);  // Retrieves the ingredient

      // Checks if ingredient is expired
      if (ingredient.isExpired()) {
        return false; // Ingredient is expired
      }

      // Converts available and needed quantities to base unit
      double availableBaseQuantity = ingredient.getUnit().convertToBaseUnit(ingredient.getQuantity());
      double neededBaseQuantity = unit.convertToBaseUnit(quantity);

      // Check if there is enough of the ingredient
      return availableBaseQuantity >= neededBaseQuantity;

    } catch (IllegalArgumentException e) {
      return false; // Handles exceptions where ingredient is not found
    }
  }

}
