package org.example;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class UI {
    App app = new App();
    private Database db;
    private final Printer p = new Printer();

    public void run() throws SQLException {
        db = new Database();
        mainMenu();
    }

    /* ----- MAIN MENU ----- */
    private void mainMenu() throws SQLException {
        while (app.runMain()) {
            p.printMenu1("MAIN MENU");
            System.out.println("""
                (1) List users\s
                (2) Search users
                (3) Add user
                (4) Update user
                (5) Delete user
                (0) Exit program""");
            handleMainMenu(p.promptCommand());
        }
    }
    private void handleMainMenu(String cmd) throws SQLException {
        switch (cmd) {
            case "0":
            case "exit": {
                app.exit();
                break;
            }
            case "1":
            case "list": {
                listMenu();
                break;
            }
            case "2":
            case "search": {
                searchMenu();
                break;
            }
            case "3":
            case "add": {
                addMenu();
                break;
            }
            case "4":
            case "update": {
                updateMenu();
                break;
            }
            case "5":
            case "delete": {
                deleteMenu();
                break;
            }
            default: {
                p.printInvalidCommand();
                break;
            }
        }
    }
    /* --x-- MAIN MENU --x-- */
    /* ----- LIST MENU ----- */
    private void listMenu() throws SQLException {
        app.runMenu();
        while (app.menuIsRunning()) {
            p.printMenu2("LIST MENU");
            System.out.println("""
                List users ordered by:
                (1) ID\s
                (2) First name
                (3) Last name
                (4) Date of birth
                (5) Next birthday
                (6) Membership
                (0) Exit
                -r  For reversed list enter command + 'r' (1r)""");
            handleListMenu(p.promptCommand());
        }
    }
    private void handleListMenu(String cmd) throws SQLException {
        var userList = new UserList(db.getUsers());
        boolean reversed = cmd.length() > 1 && cmd.replace("-", "").substring(1, 2).contains("r");
        switch (cmd.substring(0,1)) {
            case "0": {
                app.exitMenu();
                break;
            }
            case "1": {
                p.printHeading1("Users", "sorted by ID", reversed);
                p.printUserList(userList.sortById(), reversed);
                break;
            }
            case "2": {
                p.printHeading1("Users", "sorted by First name", reversed);
                p.printUserList(userList.sortByFirstname(), reversed);
                break;
            }
            case "3": {
                p.printHeading1("Users", "sorted by Last name", reversed);
                p.printUserList(userList.sortByLastname(), reversed);
                break;
            }
            case "4": {
                p.printHeading1("Users", "sorted by Date of birth", reversed);
                p.printUserList(userList.sortByDateOfBirth(), reversed);
                break;
            }
            case "5": {
                p.printHeading1("Users", "sorted by Next birthday", reversed);
                p.printUserList(userList.sortByNextBirthday(), reversed);
                break;
            }
            case "6": {
                p.printHeading1("Users", "sorted by Membership", reversed);
                p.printUserList(userList.sortByMembership(), reversed);
                break;
            }
            default: {
                p.printInvalidCommand();
                break;
            }
        }
    }
    /* --x-- LIST MENU --x-- */
    /* ----- SEARCH MENU ----- */
    private void searchMenu() throws SQLException {
        app.runMenu();
        while (app.menuIsRunning()) {
            p.printMenu2("SEARCH MENU");
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
                int id = p.promptInt("id");
                p.printMenu3("SEARCH BY ID", " Results for: " + "\""+id+"\"");
                p.printUserList(userList.searchById(id));
                break;
            }
            case "2":
            case "firstname": {
                String firstName = p.promptStringOfChars("First name");
                p.printHeading1("Users", "search by First name: \"" + firstName + "\"");
                p.printUserList(userList.searchByFirstName(firstName));
                break;
            }
            case "3":
            case "lastname": {
                String lastName = p.promptStringOfChars("Last name");
                p.printHeading1("Users", "search by Last name: \"" + lastName + "\"");
                p.printUserList(userList.searchByLastName(lastName));
                break;
            }
            case "4":
            case "email": {
                String email = p.prompt("Email");
                p.printHeading1("Users", "search by Last name: \"" + email + "\"");
                p.printUserList(userList.searchByEmail(email));
                break;
            }
            case "5":
            case "birthdate": {
                Date date = p.promptDate("Date of birth", "yyyyMMdd");
                p.printHeading1("Users", "search by Last name: \"" + date + "\"");
                p.printUserList(userList.searchByDateOfBirth(date));
                break;
            }
            case "6":
            case "membership": {
                Date date = p.promptDate("Membership", "yyyyMMdd");
                p.printHeading1("Users", "search by Membership: \"" + date + "\"");
                p.printUserList(userList.searchByMembership(date));
                break;
            }
            default: {
                p.printInvalidCommand();
                break;
            }
        }
    }
    /* --x-- SEARCH MENU --x-- */
    /* ----- ADD MENU ----- */
    private void addMenu() {
        app.runMenu();
        while (app.menuIsRunning()) {
            p.printMenu2("ADD MENU");
            addUserForm();
            app.exitMenu();
        }
    }
    private void addUserForm() {
        String firstName = p.promptStringOfChars("First name");
        String lastName = p.promptStringOfChars("Last name");
        String email = p.promptEmail("E-mail");
        Date dateOfBirth = p.promptDate("Date of Birth", "yyyyMMdd");
        addUser(new User(firstName, lastName, email, dateOfBirth));
    }
    private void addUser(User newUser) {
        p.printNewUser(newUser);
        if (!p.promptYesOrNo("Create new user?")) app.exitMenu();
        try {
            p.printAction("Creating user... ");
            boolean res = db.addUser(newUser);
            if (!res) throw new SQLException("User not created.");
            p.printSuccess("Success! ");
        } catch (SQLException e) {
            p.printDanger("Failed! ");
            if (e.getMessage().equals("[SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.email)")) {
                p.printWarning("Email already exists.");
            } else {
                p.printWarning(e.getMessage());
            }
        }
        if (p.promptYesOrNo("Add new user?")) addUserForm();
    }
    /* --x-- ADD MENU --x-- */
    /* ----- UPDATE MENU ----- */
    private void updateMenu() throws SQLException {
        p.printMenu2("UPDATE MENU");
        app.runMenu();
        int id = selectUserById();
        while (app.menuIsRunning()) {
            var user = db.getUserById(id);
            p.printUser(user);
            p.printMenu3("USER ID: " + id);
            System.out.println("""
                Update information:
                (1) First name\s
                (2) Last name
                (3) Email
                (4) Date of birth
                (5) Change user
                (0) Exit""");
            handleUpdateMenu(p.promptCommand(), user);
        }
    }
    private void handleUpdateMenu(String cmd, User user) throws SQLException {
        switch (cmd) {
            case "0":
            case "exit": {
                app.exitMenu();
                break;
            }
            case "1":
            case "firstname": {
                user.setFirstName(p.promptStringOfChars("new First name"));
                updateUser(user);
                break;
            }
            case "2":
            case "lastname": {
                user.setLastName(p.promptStringOfChars("new Last name"));
                updateUser(user);
                break;
            }
            case "3":
            case "email": {
                user.setEmail(p.promptEmail("new E-mail"));
                updateUser(user);
                break;
            }
            case "4":
            case "birthdate": {
                user.setDateOfBirth(p.promptDate("new Date of birth", "yyyyMMdd"));
                updateUser(user);
                break;
            }
            case "5":
            case "change": {
                updateMenu();
                break;
            }
            default: {
                p.printInvalidCommand();
                break;
            }
        }
    }
    private int selectUserById() {
        int id = p.promptInt("User ID");
        try {
            User user = db.getUserById(id);
            if (user.getId() == 0) throw new SQLException("No user with id: "+id+" found.");
        } catch (SQLException e) {
            p.printWarning(e.getMessage());
            if (p.promptYesOrNo("Try another ID?")) selectUserById();
            app.exitMenu();
            return -1;
        }
        return id;
    }
    private void updateUser(User user) {
        try {
            p.printAction("Updating user... ");
            if (!db.updateUser(user)) throw new SQLException("An error occurred while updating user.");
            p.printSuccess("Success!");
        } catch (SQLException e) {
            p.printDanger("Failed. ");
            p.printWarning(e.getMessage());
        }
    }
    /* --x-- UPDATE MENU --x-- */
    /* ----- DELETE MENU ----- */
    private void deleteMenu() throws SQLException {
        app.runMenu();
        while (app.menuIsRunning()) {
            p.printMenu2("DELETE MENU");
            deleteUser();
            app.exitMenu();
        }
    }
    private void deleteUser() {
        int id = p.promptInt("User ID");
        try {
            printUserToDeletedById(id);
            if (!p.promptYesOrNo("Delete user?")) return;
            p.printAction("Deleting user... ");
            boolean res = db.deleteUser(id);
            if (!res) throw new SQLException("No user with id: "+id+" found.");
            p.printSuccess("Success! User (id: "+id+") deleted.");
        } catch (SQLException e) {
            p.printDanger("Failed! ");
            p.printWarning(e.getMessage());
        }
        if (p.promptYesOrNo("Delete another user?")) deleteUser();
    }
    private void printUserToDeletedById(int id) {
        try {
            User user = db.getUserById(id);
            if (user.getId() == 0) throw new SQLException("No user with id: "+id+" found.");
            p.printUser(user);
        } catch (SQLException e){
            p.printWarning(e.getMessage());
            deleteUser();
        }
    }
    /* --x-- DELETE MENU --x-- */

}
