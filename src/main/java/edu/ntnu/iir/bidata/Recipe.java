package edu.ntnu.iir.bidata;

import java.util.ArrayList;

public class Recipe {
  private final String name;
  private final String description;
  private ArrayList<Ingredient> ingredients;

  public Recipe(final String name, final String description) {
    this.name = name;
    this.description = description;
    this.ingredients = new ArrayList<>();
  }

  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
  }

  public boolean available(Fridge fridge) {
    for (Ingredient ingredient : ingredients) {
      String name = ingredient.getName();
      double quantity = ingredient.getQuantity();
      if (!fridge.hasEnoughIngredient(name, quantity)) {
        return false;
      }
    }
    return true;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return "Recipe: " + this.name
        + "\nDescription: " + this.description
        + "\nIngredients: " + this.ingredients;
  }


}
