package edu.ntnu.iir.bidata.utils;

import java.util.Optional;

/**
 * Represents the result of an operation, either successful or failed.
 * Provides a message describing the result and, optionally, data associated with the result.
 *
 * <p>Instances of this class are immutable and can be created using the static factory methods.</p>
 *
 * @param <T> is the type of data that may be included in the result.
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.1
 */
public final class Result<T> {
  private final boolean success;
  private final T data;
  private final String message;

  /**
   * Constructs an instance of Result.
   *
   * @param success is a boolean value indicating if the operation was successful or not.
   * @param data is the data associated with the result of the operation.
   * @param message is a message providing details about the result.
   */
  private Result(boolean success, T data, String message) {
    this.success = success;
    this.data = data;
    this.message = message;
  }

  /**
   * Creates a successful result with no data and a message.
   *
   * @param message is the message describing the result.
   * @param <T> is the type of data that may be included in the result.
   *
   * @return A successful result with no data.
   */
  public static <T> Result<T> success(String message) {
    return new Result<>(true, null, message);
  }

  /**
   * Creates a successful result with data and a message.
   *
   * @param message is the message describing the result.
   * @param data is the data associated with the result.
   * @param <T> is the type of data that may be included in the result.
   *
   * @return A successful result with data.
   */
  public static <T> Result<T> success(String message, T data) {
    return new Result<>(true, data, message);
  }

  /**
   * Creates a failed result with a message.
   *
   * @param message is the message describing the result.
   * @param <T> is the type of data that may be included in the result.
   *
   * @return A failed result with a message.
   */
  public static <T> Result<T> failure(String message) {
    return new Result<>(false, null, message);
  }

  /**
   * Creates a failed result with a message and details.
   *
   * @param message is the message describing the result.
   * @param details is the details of the result.
   * @param <T> is the type of data that may be included in the result.
   *
   * @return A failed result with a message and details.
   */
  public static <T> Result<T> failure(String message, String details) {
    return new Result<>(false, null, String.format("%s\nReason: %s", message, details));
  }

  /**
   * Retrieves the message describing the result.
   *
   * @return The result message.
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * Retrieves the status of the operation.
   *
   * @return {@code true} if the operation was successful, otherwise {@code false}.
   */
  public boolean isSuccess() {
    return this.success;
  }

  /**
   * Retrieves the data associated with the result, if available.
   *
   * @return An {@code Optional} containing the data,
   *      or an empty {@code Optional} if there are none.
   */
  public Optional<T> getData() {
    return Optional.ofNullable(this.data);
  }
}
