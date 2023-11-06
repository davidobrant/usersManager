# Users Manager (Console application)

This is an example application for trying out CRUD-operations with Sqlite-database.

## How to run program

1. Pull code from Github-repository
2. Open project in IntelliJ IDEA
3. Run `main()` from `org.example.Main`

> When starting the project a database file name `users.db` will be created if not already exists.

## Project structure

### `Main.java`

This file is the entrypoint and will call the database to manage users.
Here is where all the operations are executed.

### `Database.java`

This file creates and connects the database. Contains all methods for CRUD-operations.

### `UI.java`

This file contains the User-Interface with menus and handling of commands from user in console.

### `User.java`

This is the model class for a user.

### `Utils.java`

This file contains all the helper-methods that is used throughout project.


## Image demo

How to add image `![alt test](/assets/DEMO.png)`.

**DEMO**

![alt test](/assets/DEMO.png)


## Ex...

[Link text](https://example.com)



## Instructions for Linux

...


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