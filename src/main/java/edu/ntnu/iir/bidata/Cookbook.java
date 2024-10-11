package edu.ntnu.iir.bidata;

import java.util.ArrayList;

public class Cookbook {
  private ArrayList<Recipe> recipes;

  public Cookbook() {
    recipes = new ArrayList<>();
  }

  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }
  
  public void showAvailableRecipes() {
    for (Recipe recipe : recipes) {
      if (recipe.available()) {
        System.out.println(recipe.toString());
      }
    }
  }

}
