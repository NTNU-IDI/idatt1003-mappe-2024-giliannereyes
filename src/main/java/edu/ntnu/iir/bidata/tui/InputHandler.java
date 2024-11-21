package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.model.Unit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHandler {
  private final Scanner scanner;

  public InputHandler() {
    this.scanner = new Scanner(System.in);
  }

  /**
   * Reads the next line of text from the console.
   * <p>
   *   Continuously prompts the user to enter a valid string.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @param prompt The message to display to the user.
   *
   * @return A string entered by the user.
   */
  public String readString(String prompt) {
    System.out.print(prompt);

    while (true) {
      String input = scanner.nextLine().trim();
      if (input.isEmpty() || input.isBlank()) {
        System.out.println("Invalid input. Enter a new one.");
      } else {
        return input;
      }
    }
  }

  /**
   * Reads an integer from the console.
   * <p>
   *   Continuously prompts the user to enter a valid integer.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @param prompt The message to display to the user.
   *
   * @return A valid integer entered by the user.
   *
   * @throws NumberFormatException if the input is not a valid integer.
   */
  public int readInt(String prompt) {
    System.out.print(prompt);

    while (true) {
      try {
        int userInt = Integer.parseInt(scanner.nextLine());

        // Check if the input is a negative number
        if (userInt <= 0) {
          System.out.println("Invalid input. Enter a positive integer.");
          continue; // Prompt the user again
        }

        return userInt;   // Return a valid integer

      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Enter a valid whole number.");
      }
    }
  }

  /**
   * Reads a double from the console.
   * <p>
   *   Continuously prompts the user to enter a valid double.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @param prompt The message to display to the user.
   *
   * @return A valid double entered by the user.
   *
   * @throws NumberFormatException if the input is not a valid double.
   */
  public double readDouble(String prompt) {
    System.out.print(prompt);

    while (true) {
      try {
        double userDouble = Double.parseDouble(scanner.nextLine().replace(",", "."));

        // Check if the input is a negative number
        if (userDouble <= 0) {
          System.out.println("Invalid input. Enter a positive double.");
          continue; // Prompt the user again
        }

        return userDouble;  // Return a valid double

      } catch (NumberFormatException e) {
        System.out.println("Invalid input. Enter a valid number.");
      }
    }
  }

  /**
   * Reads a date from the console in the format "dd/MM/yyyy".
   * <p>
   *   Continuously prompts the user to enter a valid date.
   *   Displays an error message if input is invalid and retries.
   * </p>
   *
   * @param prompt The message to display to the user.
   *
   * @return A valid {@link LocalDate} entered by the user.
   *
   * @throws DateTimeParseException if the input is not a valid LocalDate.
   */
  public LocalDate readDate(String prompt) {
    System.out.print(prompt);

    while (true) {
      try {
        return LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Enter a valid date with format 'dd/MM/yyyy'.");
      }
    }
  }

  /**
   * Reads a unit of measurement from the console.
   * <p>
   *   A list of valid units are displayed on the console.
   *   Continuously prompts the user to enter a valid unit. Displays
   *   an error message if input is invalid and retries.
   * </p>
   *
   * @param prompt The message to display to the user.
   *
   * @return A valid {@link Unit} entered by the user.
   *
   * @throws IllegalArgumentException if the input is an invalid unit.
   */
  public Unit readUnit(String prompt) {
    System.out.println(prompt);

    // Displays a list of valid units on the console
    for (Unit unit : Unit.values()) {
      System.out.println("* " + unit.getSymbol());
    }

    // Prompts the user to enter a unit until it can be converted to a Unit instance
    while (true) {
      try {
        return Unit.getUnitBySymbol(scanner.nextLine());
      } catch (IllegalArgumentException e) {
        System.out.println("Invalid unit. Enter a valid unit.");
      }
    }
  }

  /**
   * Reads any key pressed by the user. 'Pauses' the console output,
   * allowing the user to read content before proceeding to the next.
   */
  public void readEnter() {
    System.out.println("\nPress enter to continue...");
    scanner.nextLine();
  }

  /**
   * Reads a choice "yes" or "no" from the user.
   *
   * @param prompt The message to display to the user.
   *
   * @return {@code true} if the choice is "yes", or {@code false}
   *         if the answer is "no".
   */
  public boolean readYes(String prompt) {
    while (true) {
      int choice = readInt(prompt + "\n[1] Yes\n[2] No\n");

      if (choice == 1 || choice == 2) {
        return choice == 1;
      }

      System.out.println("Invalid input. Enter 1 for Yes or 2 for no.\n");
    }
  }

  /**
   * Closes the scanner.
   */
  public void close() {
    scanner.close();
  }
}
