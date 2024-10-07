import edu.ntnu.iir.bidata.Ingredient;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IngredientTest {

  @Test
  @DisplayName("Positive test: creating a valid Ingredient")
  public void testValidIngredientCreation() {
    LocalDate expiryDate = LocalDate.of(2024, 10, 10);
    Ingredient ingredient = new Ingredient("Milk", 1.5, 50, "liter", expiryDate);

    assertEquals("Milk", ingredient.getName());
    assertEquals(1.5, ingredient.getQuantity());
    assertEquals(50, ingredient.getPrice());
    assertEquals(33.33, ingredient.getPricePerUnit(), 0.01); // Allowing for rounding error
    assertEquals("liter", ingredient.getUnit());
  }

  @Test
  @DisplayName("Negative test: invalid name (null)")
  public void testInvalidNameNull() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient(null, 1.5, 50, "liter", LocalDate.of(2024, 10, 10)));
  }

  @Test
  @DisplayName("Negative test: invalid name (empty)")
  public void testInvalidNameEmpty() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient("", 1.5, 50, "liter", LocalDate.of(2024, 10, 10)));
  }

  @Test
  @DisplayName("Negative test: negative quantity")
  public void testNegativeQuantity() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient("Milk", -1.5, 50, "liter", LocalDate.of(2024, 10, 10)));
  }

  @Test
  @DisplayName("Negative test: negative price")
  public void testNegativePrice() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient("Milk", 1.5, -50, "liter", LocalDate.of(2024, 10, 10)));
  }

  @Test
  @DisplayName("Negative test: null unit")
  public void testNullUnit() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient("Milk", 1.5, 50, null, LocalDate.of(2024, 10, 10)));
  }

  @Test
  @DisplayName("Negative test: empty unit")
  public void testEmptyUnit() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient("Milk", 1.5, 50, "", LocalDate.of(2024, 10, 10)));
  }

  @Test
  @DisplayName("Negative test: null expiry date")
  public void testNullExpiryDate() {
    assertThrows(IllegalArgumentException.class, ()
        -> new Ingredient("Milk", 1.5, 50, "liter", null));
  }

  @Test
  @DisplayName("Positive test: check price per unit calculation")
  public void testValidPricePerUnitCalculation() {
    Ingredient ingredient = new Ingredient("Milk", 1.5, 50, "liter", LocalDate.of(2024, 10, 10));
    assertEquals(33.333, ingredient.getPricePerUnit(), 0.01);
  }






}
