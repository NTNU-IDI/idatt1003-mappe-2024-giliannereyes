package edu.ntnu.iir.bidata.utils;

import edu.ntnu.iir.bidata.model.*;

import java.time.LocalDate;

public class Validation {

  /**
   * Validates that the provided string is not null, empty or blank.
   *
   * @param str is the string to validate.
   */
  public static void validateNonEmptyString(String str) {
    if (str == null || str.isBlank()) {
      throw new IllegalArgumentException("String cannot be empty or null");
    }
  }

  /**
   * Validates that the provided number is positive.
   *
   * @param number is the number to validate.
   */
  public static void validatePositiveNumber(double number) {
    if (number <= 0) {
      throw new IllegalArgumentException("Number must be positive");
    }
  }

  /**
   * Validates that the provided date is not null.
   *
   * @param date is the date to validate.
   */
  public static void validateDate(LocalDate date) {
    if (date == null) {
      throw new IllegalArgumentException("Expiry date cannot be null.");
    }
  }

  /**
   * Validates that the provided unit is not null.
   *
   * @param unit is the unit to validate.
   */
  public static void validateUnit(Unit unit) {
    if (unit == null) {
      throw new IllegalArgumentException("Unit cannot be null.");
    }
  }

  /**
   * Validates that the provided ingredient is not null.
   *
   * @param ingredient is the ingredient to validate.
   */
  public static void validateIngredient(Ingredient ingredient) {
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null");
    }
  }

  /**
   * Validates that the provided recipe is not null.
   *
   * @param recipe is the recipe to validate.
   */
  public static void validateRecipe(Recipe recipe) {
    if (recipe == null) {
      throw new IllegalArgumentException("Recipe cannot be null");
    }
  }

  /**
   * Validates that the provided fridge is not null.
   *
   * @param fridge is the fridge to validate.
   */
  public static void validateFridge(Fridge fridge) {
    if (fridge == null) {
      throw new IllegalArgumentException("Fridge cannot be null");
    }
  }

  /**
   * Validates that the provided cookbook is not null.
   *
   * @param cookbook is the cookbook to validate.
   */
  public static void validateCookbook(Cookbook cookbook) {
    if (cookbook == null) {
      throw new IllegalArgumentException("Cookbook cannot be null");
    }
  }
}
