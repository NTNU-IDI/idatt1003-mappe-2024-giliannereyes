package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private final Map<String, List<Ingredient>> ingredientMap;

  /**
   * Constructs a new fridge with an empty list of ingredients.
   */
  public Fridge() {
    ingredientMap = new HashMap<>();
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

    List<Ingredient> ingredientBatches = ingredientMap
        .computeIfAbsent(
            newIngredient.getName().toLowerCase().trim(), ignored -> new ArrayList<>()
        );

    findExactMatch(ingredientBatches, newIngredient)
        .ifPresentOrElse(
            match -> match.increaseQuantity(newIngredient.getQuantity(), newIngredient.getUnit()),
            () -> ingredientBatches.add(newIngredient)
        );
  }

  /**
   * Removes a quantity of an ingredient from the fridge. If the quantity
   * becomes zero, the ingredient is removed from the fridge.
   *
   * @param name is the name of the ingredient to remove.
   * @param quantity is the quantity to remove.
   * @param unit is the unit of measurement of the quantity to remove.
   * @param expiryDate is the expiry date of the ingredient to remove.
   *
   * @throws IllegalArgumentException if the parameters are invalid, the ingredient
   *     is not found in the fridge or the quantity becomes negative.
   */
  public void removeIngredient(String name, double quantity, Unit unit, LocalDate expiryDate) {
    Validation.validatePositiveNumber(quantity, "Quantity to remove");
    Validation.validateNonNull(unit, "Unit");
    Validation.validateNonEmptyString(name, "Ingredient name");
    Validation.validateNonNull(expiryDate, "Expiry date");

    List<Ingredient> ingredientBatches = ingredientMap
        .getOrDefault(name.trim().toLowerCase(), new ArrayList<>());
    Ingredient targetBatch = findBatchByUnitAndExpiry(ingredientBatches, unit, expiryDate)
        .orElseThrow(() -> new IllegalArgumentException(
            "The ingredient with the specified expiry date and unit was not found."
        ));
    targetBatch.decreaseQuantity(quantity, unit);
    removeBatchIfEmpty(targetBatch, ingredientBatches, name.toLowerCase());
  }

  /**
   * Retrieves the ingredients in the fridge that match the specified name.
   *
   * @param name is the name of the ingredient to search for.
   *
   * @return A list of ingredients that match the specified name.
   *
   * @throws IllegalArgumentException if the name is null or empty.
   */
  public List<Ingredient> findIngredientsByName(String name) {
    Validation.validateNonEmptyString(name, "Ingredient name");
    return ingredientMap.getOrDefault(name.trim().toLowerCase(), new ArrayList<>());
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
    return ingredientMap.values().stream()
        .flatMap(List::stream)
        .filter(ingredient -> ingredient.getExpiryDate().isBefore(date))
        .toList();
  }

  /**
   * Retrieves all the ingredients in the fridge sorted alphabetically.
   *
   * @return A list of ingredients sorted alphabetically.
   */
  public List<Ingredient> findSortedIngredients() {
    return ingredientMap.values().stream()
        .flatMap(List::stream)
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
    return ingredientMap.values().stream()
        .flatMap(List::stream)
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
    return ingredientMap.values().stream()
        .flatMap(List::stream)
        .mapToDouble(ingredient -> ingredient.getPricePerUnit() * ingredient.getQuantity())
        .sum();
  }

  /**
   * Retrieves all ingredients in the fridge.
   *
   * @return A list containing the ingredients in the fridge.
   */
  public List<Ingredient> getIngredients() {
    return ingredientMap.values().stream()
        .flatMap(List::stream)
        .toList();
  }

  /**
   * Finds an exact match for the given ingredient in the list.
   */
  private Optional<Ingredient> findExactMatch(
      List<Ingredient> ingredients, Ingredient ingredientToFind
  ) {
    return ingredients.stream()
        .filter(existingIngredient -> existingIngredient.matchesIngredient(ingredientToFind))
        .findFirst();
  }

  /**
   * Finds a batch in the ingredient list matching the unit and expiry date.
   */
  private Optional<Ingredient> findBatchByUnitAndExpiry(
      List<Ingredient> ingredients, Unit unit, LocalDate expiryDate
  ) {
    return ingredients.stream()
        .filter(ingredient -> ingredient.getExpiryDate().equals(expiryDate)
            && ingredient.getUnit().isCompatibleWith(unit))
        .findFirst();
  }

  /**
   * Removes a specific ingredient batch from the fridge if its quantity is zero.
   *
   * @param ingredientBatch the specific batch of the ingredient to remove.
   * @param ingredientBatches the list of batches for the same ingredient name.
   * @param ingredientKey the key representing the ingredient name in the fridge.
   */
  private void removeBatchIfEmpty(
      Ingredient ingredientBatch, List<Ingredient> ingredientBatches, String ingredientKey
  ) {
    if (ingredientBatch.getQuantity() <= 0) {
      ingredientBatches.remove(ingredientBatch);
      if (ingredientBatches.isEmpty()) {
        ingredientMap.remove(ingredientKey);
      }
    }
  }
}
