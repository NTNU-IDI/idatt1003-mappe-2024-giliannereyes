package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a recipe with a name, description, instruction
 * and a list of ingredients.
 *
 * <p>Provides methods to add ingredients and retrieve each property.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.0
 */
public class Recipe {
  private String name;
  private String description;
  private String instruction;
  private final List<Ingredient> ingredients;

  /**
   * Constructs a new Recipe instance.
   *
   * @param name is the name of the recipe.
   * @param description is a description of the recipe.
   * @param instruction is the instruction for the recipe.
   *
   * @throws IllegalArgumentException if the name, description or instruction is null or empty.
   */
  public Recipe(String name, String description, String instruction) {
    setName(name);
    setDescription(description);
    setInstruction(instruction);
    this.ingredients = new ArrayList<>();
  }

  /**
   * Adds an ingredient to the recipe.
   *
   * @param ingredient is the ingredient to add.
   *
   * @throws IllegalArgumentException if the ingredient is null.
   */
  public void addIngredient(Ingredient ingredient) {
    Validation.validateNonNull(ingredient, "Ingredient");
    ingredients.add(ingredient);
  }

  /**
   * Retrieves the name of the recipe.
   *
   * @return The name of the recipe.
   */
  public String getName() {
    return name;
  }

  /**
   * Retrieves the description of the recipe.
   *
   * @return The description of the recipe.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Retrieves the instruction of the recipe.
   *
   * @return The instruction of the recipe.
   */
  public String getInstruction() {
    return instruction;
  }

  /**
   * Retrieves a list of the ingredients the recipe requires.
   *
   * @return A list of ingredients.
   */
  public List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredients);
  }

  /**
   * Returns a string representation of the recipe, including its name,
   * description, instruction and ingredients.
   *
   * @return A string representation of the recipe.
   */
  @Override
  public String toString() {
    return String.format(
        "Name: %s\nDescription: %s\nInstruction: %s\nIngredients:\n%s",
        name, description, instruction, getFormattedIngredientList());
  }

  /**
   * Generates a formatted string representation of all ingredients
   * in the recipe, including their name, quantity and unit of measurement.
   *
   * @return A string representation containing each ingredient's details.
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

  /**
   * Sets the name of the recipe.
   *
   * @param name is the name of the recipe.
   *
   * @throws IllegalArgumentException if the name is null or empty.
   */
  private void setName(String name) {
    Validation.validateNonEmptyString(name, "Recipe name");
    this.name = name;
  }

  /**
   * Sets the description of the recipe.
   *
   * @param description is the description of the recipe.
   *
   * @throws IllegalArgumentException if the description is null or empty.
   */
  private void setDescription(String description) {
    Validation.validateNonEmptyString(description, "Description");
    this.description = description;
  }

  /**
   * Sets the instruction of the recipe.
   *
   * @param instruction is the instruction of the recipe.
   *
   * @throws IllegalArgumentException if the instruction is null or empty.
   */
  private void setInstruction(String instruction) {
    Validation.validateNonEmptyString(instruction, "Instruction");
    this.instruction = instruction;
  }
}
