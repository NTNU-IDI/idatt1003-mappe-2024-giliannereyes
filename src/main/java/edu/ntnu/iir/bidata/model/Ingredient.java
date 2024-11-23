package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.time.LocalDate;

/**
 * Represents an ingredient by name, quantity, price, price per unit,
 * unit of measurement and expiry date.
 *
 *<p>This class provides methods to validate the properties of the ingredient,
 *   and to manipulate and retrieve its details.</p>
 */
public class Ingredient {
  private final String name;  // Name of the ingredient
  private double quantity;  // Quantity of the ingredient in specified unit
  private double pricePerUnit;  // Price per unit of the ingredient
  private final Unit unit;  // Unit of measurement for the ingredient
  private LocalDate expiryDate; // Expiry date of the ingredient

  /**
   * Constructs a new Ingredient instance.
   *
   * @param name The name of the ingredient.
   * @param quantity The quantity of the ingredient in specified unit.
   * @param pricePerUnit The price per unit of the ingredient.
   * @param unit  The unit of measurement.
   * @param expiryDate  The expiry date of the ingredient.
   *
   * @throws IllegalArgumentException if any of the parameters are invalid.
   */
  public Ingredient(
      String name, double quantity, double pricePerUnit, Unit unit, LocalDate expiryDate) {
    Validation.validateNonEmptyString(name);
    Validation.validatePositiveNumber(quantity);
    Validation.validatePositiveNumber(pricePerUnit);
    Validation.validateUnit(unit);
    Validation.validateDate(expiryDate);

    this.name = name;
    this.quantity = quantity;
    this.pricePerUnit = pricePerUnit;
    this.unit = unit;
    this.expiryDate = expiryDate;
  }

  /**
   * Constructs a new Ingredient instance.
   *
   * @param name The name of the ingredient.
   * @param quantity The quantity of the ingredient in specified unit.
   * @param unit  The unit of measurement.
   *
   * @throws IllegalArgumentException if any of the parameters are invalid.
   */
  public Ingredient(String name, double quantity, Unit unit) {
    Validation.validateNonEmptyString(name);
    Validation.validatePositiveNumber(quantity);
    Validation.validateUnit(unit);

    this.name = name;
    this.quantity = quantity;
    this.unit = unit;
  }

  /**
   * Removes a specified quantity from the ingredient.
   *
   * @param quantityToRemove The quantity to remove.
   * @param unitToRemove The unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if the provided quantity or unit is invalid,
   *         or if specified quantity is greater than available quantity.
   */
  public void decreaseQuantity(double quantityToRemove, Unit unitToRemove) {
    validateQuantityOperation(quantityToRemove, unitToRemove);

    double baseAvailable = unit.convertToBaseUnitValue(quantity);
    double baseToRemove = unitToRemove.convertToBaseUnitValue(quantityToRemove);
    double updatedBaseQuantity = baseAvailable - baseToRemove;

    Validation.validateNonNegativeNumber(updatedBaseQuantity);
    quantity = unit.convertFromBaseUnitValue(updatedBaseQuantity);
  }

  /**
   * Increases the ingredient's quantity by the specified amount.
   *
   * @param quantityToAdd The quantity to add.
   * @param unitToAdd The unit of measurement of the quantity to add.
   *
   * @throws IllegalArgumentException if the quantity is negative.
   */
  public void increaseQuantity(double quantityToAdd, Unit unitToAdd) {
    validateQuantityOperation(quantityToAdd, unitToAdd);

    double baseAvailable = unit.convertToBaseUnitValue(quantity);
    double baseToAdd = unitToAdd.convertToBaseUnitValue(quantityToAdd);
    double updatedBaseQuantity = baseAvailable + baseToAdd;

    this.quantity = unit.convertFromBaseUnitValue(updatedBaseQuantity);
  }

  /**
   * Checks if the ingredient is expired.
   *
   * @return {@code true} if the ingredient is expired.
   *         {@code false} if the ingredient is not expired.
   */
  public boolean isExpired() {
    return this.expiryDate.isBefore(LocalDate.now());
  }

  /**
   * Checks if another ingredient is the same as this one by checking if the
   * expiry date, price per unit and name are the same. Checks if the ingredient's
   * unit of measurement is compatible too.
   *
   * @param otherIngredient The ingredient to compare with.
   *
   * @return {@code true} if this and the other ingredient are of the same type.
   *         Otherwise, {@code false}.
   */
  public boolean matchesIngredient(Ingredient otherIngredient) {
    Validation.validateIngredient(otherIngredient);
    return otherIngredient.getName().equals(name)
        && otherIngredient.getExpiryDate().equals(expiryDate)
        && otherIngredient.getPricePerUnit() == (pricePerUnit)
        && otherIngredient.getUnit().isCompatibleWith(unit);
  }

  /**
   * Verifies whether another unit type matches the ingredient's unit
   * type, in which a type is either volume or mass.
   *
   * @param otherUnit The unit of measurement to compare with.
   *
   * @throws IllegalArgumentException if the unit types do not match.
   */
  private void verifyUnitMatch(Unit otherUnit) {
    if (!unit.isCompatibleWith(otherUnit)) {
      throw new IllegalArgumentException("Unit mismatch: Cannot operate with "
          + unit.getSymbol() + " on an ingredient measured in " + this.unit.getSymbol());
    }
  }

  /**
   * Validates the quantity operation by checking if the quantity is positive,
   * and if the unit of measurement is valid.
   *
   * @param quantity is the quantity to validate.
   * @param unit is the unit of measurement to validate.
   */
  private void validateQuantityOperation(double quantity, Unit unit) {
    Validation.validatePositiveNumber(quantity);
    Validation.validateUnit(unit);
    verifyUnitMatch(unit);
  }

  /**
   * Returns a string representation of the ingredient, including its name,
   * quantity, unit of measurement, total price, price per unit, and
   * expiry date.
   *
   * @return A string representation of the ingredient containing its details.
   */
  @Override
  public String toString() {
    return String.format(
        "%-15s %-10.2f %-10s %-15s %-15s %-10s",
        name,
        quantity,
        unit.getSymbol(),
        expiryDate.toString(),
        String.format("%.2f/%s kr", pricePerUnit, unit.getSymbol()),
        String.format("%.2f kr", getPrice())
    );
  }

  /**
   * Returns the name of the ingredient.
   *
   * @return name of the ingredient.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the quantity of the ingredient.
   *
   * @return the quantity of the ingredient.
   */
  public double getQuantity() {
    return quantity;
  }

  /**
   * Returns the total price of the ingredient.
   * Price is calculated based on the ingredient's current quantity and price per unit.
   *
   * @return the total price of the ingredient.
   */
  public double getPrice() {
    return quantity * pricePerUnit;
  }

  /**
   * Returns the price per unit of the ingredient.
   *
   * @return the price per unit of the ingredient.
   */
  public double getPricePerUnit() {
    return pricePerUnit;
  }

  /**
   * Returns the unit of measurement for the ingredient.
   *
   * @return the unit of measurement.
   */
  public Unit getUnit() {
    return unit;
  }

  /**
   * Returns the expiry date of the ingredient.
   *
   * @return the expiry date.
   */
  public LocalDate getExpiryDate() {
    return expiryDate;
  }

}
