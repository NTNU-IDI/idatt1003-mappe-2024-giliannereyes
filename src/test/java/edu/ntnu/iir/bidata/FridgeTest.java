package edu.ntnu.iir.bidata;

import edu.ntnu.iir.bidata.model.Fridge;
import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Fridge} class.
 *
 * <p> The following tests are included: </p>
 *
 * <b> Positive tests: </b>
 *
 * <ul>
 *   <li>Adds a valid ingredient to the fridge.</li>
 *   <li>Adds a duplicate ingredient and ensures that the
 *   existing ingredient's quantity is increased instead.</li>
 *   <li>Finds ingredient by name regardless of case or surrounding whitespace<./li>
 *   <lI>Decreases an ingredient's quantity by valid amounts and
 *   removes ingredients if quantity is zero.</lI>
 *   <li>Finds the ingredients expiring before a specified date.</li>
 *   <lI>Sorts the ingredients alphabetically.</lI>
 *   <li>Calculates the correct total price of all ingredients.</li>
 * </ul>
 *
 * <b> Negative tests: </b>
 *
 * <ul>
 *   <li>Adds a {@code null} ingredient.</li>
 *   <li>Decreases the quantity of a non-existent ingredient.</li>
 *   <li>Decreases the quantity with an amount that
 *   results to a negative quantity for the ingredient.</li>
 *   <li>Decreases an ingredient's quantity with a negative amount.</li>
 *   <li>Decreases an ingredient's quantity with a {@code null} unit.</li>
 *   <li>Finds a non-existent ingredient by name.</li>
 *   <li>Sorts ingredients from an empty fridge.</li>
 *   <li>Calculates the total price of ingredients when the fridge is empty.</li>

 * </ul>
 */
public class FridgeTest {
  private Fridge fridge;

  @BeforeEach
  void setUp() {
    fridge = new Fridge();
  }

  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test adding an ingredient to the fridge where the ingredient is a valid instance
   * of the class Ingredient. There are no ingredients with the same name in the fridge.
   *
   * <p>Expected outcome: The ingredient should be added to fridge. The fridge should contain
   * the new ingredient when checking the fridge's ingredients.</p>
   */
  @Test
  public void addNewValidIngredient() {
    // Adds a new ingredient
    Ingredient ingredient = new Ingredient("Egg", 12, 30, Unit.KILOGRAM, LocalDate.of(2024,11,9));
    fridge.addIngredient(ingredient);

    // Checks if the ingredient was added to the fridge
    assertTrue(fridge.getIngredients().contains(ingredient));
  }

  /**
   * Test adding an ingredient with the same name as an existing ingredient
   * in the fridge.
   *
   * <p> Expected outcome: The new ingredient should not be added to the fridge. Instead,
   * the existing ingredient's quantity should be increased with the new ingredient's quantity.</p>
   */
  @Test
  public void addDuplicateIngredient() {
    // Adds a new ingredient 'Milk' to fridge
    Ingredient milk1 = new Ingredient("Milk", 1, 20, Unit.LITRE, LocalDate.of(2024,12,27));
    fridge.addIngredient(milk1);

    // Test adding another ingredient called 'Milk' (same unit)
    Ingredient milk2 = new Ingredient("milk", 2, 20, Unit.LITRE, LocalDate.of(2024,12,27));
    fridge.addIngredient(milk2);
    assertFalse(fridge.getIngredients().contains(milk2)); // Fridge should not contain the new 'Milk'

    // Verify that the quantity of ingredient 'Milk' should be updated
    assertEquals(3,
        fridge.findIngredientByName("milk").get().getQuantity());

    // Test adding another ingredient called 'Milk' (different unit)
    Ingredient milk3 = new Ingredient("MILK", 10.5, 20, Unit.DECILITRE, LocalDate.of(2024,12,27));
    fridge.addIngredient(milk3);
    assertFalse(fridge.getIngredients().contains(milk3)); // Fridge should not contain the new 'Milk'

    // Verify that the quantity of ingredient 'Milk' should be updated
    // 3 litres + 10.5 decilitres = 4.05 litres
    assertEquals(4.05,
        fridge.findIngredientByName("milk").get().getQuantity());
  }

  /**
   * Test finding an ingredient by its name.
   *
   * <p> Expected outcome: The correct ingredient should be located in all cases, even if the
   * capitalization is not exactly the same, or if there are trailing or leading whitespaces.</p>
   */
  @Test
  public void findIngredientByCorrectName() {
    // Adds a new ingredient 'Egg' to fridge
    Ingredient egg = new Ingredient("Egg", 2, 30, Unit.KILOGRAM, LocalDate.of(2024,12,27));
    fridge.addIngredient(egg);

    // Test finding ingredient 'Egg' with lowercase letters
    assertEquals(egg, fridge.findIngredientByName("egg").get());

    // Test finding ingredient 'Egg' with uppercase letters
    assertEquals(egg, fridge.findIngredientByName("EGG").get());

    // Test finding ingredient 'Egg' with other capitalization
    assertEquals(egg, fridge.findIngredientByName("eGG").get());

    // Test finding ingredient 'Egg' with whitespace
    assertEquals(egg, fridge.findIngredientByName("    egg   ").get());
  }

  /**
   * Test decreasing an ingredient's quantity with a valid amount. The ingredient is stored in the fridge.
   *
   * <p> Expected outcome: It should be able to locate the correct ingredient, and the ingredient's
   * quantity should be decreased with the specified valid amount. The ingredient
   * should be removed from the fridge if the quantity becomes zero.</p>
   */
  @Test
  public void decreaseIngredientWithValidQuantity() {
    // Adds a new ingredient 'Egg' to fridge
    // Quantity = 2 kg
    Ingredient egg = new Ingredient("Egg", 2, 30, Unit.KILOGRAM, LocalDate.of(2024,12,27));
    fridge.addIngredient(egg);

    // Test decreasing the ingredient's quantity with a valid quantity (same unit)
    fridge.decreaseIngredientQuantity("Egg", 1.5, Unit.KILOGRAM); // Remove 1.5 kg
    assertEquals(0.5, fridge.findIngredientByName("egg").get().getQuantity());

    // Test decreasing the ingredient's quantity with a valid quantity (different unit)
    fridge.decreaseIngredientQuantity("Egg", 100, Unit.GRAM); // Remove 0.1 kg
    assertEquals(0.4, fridge.findIngredientByName("egg").get().getQuantity());

    // Test removing all of available quantity
    fridge.decreaseIngredientQuantity("Egg", 400, Unit.GRAM); // Remove 0.4 kg

    // The ingredient should not be found in the fridge
    Optional<Ingredient> optEgg = fridge.findIngredientByName("Egg");
    assertTrue(optEgg.isEmpty());
  }

  /**
   * Test retrieving a list of ingredients expiring before a specified date.
   *
   * <p> Expected outcome: Only the ingredients that expire before the specified
   * date are returned.</p>
   */
  @Test
  public void findCorrectIngredientsBeforeDate() {
    // Adds a new ingredient 'Egg' to fridge
    Ingredient egg = new Ingredient("Egg", 2, 30, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(egg);

    // Adds a new ingredient 'Milk' to fridge
    Ingredient milk = new Ingredient("Milk", 1, 20, Unit.LITRE, LocalDate.of(2024,12,27));
    fridge.addIngredient(milk);

    // Adds a new ingredient 'Milk' to fridge
    Ingredient bread = new Ingredient("Bread", 0.5, 10, Unit.KILOGRAM, LocalDate.of(2024,10,28));
    fridge.addIngredient(bread);

    // Test retrieving ingredients expiring before 28th Cct 2024
    LocalDate date = LocalDate.of(2024,10,28);

    // The list should only contain the ingredient 'Egg'
    assertTrue(fridge.findIngredientsBeforeDate(date).contains(egg));
    assertFalse(fridge.findIngredientsBeforeDate(date).contains(milk));
    assertFalse(fridge.findIngredientsBeforeDate(date).contains(bread));
  }

  /**
   * Test sorting a list of the ingredients alphabetically.
   *
   * <p>Expected outcome: The returned list should contain all the
   * ingredients in the fridge in alphabetical order.</p>
   */
  @Test
  public void findSortedIngredients() {
    // Adds a new ingredient 'Cheese' to fridge
    Ingredient cheese = new Ingredient("Cheese", 0.5, 30, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(cheese);

    // Adds a new ingredient 'Apple' to fridge
    Ingredient apple = new Ingredient("Apple", 1, 20, Unit.KILOGRAM, LocalDate.of(2024,12,27));
    fridge.addIngredient(apple);

    // Adds a new ingredient 'Butter' to fridge
    Ingredient butter = new Ingredient("Butter", 0.2, 10, Unit.KILOGRAM, LocalDate.of(2024,10,28));
    fridge.addIngredient(butter);

    // Test retrieving the ingredients in alphabetical order
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();

    assertEquals("Apple", sortedIngredients.get(0).getName());
    assertEquals("Butter", sortedIngredients.get(1).getName());
    assertEquals("Cheese", sortedIngredients.get(2).getName());
  }

  /**
   * Test calculating the total price of all ingredients in the fridge.
   *
   * <p> Expected outcome: The total price calculated should be the sum of
   * prices of all ingredients.</p>
   */
  @Test
  public void calculateTotalPrice() {
    // Adds a new ingredient 'bread' to fridge
    // Price = 60.99 kr
    Ingredient bread = new Ingredient("Bread", 1, 60.99, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(bread);

    // Adds a new ingredient 'Apple' to fridge
    // Price = 80 kr
    Ingredient apple = new Ingredient("Apple", 2, 40, Unit.KILOGRAM, LocalDate.of(2024,12,27));
    fridge.addIngredient(apple);

    // Test calculating the total price
    assertEquals(140.99, fridge.calculateTotalPrice());
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test adding a {@code null} ingredient to the fridge.
   *
   * <p> Expected outcome: The ingredient should not be added to the fridge,
   * and an IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void addNullIngredient() {
    assertThrows(IllegalArgumentException.class, () -> fridge.addIngredient(null));
  }

  /**
   * Test removing a non-existent ingredient.
   *
   * <p> Expected outcome: There should be no ingredient with quantity decreased,
   * and an IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void decreaseNonExistentIngredientQuantity() {
    // Add a new ingredient 'Bread' to the fridge
    Ingredient bread = new Ingredient("Bread", 3, 60, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(bread);

    // Attempt to remove 2 kg of non-existent ingredient 'Egg'
    assertThrows(IllegalArgumentException.class, () -> fridge.decreaseIngredientQuantity("Egg", 2, Unit.KILOGRAM));

    // Verify that ingredient 'Bread' was not updated
    assertEquals(3,fridge.findIngredientByName("Bread").get().getQuantity());
  }

  /**
   * Test decreasing an ingredient's quantity to below zero.
   *
   * <p> Expected outcome: The quantity will not be decreased, and an
   * IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void decreaseToNegativeQuantity() {
    // Add a new ingredient 'Bread' to the fridge
    // Quantity = 3 kg
    Ingredient bread = new Ingredient("Bread", 3, 60, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(bread);

    // Attempts to remove 5 kg of ingredient 'Bread'
    assertThrows(IllegalArgumentException.class, () -> fridge.decreaseIngredientQuantity("Bread", 5, Unit.KILOGRAM));

    // Verify that the quantity was not decreased
    assertEquals(3,fridge.findIngredientByName("Bread").get().getQuantity());
  }

  /**
   * Test decreasing an ingredient's quantity with a negative amount.
   *
   * <p> Expected outcome: The ingredient's quantity should not be changed, and an
   * IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void decreaseQuantityWithNegativeQuantity() {
    // Add a new ingredient 'Bread' to the fridge
    // Quantity = 3 kg
    Ingredient bread = new Ingredient("Bread", 3, 60, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(bread);

    // Attempts to remove -2 kg of ingredient 'Bread'
    assertThrows(IllegalArgumentException.class, () -> fridge.decreaseIngredientQuantity("Bread", -2, Unit.KILOGRAM));
  }

  /**
   * Test decreasing an ingredient's quantity using a null unit.
   *
   * <p> Expected outcome: The ingredient's quantity should not be changed, and an
   * IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void decreaseQuantityWithNullUnit() {
    // Add a new ingredient 'Milk' to the fridge
    // Quantity = 3.5 litres
    Ingredient milk = new Ingredient("Milk", 3.5, 60, Unit.LITRE, LocalDate.of(2024,10,27));
    fridge.addIngredient(milk);

    // Attempts to remove 2 of null unit of ingredient 'Milk'
    assertThrows(IllegalArgumentException.class, () -> fridge.decreaseIngredientQuantity("Milk", 2, null));
  }

  /**
   * Test finding a non-existent ingredient by name.
   *
   * <p> Expected outcome: The search should return an empty {@code Optional},
   * as no ingredient should match the search.</p>
   */
  @Test
  public void findNonExistentIngredientByName() {
    // Add a new ingredient 'Bread' to the fridge
    Ingredient bread = new Ingredient("Bread", 3, 60, Unit.KILOGRAM, LocalDate.of(2024,10,27));
    fridge.addIngredient(bread);

    // Attempts to find non-existent ingredient 'Milk'
    assertTrue(fridge.findIngredientByName("Milk").isEmpty());
  }

  /**
   * Test sorting ingredients alphabetically when the fridge is empty.
   *
   * <p> Expected outcome: An empty list should be returned. </p>
   */
  @Test
  public void findEmptySortedIngredients() {
    List<Ingredient> sortedIngredients = fridge.findSortedIngredients();
    assertTrue(sortedIngredients.isEmpty());
  }

  /**
   * Test calculating the total price when the fridge is empty.
   *
   * <p> Expected outcome: The total price is zero.</p>
   */
  @Test
  public void calculatePriceOfEmptyFridge() {
    double totalPrice = fridge.calculateTotalPrice();
    assertEquals(0, totalPrice);
  }

}
