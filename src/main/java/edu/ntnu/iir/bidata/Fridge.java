package edu.ntnu.iir.bidata;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
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
    // Use an iterator to safely remove items from the inventory
    Iterator<Ingredient> iterator = inventory.iterator();
    while (iterator.hasNext()) {
      Ingredient ingredient = iterator.next();
      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        ingredient.removeQuantity(quantity);

        // Ingredient is removed from inventory if quantity = 0
        if (ingredient.getQuantity() <= 0) { // Use <= to cover cases where quantity might go negative
          iterator.remove(); // Safely remove the ingredient
        }
        // Break out of the loop after removing the ingredient
        break; // Exit once we've found and processed the ingredient
      }
    }
    /**
    for (Ingredient ingredient : inventory) {
      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        ingredient.removeQuantity(quantity);

        // Ingredient is removed from inventory if quantity = 0
        if (ingredient.getQuantity() == 0) {
          inventory.remove(ingredient);
        }
      }
    }
     */
  }

  public Ingredient searchIngredient(String ingredientName) {
    for (Ingredient ingredient : inventory) {
      if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
        return ingredient;
      }
    }
    return null;
  }

  public void showAllIngredients() {
    for (Ingredient ingredient : inventory) {
      System.out.println(ingredient);
      System.out.println();
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

  // Used in Recipe-class
  public boolean hasEnoughIngredient(String name, double quantity) {
    for (Ingredient ingredient : inventory) {
      if (ingredient.getName().equalsIgnoreCase(name)) {
        if (ingredient.getQuantity() >= quantity) {
          return true;
        }
      }
    }
    return false;
  }

}
