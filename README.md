# Portfolio project IDATT1003

STUDENT NAME = "Gilianne Kate Alivia Reyes"  
STUDENT ID = "137409"

## Project description

[//]: # (TODO: Write a short description of your project/product here.)
This project was developed as a part of the course IDATT1003 (Programming 1), 
which is a part of the Bachelor's program in Computer Science at NTNU. The application is
a Java-based food management system.

## Project structure

[//]: # (TODO: Describe the structure of your project here. How have you used packages in your structure. Where are all sourcefiles stored. Where are all JUnit-test classes stored. etc.)
This project is structured using Maven. The source files are stored in the `src/main/java` directory
while the JUnit test classes are stored in the `src/test/java` directory. The project is divided into
different packages: `model`, `utils`, `ui` and `app`. The `model` package includes the model classes
`Ingredient`, `Recipe`, `Fridge`, `Cookbook`, `MealPlanner` and `Unit`. The `utils` package's
classes include helper methods used accross the project. The `ui` package includes the classes
that interact with the user, which includes the `Ui` and `InputHandler`.
The main application is stored in the `app` package.

## Link to repository
https://github.com/NTNU-IDI/idatt1003-mappe-2024-giliannereyes

[//]: # (TODO: Include a link to your GitHub repository here.)

## How to run the project
To run the project, you can run the main method of the `FoodManagementApp` in the `app` package.
The main method will start the application which is interactable through the console. The console
will present a menu with different options which the user can choose from by typing
the corresponding number and pressing enter.

1. Add ingredient 
2. Search ingredient 
3. Remove ingredient
4. View sorted ingredients 
5. Add recipe 
6. Check recipe ingredients 
7. Get recipe suggestions



[//]: # (TODO: Describe how to run your project here. What is the main class? What is the main method?
What is the input and output of the program? What is the expected behaviour of the program?)

## How to run the tests
To run the tests, navigate to the `src/test/java` directory, select a test class and run it.
Or you can run all the tests by right-clicking the `src/test/java` directory and
selecting `Run 'All Tests'`. Each test class is named after the class they are testing
along with "Test" as a suffix. All the model classes are tested.

[//]: # (TODO: Describe how to run the tests here.)

## References
The application in this project was developed by the student. Certain
parts of the code were generated or inspired by ChatGPT or GitHub Copilot.
These are stated clearly in the JavaDocs. 

[//]: # (TODO: Include references here, if any. For example, if you have used code from the course book, include a reference to the chapter.
Or if you have used code from a website or other source, include a link to the source.)

