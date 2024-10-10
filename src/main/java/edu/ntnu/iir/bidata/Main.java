package edu.ntnu.iir.bidata;

import java.time.LocalDate;

/**
 * Code in this class is temporary and for test purposes only.
 */
public class Main {
  public static void main(String[] args) {
    Fridge fridge = new Fridge();

    // New ingredient "Milk"
    String name = "Milk";
    double quantity = 1;
    double price = 30;
    String unit = "liter";
    LocalDate expiryDate = LocalDate.of(2024,10,10);
    Ingredient milk = new Ingredient(name, quantity, price, unit, expiryDate);

    // New ingredient "Egg"
    String name2 = "Egg";
    double quantity2 = 12;
    double price2 = 58;
    String unit2 = "piece";
    LocalDate expiryDate2 = LocalDate.of(2024,10,1);
    Ingredient egg = new Ingredient(name2, quantity2, price2, unit2, expiryDate2);


    // Testing method "addIngredient"
    fridge.addIngredient(milk);
    fridge.addIngredient(egg);

    fridge.showAllIngredients();  // Should show info on milk, egg

    // Testing method "removeIngredient"
    System.out.println("Testing method 'removeIngredient'...");
    fridge.removeIngredient("milk", 1);
    fridge.removeIngredient("Egg", 4);
    fridge.showAllIngredients();  // Should show egg (quantity=8) and no milk-instance


    // Testing method "searchIngredient"
    System.out.println("Testing method 'searchIngredient'...");
    Ingredient targetIngredient = fridge.searchIngredient("egg");
    System.out.println(targetIngredient);

    // Method "showAllIngredients" is already tested.

    // Testing method "showAllExpiredIngredients"
    System.out.println("Testing method 'showAllExpiredIngredients'...");
    fridge.showAllExpiredIngredients(); // Expected output: egg

    // New ingredient "Potato"
    String name3 = "Potato";
    double quantity3 = 5;
    double price3 = 10;
    String unit3 = "piece";
    LocalDate expiryDate3 = LocalDate.of(2023,10,1);
    Ingredient potato = new Ingredient(name3, quantity3, price3, unit3, expiryDate3);
    fridge.addIngredient(potato);

    // Testing method calculateTotalValue
    System.out.println("Testing method 'calculateTotalValue'...");
    System.out.println(fridge.calculateTotalValue());
  }

}
