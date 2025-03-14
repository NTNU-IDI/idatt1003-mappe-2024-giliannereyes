package edu.ntnu.idi.idatt.app;

import edu.ntnu.idi.idatt.presentation.UserInterface;

/**
 * Wrapper class for the Food Management Application.
 */
public class FoodManagementApp {
  /**
   * Main method to start the application.
   *
   * @param args are the arguments for the main method.
   */
  public static void main(String[] args) {
    UserInterface userInterface = new UserInterface();
    userInterface.start();
  }
}
