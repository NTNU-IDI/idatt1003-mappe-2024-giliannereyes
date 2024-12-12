package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.time.LocalDate;

/**
 * Represents an ingredient with fields such as name, quantity,
 * price per unit, unit of measurement, and expiry date.
 *
 * <p>Provides methods to validate ingredient properties, check expiration
 * status, set quantity and check compatibility with other ingredients.</p>
 *
 * @author Gilianne Reyes
 * @version 1.3
 * @since 1.0
 */
public class Ingredient {
  private String name;
  private double quantity;
  private double pricePerUnit;
  private Unit unit;
  private LocalDate expiryDate;
  private final double tolerance = 1e-6;

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
   * @param unitToRemove is the unit the quantity to remove is measured in.
   *
   * @throws IllegalArgumentException if the provided quantity or unit is invalid,
   *     or if the specified quantity is greater than available quantity.
   */
  public void decreaseQuantity(double quantityToRemove, Unit unitToRemove) {
    validateQuantityOperation(quantityToRemove, unitToRemove);
    double convertedQuantityToRemove = unitToRemove.convertTo(unit, quantityToRemove);
    double updatedQuantity = quantity - convertedQuantityToRemove;

    if (updatedQuantity < -tolerance) {
      Validation.validateNonNegativeNumber(updatedQuantity, "Remaining quantity after removal");
    } else if (updatedQuantity < tolerance) {
      setQuantity(0); // Close enough to zero to be considered zero
    } else {
      setQuantity(updatedQuantity);
    }
  }

  /**
   * Increases the quantity of the ingredient by the specified amount.
   *
   * @param quantityToAdd is the quantity to add.
   * @param unitToAdd is the unit the quantity to add is measured in.
   *
   * @throws IllegalArgumentException if the quantity is negative, or the unit
   *      is null or incompatible.
   */
  public void increaseQuantity(double quantityToAdd, Unit unitToAdd) {
    validateQuantityOperation(quantityToAdd, unitToAdd);
    double convertedQuantityToAdd = unitToAdd.convertTo(unit, quantityToAdd);
    double updatedQuantity = quantity + convertedQuantityToAdd;
    setQuantity(updatedQuantity);
  }

  /**
   * Checks if the ingredient is expired by comparing
   * the expiry date with the current date.
   *
   * @return {@code true} if the ingredient is expired. Otherwise, {@code false}.
   */
  public boolean isExpired() {
    return this.expiryDate.isBefore(LocalDate.now());
  }

  /**
   * Checks if the ingredient matches another ingredient in terms of name,
   * expiry date, and price per unit and unit type.
   *
   * @param otherIngredient is the ingredient to compare with.
   *
   * @return {@code true} if the ingredients match. Otherwise, {@code false}.
   *
   * @throws IllegalArgumentException if the provided ingredient is null.
   */
  public boolean matchesIngredient(Ingredient otherIngredient) {
    Validation.validateNonNull(otherIngredient, "Ingredient");
    if (!otherIngredient.getUnit().isCompatibleWith(unit)) {
      return false;
    }
    double conversionRatio = unit.getConversionRatio(otherIngredient.getUnit());
    double normalizedPricePerUnit = pricePerUnit * conversionRatio;
    return otherIngredient.getName().trim().equalsIgnoreCase(name.trim())
        && otherIngredient.getExpiryDate().equals(expiryDate)
        && Math.abs(otherIngredient.getPricePerUnit() - normalizedPricePerUnit) < tolerance;
  }

  /**
   * Converts the quantity of the ingredient to the value
   * in the provided unit.
   *
   * @param targetUnit is the unit to convert to.
   *
   * @return The quantity measured in the specified unit.
   *
   * @throws IllegalArgumentException if the provided unit is null or incompatible.
   */
  public double convertQuantityTo(Unit targetUnit) {
    Validation.validateNonNull(targetUnit, "Unit");
    return unit.convertTo(targetUnit, quantity);
  }

  /**
   * Verifies if the unit is compatible with another unit.
   *
   * @param otherUnit is the unit to compare with.
   *
   * @return {@code true} if the units are compatible. Otherwise, {@code false}.
   *
   * @throws IllegalArgumentException if the provided unit is null.
   */
  public boolean unitIsCompatibleWith(Unit otherUnit) {
    Validation.validateNonNull(otherUnit, "Unit");
    return unit.isCompatibleWith(otherUnit);
  }

  /**
   * Validates if the provided quantity is positive and the unit is not
   * null or incompatible with the ingredient's unit.
   *
   * @param quantity is the quantity to validate.
   * @param unit is the unit to validate.
   *
   * @throws IllegalArgumentException if the quantity is negative, or
   *      the unit is null or incompatible.
   */
  private void validateQuantityOperation(double quantity, Unit unit) {
    Validation.validatePositiveNumber(quantity, "Quantity");
    Validation.validateNonNull(unit, "Unit");
    if (!this.unit.isCompatibleWith(unit)) {
      throw new IllegalArgumentException("Unit mismatch: Cannot operate with "
          + unit.getSymbol() + " on an ingredient measured in " + this.unit.getSymbol());
    }
  }

  /**
   * Returns a string representation of the ingredient containing its details.
   * <strong>The format string was generated by ChatGPT.</strong>
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
