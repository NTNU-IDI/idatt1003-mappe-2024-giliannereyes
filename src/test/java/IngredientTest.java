import edu.ntnu.iir.bidata.model.Ingredient;
import edu.ntnu.iir.bidata.model.Unit;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the class Ingredient.
 *
 * <p> The following tests are included: </p>
 *
 * <b> Positive tests: </b>
 *
 * <ul>
 *   <li>Creates of a Ingredient object with valid attributes </li>
 *   <li>Calculates the correct price based on price per unit and quantity</li>
 *   <li>Correctly removes a valid specified quantity from the ingredient's quantity</li>
 * </ul>
 *
 * <b> Negative tests: </b>
 *
 * <ul>
 *   <li>Throws an IllegalArgumentException when constructing an instance with invalid name</li>
 *   <li>Throws an IllegalArgumentException when constructing an instance with invalid quantity</li>
 *   <li>Throws an IllegalArgumentException when constructing an instance with invalid price per unit</li>
 *   <li>Throws an IllegalArgumentException when constructing an instance with invalid unit</li>
 *   <lI>Throws an IllegalArgumentException when constructing an instance with invalid expiry date</lI>
 *   <li>Throws an IllegalArgumentException when removing an invalid quantity</li>
 * </ul>
 */
public class IngredientTest {
  // --------------------------- POSITIVE TESTS ----------------------------------

  /**
   * Test creation of an instance of Ingredient where a valid name, valid quantity,
   * valid price per unit, valid unit of measurement and valid expiry date are provided.
   *
   * <p> Expected outcome: An instance of Ingredient is created. When retrieving the attributes
   * with the Ingredient class' get-methods, the same attributes provided during
   * creation of the instance should be returned.</p>
   */
  @Test
  public void createInstanceWithValidAttributes() {
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
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    // Test removing a valid quantity of the same unit
    ingredient.decreaseQuantity(1, Unit.LITRE);
    assertEquals(1, ingredient.getQuantity());

    // Test removing a valid quantity of a different unit
    ingredient.decreaseQuantity(10, Unit.DECILITRE);  // Note: 10 dL = 1 L
    assertEquals(0, ingredient.getQuantity());
  }


  /**
   * Test checking if an ingredient is expired with an expired ingredient.
   *
   * <p> Expected outcome: The method that checks if an ingredient is expired
   * should return {@code true} as the ingredient is expired.</p>
   */
  @Test
  public void checkExpiredIngredient() {
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2000, 10, 14));

    // Test checking if method isExpired returns true
    assertTrue(ingredient.isExpired());
  }

  /**
   * Test checking if an ingredient is expired with an ingredient that is not expired.
   *
   * <p> Expected outcome: The method that checks if an ingredient is expired
   * should return {@code false} as the ingredient is not expired.</p>
   */
  @Test
  public void checkNotExpiredIngredient() {
    Ingredient ingredient = new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    // Test checking if method isExpired returns true
    assertFalse(ingredient.isExpired());
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
   * Test removing a specific invalid amount from the ingredient's quantity.
   * An invalid amount means an amount that would make the quantity negative,
   * or a negative value as this would add to the quantity.
   *
   * <p> Expected outcome: The provided quantity to remove should not be subtracted
   * from the ingredient's quantity. An IllegalArgumentException should be thrown
   * and the quantity should not be changed in both cases.</p>
   */
  @Test
  public void removeInvalidQuantity() {
    Ingredient ingredient =
        new Ingredient("Milk", 2, 15, Unit.LITRE, LocalDate.of(2025, 10, 14));

    // Test that an IllegalArgumentException is being thrown
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.decreaseQuantity(5, Unit.LITRE);  // Larger value than the available quantity
    });

    // Test that an IllegalArgumentException is being thrown
    assertThrows(IllegalArgumentException.class, () -> {
      ingredient.decreaseQuantity(-2, Unit.LITRE);  // Negative value
    });

    // Verify that the quantity did not change
    assertEquals(2, ingredient.getQuantity());
  }

}





