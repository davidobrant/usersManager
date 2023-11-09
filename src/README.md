# Users Manager (Console application)

This is an example application for trying out CRUD-operations with Sqlite-database.

## How to run program

1. Pull code from Github-repository
2. Open project in IntelliJ IDEA
3. Run `main()` from `org.example.Main`

> When starting the project a database file name `users.db` will be created if not already exists.

> If `users.db` is empty the program will offer to populate file with mock-data. 

## Project structure

### `Main.java`

This file is the entrypoint and will create a new instance of the `UI.java` class (user interface) and call `UI.run()`.

### `UI.java`

This file contains the User-Interface with menus and handling of commands from user in console.
Here is where all the operations are executed.

### `Database.java`

This file creates and connects the database. Contains all methods for CRUD-operations.

### `User.java`

This is the model class for a user with two kinds of constructors and getters/setters for user information. 

### `UserList.java`

This is the model class for lists of users. File contains the logic for sorting and search users.

## utils

>This folder contains all the helper files and methods that is used throughout project. 

### `App.java`

Contains variables and methods for starting and exiting the program as well as the different menus.

### `Printer.java` 

This file contains methods for handling what is printed out and also what is received from user input via console. 
(methods to print or prompt).

### `Formatter.java`

This file is simply formatting and returning various strings that is to be printed. 
To gather all methods in one place makes it easier to change the appearance.

### `Colors.java`

This file with return a `HashMap<color, color_code>` to be used is wanting to color text or menus.

### `Table.java`

File will method for printing users in a tableview when presenting lists or search results of users.

### `Utilities.java`

File contains other "helper methods" for example validating or creating a `java.sql.Date Object`.

### `MockData.java`

Contains method for populating database with mock-users.


## Images of Program

**MAIN MENU**

![alt test](/assets/main_menu.png)



## Instructions for Linux

> To run Java on Linux first make sure to have Java Development Kit (JDK) installed.
Check by command `java --version` in terminal. 
> [Instructions for Ubuntu](https://ubuntu.com/tutorials/install-jre#1-overview)

Start in root directory for project.

1. Navigate to package directory `cd src/main/java/org.example/`.
2. Run command `javac Main.java` to compile `*.java` files into `*.class`. 
3. Run command `java Main` to run `Main.class`.

Program will now start.


## Clean code

This code demonstrates using clean-code principles by having descriptive names.

- `getAllCars()`: Describes exactly wht it does, gets all cars from database and returns arraylist.
- `throws SQLException`: Throw the exception to the caller so the error can be handled correctly.
- `executeSelectSql`: Describes exactly what it does, takes a SQL query string and executes it and returns the result as `ResultSet`.

```java
public static void main(String[] args) {
    System.out.println("demo");
}
```
UserList
In List- and Search-menus we only fetch users once and use the UserList class to sort or filter the results. 
This method prevents program from making too many requests to the database.

KISS

DRY

