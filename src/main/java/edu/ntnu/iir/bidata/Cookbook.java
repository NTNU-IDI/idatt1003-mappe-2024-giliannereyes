package edu.ntnu.iir.bidata;

import java.util.ArrayList;

public class Cookbook {
  private final ArrayList<Recipe> recipes;

  public Cookbook() {
    recipes = new ArrayList<>();
  }

  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }
  
  public void showAvailableRecipes(Fridge fridge) {
    for (Recipe recipe : recipes) {
      if (recipe.available(fridge)) {
        recipe.showRecipe();
      }
    }
  }

  public Recipe getRecipeByName(String name) {
    for (Recipe recipe : recipes) {
      if (recipe.getName().equalsIgnoreCase(name)) {
        return recipe;
      }
    }
    return null;
  }

}
