package edu.ntnu.iir.bidata.MainApp;

import edu.ntnu.iir.bidata.utils.Manager;
import edu.ntnu.iir.bidata.model.MealPlanner;
import edu.ntnu.iir.bidata.model.Cookbook;
import edu.ntnu.iir.bidata.model.Fridge;
import edu.ntnu.iir.bidata.ui.InputHandler;
import edu.ntnu.iir.bidata.ui.Ui;

public class Main {
  public static void main(String[] args) {
    // Create instances of the classes needed to run the application
    Fridge fridge = new Fridge();
    Cookbook cookbook = new Cookbook();
    MealPlanner mealPlanner = new MealPlanner(fridge, cookbook);
    InputHandler inputHandler = new InputHandler();
    Manager manager = new Manager(fridge, cookbook, mealPlanner);

    // Start the application by creating a new Ui instance and calling the start method
    Ui ui = new Ui(inputHandler, manager);
    ui.start();
  }
}
