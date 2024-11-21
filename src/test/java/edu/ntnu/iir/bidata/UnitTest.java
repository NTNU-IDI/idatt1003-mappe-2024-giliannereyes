package edu.ntnu.iir.bidata;

import edu.ntnu.iir.bidata.model.Unit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for {@link Unit}.
 *
 * This class verifies the functionality of the {@link Unit} enum, including
 * symbol retrieval, value conversions, unit type comparisons, and symbol lookups.
 * Both positive and negative tests are included.
 */
public class UnitTest {
  // --------------------------- POSITIVE TESTS ----------------------------------
  /**
   * Tests the {@link Unit#convertToBaseUnitValue(double)} method.
   *
   * Expected outcome: The method should correctly convert values to the base unit.
   */
  @Test
  public void testConvertToBaseUnitValue() {
    // Volume Units
    assertEquals(1.0, Unit.LITRE.convertToBaseUnitValue(1.0), 0.00001); // 1 L = 1 L
    assertEquals(0.1, Unit.DECILITRE.convertToBaseUnitValue(1.0), 0.00001); // 1 dL = 0.1 L
    assertEquals(0.001, Unit.MILLILITRE.convertToBaseUnitValue(1.0), 0.00001); // 1 mL = 0.001 L

    // Mass Units
    assertEquals(1.0, Unit.KILOGRAM.convertToBaseUnitValue(1.0), 0.00001); // 1 kg = 1 kg
    assertEquals(0.001, Unit.GRAM.convertToBaseUnitValue(1.0), 0.00001); // 1 g = 0.001 kg
    assertEquals(0.000001, Unit.MILLIGRAM.convertToBaseUnitValue(1.0), 0.00001); // 1 mg = 0.000001 kg
  }

  /**
   * Tests the {@link Unit#convertFromBaseUnitValue(double)} method.
   *
   * Expected outcome: The method should correctly convert values from the base unit.
   */
  @Test
  public void testConvertFromBaseUnitValue() {
    // Volume Units
    assertEquals(10.0, Unit.DECILITRE.convertFromBaseUnitValue(1.0), 0.00001); // 1 L = 10 dL
    assertEquals(1000.0, Unit.MILLILITRE.convertFromBaseUnitValue(1.0), 0.00001); // 1 L = 1000 mL

    // Mass Units
    assertEquals(1000.0, Unit.GRAM.convertFromBaseUnitValue(1.0), 0.00001); // 1 kg = 1000 g
    assertEquals(1000000.0, Unit.MILLIGRAM.convertFromBaseUnitValue(1.0), 0.00001); // 1 kg = 1,000,000 mg
  }

  /**
   * Tests the {@link Unit#notSameType(Unit)} method.
   *
   * Expected outcome: The method should correctly identify units of different types.
   */
  @Test
  public void testNotSameType() {
    assertTrue(Unit.LITRE.notSameType(Unit.KILOGRAM)); // Volume vs Mass
    assertFalse(Unit.LITRE.notSameType(Unit.MILLILITRE)); // Both Volume
    assertFalse(Unit.GRAM.notSameType(Unit.MILLIGRAM)); // Both Mass
  }

  /**
   * Tests the {@link Unit#getUnitBySymbol(String)} method.
   *
   * Expected outcome: The method should return the correct unit for valid symbols.
   */
  @Test
  public void testGetUnitBySymbol() {
    assertEquals(Unit.LITRE, Unit.getUnitBySymbol("L"));
    assertEquals(Unit.MILLILITRE, Unit.getUnitBySymbol("mL"));
    assertEquals(Unit.GRAM, Unit.getUnitBySymbol("g"));
    assertEquals(Unit.MILLIGRAM, Unit.getUnitBySymbol("mg"));
  }

  // --------------------------- NEGATIVE TESTS ----------------------------------

  /**
   * Tests the {@link Unit#getUnitBySymbol(String)} method with invalid inputs.
   *
   * Expected outcome: The method should throw an {@link IllegalArgumentException}
   * for invalid symbols.
   */
  @Test
  public void testGetUnitBySymbolInvalid() {
    // Invalid symbol
    assertThrows(IllegalArgumentException.class, () ->
        Unit.getUnitBySymbol("invalid")
    );

    // Null input
    assertThrows(IllegalArgumentException.class, () ->
        Unit.getUnitBySymbol(null)
    );

    // Empty string
    assertThrows(IllegalArgumentException.class, () ->
        Unit.getUnitBySymbol("")
    );
  }

  /**
   * Tests the {@link Unit#convertToBaseUnitValue(double)} method with invalid expectations.
   *
   * Expected outcome: The returned values should not match incorrect expectations.
   */
  @Test
  public void testConvertToBaseUnitValueInvalid() {
    assertNotEquals(2.0, Unit.LITRE.convertToBaseUnitValue(1.0)); // Incorrect expectation
    assertNotEquals(0.5, Unit.DECILITRE.convertToBaseUnitValue(1.0)); // Incorrect expectation
    assertNotEquals(0.01, Unit.MILLILITRE.convertToBaseUnitValue(1.0)); // Incorrect expectation
  }

  /**
   * Tests the {@link Unit#convertFromBaseUnitValue(double)} method with invalid expectations.
   *
   * Expected outcome: The returned values should not match incorrect expectations.
   */
  @Test
  public void testConvertFromBaseUnitValueInvalid() {
    assertNotEquals(20.0, Unit.DECILITRE.convertFromBaseUnitValue(1.0)); // Incorrect expectation
    assertNotEquals(2000.0, Unit.MILLILITRE.convertFromBaseUnitValue(1.0)); // Incorrect expectation
  }
}
