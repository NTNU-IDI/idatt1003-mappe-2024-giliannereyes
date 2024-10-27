package edu.ntnu.iir.bidata.manager;

import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;
import edu.ntnu.iir.bidata.storage.Fridge;
import edu.ntnu.iir.bidata.tui.InputHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class IngredientManager {
  private final Fridge fridge;
  private final InputHandler inputHandler;

  public IngredientManager(Fridge fridge, InputHandler inputHandler) {
    this.fridge = fridge;
    this.inputHandler = inputHandler;
  }

  /**
   * JavaDocs!
   */
  public void createIngredient() {
    // Reads ingredient details from user
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");
    double pricePerUnit = inputHandler.readDouble("Enter ingredient's price per unit: ");
    LocalDate expiryDate = inputHandler.readDate("Enter ingredient's expiry date: ");

    // Attempts to create a new ingredient
    try {
      Ingredient ingredient = new Ingredient(name, quantity, pricePerUnit, unit, expiryDate);
      fridge.addIngredient(ingredient);
      System.out.println("New ingredient added to fridge!");

      // If the ingredient details are invalid
    } catch (IllegalArgumentException e) {
      System.out.printf("""
          Ingredient could not be added due to:
          %s
          """, e.getMessage());
    }
  }

  /**
   * JavaDocs!
   */
  public void searchForIngredient() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Optional<Ingredient> ingredient = fridge.findIngredientByName(name);

    // Shows ingredient information if ingredient exists
    if (ingredient.isPresent()) {
      System.out.printf("""
          Ingredient found!
          Here are the %s's information:
          %s
          """, ingredient.get().getName(), ingredient.get());
    } else {
      System.out.println("Ingredient not found!");
    }
  }

  /**
   * Javadocs!
   */
  public void decreaseIngredientQuantity() {
    String name = inputHandler.readString("Enter ingredient name: ");
    Unit unit = inputHandler.readUnit("Enter the unit of the quantity to remove: ");
    double quantity = inputHandler.readDouble("Enter quantity to remove: ");

    try {
      fridge.decreaseIngredientQuantity(name, quantity, unit);
      System.out.printf("%f.2f %s of %s was successfully removed from the fridge!\n", quantity, unit, name);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  public void checkExpiringIngredients() {
    LocalDate expiryDate = inputHandler.readDate("Enter the expiry date in this format 'dd/MM/yyyy': ");

    try {
      List<Ingredient> ingredients = fridge.findIngredientsBeforeDate(expiryDate);
      System.out.println("Here are the ingredients that expire before " + expiryDate + ":");

      for (Ingredient ingredient : ingredients) {
        System.out.println(ingredient);
      }

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * JavaDocs!
   */
  public void showSortedIngredients() {
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();

    try {
      System.out.println("Ingredients in the fridge sorted alphabetically: ");

      for (Ingredient ingredient : sortedIngredients) {
        System.out.println(ingredient);
      }

    } catch (NoSuchElementException e) {
      System.out.println(e.getMessage());
    }
  }
}
