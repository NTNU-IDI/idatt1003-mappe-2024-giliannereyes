package edu.ntnu.iir.bidata;

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
  private final String name;  // Name of the ingredient
  private double quantity;  // Quantity of the ingredient
  private final double price; // Total price for the quantity of the ingredient
  private final double pricePerUnit;  // Price per unit of the ingredient
  private final Unit unit;  // Unit of measurement for the ingredient
  private final LocalDate expiryDate; // Expiry date of the ingredient

  /**
   * Constructs a new Ingredient instance.
   *
   * @param name The name of the ingredient.
   * @param quantity The quantity of the ingredient.
   * @param pricePerUnit The price per unit of the ingredient.
   * @param unit  The unit of measurement.
   * @param expiryDate  The expiry date of the ingredient.
   * @throws IllegalArgumentException if any of the parameters are invalid.
   */
  public Ingredient(
      String name, double quantity, double pricePerUnit, Unit unit, LocalDate expiryDate) {
    this.name = name;
    this.quantity = quantity;
    this.pricePerUnit = pricePerUnit;
    this.price = pricePerUnit * quantity; // Calculate total price based on quantity and price per unit
    this.unit = unit;
    this.expiryDate = expiryDate;

    // Validates parameters
    validateName();
    validateQuantity();
    validatePricePerUnit();
    validateUnit();
    validateExpiryDate();
  }

  /**
   * Validates the name of the ingredient.
   *
   * <p> Checks if the name is null, empty or blank.
   * If either condition is true, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the name is null, empty or blank.
   */
  public void validateName() {
    if (name == null || name.isEmpty() || name.isBlank()) {
      throw new IllegalArgumentException("Ingredient name is null or empty.");
    }
  }

  /**
   * Validates the quantity of the ingredient.
   *
   * <p> Checks if the quantity is less than zero.
   * If so, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the quantity is zero.
   */
  public void validateQuantity() {
    if (quantity < 0) {
      throw new IllegalArgumentException("Ingredient quantity is negative.");
    }
  }

  /**
   * Validates the price of the ingredient.
   *
   * <p> Checks if the price per unit is negative.
   * If so, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the price per unit is negative.
   */
  public void validatePricePerUnit() {
    if (pricePerUnit < 0) {
      throw new IllegalArgumentException("Ingredient price is negative.");
    }
  }

  /**
   * Validates the unit of measurement for the ingredient.
   *
   * <p> Checks if the unit is null.
   * If so, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the unit is null.
   */
  public void validateUnit()  {
    if (unit == null) {
      throw new IllegalArgumentException("Ingredient unit is null, empty or blank.");
    }
  }

  /**
   * Validates the expiry date of the ingredient.
   *
   * <p> Checks if the expiry date is null.
   * If so, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the expiry date is null.
   */
  public void validateExpiryDate() {
    if (expiryDate == null) {
      throw new IllegalArgumentException("Ingredient expiryDate is null.");
    }
  }

  /**
   * Removes a specified quantity from the ingredient.
   * <p>
   *   Decreases the quantity of the ingredient by the specified amount.
   *   If specified amount is negative or greater than the available quantity,
   *   an IllegalArgumentException is thrown to prevent the quantity from becoming larger or negative.
   * </p>
   *
   * @param quantity The quantity to remove.
   * @param unit The unit of measurement of the quantity to remove.
   *
   * @throws IllegalArgumentException if specified quantity is greater than available quantity.
   */
  public void removeQuantity(double quantity, Unit unit) {
    // Convert quantities of ingredients to their base unit values
    double availableBase = this.unit.convertToBaseUnit(this.quantity);
    double removeBase = unit.convertToBaseUnit(quantity);

    // Checks for invalid input
    if ((availableBase - removeBase) < 0) {
      throw new IllegalArgumentException("Insufficient amount of ingredients. Cannot remove " + quantity + " " + unit);
    } else if (quantity < 0) {
      throw new IllegalArgumentException("Provided quantity is negative. Cannot remove " + quantity + " " + unit);
    }

    // Decrease the ingredient's available quantity in base unit
    double newAvailableBase = availableBase - removeBase;
    // Update the ingredient's quantity
    this.quantity -= this.unit.convertFromBaseUnit(newAvailableBase);
  }

  /**
   * Displays the details of the ingredient.
   * <p>
   *   Prints the ingredient's name, quantity, total price, price per unit,
   *   unit of measurement and expiry date to the console.
   * </p>
   */
  public void showDetails() {
    System.out.println("Name: " + name);
    System.out.println("Quantity: " + quantity + " " + unit);
    System.out.println("Price: " + price + " kr");
    System.out.println("Price per unit: " + pricePerUnit + "kr/" + unit);
    System.out.println("Expiry Date: " + expiryDate);
  }

  // Getters for ingredient properties

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
   *
   * @return the total price of the ingredient.
   */
  public double getPrice() {
    return this.price;
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
   * Returns a string representation of the ingredient, including its name,
   * quantity, unit of measurement, total price, price per unit, and
   * expiry date.
   *
   * @return A string formatted to display the ingredient's details.
   */
  @Override
  public String toString() {
    return "Name: " + name + "\n" +
        "Quantity: " + quantity + " " + unit + "\n" +
        "Price: " + price + " kr\n" +
        "Price per unit: " + pricePerUnit + " kr/" + unit + "\n" +
        "Expiry Date: " + expiryDate;
  }

}
