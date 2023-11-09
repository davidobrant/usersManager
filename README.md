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


## Screenshot of Program

**MAIN MENU**

![alt test](../assets/main_menu.png)

**SUB MENU**

![alt test](../assets/sub_menu.png)



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

- `getUserById(int id)`: Takes in an ID as parameter and returns the user with given ID.
- `db.getUsers()`: Describes exactly what it does. Through the instance of the database it will get all users and returns `ArrayList<User>`.
- `throws SQLException`: Throws the exception to the caller so the error can be handled correctly.
- `executeSelectUsersBySql()`: Method takes a SQL query string and executes it, returns the selected users in result as `ResultSet`.
- `printUsersInTableView(ArrayList<User> users)`: Receives an `ArrayList<User> users` and prints users in a formatted table-view.
- `promptDate()`: Prompts user for a valid `Date` input. If given input is not valid the method will throw an exception.



### Understandability and Continuety

The whole application follows a simple structure with a menu with selections of options for the user to choose among.
The chosen option will take the user to next menu. The (0)-command will always take you back to the menu above the current and finally get user back to the main menu.
Pressing (0) or 'exit' will close program.

When prompted the text will always be styled in a certain way (blue text and preceded by `> Enter <input>: `) and always on a new line.

Informational messages is given in color-coded text for better and faster understanding and better intuitiveness.
The program also is giving the user information about actions taken and operations executed.
For example a message will appear when starting to add a new user and another will verify that the operation is successful or not.



### Example from `UI.java`
```java
private void searchMenu() throws SQLException {
        app.runMenu();
        while (app.menuIsRunning()) {
        p.printSubMenu("SEARCH MENU");
        System.out.println("""
            Search users by:\s
            (1) ID
            (2) First name
            (3) Last name
            (4) Email
            (5) Date of Birth
            (6) Membership
            (0) Exit""");
        handleSearchMenu(p.promptCommand());
    }
}
```
This is an example of a submenu in the program.
The options are fairly straightforward and easy to understand. Press (1) or type "id" to choose action to search by ID.
The program will then prompt user for a User ID and present the results.

```java
public String promptCommand() {
    System.out.print(f.prompt("command"));
    return scanner.next().toLowerCase();
}
```
This is an example of a "prompt" method. In this case there are no parameters and all it does is formats
the string 'command' as a prompt with the help of the `Formatter.java` class which will print as result `> Enter command: ` in blue text.
The method then returns the input entered by user.

```java
private void handleSearchMenu(String cmd) throws SQLException {
    var userList = new UserList(db.getUsers());
    switch (cmd) {
        case "0":
        case "exit": {
            app.exitMenu();
            break;
        }
        case "1":
        case "id": {
            int id = p.promptInt("User ID");
            p.printHeading("SEARCH BY ID", " Results for: \"" + id + "\"");
            p.printUsers(userList.searchById(id));
            break;
        }
        case "2":
        case "firstname": {
            String firstName = p.promptStringOfChars("First name");
            p.printHeading("SEARCH BY FIRSTNAME", " Results for: \"" + firstName + "\"");
            p.printUsers(userList.searchByFirstName(firstName));
            break;
        }
        {...}
        default: {
            p.printInvalidCommand();
            break;
        }
    }
}
```
A switch statement handles the command from user and executes the chosen option. If the command is not recognized and is not among the alternatives
a message is shown to user. Unless `app.exitMenu()` is called, the `while (app.menuIsRunning())` loop will print the menu once more for user to input a valid command.


### Example from `UserList.java`
```java
public ArrayList<User> searchByFirstName(String firstName) {
    return new ArrayList<>(users.stream().filter(user -> user.getFirstName().equalsIgnoreCase(firstName)).toList());
}
```
In List- and Search-menus we only fetch users once and use the `UserList.java` class to sort or filter the results.
This method prevents program from making too many requests to the database. This will make the program more effective.


### Naming conventions
The naming of variables and methods are as simple and as self-explanatory as possibly, only describing what is necessary to understand their purpose.
Magic variables are stored as constants with capital letters. For example REGEX is stored at the top of the `Utilitis.java` class and accessed by instance of class.


### Functions/Methods
Functions are broken down into smaller ones to be as generic and performance-wise as simple as possible.
Functions will also have as few parameters as is needed.


### Comments
No unnecessary comments. The only ones used are for explaining parameters/returns/throws in methods and in `UI.java` for easily getting an overview of file.
```java
/**
     * Prompts user for parameter <string> and return the value of input.
     * Validates that input is of type 'int' and prompts user again if input is invalid.
     * 
     * @param string - Describing name of requested variable. Ex "> Enter <string>: ".
     * @return int - The value of input from user.
     */
    public int promptInt(String string) {
        try {
            System.out.print(f.prompt(string));
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            printWarning("Only digits allowed. Please try again!");
            return promptInt(string);
        }
    }
```


### Code structure
Related code is vertically dense or is easily accessed by tracing origin by instance.
Similar type of methods are next to each other and are following a logical structure.
Main functions start at the top and remaining functions follows in descending order of necessity.
Indentation is kept all throw project.

### Objects and data structures
The internal structure is kept private and the variables is accessed by getters and setters all throughout project.
No unnecessary methods within classes and the classes keeps to their simplest tasks.
Instances of classes are used instead of static methods.