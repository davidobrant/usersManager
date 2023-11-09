package org.example;

import org.example.utils.App;
import org.example.utils.MockData;
import org.example.utils.Printer;

import java.sql.Date;
import java.sql.SQLException;

public class UI {
    App app = new App();
    private Database db;
    private final Printer p = new Printer();

    public void run() throws SQLException {
        db = new Database();
        new MockData();
        mainMenu();
    }

    /* ----- MAIN MENU ----- */
    private void mainMenu() throws SQLException {
        while (app.runMain()) {
            p.printMainMenu("MAIN MENU");
            System.out.println("""
                (1) List users\s
                (2) Search users
                (3) Add user
                (4) Update user
                (5) Delete user
                (6) Delete All
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
            case "6":
            case "deleteall": {
                deleteAllMenu();
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
            p.printSubMenu("LIST MENU");
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
                p.printHeading("USERS", "sorted by 'ID'", reversed);
                p.printUsers(userList.sortById(), reversed);
                break;
            }
            case "2": {
                p.printHeading("USERS", "sorted by 'First name'", reversed);
                p.printUsers(userList.sortByFirstname(), reversed);
                break;
            }
            case "3": {
                p.printHeading("USERS", "sorted by 'Last name'", reversed);
                p.printUsers(userList.sortByLastname(), reversed);
                break;
            }
            case "4": {
                p.printHeading("USERS", "sorted by 'Date of birth'", reversed);
                p.printUsers(userList.sortByDateOfBirth(), reversed);
                break;
            }
            case "5": {
                p.printHeading("USERS", "sorted by 'Next birthday'", reversed);
                p.printUsers(userList.sortByNextBirthday(), reversed);
                break;
            }
            case "6": {
                p.printHeading("USERS", "sorted by 'Membership'", reversed);
                p.printUsers(userList.sortByMembership(), reversed);
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
            case "3":
            case "lastname": {
                String lastName = p.promptStringOfChars("Last name");
                p.printHeading("SEARCH BY LASTNAME", " Results for: \"" + lastName + "\"");
                p.printUsers(userList.searchByLastName(lastName));
                break;
            }
            case "4":
            case "email": {
                String email = p.prompt("Email");
                p.printHeading("SEARCH BY EMAIL", " Results for: \"" + email + "\"");
                p.printUsers(userList.searchByEmail(email));
                break;
            }
            case "5":
            case "birthdate": {
                Date date = p.promptDate("Date of birth", "yyyyMMdd");
                boolean beforeOrAfter = p.promptBeforeOrAfter(date);
                String beforeOrAfterString = beforeOrAfter ? "before" : "after";
                p.printHeading("SEARCH BY BIRTHDATE", " Results for: \"" + beforeOrAfterString + " " + date + "\"");
                p.printUsers(userList.searchByDateOfBirth(date, beforeOrAfter));
                break;
            }
            case "6":
            case "membership": {
                Date date = p.promptDate("Membership", "yyyyMMdd");
                boolean beforeOrAfter = p.promptBeforeOrAfter(date);
                String beforeOrAfterString = beforeOrAfter ? "before" : "after";
                p.printHeading("SEARCH BY MEMBERSHIP", " Results for: \"" + beforeOrAfterString + " " + date + "\"");
                p.printUsers(userList.searchByMembership(date, beforeOrAfter));
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
            p.printSubMenu("ADD MENU");
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
        if (!p.promptYesOrNo("Create new user?")) {
            if (p.promptYesOrNo("Add another user?")) addUserForm();
            return;
        }
        try {
            p.printAction("Creating user... ");
            boolean res = db.addUser(newUser);
            if (!res) throw new SQLException("User not created.");
            p.printSuccess("Done! ");
        } catch (SQLException e) {
            p.printDanger("Failed! ");
            if (e.getMessage().equals("[SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.email)")) {
                p.printWarning("Email already exists.");
            } else {
                p.printWarning(e.getMessage());
            }
        }
        if (p.promptYesOrNo("Add another user?")) addUserForm();
    }
    /* --x-- ADD MENU --x-- */
    /* ----- UPDATE MENU ----- */
    private void updateMenu() throws SQLException {
        p.printSubMenu("UPDATE MENU");
        User user = selectUser();
        if (user == null) return;
        app.runMenu();
        while (app.menuIsRunning()) {
            p.printUser(user);
            p.printHeading("USER ID: " + user.getId());
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
    private User selectUser() {
        int id = p.promptInt("User ID");
        try {
            return getUserById(id);
        } catch (SQLException e) {
            p.printWarning(e.getMessage());
            if (p.promptYesOrNo("Try another ID?")) selectUser();
            return null;
        }
    }
    private User getUserById(int id) throws SQLException {
        User user = db.getUserById(id);
        if (user.getId() == 0) throw new SQLException("No user with id: "+id+" found.");
        return user;
    }
    private void updateUser(User user) {
        try {
            p.printAction("Updating user... ");
            if (!db.updateUser(user)) throw new SQLException("An error occurred while updating user.");
            p.printSuccess("Done! ");
        } catch (SQLException e) {
            p.printDanger("Failed. ");
            if (e.getMessage().equals("[SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: users.email)")) {
                p.printWarning("Email already exists.");
            } else p.printWarning(e.getMessage());
        }
    }
    /* --x-- UPDATE MENU --x-- */
    /* ----- DELETE MENU ----- */
    private void deleteMenu() {
        app.runMenu();
        while (app.menuIsRunning()) {
            p.printSubMenu("DELETE MENU");
            deleteUser();
            app.exitMenu();
        }
    }
    private void deleteUser() {
        int id = p.promptInt("User ID");
        try {
            if (!printUserById(id)) return;
            if (!p.promptYesOrNo("Delete user?")) return;
            p.printAction("Deleting user... ");
            if (!db.deleteUser(id)) throw new SQLException("Could not delete user ID: " + id);
            p.printSuccess("Done! ");
        } catch (SQLException e) {
            p.printDanger("Failed! ");
            p.printWarning(e.getMessage());
        }
        if (p.promptYesOrNo("Delete another user?")) deleteUser();
    }
    private boolean printUserById(int id) {
        try {
            User user = getUserById(id);
            p.printUser(user);
            return true;
        } catch (SQLException e){
            p.printWarning(e.getMessage());
            if (p.promptYesOrNo("Try another ID?")) deleteUser();
            return false;
        }
    }
    /* --x-- DELETE MENU --x-- */
    /* ----- DELETE ALL MENU ----- */
    private void deleteAllMenu() {
        p.printSubMenu("DELETE ALL");
        try {
            if (p.promptDeleteAll()) {
                p.printAction("Purging db... ");
                db.deleteAllUsers();
                p.printSuccess("Done!");
            } else {
                p.printWarning("\nNo action taken.");
                p.printInfo("\nReturning... ");
            }
        } catch (SQLException e) {
            p.printDanger("Failed!");
            p.printWarning("Something went wrong.");
        }
    }
    /* --x-- DELETE ALL MENU --x-- */
}
