package edu.ntnu.iir.bidata.model;

import edu.ntnu.iir.bidata.utils.Validation;
import java.util.Arrays;

/**
 * Represents a unit of measurement, which can be of type volume, mass or piece.
 * Each unit has a symbol, a conversion factor to the base unit and a type.
 *
 * <p>Provides methods to convert values between units and to check if two units are compatible.</p>
 *
 * <p>Base units: litre for volume, kilogram for mass and piece for items measured by count.</p>
 *
 * @author Gilianne Reyes
 * @version 1.2
 * @since 1.1
 */
public enum Unit {
  // ChatGPT generated the conversion factors for each unit.
  LITRE("L", 1.0, UnitType.VOLUME),            // Base unit for volume (liters)
  DECILITRE("dL", 0.1, UnitType.VOLUME),
  MILLILITRE("mL", 0.001, UnitType.VOLUME),
  KILOGRAM("kg", 1.0, UnitType.MASS),        // Base unit for mass (kilograms)
  GRAM("g", 0.001, UnitType.MASS),
  MILLIGRAM("mg", 0.000001, UnitType.MASS),
  PIECE("piece", 1.0, UnitType.PIECE);      // Base unit for items measured by count

  private final String symbol;
  private final double conversionFactor;
  private final UnitType type;

  /**
   * Constructor for the Unit enum.
   *
   * @param symbol is the symbol representing the unit.
   * @param conversionFactor is the conversion factor to the base unit.
   */
  Unit(String symbol, double conversionFactor, UnitType unitType) {
    this.symbol = symbol;
    this.conversionFactor = conversionFactor;
    this.type = unitType;
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
   * Checks if the units are compatible by comparing their types.
   *
   * @param otherUnit is the unit to compare with.
   *
   * @return {@code true} if the units are compatible, {@code false} otherwise.
   *
   * @throws IllegalArgumentException if the provided unit is null.
   */
  public boolean isCompatibleWith(Unit otherUnit) {
    Validation.validateNonNull(otherUnit, "Unit");
    return type == otherUnit.type;
  }

  /**
   * Convert a value to its base unit value.
   *
   * @param value is the value to convert.
   *
   * @return The value in base unit.
   *
   * @throws IllegalArgumentException if the value to convert is negative.
   */
  public double convertToBaseUnitValue(double value) {
    Validation.validateNonNegativeNumber(value, "Value to convert");
    return conversionFactor * value;
  }

  /**
   * Converts a base unit value to a value of this unit.
   *
   * @param value is the value in base unit to convert.
   *
   * @return The value in this unit.
   *
   * @throws IllegalArgumentException if the value or conversion factor is negative.
   */
  public double convertFromBaseUnitValue(double value) {
    Validation.validateNonNegativeNumber(value, "Value to convert");
    Validation.validateNonNegativeNumber(conversionFactor, "Conversion factor");
    return value / conversionFactor;
  }

  /**
   * Retrieves a unit by its symbol.
   *
   * @param symbol is the symbol of the unit to retrieve.
   *
   * @return A unit with the provided symbol.
   *
   * @throws IllegalArgumentException if the provided symbol does not match any unit.
   */
  public static Unit getUnitBySymbol(String symbol) {
    Validation.validateNonEmptyString(symbol, "Unit symbol");
    return Arrays.stream(Unit.values())
        .filter(unit -> unit.getSymbol().equalsIgnoreCase(symbol))
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException("Provided unit is not valid. Enter another unit."));
  }

  /**
   * Represents the type of unit.
   */
  public enum UnitType {
    VOLUME,
    MASS,
    PIECE
  }
}
