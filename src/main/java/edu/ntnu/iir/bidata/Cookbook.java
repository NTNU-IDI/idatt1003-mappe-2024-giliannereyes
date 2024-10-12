package edu.ntnu.iir.bidata;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a cookbook with a collection of recipes.
 * <br>
 * Cookbook has the following functionalities:
 * <ul>
 *   <li> Add a recipe</li>
 *   <li> Display available recipes</li>
 *   <li> Retrieve a recipe by name</li>
 * </ul>
 */
public class Cookbook {
  private final ArrayList<Recipe> recipes;

  /**
   * Constructs a new Cookbook with an empty list of recipes.
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
   * Displays all available recipes that can be made with the ingredients in the fridge.
   *
   * @param fridge The fridge containing the ingredients to check against recipes.
   */
  public void showAvailableRecipes(Fridge fridge) {
    recipes.stream()
        .filter(recipe -> recipe.available(fridge)) // Filters available recipes
        .forEach(Recipe::showRecipe); // Displays each available recipe
  }

  /**
   * Retrieves a recipe by its name.
   *
   * @param name The name of the recipe to retrieve.
   *
   * @return An Optional containing the recipe if found, or an empty Optional if not found.
   */
  public Optional<Recipe> getRecipeByName(String name) {
    return recipes.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
        .findAny();
  }

}
