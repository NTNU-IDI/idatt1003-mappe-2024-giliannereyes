package edu.ntnu.iir.bidata.tui;

import edu.ntnu.iir.bidata.manager.Manager;
import edu.ntnu.iir.bidata.manager.Result;
import edu.ntnu.iir.bidata.model.Recipe;
import edu.ntnu.iir.bidata.model.Unit;

import java.util.List;

/**
 * The RecipeUi class is responsible for managing user interaction
 * related to recipes, such as prompting the user to add new recipes,
 * check recipe ingredients and display recipes in the console.
 */
public class RecipeUi {
  private final Manager manager;
  private final InputHandler inputHandler;

  /**
   * Constructs a new RecipeUi instance.
   *
   * @param manager The manager to handle business logic related to recipes.
   * @param inputHandler The input handler to receive and validate user input.
   */
  public RecipeUi(Manager manager, InputHandler inputHandler) {
    this.manager = manager;
    this.inputHandler = inputHandler;
  }

  /**
   * Prompts the user for recipe details needed to create a new recipe. Then calls
   * the Manager class to create and add a recipe to cookbook.
   * Displays a success or failure message. If successful, prompts the user to add
   * ingredients to the recipe.
   */
  public void promptAddRecipe() {
    String name = inputHandler.readString("Enter recipe name: ");
    String description = inputHandler.readString("Enter recipe description: ");
    String instruction = inputHandler.readString("Enter recipe instruction: ");

    Result<Recipe> result = manager.createRecipe(name, description, instruction);
    System.out.println(result.getMessage());

    if (result.isSuccess()) {
      result.getData().ifPresent(recipe -> {
        addIngredientsToRecipe(recipe);
        System.out.println(manager.addRecipeToCookbook(recipe).getMessage());
      });
    }
  }

  /**
   * Prompts the user to enter the name of a recipe and checks if the
   * ingredients for the recipe are available using the manager. Displays
   * a message informing the user of the result.
   */
  public void promptRecipeIngredientsCheck() {
    String name = inputHandler.readString("Enter recipe name: ");

    Result<Void> result = manager.checkRecipeIngredients(name);
    System.out.println(result.getMessage());
  }

  /**
   * Displays recipes that has all required ingredients available in the fridge,
   * which are retrieved using the manager. Or informs the user if there are none.
   */
  public void displaySuggestedRecipes() {
    Result<List<Recipe>> result = manager.getSuggestedRecipes();
    System.out.println(result.getMessage());

    if (result.isSuccess()) {
      List<Recipe> recipes = result.getData().orElseGet(List::of);
      recipes.forEach(System.out::println);
    }
  }

  /**
   * Adds ingredients created by the user to a recipe until the user stops.
   *
   * @param recipe The recipe to which the ingredients are added to.
   */
  private void addIngredientsToRecipe(Recipe recipe) {
    boolean addingIngredients = true;

    while (addingIngredients) {
      promptAddIngredient(recipe);

      System.out.println("Would you like to continue adding ingredients?");
      int choice = inputHandler.readInt("[1] Yes\n[2] No\n");
      addingIngredients = (choice == 1);
    }
  }

  /**
   * Prompts the user to create a new ingredient and add it to a recipe.
   *
   * @param recipe The recipe to which an ingredient is added to.
   */
  private void promptAddIngredient(Recipe recipe) {
    String name = inputHandler.readString("Enter ingredient name: ");
    double quantity = inputHandler.readDouble("Enter ingredient quantity: ");
    Unit unit = inputHandler.readUnit("Enter ingredient unit:");

    Result<Void> result = manager.addIngredientToRecipe(recipe, name, quantity, unit);
    System.out.println(result.getMessage());
  }

}
