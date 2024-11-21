package edu.ntnu.iir.bidata.utils;

import java.util.Collection;
import java.util.Optional;

/**
 * The Result class represents the result of an operation, either successful or unsuccessful.
 * It can hold a message and data of the type {@code T} if successful,
 * or just a message.
 *
 * @param <T> The type of data that may be included in the result.
 */
public class Result<T> {
  private final boolean success;
  private final T data;
  private final String message;

  /**
   * Constructs an instance of Result.
   *
   * @param success A boolean value indicating if the operation was successful or not.
   * @param message A message providing details about the result.
   */
  public Result(boolean success, String message) {
    this.success = success;
    this.data = null;
    this.message = message;
  }

  /**
   * Constructs an instance of Result.
   *
   * @param success A boolean value indicating if the operation was successful or not.
   * @param data The data associated with the result of the operation.
   * @param message A message providing details about the result.
   */
  public Result(boolean success, T data, String message) {
    this.success = success;
    this.data = data;
    this.message = message;
  }

  /**
   * Gets the message describing the result.
   *
   * @return The result message.
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Indicates whether the operation was successful or not.
   *
   * @return {@code true} if the operation was successful, otherwise {@code false}.
   */
  public boolean isSuccess() {
    return this.success;
  }

  /**
   * Gets the data associated with the result, if available.
   *
   * @return An {@code Optional} containing the data, or an empty {@code Optional} if there are none.
   */
  public Optional<T> getData() {
    return Optional.ofNullable(this.data);
  }

  public String formatResult() {
      StringBuilder builder = new StringBuilder();
      builder.append(message).append("\n");

      if (data != null) {
        if (data instanceof Collection<?> collection) {
          collection.forEach(item -> builder.append(item).append("\n"));
        } else {
          builder.append(data).append("\n");
        }
      }

      return builder.toString();
  }

  @Override
  public String toString() {
    return formatResult();
  }

}
