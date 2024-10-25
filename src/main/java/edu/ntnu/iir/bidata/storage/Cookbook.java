package edu.ntnu.iir.bidata.storage;

import edu.ntnu.iir.bidata.model.Recipe;

import java.util.ArrayList;

/**
 * Represents a cookbook with a collection of recipes.
 * <br>
 * Cookbook has the following functionalities:
 * <ul>
 *   <li> Add a recipe</li>
 *   <li> Display recipes that can be made with the ingredients in the fridge</li>
 *   <li> Retrieve a recipe by name</li>
 * </ul>
 */
public class Cookbook {
  private final ArrayList<Recipe> recipes;

  /**
   * Constructs a new Cookbook instance with an empty list of recipes.
   */
  public Cookbook() {
    recipes = new ArrayList<>();
  }

  /**
   * Adds a recipe to the cookbook.
   *
   * @param recipe The recipe to be added.
   */
  public void addRecipe(Recipe recipe) {
    recipes.add(recipe);
  }

  /**
   * Retrieves the recipes in the cookbook.
   *
   * @return A list of recipes.
   */
  public ArrayList<Recipe> getRecipes() {
    return this.recipes;
  }

}
