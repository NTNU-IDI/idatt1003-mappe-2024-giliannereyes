package edu.ntnu.iir.bidata;

import java.util.ArrayList;

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
  private final ArrayList<Ingredient> ingredients;
  private final ArrayList<String> instructions;

  /**
   * Constructs a new Recipe instance.
   *
   * @param name The name of the recipe.
   * @param description A short description of the recipe.
   */
  public Recipe(String name, String description) {
    this.name = name;
    this.description = description;
    this.ingredients = new ArrayList<>();
    this.instructions = new ArrayList<>();
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
   * Adds an instruction to the recipe. Multiple instructions (steps) can be added
   * to the recipe.
   *
   * @param instruction The instruction to add.
   */
  public void addInstruction(String instruction) {
    instructions.add(instruction);
  }

  /**
   * Checks if a recipe can be made by checking if all needed ingredients are in the fridge.
   *
   * @param fridge The fridge instance to check.
   *
   * @return {@code true} if fridge has all ingredients needed.
   *         {@code false} if fridge does not have all the ingredients needed.
   */
  public boolean available(Fridge fridge) {
    return ingredients.stream()
        .allMatch(ingredient -> // Checks if fridge has enough of each ingredient
            fridge.hasEnoughIngredient(ingredient.getName(), ingredient.getQuantity()));
  }

  /**
   * Returns the name of the recipe.
   *
   * @return the name of the recipe.
   */
  public String getName() {
    return name;
  }

  /**
   * Displays the name, description, ingredients and instructions  of the recipe.
   */
  public void showRecipe() {
    System.out.println("Recipe: " + name);
    System.out.println("Description: " + description);

    // Displays each ingredient's name and quantity
    System.out.println("Ingredients: ");
    ingredients.forEach(ingredient ->
        System.out.println(ingredient.getName() + ", "
            + ingredient.getQuantity() + " " + ingredient.getUnit()));

    // Displays each of the recipe's instructions in an ordered manner
    System.out.println("\nInstructions: ");
    for (int i = 0; i < instructions.size(); i++) {
      System.out.println((i + 1) + ". " + instructions.get(i)); // (i + 1) for 1-based indexing
    }

  }


}
