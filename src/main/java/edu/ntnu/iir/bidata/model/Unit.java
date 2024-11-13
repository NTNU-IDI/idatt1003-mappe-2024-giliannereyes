package edu.ntnu.iir.bidata.model;

import java.util.Arrays;

/**
 * An enum 'Unit' that represents different units of measurements for volume and
 * mass that are relevant for ingredients and recipes.
 */
public enum Unit {
  // Volume Units
  LITRE("L", 1.0, UnitType.VOLUME),            // Base unit for volume (liters)
  DECILITRE("dL", 0.1, UnitType.VOLUME),       // 1 dL = 0.1 L
  MILLILITRE("mL", 0.001, UnitType.VOLUME),    // 1 mL = 0.001 L

  // Mass Units
  KILOGRAM("kg", 1.0, UnitType.MASS),        // Base unit for mass (kilograms)
  GRAM("g", 0.001, UnitType.MASS),           // 1 g = 0.001 kg
  MILLIGRAM("mg", 0.000001, UnitType.MASS);  // 1 mg = 0.000001 kg

  private final String symbol;
  private final double conversionFactor; // Conversion factor to base unit
  private final UnitType unitType;           // Type of unit (volume or mass)

  /**
   * Constructs a new Unit instance.
   *
   * @param symbol The symbol representing the unit.
   * @param conversionFactor The conversion factor to the base unit.
   */
  Unit(String symbol, double conversionFactor, UnitType unitType) {
    this.symbol = symbol;
    this.conversionFactor = conversionFactor;
    this.unitType = unitType;
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
   * Checks if two units are of different types. The type is either volume or mass.
   *
   * @param otherUnit The other unit to compare with.
   *
   * @return {@code true} if the unit types are not the same. Else {@code false}.
   */
  public boolean notSameType(Unit otherUnit) {
    return this.unitType != otherUnit.unitType;
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
   * Converts a value from the base unit.
   *
   * @param value The value in base unit to convert.
   *
   * @return The value in specified unit.
   */
  public double convertFromBaseUnit(double value) {
    return value / conversionFactor;
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

  /**
   * An enum that for separating units between volume or mass types.
   */
  public enum UnitType {
    VOLUME, // Represents units for volume
    MASS  // Represents units for mass
  }
}
