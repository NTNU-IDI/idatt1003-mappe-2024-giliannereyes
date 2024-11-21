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
  private final String instruction;
  private final List<Ingredient> ingredients;

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
    this.instruction = instruction;
    this.ingredients = new ArrayList<>();

    validateNonEmptyString(name, "name");
    validateNonEmptyString(description, "description");
    validateNonEmptyString(instruction, "instruction");
  }

  /**
   * Adds an ingredient to the list of ingredients.
   *
   * @param ingredient The ingredient to add.
   *
   * @throws IllegalArgumentException if the ingredient is null.
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

  /**
   * Returns a string representation of the recipe, including its name,
   * description, instruction and ingredients.
   *
   * @return A string formatted to display the recipe's details.
   */
  @Override
  public String toString() {
    return "Recipe: " + this.name
        + "\nDescription: " + this.description
        + "\nInstruction: " + this.instruction
        + "\nIngredients:\n" + this.getFormattedIngredientList();
  }

  /**
   * Validates that a string is not null, empty or blank.
   *
   * @param value The string to validate.
   * @param fieldName The name of the field being validated.
   *
   * @throws IllegalArgumentException if the value is null, empty or blank.
   */
  private void validateNonEmptyString(String value, String fieldName) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException(
          String.format("The %s cannot be null, empty or blank.", fieldName));
    }
  }

  /**
   * Generates a formatted string representation of all ingredients
   * in the recipe.
   *
   * @return A string representation containing each ingredient's name,
   *         quantity and unit of measurement.
   */
  private String getFormattedIngredientList() {
    StringBuilder ingredientListBuilder = new StringBuilder();

    for (Ingredient ingredient : ingredients) {
      String ingredientString = String.format(
          "%s - %.2f %s \n", ingredient.getName(), ingredient.getQuantity(), ingredient.getUnit());
      ingredientListBuilder.append(ingredientString);
    }

    return ingredientListBuilder.toString();
  }

}
