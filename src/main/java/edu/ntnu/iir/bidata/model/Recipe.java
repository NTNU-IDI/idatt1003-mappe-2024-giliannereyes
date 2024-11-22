package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;

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
    Validation.validateNonEmptyString(name);
    Validation.validateNonEmptyString(description);
    Validation.validateNonEmptyString(instruction);

    this.name = name;
    this.description = description;
    this.instruction = instruction;
    this.ingredients = new ArrayList<>();
  }

  /**
   * Adds an ingredient to the list of ingredients.
   *
   * @param ingredient The ingredient to add.
   *
   * @throws IllegalArgumentException if the ingredient is null.
   */
  public void addIngredient(Ingredient ingredient) {
    Validation.validateIngredient(ingredient);
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
    return String.format(
        "Name: %s\nDescription: %s\nInstruction: %s\nIngredients:\n%s",
        name, description, instruction, getFormattedIngredientList());
  }

  /**
   * Generates a formatted string representation of all ingredients
   * in the recipe.
   *
   * @return A string representation containing each ingredient's name,
   *         quantity and unit of measurement.
   */
  private String getFormattedIngredientList() {
    StringBuilder builder = new StringBuilder();

    ingredients.forEach(ingredient ->
        builder.append(
            String.format(
                "%s - %.2f %s%n",
                ingredient.getName(),
                ingredient.getQuantity(),
                ingredient.getUnit().getSymbol()
            )
        )
    );

    return builder.toString();
  }

}
