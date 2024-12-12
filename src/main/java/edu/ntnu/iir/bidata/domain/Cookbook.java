package edu.ntnu.iir.bidata.domain;

import edu.ntnu.iir.bidata.utils.Validation;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Represents a cookbook with a collection of {@link Recipe}.
 *
 * <p>Provides methods to add a recipe, search for a recipe by name,
 * and retrieve all recipes.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.0
 */
public class Cookbook {
  private final ArrayList<Recipe> recipes;

  /**
   * Constructs a Cookbook instance with an empty list of recipes.
   */
  public Cookbook() {
    recipes = new ArrayList<>();
  }

  /**
   * Adds a recipe to the cookbook, if it is not already present.
   *
   * @param recipe is the recipe to add.
   *
   * @throws IllegalArgumentException if the recipe is null,
   *      or if the recipe is already in the cookbook.
   */
  public void addRecipe(Recipe recipe) {
    Validation.validateNonNull(recipe, "Recipe");
    if (findRecipeByName(recipe.getName()).isPresent()) {
      throw new IllegalArgumentException(
          "A recipe with the same name already exists in the cookbook."
      );
    }
    recipes.add(recipe);
  }

  /**
   * Searches for and retrieves a recipe by its name.
   *
   * @param name is the name of the recipe to search for.
   *
   * @return An {@link Optional} containing the recipe if found.
   *         Otherwise, an empty {@link Optional}.
   *
   * @throws IllegalArgumentException if the name is null or empty.
   */
  public Optional<Recipe> findRecipeByName(String name) {
    Validation.validateNonEmptyString(name, "Recipe name");
    return recipes.stream()
        .filter(recipe -> recipe.getName().equalsIgnoreCase(name.trim()))
        .findFirst();
  }

  /**
   * Retrieves the recipes in the cookbook.
   *
   * @return A list of recipes.
   */
  public ArrayList<Recipe> getRecipes() {
    return new ArrayList<>(recipes);
  }
}
