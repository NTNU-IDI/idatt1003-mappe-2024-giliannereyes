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

  public void showRecipe() {
    System.out.println("Name: " + name);
    System.out.println("Description: " + description);
    System.out.println("Ingredients: ");
    for (Ingredient ingredient : ingredients) {
      System.out.println(ingredient.getName() + "\t" + ingredient.getQuantity());
    }
  }

}
