import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link Ingredient} class.
 *
 * <p> The following tests are included: </p>
 *
 * <b> Positive tests: </b>
 *
 * <ul>
 *   <li>Creates an Ingredient instance with valid fields</li>
 *   <li>Calculates the correct price based on price per unit and quantity</li>
 *   <li>Correctly removes a valid specified quantity from the ingredient's quantity</li>
 *   <li>Correctly returns {@code true} or {@code false} when checking if ingredient is expired</li>
 *   <li>Correctly increases the ingredient's quantity with a valid specified quantity</li>
 * </ul>
 *
 * <b> Negative tests: </b>
 *
 * <ul>
 *   <li>Creates an Ingredient instance with invalid name</li>
 *   <li>Creates an Ingredient instance with invalid quantity</li>
 *   <li>Creates an Ingredient instance with invalid price per unit</li>
 *   <li>Creates an Ingredient instance with invalid unit</li>
 *   <lI>Creates an Ingredient instance with invalid expiry date</lI>
 *   <li>Removes an invalid quantity</li>
 *   <li>Increases quantity with negative quantity</li>
 * </ul>
 */
public class IngredientTest {
  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test creation of an instance of Ingredient where a valid name, valid quantity,
   * valid price per unit, valid unit of measurement and valid expiry date are provided.
   *
   * <p> Expected outcome: An instance of Ingredient is created. When retrieving the fields
   * with the Ingredient class' get-methods, the same fields provided during
   * creation of the instance should be returned.</p>
   */
  @Test
  public void createInstanceWithValidFields() {
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    String actualName = ingredient.getName();
    double actualQuantity = ingredient.getQuantity();
    double actualPricePerUnit = ingredient.getPricePerUnit();
    Unit actualUnit = ingredient.getUnit();
    LocalDate actualExpiryDate = ingredient.getExpiryDate();

    assertEquals("Milk", actualName);
    assertEquals(2.0, actualQuantity);
    assertEquals(15.0, actualPricePerUnit);
    assertEquals(Unit.LITRE, actualUnit);
    assertEquals(LocalDate.of(2025, 10, 14), actualExpiryDate);
  }

  /**
   * Test creation of an instance of Ingredient where a valid name, valid quantity,
   * valid price per unit are provided, and without price per unit and expiry date.
   *
   * <p> Expected outcome: An instance of Ingredient is created with the second constructor.
   * When retrieving the fields with the Ingredient class' get-methods,
   * the same fields provided during creation of the instance should be returned.</p>
   */
  @Test
  public void createInstanceWithoutPriceAndExpiryDate() {
    // Create an ingredient without price per unit and expiry date
    Ingredient ingredient = new Ingredient("Flour", 5, Unit.KILOGRAM);

    String actualName = ingredient.getName();
    double actualQuantity = ingredient.getQuantity();
    Unit actualUnit = ingredient.getUnit();

    assertEquals("Flour", actualName);
    assertEquals(5.0, actualQuantity);
    assertEquals(Unit.KILOGRAM, actualUnit);
  }

  /**
   * Test calculating the price of the ingredient based on quantity and price per unit.
   *
   * <p> Expected outcome: The price of the ingredient should be equal
   * to the price calculated manually with price per unit and quantity.</p>
   */
  @Test
  public void calculateCorrectPrice() {
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    double actualPricePerUnit = ingredient.getPricePerUnit();
    double actualQuantity = ingredient.getQuantity();
    double actualPrice = ingredient.getPrice();

    assertEquals(actualPricePerUnit * actualQuantity, actualPrice);
  }

  /**
   * Test removing a specific valid amount from the ingredient's quantity.
   *
   * <p> Expected outcome: The provided quantity to remove should be subtracted
   * from the ingredient's quantity.</p>
   */
  @Test
  public void removeValidQuantity() {
    Ingredient ingredient = new Ingredient("Milk", 2.5, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    // Test removing a valid quantity of the same unit
    ingredient.decreaseQuantity(1, Unit.LITRE);
    assertEquals(1.5, ingredient.getQuantity());

    // Test removing a valid quantity of a different unit
    ingredient.decreaseQuantity(10, Unit.DECILITRE);  // Note: 10 dL = 1 L
    assertEquals(0.5, ingredient.getQuantity());

    // Test removing a valid decimal quantity
    ingredient.decreaseQuantity(5, Unit.DECILITRE);
    assertEquals(0, ingredient.getQuantity());
  }

  /**
   * Test checking if an ingredient is expired with both an ingredient that is not expired and
   * an ingredient that is expired.
   *
   * <p> Expected outcome: The method that checks if an ingredient is expired
   * should return {@code false} for the first ingredient and {@code true} for the second.</p>
   */
  @Test
  public void checkForExpiredIngredient() {
    // Checks for ingredient that is not expired
    Ingredient freshIngredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2026, 10, 14));
    assertFalse(freshIngredient.isExpired());

    // Checks for ingredient that is expired
    Ingredient expiredIngredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2020, 10, 14));
    assertTrue(expiredIngredient.isExpired());

  }

  /**
   * Test checking if an ingredient's quantity increases with a specified valid quantity.
   *
   * <p> Expected outcome: The ingredient's quantity should be increased with the specified
   * valid quantity.</p>
   */
  @Test
  public void addValidQuantity() {
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2026, 10, 14));

    // Test increasing the quantity with a valid amount of the same unit
    ingredient.increaseQuantity(1, Unit.LITRE);
    assertEquals(3, ingredient.getQuantity());

    // Test increasing the quantity with a valid amount of a different unit
    ingredient.increaseQuantity(5, Unit.DECILITRE);
    assertEquals(3.5, ingredient.getQuantity());

    // Test increasing quantity with a decimal amount of a different unit
    ingredient.increaseQuantity(0.1, Unit.DECILITRE);
    assertEquals(3.51, ingredient.getQuantity());
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Test creation of an instance of Ingredient where the names provided
   * are invalid values of {@code blank}, {@code empty} and {@code null}.
   *
   * <p> Expected outcome: An instance should not be created if the
   * name is set to {@code blank}, {@code empty} or {@code null}.
   * An IllegalArgumentException should be thrown in all 3 cases.</p>
   */
  @Test
  public void createInstanceWithInvalidName() {
    // Ingredient instance with a blank name
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("    ", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14)));

    // Ingredient instance with an empty name
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14)));

    // Ingredient instance with a null name
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient(null, 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14)));
  }

  /**
   * Test creation of an instance of Ingredient where the quantity provided
   * is an invalid (negative) value.
   *
   * <p> Expected outcome: An instance should not be created if the
   * quantity provided is negative, and an IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void createInstanceWithInvalidQuantity() {
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("Milk", -2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14)));
  }

  /**
   * Test creation of an instance of Ingredient where the price per unit provided
   * is an invalid (negative) value.
   *
   * <p> Expected outcome: An instance should not be created if the
   * price per unit provided is negative, and an IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void createInstanceWithInvalidPricePerUnit() {
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("Milk", 2, -17, Unit.LITRE, LocalDate.of(2025, 10, 14)));
  }

  /**
   * Test creation of an instance of Ingredient where the units provided
   * are invalid values of {@code blank}, {@code empty} and {@code null}.
   *
   * <p> Expected outcome: An instance should not be created if the
   * unit is set to {@code null}. An IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void createInstanceWithInvalidUnit() {
    // Ingredient instance with a null unit
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("Milk", 2, 15, null, LocalDate.of(2025, 10, 14)));
  }

  /**
   * Test creation of an instance of Ingredient where the expiry date provided
   * is an invalid value of {@code null}.
   *
   * <p> Expected outcome: An instance should not be created if the
   * expiry date provided is null, and an IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void createInstanceWithInvalidExpiryDate() {
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("Milk", 2, 15, Unit.LITRE, null));
  }

  /**
   * Test removing a specific invalid amounts from the ingredient's quantity.
   * An invalid amount means an amount that would make the quantity negative,
   * or a negative value as this would add to the quantity.
   *
   * <p> Expected outcome: The provided quantity to remove should not be subtracted
   * from the ingredient's quantity. An IllegalArgumentException should be thrown
   * and the quantity should not be changed in all cases.</p>
   */
  @Test
  public void removeInvalidQuantity() {
    Ingredient ingredient =
        new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    // Test removing a larger quantity than available
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.decreaseQuantity(5, Unit.LITRE);
    });

    // Test removing a quantity that is negative
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.decreaseQuantity(-2, Unit.LITRE);
    });

    // Test removing a larger decimal quantity than available
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.decreaseQuantity(-2.1, Unit.LITRE);
    });

    // Verify that the quantity did not change
    assertEquals(2, ingredient.getQuantity());
  }

  /**
   * Test increasing the ingredient's quantity with negative amounts.
   *
   * <p> Expected outcome: The provided quantity should not be added to the
   * ingredient's quantity as that would decrease the ingredient's quantity instead.
   * An IllegalArgumentException should be thrown in both cases.</p>
   */
  @Test
  public void addInvalidQuantity() {
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2026, 10, 14));

    // Test adding a negative whole amount
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.increaseQuantity(-5, Unit.LITRE);
    });

    // Test adding a negative decimal amount
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.increaseQuantity(-2.5, Unit.LITRE);
    });

    // Verify that the quantity did not change
    assertEquals(2, ingredient.getQuantity());
  }

  /**
   * Test creating a new invalid Ingredient instance with the second constructor.
   *
   * <p> Expected outcome: In all cases, a new instance of Ingredient should not
   * be created, and an IllegalArgumentException should be thrown.</p>
   */
  @Test
  public void createInvalidInstanceWithSecondConstructor() {
    // Ingredient instance with a null name
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient(null, 5, Unit.KILOGRAM));

    // Ingredient instance with an empty name
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("", 5, Unit.KILOGRAM));

    // Ingredient instance with a blank name
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("   ", 5, Unit.KILOGRAM));

    // Ingredient instance with a negative quantity
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("Flour", -5, Unit.KILOGRAM));

    // Ingredient instance with a null unit
    assertThrows(IllegalArgumentException.class, () ->
        new Ingredient("Flour", 5, null));
  }

}





