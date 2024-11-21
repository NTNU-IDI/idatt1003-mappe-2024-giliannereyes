package edu.ntnu.iir.bidata.storage;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    ingredients = new ArrayList<>();
  }

  /**
   * Adds a new ingredient to the fridge if it does not already exist in the fridge.
   *
   * <p>If an ingredient of the same type already exists, the quantity of
   * the existing ingredient is increased with the new ingredient's quantity instead.</p>
   *
   * @param newIngredient The ingredient to add.
   *
   * @throws IllegalArgumentException if the ingredient is null, or if it
   *                                  has the same name as a different ingredient in the fridge.
   */
  public void addIngredient(Ingredient newIngredient) {
    validateIngredient(newIngredient);

    findIngredientByName(newIngredient.getName())
        .ifPresentOrElse(
            storedIngredient -> mergeIngredients(storedIngredient, newIngredient),
            () -> ingredients.add(newIngredient)
        );
  }

  /**
   * Removes a specified quantity of an ingredient by name.
   *
   * @param name  The name of the ingredient to remove.
   * @param quantity  The quantity to remove.
   * @param unit The unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if the ingredient is not found,
   *                                  or if the quantity provided is negative or becomes negative,
   */
  public void decreaseIngredientQuantity(String name, double quantity, Unit unit) {
    validateQuantity(quantity);
    validateUnit(unit);
    validateName(name);

    Ingredient ingredient = findIngredientByName(name)
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("Ingredient '%s' not found", name)));

    ingredient.decreaseQuantity(quantity, unit);
    removeIfEmpty(ingredient);
  }

  /**
   * Searches for and retrieves an ingredient by the ingredient's name.
   *
   * @param name The name of the ingredient to search for.
   *
   * @return An Optional containing the ingredient if an ingredient with the same name is found.
   *         Otherwise, an empty Optional.
   */
  public Optional<Ingredient> findIngredientByName(String name) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name.trim()))
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
        .mapToDouble(Ingredient::getPrice)
        .sum();
  }

  /**
   * Retrieves all the fridge's ingredients.
   * Note: This method is only used for unit testing.
   *
   * @return A list containing the fridge's ingredients.
   */
  public List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredients);
  }

  /**
   * Merges two of the same ingredients by increasing the quantity of
   * the first one stored with the new one's quantity.
   *
   * @param storedIngredient The ingredient that is stored in the fridge.
   * @param newIngredient The new ingredient to add in the fridge.
   *
   * @throws IllegalArgumentException if the ingredients are not the same.
   */
  private void mergeIngredients(Ingredient storedIngredient, Ingredient newIngredient) {
    if (!storedIngredient.isSameAs(newIngredient)) {
      throw new IllegalArgumentException(
          "An ingredient with the same name but is different already exists."
      );
    }

    storedIngredient.increaseQuantity(newIngredient.getQuantity(), newIngredient.getUnit());
  }

  /**
   * Removes the ingredient from the fridge if the quantity is zero.
   *
   * @param ingredient The ingredient to remove.
   */
  private void removeIfEmpty(Ingredient ingredient) {
    if (ingredient.getQuantity() == 0) {
      ingredients.remove(ingredient);
    }
  }

  private void validateIngredient(Ingredient ingredient) {
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null");
    }
  }

  private void validateQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative.");
    }
  }

  private void validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
  }

  private void validateName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Ingredient name cannot be null or blank.");
    }
  }

}
