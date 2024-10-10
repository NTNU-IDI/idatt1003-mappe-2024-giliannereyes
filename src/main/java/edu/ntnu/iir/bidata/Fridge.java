package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Info
 */
public class Fridge {
  private List<Ingredient> inventory;

  /**
   * Constructor of the Fridge class.
   */
  public Fridge() {
    inventory = new ArrayList<Ingredient>();
  }

  public void addIngredient(Ingredient ingredient) {
    inventory.add(ingredient);
  }

  /**
   * Removes quantity of an ingredient.
   * @param ingredientName
   * @param quantity
   */
  public void removeIngredient(String ingredientName, double quantity) {
    for (Ingredient ingredient : inventory) {
      if (ingredient.getName().equals(ingredientName)) {
        ingredient.removeQuantity(quantity);
      }
    }
  }

  public Ingredient searchIngredient(String ingredientName) {
    for (Ingredient ingredient : inventory) {
      if (ingredient.getName().equals(ingredientName)) {
        return ingredient;
      }
    }
    return null;
  }


  public void showAllIngredients() {
    for (Ingredient ingredient : inventory) {
      System.out.println(ingredient);
    }
  }

  public void showAllExpiredIngredients() {
    LocalDate currentDate = LocalDate.now();
    double totalValue = 0.0;

    System.out.println("An overview of expired ingredients:");

    for (Ingredient ingredient : inventory) {
      if (ingredient.getExpiryDate().isBefore(currentDate)) {
        System.out.println(ingredient);
        totalValue += ingredient.getPrice();
      }
    }

    System.out.printf("Total value: %s kr\n", totalValue);
  }

  public double calculateTotalValue() {
    double totalValue = 0.0;

    for (Ingredient ingredient : inventory) {
      totalValue += ingredient.getPrice();
    }

    return totalValue;
  }

}
