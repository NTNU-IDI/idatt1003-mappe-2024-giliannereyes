package edu.ntnu.iir.bidata;

import java.util.Arrays;

/**
 * An enum 'Unit' that represents different units of measurements for volume and
 * mass that are relevant for ingredients and recipes.
 */
public enum Unit {
  // Volume Units
  LITRE("L", 1.0),            // Base unit for volume (liters)
  DECILITRE("dL", 0.1),       // 1 dL = 0.1 L
  MILLILITRE("mL", 0.001),    // 1 mL = 0.001 L
  TEASPOON("tsp", 0.00492892), // 1 tsp = 4.92892 mL
  TABLESPOON("tbsp", 0.0147868), // 1 tbsp = 14.7868 mL
  CUP("cup", 0.24),           // 1 cup = 240 mL

  // Mass Units
  KILOGRAM("kg", 1.0),        // Base unit for mass (kilograms)
  GRAM("g", 0.001),           // 1 g = 0.001 kg
  MILLIGRAM("mg", 0.000001);  // 1 mg = 0.000001 kg

  private final String symbol;
  private final double conversionFactor; // Conversion factor to base unit

  /**
   * Constructs a new Unit instance.
   *
   * @param symbol The symbol representing the unit.
   * @param conversionFactor The conversion factor to the base unit.
   */
  Unit(String symbol, double conversionFactor) {
    this.symbol = symbol;
    this.conversionFactor = conversionFactor;
  }

  /**
   * Retrieves the symbol of the unit.
   *
   * @return The symbol of the unit.
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Converts a value to the base unit.
   *
   * @param value The value to convert.
   *
   * @return The value in base unit.
   */
  public double convertToBaseUnit(double value) {
    return conversionFactor * value;
  }

  /**
   * Retrieves a Unit instance that matches the provided symbol.
   *
   * @param symbol The symbol of the unit to retrieve.
   *
   * @return A Unit instance.
   *
   * @throws IllegalArgumentException if the provided symbol does not match any unit.
   */
  public static Unit getUnitBySymbol(String symbol) {
    return Arrays.stream(Unit.values())
        .filter(unit -> unit.getSymbol().equalsIgnoreCase(symbol))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Provided unit is not valid. Enter another unit."));
  }

}
