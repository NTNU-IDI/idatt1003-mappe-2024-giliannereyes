package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a cookbook with a collection of recipes.
 * <br>
 * Cookbook has the following functionalities:
 * <ul>
 *   <li>Add a recipe.</li>
 *   <li>Display recipes that can be made with the ingredients in the fridge.</li>
 *   <li>Retrieve a recipe by name.</li>
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
   *
   * @throws IllegalArgumentException if the recipe is {@code null},
   *                                  or if the recipe is already in the cookbook.
   */
  public void addRecipe(Recipe recipe) {
    Validation.validateRecipe(recipe);

    findRecipeByName(recipe.getName())
        .ifPresentOrElse(
            r -> {
              throw new IllegalArgumentException("Recipe is already in the cookbook: " + r);
            },
            () -> recipes.add(recipe)
        );
  }

  /**
   * Retrieves the recipes in the cookbook.
   *
   * @return A list of recipes.
   */
  public ArrayList<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }

  /**
   * Searches for and retrieves a recipe by the recipe's name.
   *
   * @param name The name of the recipe to search for.
   *
   * @return An Optional containing the recipe if a recipe with the same name is found.
   *         Otherwise, an empty Optional.
   */
  public Optional<Recipe> findRecipeByName(String name) {
    Validation.validateNonEmptyString(name);
    return recipes.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(name.trim()))
        .findFirst();
  }
}
