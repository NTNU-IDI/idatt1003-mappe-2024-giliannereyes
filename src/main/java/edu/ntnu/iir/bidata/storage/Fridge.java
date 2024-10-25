package edu.ntnu.iir.bidata.storage;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;

import java.time.LocalDate;
import java.util.*;

/**
 * Represents a fridge that stores ingredients.
 *<br>
 * Fridge has the following functionalities:
 * <ul>
 *   <li></li>
 * </ul>
 */
public class Fridge {
  private final List<Ingredient> ingredients; // List to hold the ingredients

  /**
   * Constructs a new Fridge instance that initializes the ingredients list.
   */
  public Fridge() {
    ingredients = new ArrayList<>();  // Initialize the ingredients as an empty ArrayList
  }

  /**
   * Adds an ingredient to the ingredients.
   *
   * @param ingredient The ingredient to add.
   */
  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
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
  public void reduceIngredientQuantity(String ingredientName, double quantity, Unit unit) {
    // Use an iterator to safely remove items from the ingredients
    Iterator<Ingredient> iterator = ingredients.iterator();

    while (iterator.hasNext()) {
      Ingredient ingredient = iterator.next();

      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        ingredient.decreaseQuantity(quantity, unit);  // Remove the specified quantity

        // Ingredient is removed from ingredients if quantity equals zero
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
  public Ingredient getIngredientByName(String ingredientName) {
    return ingredients.stream()
        // Filters by ingredient name
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(ingredientName))
        // Returns first instance of specified ingredient
        .findFirst()
        // Throws an IllegalArgumentException if no ingredient is found
        .orElseThrow(() ->
            new IllegalArgumentException("Ingredient '" + ingredientName + "' not found."));
  }

  /**
   * Retrieves all the ingredients that expire before the specified date.
   *
   * @param date The latest date ingredients can expire.
   *
   * @return A list of ingredients.
   */
  public List<Ingredient> getIngredientsBeforeDate(LocalDate date) {
    List<Ingredient> expiringIngredients = ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .toList();

    // If there are no expiring ingredients before the specified date
    if (expiringIngredients.isEmpty()) {
      throw new NoSuchElementException("There are no ingredients that expire before this date.");
    }

    return expiringIngredients;
  }

  /**
   * Retrieves all the ingredients in the fridge sorted alphabetically.
   *
   * @return A list of ingredients.
   */
  public List<Ingredient> getSortedIngredients() {
    List<Ingredient> sortedIngredients = ingredients.stream()
        .sorted(Comparator.comparing(Ingredient::getName))
        .toList();

    // If there are no ingredients in the fridge
    if (sortedIngredients.isEmpty()) {
      throw new NoSuchElementException("There are no ingredients that expire before this date.");
    }

    return sortedIngredients;
  }

  /**
   * Calculates the total price of all ingredients in the provided list.
   *
   * @param ingredientList The list of ingredients.
   *
   * @return The total price of the ingredients in the list.
   */
  public double calculatePrice(List<Ingredient> ingredientList) {
    return ingredientList.stream()
        .mapToDouble(Ingredient::getPrice) // Maps each ingredient instance to its price
        .sum(); // Calculates the sum of the prices
  }

}
