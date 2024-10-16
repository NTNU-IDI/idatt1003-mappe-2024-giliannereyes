package edu.ntnu.iir.bidata;

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
   * Displays all recipes that can be made with the ingredients in the fridge.
   * Details of each recipe is displayed on the console.
   *
   * @param fridge The fridge containing the ingredients to check against recipes.
   */
  public void showAvailableRecipes(Fridge fridge) {
    if (recipes.isEmpty()) {
      System.out.println("There are no recipes available.");
    } else {
      recipes.stream()
          .filter(recipe -> recipe.hasRequiredIngredients(fridge)) // Filters recipes that can be made
          .forEach(Recipe::showRecipe); // Displays details of each recipe
    }
  }

  /**
   * Retrieves a recipe from the cookbook by its name.
   *
   * @param name The name of the recipe to retrieve.
   *
   * @return The recipe to retrieve.
   *
   * @throws IllegalArgumentException if a recipe with the provided name does not exist.
   */
  public Recipe findRecipeByName(String name) {
    return recipes.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(name))
        .findAny()
        .orElseThrow(() -> new IllegalArgumentException("Recipe not found: " + name));
  }

  /**
   * Checks if the required ingredients for a recipe in the cookbook
   * is available in the fridge.
   *
   * @param fridge The fridge to check for ingredients.
   * @param recipeName The name of the recipe to check.
   *
   * @return {@code true} if the recipe has all the required ingredients available.
   *         Otherwise, it returns {@code false}.
   */
  public boolean canMakeRecipe(Fridge fridge, String recipeName) {
    Recipe recipe = findRecipeByName(recipeName);
    return recipe.hasRequiredIngredients(fridge);
  }

}
