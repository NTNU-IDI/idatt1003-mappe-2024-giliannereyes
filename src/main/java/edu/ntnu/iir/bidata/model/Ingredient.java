package edu.ntnu.iir.bidata.model;

import java.time.LocalDate;

/**
 * Represents an ingredient by name, quantity, price,
 * price per unit, unit of measurement and expiry date.
 *<p>
 *   This class provides methods to validate the properties of the ingredient,
 *   and to manipulate and retrieve its details.
 *</p>
 */
public class Ingredient {
  private String name;  // Name of the ingredient
  private double quantity;  // Quantity of the ingredient in specified unit
  private double pricePerUnit;  // Price per unit of the ingredient
  private Unit unit;  // Unit of measurement for the ingredient
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

    setName(name);
    setQuantity(quantity);
    setPricePerUnit(pricePerUnit);
    setUnit(unit);
    setExpiryDate(expiryDate);
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
    setName(name);
    setQuantity(quantity);
    setUnit(unit);
  }

  /**
   * Sets the name of the ingredient.
   *
   * @param name The name of the ingredient. A name that is empty, blank or
   *             {@code null} is not accepted.
   *
   * @throws IllegalArgumentException if the name is empty, blank or {@code null}.
   */
  private void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Ingredient name cannot be null, empty or blank.");
    }

    this.name = name;
  }

  /**
   * Sets the quantity of the ingredient.
   *
   * @param quantity The quantity of the ingredient. A quantity that is negative is
   *                 not accepted.
   *
   * @throws IllegalArgumentException if the quantity is negative.
   */
  private void setQuantity(double quantity) {
    if (quantity < 0) {
      throw new IllegalArgumentException("Ingredient quantity cannot be negative.");
    }

    this.quantity = quantity;
  }

  /**
   * Sets the price per unit of the ingredient.
   *
   * @param pricePerUnit The ingredient's price per unit. A price per unit that is
   *                     negative or zero is not accepted.
   *
   * @throws IllegalArgumentException if the price per unit is negative or zero.
   */
  private void setPricePerUnit(double pricePerUnit) {
    if (pricePerUnit <= 0) {
      throw new IllegalArgumentException("Ingredient price cannot be negative or zero.");
    }

    this.pricePerUnit = pricePerUnit;
  }

  /**
   * Sets the unit of measurement of the ingredient.
   *
   * @param unit The ingredient's unit of measurement. The unit of measurement
   *             cannot be {@code null}.
   *
   * @throws IllegalArgumentException if the unit of measurement is {@code null}.
   */
  private void setUnit(Unit unit)  {
    if (unit == null) {
      throw new IllegalArgumentException("Ingredient unit cannot be null.");
    }

    this.unit = unit;
  }

  /**
   * Sets the expiry date of the ingredient.
   *
   * @param expiryDate The ingredient's expiry date. Expiry date cannot be {@code null}.
   *                   Expiry date can be a date in the past as an ingredient can be already expired.
   *
   * @throws IllegalArgumentException if the expiry date is {@code null}.
   */
  private void setExpiryDate(LocalDate expiryDate) {
    if (expiryDate == null) {
      throw new IllegalArgumentException("Ingredient expiry date cannot be null.");
    }

    this.expiryDate = expiryDate;
  }

  /**
   * Removes a specified quantity from the ingredient.
   * <p>
   *   Decreases the quantity of the ingredient by the specified amount.
   *   If specified amount is negative or greater than the available quantity,
   *   an IllegalArgumentException is thrown to prevent the quantity from becoming larger or negative.
   * </p>
   *
   * @param inputQuantity The quantity to remove.
   * @param inputUnit The unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if specified quantity is greater than available quantity.
   */
  public void decreaseQuantity(double inputQuantity, Unit inputUnit) {
    validateQuantityAndUnit(inputQuantity, inputUnit);

    // Convert quantities of ingredients to their base unit values
    double baseAvailable = this.unit.convertToBaseUnit(this.quantity);
    double baseToRemove = inputUnit.convertToBaseUnit(inputQuantity);

    // Checks for invalid input
    if ((baseAvailable - baseToRemove) < 0) {
      throw new IllegalArgumentException("Insufficient amount of ingredients. Cannot remove " + inputQuantity + " " + inputUnit);
    }

    // Decrease the ingredient's available quantity in base unit
    double newAvailableBase = baseAvailable - baseToRemove;
    // Update the ingredient's quantity
    this.quantity = this.unit.convertFromBaseUnit(newAvailableBase);
  }

  /**
   * Increases the ingredient's quantity by the specified amount.
   *
   * @param inputQuantity The quantity to add.
   * @param inputUnit The unit of measurement of the quantity to add.
   *
   * @throws IllegalArgumentException if the quantity is negative.
   */
  public void increaseQuantity(double inputQuantity, Unit inputUnit) {
    validateQuantityAndUnit(inputQuantity, inputUnit);

    // Convert quantities of ingredients to their base unit values
    double baseAvailable = this.unit.convertToBaseUnit(this.quantity);
    double baseToAdd = inputUnit.convertToBaseUnit(inputQuantity);

    // Increase the ingredient's available quantity in base unit
    double newAvailableBase = baseAvailable + baseToAdd;
    // Update the ingredient's quantity
    this.quantity = this.unit.convertFromBaseUnit(newAvailableBase);
  }

  /**
   * Validates the quantity and unit for operations.
   *
   * @param inputQuantity The quantity to validate.
   * @param inputUnit The unit to validate.
   *
   * @throws IllegalArgumentException if the unit is incompatible or null, or if the quantity is negative.
   */
  private void validateQuantityAndUnit(double inputQuantity, Unit inputUnit) {
    if (inputUnit == null) {
      throw new IllegalArgumentException("Provided unit cannot be null.");
    }

    if (!this.unit.isSameType(inputUnit)) {
      throw new IllegalArgumentException("Unit mismatch: Cannot operate with " +
          inputUnit.getSymbol() + " on an ingredient measured in " + this.unit.getSymbol());
    }

    if (inputQuantity < 0) {
      throw new IllegalArgumentException("Invalid quantity: Quantity cannot be negative.");
    }
  }

  // Getters for ingredient fields

  /**
   * Returns the name of the ingredient.
   *
   * @return name of the ingredient.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the quantity of the ingredient.
   *
   * @return the quantity of the ingredient.
   */
  public double getQuantity() {
    return this.quantity;
  }

  /**
   * Returns the total price of the ingredient.
   * Price is calculated based on the ingredient's current quantity and price per unit.
   *
   * @return the total price of the ingredient.
   */
  public double getPrice() {
    return this.quantity * this.pricePerUnit;
  }

  /**
   * Returns the price per unit of the ingredient.
   *
   * @return the price per unit of the ingredient.
   */
  public double getPricePerUnit() {
    return this.pricePerUnit;
  }

  /**
   * Returns the unit of measurement for the ingredient.
   *
   * @return the unit of measurement.
   */
  public Unit getUnit() {
    return this.unit;
  }

  /**
   * Returns the expiry date of the ingredient.
   *
   * @return the expiry date.
   */
  public LocalDate getExpiryDate() {
    return this.expiryDate;
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
   * <p>Returns a string representation of the ingredient, including its name,
   * quantity, unit of measurement, total price, price per unit, and
   * expiry date.</p>
   *
   * @return A string formatted to display the ingredient's details.
   */
  @Override
  public String toString() {
    return "Name: " + name + "\n" +
        "Quantity: " + quantity + " " + unit.getSymbol() + "\n" +
        "Price: " + getPrice() + " kr\n" +
        "Price per unit: " + pricePerUnit + " kr/" + unit.getSymbol() + "\n" +
        "Expiry Date: " + expiryDate;
  }

}
