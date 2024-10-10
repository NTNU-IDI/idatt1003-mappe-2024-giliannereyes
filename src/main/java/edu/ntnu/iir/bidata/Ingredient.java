package edu.ntnu.iir.bidata;

import java.time.LocalDate;

/**
 * Represents an ingredient in the fridge or a recipe.
 * <p> The Ingredient class holds information about a specific ingredient,
 * including its name, quantity per unit, unit of measurement,
 * expiry date and price per unit.</p>
 */
public class Ingredient {
  private final String name;
  private double quantity;
  private double price;
  private double pricePerUnit;
  private final String unit;
  private final LocalDate expiryDate;

  /**
   * Constructor to create a new instance of Ingredient.
   *
   * @param name The name of the ingredient.
   * @param quantity The quantity of the ingredient.
   * @param price The price per unit of the ingredient.
   * @param unit  The unit of measurement.
   * @param expiryDate  The expiry date of the ingredient.
   */
  public Ingredient(
      String name, double quantity, double price, String unit, LocalDate expiryDate)
  {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.pricePerUnit = this.price / this.quantity;
    this.unit = unit;
    this.expiryDate = expiryDate;

    validateName();
    validateQuantity();
    validatePrice();
    validateUnit();
    validateExpiryDate();
  }

  /**
   * Validates the name of the ingredient.
   *
   * <p> This method checks if the name is null or empty.
   * If either condition is true, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the name is null or empty.
   */
  public void validateName() {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException("Ingredient name is null or empty.");
    }
  }

  /**
   * Validates the quantity of the ingredient.
   *
   * <p> This method checks if the quantity is less than zero.
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
   * <p> This method checks if the price is negative.
   * If so, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the price is negative.
   */
  public void validatePrice() {
    if (price < 0) {
      throw new IllegalArgumentException("Ingredient price is negative.");
    }
  }

  /**
   * Validates the unit of measurement for the ingredient.
   *
   * <p> This method checks if the unit null or empty.
   * If either condition is true, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the unit is null or empty
   */
  public void validateUnit()  {
    if (unit == null || unit.isEmpty()) {
      throw new IllegalArgumentException("Ingredient unit is null or empty.");
    }
  }

  /**
   * Validates the expiry date of the ingredient.
   *
   * <p> This method checks if the expiry date is null.
   * If so, an IllegalArgumentException is thrown.</p>
   *
   * @throws IllegalArgumentException if the expiry date is null.
   */
  public void validateExpiryDate() {
    if (expiryDate == null) {
      throw new IllegalArgumentException("Ingredient expiryDate is null.");
    }
  }

  public void removeQuantity(double quantity) {
    this.quantity -= quantity;
  }

  public void addQuantity(double quantity) {
    this.quantity += quantity;
  }

  // Get-methods
  public String getName() {
    return this.name;
  }

  public double getQuantity() {
    return this.quantity;
  }

  public double getPrice() {
    return this.price;
  }

  public double getPricePerUnit() {
    return this.pricePerUnit;
  }

  public String getUnit() {
    return this.unit;
  }

  public LocalDate getExpiryDate() {
    return this.expiryDate;
  }

}
