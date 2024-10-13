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
  private double price; // Total price for the quantity of the ingredient
  private final double pricePerUnit;  // Price per unit of the ingredient
  private final String unit;  // Unit of measurement for the ingredient
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
      String name, double quantity, double pricePerUnit, String unit, LocalDate expiryDate)
  {
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
   * <p> Checks if the unit null, empty or blank.
   * If either condition is true, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the unit is null, empty or blank.
   */
  public void validateUnit()  {
    if (unit == null || unit.isEmpty() || unit.isBlank()) {
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
   * @param quantity The amount of quantity to remove.
   * @throws IllegalArgumentException if specified quantity is greater than available quantity.
   */
  public void removeQuantity(double quantity) {
    if ((this.quantity - quantity) < 0) {
      throw new IllegalArgumentException("Insufficient amount of ingredients. Cannot remove " + quantity + " " + this.unit);
    } else if (quantity < 0) {
      throw new IllegalArgumentException("Provided quantity is negative. Cannot remove " + quantity + " " + this.unit);
    }
    this.quantity -= quantity;  // Decrease the available quantity
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
  public String getUnit() {
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

}
