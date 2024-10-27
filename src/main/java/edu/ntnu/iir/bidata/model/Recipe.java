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
  private String name;
  private String description;
  private String instruction;
  private final List<Ingredient> ingredients;

  /**
   * Constructs a new Recipe instance.
   *
   * @param name The name of the recipe.
   * @param description A short description of the recipe.
   * @param instruction The instruction for the recipe.
   */
  public Recipe(String name, String description, String instruction) {
    setName(name);
    setDescription(description);
    setInstruction(instruction);
    this.ingredients = new ArrayList<>();
  }

  /**
   * Sets the name of the recipe.
   *
   * @param name The name of the recipe. A name that is empty, blank or
   *             {@code null} is not accepted.
   *
   * @throws IllegalArgumentException if the name is empty, blank or {@code null}.
   */
  private void setName(String name) {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("Recipe name cannot be null, empty or blank.");
    }

    this.name = name;
  }

  /**
   * Sets the description of the recipe.
   *
   * @param description The description of the recipe. A description that is empty, blank or
   *             {@code null} is not accepted.
   *
   * @throws IllegalArgumentException if the description is empty, blank or {@code null}.
   */
  private void setDescription(String description) {
    if (description == null || description.isBlank()) {
      throw new IllegalArgumentException("Recipe description cannot be null, empty or blank.");
    }

    this.description = description;
  }

  /**
   * Sets the instruction of the recipe.
   *
   * @param instruction The instruction of the recipe. An instruction that is empty, blank or
   *             {@code null} is not accepted.
   *
   * @throws IllegalArgumentException if the instruction is empty, blank or {@code null}.
   */
  private void setInstruction(String instruction) {
    if (instruction == null || instruction.isBlank()) {
      throw new IllegalArgumentException("Recipe instruction cannot be null, empty or blank.");
    }

    this.instruction = instruction;
  }

  /**
   * Adds an ingredient to the list of ingredients.
   *
   * @param ingredient The ingredient to add.
   */
  public void addIngredient(Ingredient ingredient) {
    if (ingredient == null) {
      throw new IllegalArgumentException("Ingredient cannot be null.");
    }

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
   * Returns the description of the recipe.
   *
   * @return the description of the recipe.
   */
  public String getDescription() {
    return this.description;
  }

  /**
   * Returns the instruction of the recipe.
   *
   * @return the instruction of the recipe.
   */
  public String getInstruction() {
    return this.instruction;
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
