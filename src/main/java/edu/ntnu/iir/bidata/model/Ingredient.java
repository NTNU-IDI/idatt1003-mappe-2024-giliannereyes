package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.time.LocalDate;

/**
 * Represents an ingredient with properties such as name, quantity,
 * price per unit, unit of measurement, and expiry date.
 *
 * <p>Provides methods to validate ingredient properties, check expiration
 * status, set quantity and check compatibility with other ingredients.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.0
 */
public class Ingredient {
  private String name;
  private double quantity;
  private double pricePerUnit;
  private Unit unit;
  private LocalDate expiryDate;
  private static final double tolerance = 1e-6;

  /**
   * Constructs a new Ingredient instance.
   *
   * @param name is the name of the ingredient.
   * @param quantity is the quantity of the ingredient in specified unit.
   * @param pricePerUnit is the price per unit of the ingredient.
   * @param unit is the ingredient's unit of measurement.
   * @param expiryDate is the expiry date of the ingredient.
   *
   * @throws IllegalArgumentException if any of the parameters are invalid.
   */
  public Ingredient(
      String name, double quantity, double pricePerUnit, Unit unit, LocalDate expiryDate) {
    setName(name);
    setQuantity(quantity);
    setPricePerUnit(pricePerUnit);
    setUnit(unit);
    setExpiryDate(expiryDate);
  }

  /**
   * Constructs a new Ingredient instance.
   *
   * @param name is the name of the ingredient.
   * @param quantity is the quantity of the ingredient in specified unit.
   * @param unit is the ingredient's unit of measurement.
   *
   * @throws IllegalArgumentException if any of the parameters are invalid.
   */
  public Ingredient(String name, double quantity, Unit unit) {
    setName(name);
    setQuantity(quantity);
    setUnit(unit);
  }

  /**
   * Removes the specified quantity from the ingredient.
   *
   * @param quantityToRemove is the quantity to remove.
   * @param unitToRemove is the unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if the provided quantity or unit is invalid,
   *     or if the specified quantity is greater than available quantity.
   */
  public void decreaseQuantity(double quantityToRemove, Unit unitToRemove) {
    validateQuantityOperation(quantityToRemove, unitToRemove);
    double baseAvailable = unit.convertToBaseUnitValue(quantity);
    double baseToRemove = unitToRemove.convertToBaseUnitValue(quantityToRemove);
    double updatedBaseQuantity = baseAvailable - baseToRemove;
    if (updatedBaseQuantity < -tolerance) {
      Validation.validateNonNegativeNumber(updatedBaseQuantity, "Remaining quantity after removal");
    }
    setQuantity(unit.convertFromBaseUnitValue(updatedBaseQuantity));
  }

  /**
   * Increases the quantity of the ingredient by the specified amount.
   *
   * @param quantityToAdd is the quantity to add.
   * @param unitToAdd is the unit of measurement of the quantity to add.
   *
   * @throws IllegalArgumentException if the quantity is negative.
   */
  public void increaseQuantity(double quantityToAdd, Unit unitToAdd) {
    validateQuantityOperation(quantityToAdd, unitToAdd);
    double baseAvailable = unit.convertToBaseUnitValue(quantity);
    double baseToAdd = unitToAdd.convertToBaseUnitValue(quantityToAdd);
    double updatedBaseQuantity = baseAvailable + baseToAdd;
    setQuantity(unit.convertFromBaseUnitValue(updatedBaseQuantity));
  }

  /**
   * Checks if the ingredient is expired by comparing the expiry date with the
   * current date, and returns the result.
   *
   * @return {@code true} if the ingredient is expired. Otherwise, {@code false}.
   */
  public boolean isExpired() {
    return this.expiryDate.isBefore(LocalDate.now());
  }

  /**
   * Checks if the ingredient matches another ingredient by comparing their
   * name, expiry date, price per unit, and unit of measurement. A match
   * indicates that the ingredients are of the same type.
   *
   * @param otherIngredient is the ingredient to compare with.
   *
   * @return {@code true} if the ingredients match. Otherwise, {@code false}.
   */
  public boolean matchesIngredient(Ingredient otherIngredient) {
    Validation.validateNonNull(otherIngredient, "Ingredient");
    return otherIngredient.getName().equalsIgnoreCase(name)
        && otherIngredient.getExpiryDate().equals(expiryDate)
        && otherIngredient.getPricePerUnit() == (pricePerUnit)
        && otherIngredient.getUnit().isCompatibleWith(unit);
  }

  /**
   * Verifies if the unit of measurement of the ingredient is compatible with
   * another unit of measurement. Compatibility is determined by the unit type.
   *
   * @param otherUnit is the unit of measurement to compare with.
   *
   * @throws IllegalArgumentException if the unit types do not match.
   */
  private void verifyUnitMatch(Unit otherUnit) {
    if (!unit.isCompatibleWith(otherUnit)) {
      throw new IllegalArgumentException("Unit mismatch: Cannot operate with "
          + otherUnit.getSymbol() + " on an ingredient measured in " + this.unit.getSymbol());
    }
  }

  /**
   * Validates the quantity operation by checking if the quantity is positive,
   * and if the unit of measurement is valid.
   *
   * @param quantity is the quantity to validate.
   * @param unit is the unit of measurement to validate.
   *
   * @throws IllegalArgumentException if the quantity is negative or the unit is null.
   */
  private void validateQuantityOperation(double quantity, Unit unit) {
    Validation.validatePositiveNumber(quantity, "Quantity");
    Validation.validateNonNull(unit, "Unit");
    verifyUnitMatch(unit);
  }

  /**
   * Returns a string representation of the ingredient containing its details.
   * <strong>The string format was generated by ChatGPT.</strong>
   *
   * @return A string representation of the ingredient.
   */
  @Override
  public String toString() {
    return String.format(
        "| %-18s | %-15.2f | %-15s | %-18s | %-18s | %-15s |",
        name,
        quantity,
        unit.getSymbol(),
        expiryDate.toString(),
        String.format("%.2f/%s kr", pricePerUnit, unit.getSymbol()),
        String.format("%.2f kr", getPrice())
    );
  }

  /**
   * Retrieves the name of the ingredient.
   *
   * @return name of the ingredient.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the quantity of the ingredient.
   *
   * @return the quantity of the ingredient.
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Retrieves the total price of the ingredient.
   * Price is calculated based on the ingredient's current quantity and price per unit.
   *
   * @return the total price of the ingredient.
   */
  public double getPrice() {
    return quantity * pricePerUnit;
  }

  /**
   * Retrieves the price per unit of the ingredient.
   *
   * @return the price per unit of the ingredient.
   */
  public double getPricePerUnit() {
    return pricePerUnit;
  }

  /**
   * Retrieves the unit of measurement for the ingredient.
   *
   * @return the unit of measurement.
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   * Retrieves the expiry date of the ingredient.
   *
   * @return the expiry date.
   */
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

  /**
   * Sets the name of the ingredient.
   *
   * @param name is the name of the ingredient.
   *
   * @throws IllegalArgumentException if the name is empty or null.
   */
  private void setName(String name) {
    Validation.validateNonEmptyString(name, "Ingredient name");
    this.name = name;
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param quantity is the quantity of the ingredient.
   *
   * @throws IllegalArgumentException if the quantity is negative.
   */
  private void setQuantity(double quantity) {
    Validation.validateNonNegativeNumber(quantity, "Ingredient quantity");
    this.quantity = quantity;
  }

  /**
   * Sets the price per unit of the ingredient.
   *
   * @param pricePerUnit is the price per unit of the ingredient.
   *
   * @throws IllegalArgumentException if the price per unit is negative.
   */
  private void setPricePerUnit(double pricePerUnit) {
    Validation.validatePositiveNumber(pricePerUnit, "Price per unit");
    this.pricePerUnit = pricePerUnit;
  }

  /**
   * Sets the unit of measurement for the ingredient.
   *
   * @param unit is the unit of measurement.
   *
   * @throws IllegalArgumentException if the unit is null.
   */
  private void setUnit(Unit unit) {
    Validation.validateNonNull(unit, "Unit");
    this.unit = unit;
  }

  /**
   * Sets the expiry date of the ingredient.
   *
   * @param expiryDate is the expiry date of the ingredient.
   *
   * @throws IllegalArgumentException if the expiry date is null
   */
  private void setExpiryDate(LocalDate expiryDate) {
    Validation.validateNonNull(expiryDate, "Date");
    this.expiryDate = expiryDate;
  }
}
