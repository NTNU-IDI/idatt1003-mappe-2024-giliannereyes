package edu.ntnu.iir.bidata.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recipe by name, description, and a list of ingredients.
 *<br>
 * Recipe supports the following functionality:
 * <ul>
 *   <li>Add an ingredient</li>
 *   <li>Check availability of the recipe</li>
 *   <li>Display recipe details</li>
 * </ul>
 */
public class Recipe {
  private final String name;
  private final String description;
  private final List<Ingredient> ingredients;
  private final String instruction;

  /**
   * Constructs a new Recipe instance.
   *
   * @param name The name of the recipe.
   * @param description A short description of the recipe.
   * @param instruction The instruction for the recipe.
   */
  public Recipe(String name, String description, String instruction) {
    this.name = name;
    this.description = description;
    this.ingredients = new ArrayList<>();
    this.instruction = instruction;
  }

  /**
   * Adds an ingredient to the list of ingredients.
   *
   * @param ingredient The ingredient to add.
   */
  public void addIngredient(Ingredient ingredient) {
    ingredients.add(ingredient);
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the name of the recipe.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns a list of all the ingredients the recipe requires.
   *
   * @return A list of ingredients.
   */
  public List<Ingredient> getIngredients() {
    return this.ingredients;
  }

  @Override
  public String toString() {
    return "Recipe: " + this.name
        + "\nDescription: " + this.description
        + "\nInstruction: " + this.instruction
        + "\nIngredients: " + this.ingredients;
  }
}
