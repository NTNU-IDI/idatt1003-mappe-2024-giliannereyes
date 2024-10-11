package edu.ntnu.iir.bidata;

import java.util.ArrayList;

public class Recipe {
  private final String name;
  private final String description;
  private ArrayList<Ingredient> ingredients;

  public Recipe(final String name, final String description) {
    this.name = name;
    this.description = description;
  }

  public void addIngredient(Ingredient ingredient, double quantity) {
    ingredients.add(ingredient);
  }

  public boolean available() {
    return false;
  }

  @Override
  public String toString() {
    return "Recipe: " + this.name
        + "\nDescription: " + this.description
        + "\nIngredients: " + this.ingredients;
  }


}
