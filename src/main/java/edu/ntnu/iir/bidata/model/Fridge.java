package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Represents a fridge that contains a list of {@link Ingredient}.
 *
 * <p>The fridge can add, remove, and search for ingredients. It can also calculate
 * the value of ingredients in the fridge, and retrieve all ingredients or
 * ingredients that expire before a specified date.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.0
 */
public class Fridge {
  private final List<Ingredient> ingredients;

  /**
   * Constructs a new fridge with an empty list of ingredients.
   */
  public Fridge() {
    ingredients = new ArrayList<>();
  }

  /**
   * Adds a new ingredient to the fridge if it is not already stored, or
   * adds the quantity of the new ingredient to the stored ingredient.
   *
   * @param newIngredient is the ingredient to add.
   *
   * @throws IllegalArgumentException if the ingredient is null, or if the
   *      ingredient cannot be added to the fridge.
   */
  public void addIngredient(Ingredient newIngredient) {
    Validation.validateNonNull(newIngredient, "Ingredient");
    findIngredientByName(newIngredient.getName())
        .ifPresentOrElse(
            storedIngredient -> mergeIngredients(storedIngredient, newIngredient),
            () -> ingredients.add(newIngredient)
        );
  }

  /**
   * Removes a quantity of an ingredient from the fridge. If the quantity
   * becomes zero, the ingredient is removed from the fridge.
   *
   * @param name is the name of the ingredient to remove.
   * @param quantity is the quantity to remove.
   * @param unit is the unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if the parameters are invalid, the ingredient
   *      is not found in the fridge or the quantity becomes negative.
   */
  public void removeIngredient(String name, double quantity, Unit unit) {
    Validation.validatePositiveNumber(quantity, "Quantity to remove");
    Validation.validateNonNull(unit, "Unit");
    Validation.validateNonEmptyString(name, "Ingredient name");
    Ingredient ingredient = findIngredientByName(name)
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("Ingredient '%s' not found", name)));
    ingredient.decreaseQuantity(quantity, unit);
    removeIfEmpty(ingredient);
  }

  /**
   * Searches for an ingredient in the fridge by its name, and
   * retrieves the ingredient if it is found.
   *
   * @param name is the name of the ingredient to search for.
   *
   * @return An {@link Optional} containing the ingredient if found.
   *      Otherwise, an empty {@link Optional}.
   *
   * @throws IllegalArgumentException if the name is blank.
   */
  public Optional<Ingredient> findIngredientByName(String name) {
    Validation.validateNonEmptyString(name, "Ingredient name");
    return ingredients.stream()
        .filter(ingredient -> ingredient.getName().equalsIgnoreCase(name.trim()))
        .findFirst();
  }

  /**
   * Retrieves ingredients in the fridge that expire before the specified date.
   *
   * @param date is the cutoff date to check if the ingredient is expired.
   *
   * @return A list of ingredients that expire before the specified date.
   *
   * @throws IllegalArgumentException if the date is null.
   */
  public List<Ingredient> findExpiringIngredientsBeforeDate(LocalDate date) {
    Validation.validateNonNull(date, "Date");
    return ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .toList();
  }

  /**
   * Retrieves all the ingredients in the fridge sorted alphabetically.
   *
   * @return A list of ingredients sorted alphabetically.
   */
  public List<Ingredient> findSortedIngredients() {
    return ingredients.stream()
        .sorted(Comparator.comparing(Ingredient::getName))
        .toList();
  }

  /**
   * Calculates the total value of the ingredients in the fridge
   * that expire before the specified date.
   *
   * @param date is the cutoff date to check if the ingredient is expired.
   *
   * @return The total value of the ingredients expiring before the specified date.
   */
  public double calculateExpiringValueByDate(LocalDate date) {
    Validation.validateNonNull(date, "Date");
    return ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .mapToDouble(Ingredient::getPrice)
        .sum();
  }

  /**
   * Calculates the total value of the ingredients in the fridge.
   *
   * @return The total value of all ingredients in the fridge.
   */
  public double calculateTotalValue() {
    return ingredients.stream()
        .mapToDouble(Ingredient::getPrice)
        .sum();
  }

  /**
   * Retrieves all ingredients in the fridge.
   *
   * @return A list containing the ingredients in the fridge.
   */
  public List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredients);
  }

  /**
   * Increases the quantity of the stored ingredient by the quantity
   * of the new ingredient, if the ingredients are the same.
   *
   * @param storedIngredient is the ingredient that is stored in the fridge.
   * @param newIngredient is the ingredient to add to the stored ingredient.
   *
   * @throws IllegalArgumentException if the ingredients are not the same.
   */
  private void mergeIngredients(Ingredient storedIngredient, Ingredient newIngredient) {
    if (!storedIngredient.matchesIngredient(newIngredient)) {
      throw new IllegalArgumentException(
          "An ingredient with the same name but has different attributes already exists."
      );
    }
    storedIngredient.increaseQuantity(newIngredient.getQuantity(), newIngredient.getUnit());
  }

  /**
   * Removes the ingredient from the fridge if the quantity is zero.
   *
   * @param ingredient is the ingredient to remove.
   */
  private void removeIfEmpty(Ingredient ingredient) {
    if (ingredient.getQuantity() == 0) {
      ingredients.remove(ingredient);
    }
  }
}
