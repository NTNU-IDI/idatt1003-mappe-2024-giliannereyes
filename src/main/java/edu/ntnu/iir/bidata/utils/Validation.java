package edu.ntnu.iir.bidata.utils;

/**
 * Utility class for validating parameters.
 *
 * <p>Provides methods for validating strings, numbers and objects.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.1
 */
public final class Validation {
  /**
   * Prevents instantiation of the utility class.
   */
  private Validation() {
    throw new UnsupportedOperationException("Utility class cannot be instantiated");
  }

  /**
   * Validates that the provided string is not null or empty.
   *
   * @param str is the string to validate.
   *
   * @throws IllegalArgumentException if the string is null or empty.
   */
  public static void validateNonEmptyString(String str, String fieldName) {
    if (str == null || str.isBlank()) {
      throw new IllegalArgumentException(fieldName + " cannot be empty or null");
    }
  }

  /**
   * Validates that the provided number is positive.
   *
   * @param number is the number to validate.
   *
   * @throws IllegalArgumentException if the number is not positive.
   */
  public static void validatePositiveNumber(double number, String fieldName) {
    if (number <= 0) {
      throw new IllegalArgumentException(fieldName + " must be positive.");
    }
  }

  /**
   * Validates that the provided number is non-negative.
   *
   * @param number is the number to validate.
   *
   * @throws IllegalArgumentException if the number is negative.
   */
  public static void validateNonNegativeNumber(double number, String fieldName) {
    if (number < 0) {
      throw new IllegalArgumentException(fieldName + " cannot be negative.");
    }
  }

  /**
   * Validates that the provided object is not null.
   *
   * @param obj is the object to validate.
   *
   * @throws IllegalArgumentException if the object is null.
   */
  public static void validateNonNull(Object obj, String fieldName) {
    if (obj == null) {
      throw new IllegalArgumentException(fieldName +  " cannot be null.");
    }
  }
}
