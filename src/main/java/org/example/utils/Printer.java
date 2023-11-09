package org.example.utils;

import org.example.User;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Printer {
    Formatter f = new Formatter();
    Utilities u = new Utilities();
    Table t = new Table();
    Scanner scanner = new Scanner(System.in);


    /* ----- PRINT ----- */
    public void printMainMenu(String string) {
        System.out.println(f.menu1(string));
    }
    public void printSubMenu(String string) {
        System.out.println(f.menu2(string));
    }
    public void printHeading(String string) {
        System.out.println(f.menu3(string));
    }
    public void printHeading(String title, String description) {
        System.out.println(f.menu3(title) + description);
    }
    public void printHeading(String title, String description, boolean reversed) {
        System.out.println(f.menu3(title) + f.description(description, reversed));
    }

    public void printAction(String string) {
        System.out.print("\n" + string);
    }
    public void printSuccess(String string) {
        System.out.println(f.setColor("green", string));
    }
    public void printInfo(String string) {
        System.out.println(f.setColor("cyan", string));
    }
    public void printWarning(String string) {
        System.out.println(f.setColor("yellow", string));
    }
    public void printDanger(String string) {
        System.out.println(f.setColor("red", string));
    }

    public void printInvalidCommand() {
        printDanger("\nInvalid command...");
    }

    public void printNewUser(User user) {
        printInfo("\nFirstname: " + user.getFirstName() + "\t" +
                "Lastname: " + user.getLastName() + "\t" +
                "Email: " + user.getEmail() + "\t" +
                "Date of birth: " + user.getDateOfBirth()
        );
    }
    public void printUser(User user) {
        printInfo("\nID:" + user.getId() + "\t" +
                "Firstname: " + user.getFirstName() + "\t" +
                "Lastname: " + user.getLastName() + "\t" +
                "Email: " + user.getEmail() + "\t" +
                "Date of birth: " + user.getDateOfBirth() + "\t" +
                "Member since: " + user.getCreatedAt()
        );
    }
    public void printUsers(ArrayList<User> users) {
        if (users.isEmpty()) printNoUsersMessage();
        else t.printUsersInTableView(users);
        promptEnterKey();
    }
    public void printUsers(ArrayList<User> users, boolean reversed) {
        if (users.isEmpty()) printNoUsersMessage();
        else {
            if (reversed) t.printUsersInTableView(new ArrayList<>(users.reversed()));
            else t.printUsersInTableView(users);
        }
        promptEnterKey();
    }
    private void printNoUsersMessage() {
        printInfo("\nNo users found...");
    }
    /* --x-- PRINT --x-- */
    /* ----- PROMPT ----- */
    public String prompt(String string) {
        System.out.print(f.prompt(string));
        return scanner.next();
    }
    /**
     * Prompts user for String consisting of only characters
     * @param string - Requested variable name
     * @return input string from user
     */
    public String promptStringOfChars(String string) {
        try {
            System.out.print(f.prompt(string));
            String input = scanner.next();
            if (!input.matches(u.REGEX_CHARS)) throw new InputMismatchException();
            return input;
        } catch (InputMismatchException e) {
            printWarning("Only characters allowed. Please try again!");
            scanner.nextLine();
            return promptStringOfChars(string);
        }
    }
    /**
     * Prompts user for valid email
     * @return input email from user
     */
    public String promptEmail(String string) {
        try {
            System.out.print(f.prompt(string));
            String input = scanner.next();
            if (input.length() < 9 || !input.matches(u.REGEX_EMAIL)) throw new InputMismatchException();
            return input;
        } catch (InputMismatchException e) {
            printWarning("Not valid email. Please try again!");
            scanner.nextLine();
            return promptEmail(string);
        }
    }
    public Date promptDate(String string, String format) {
        try {
            System.out.print(f.prompt(string+" ("+format+")"));
            String input = scanner.next().trim().replace("-", "");
            u.validateDate(input, format);
            return u.createSqlDate(input);
        } catch (InputMismatchException | ParseException e) {
            if (e.getMessage() == null) {
                printInfo("Valid date must be " + format.length() + " digits (" + format + ")");
            } else {
                printWarning(e.getMessage());
            }
            scanner.nextLine();
            return promptDate(string, format);
        }
    }
    public boolean promptBeforeOrAfter(Date date) {
        System.out.print(f.prompt("search for results BEFORE or AFTER \"" + date.toString() + "\"? (before/after)"));
        String res = scanner.next();
        return res.startsWith("b");
    }

    /**
     * Prompts user for parameter named <string> and return the value of input.
     * Validates that input is of type 'int' and runs again is input not valid.
     *
     * @param string - Describing name of requested variable. Ex "User ID".
     * @return int - The value of input from user.
     */
    public int promptInt(String string) {
        try {
            System.out.print(f.prompt(string));
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            printWarning("Only digits allowed. Please try again!");
            scanner.nextLine();
            return promptInt(string);
        }
    }
    public String promptCommand() {
        System.out.print(f.prompt("command"));
        return scanner.next().toLowerCase();
    }
    /**
     * Prompts user to continue on "ENTER"
     * Used for staying on a results page
     */
    public void promptEnterKey(){
        scanner.nextLine();
        System.out.print("\nPress \"" + f.setColor("blue","ENTER") + "\" to continue...");
        scanner.nextLine();
    }
    /**
     * Prompts user to 'Continue? (Y/n)'
     * Will not continue on "ENTER"
     * @return boolean true for yes, false for no
     */
    public boolean promptYesOrNo(String string){
        System.out.print("\n" + f.setColor("blue", "> " + string + " (y/n): "));
        return scanner.next().startsWith("y");
    }
    public boolean promptDeleteAll() {
        System.out.print("\n" + f.setColor("yellow", "DELETE ALL USERS? (irreversible)") + f.setColor("red","\n> Enter \"deleteall\": "));
        return scanner.next().equalsIgnoreCase("deleteall");
    }
    /* --x-- PROMPT --x-- */
}
