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
 *   <li>Add ingredients</li>
 *   <li>Remove ingredients</li>
 *   <li>Retrieve ingredients expiring before a specific date</li>
 *   <li>Retrieve alphabetically sorted ingredients</li>
 *   <li>Calculate total price of ingredients</li>
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
   * Adds a new ingredient to the fridge if it does not already exist in the fridge.
   *
   * <p>If an ingredient of the same type already exists, the quantity of
   * the existing ingredient is increased with the new ingredient's quantity instead.</p>
   *
   * Note: It is assumed that ingredients with the same name have the same
   * price and expiry date.
   *
   * @param newIngredient The ingredient to add.
   *
   * @throws IllegalArgumentException if the ingredient is {@code null}.
   */
  public void addIngredient(Ingredient newIngredient) {
    if (newIngredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null");
    }

    Optional<Ingredient> optIngredient = findIngredientByName(newIngredient.getName());

    if (optIngredient.isPresent()) {
      Ingredient existingIngredient = optIngredient.get();
      existingIngredient.increaseQuantity(newIngredient.getQuantity(), newIngredient.getUnit());
    } else {
      ingredients.add(newIngredient);
    }
  }

  /**
   * Removes a specified quantity of an ingredient by name.
   *
   * @param ingredientName  The name of the ingredient to remove.
   * @param quantity  The quantity to remove.
   * @param unit The unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if the quantity provided is negative, if ingredient is not found
   *                                  or if removing the quantity results in a negative quantity.
   */
  public void decreaseIngredientQuantity(String ingredientName, double quantity, Unit unit) {
    if (quantity < 0 || unit == null) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    Optional <Ingredient> optIngredient = findIngredientByName(ingredientName);

    if (optIngredient.isPresent()) {
      Ingredient ingredient = optIngredient.get();
      ingredient.decreaseQuantity(quantity, unit);

      if (ingredient.getQuantity() == 0) {
        ingredients.remove(ingredient);
      }

    } else {
      throw new IllegalArgumentException("Ingredient '" + ingredientName + "' not found.");
    }
  }

  /**
   * Searches for and retrieves an ingredient by the ingredient's name.
   *
   * @param ingredientName The name of the ingredient to search for.
   *
   * @return An Optional containing the ingredient if an ingredient with the same name is found.
   *         Otherwise, an empty Optional.
   */
  public Optional<Ingredient> findIngredientByName(String ingredientName) {
    return ingredients.stream()
        // Filters by ingredient name
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(ingredientName.trim()))
        // Returns first instance of specified ingredient
        .findFirst();
  }

  /**
   * Retrieves all the ingredients that expire before the specified date.
   *
   * @param date The latest date ingredients can expire.
   *
   * @return A list of ingredients.
   */
  public List<Ingredient> findIngredientsBeforeDate(LocalDate date) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .toList();
  }

  /**
   * Retrieves all the ingredients in the fridge sorted alphabetically.
   *
   * @return A list of ingredients.
   */
  public List<Ingredient> findSortedIngredients() {
    return ingredients.stream()
        .sorted(Comparator.comparing(Ingredient::getName))
        .toList();
  }

  /**
   * Calculates the total price of all ingredients in the fridge.
   *
   * @return The total price of the ingredients in the fridge.
   */
  public double calculateTotalPrice() {
    return ingredients.stream()
        .mapToDouble(Ingredient::getPrice) // Maps each ingredient instance to its price
        .sum(); // Calculates the sum of the prices
  }

  /**
   * Retrieves all the fridge's ingredients.
   * Note: This method is only used for unit testing.
   *
   * @return A list containing the fridge's ingredients.
   */
  public List<Ingredient> getIngredients() {
    return ingredients;
  }

}
