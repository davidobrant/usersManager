package org.example;

import org.w3c.dom.ls.LSOutput;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Printer {
    Formatter f = new Formatter();
    Utilities u = new Utilities();
    Scanner scanner = new Scanner(System.in);


    /* ----- PRINT ----- */
    public void print(String string) {
        System.out.println(string);
    }
    public void print(int i) {
        System.out.println(Integer.toString(i));
    }
    public void printMenu1(String string) {
        System.out.println(f.menu1(string));
    }
    public void printMenu2(String string) {
        System.out.println(f.menu2(string));
    }
    public void printMenu3(String string) {
        System.out.println(f.menu3(string));
    }
    public void printMenu3(String title, String description) {
        System.out.println(f.menu3(title) + description);
    }

    public void printHeading1(String string) {
        System.out.println(f.heading1(string));
    }
    public void printHeading1(String heading, String description) {
        System.out.println(f.heading1(heading, description));
    }
    public void printHeading1(String heading, String description, boolean reversed) {
        printInfo(f.heading1(heading, description, reversed));
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
    public void printUserList(ArrayList<User> users) {
        users.forEach(System.out::println);
        promptEnterKey();
    }
    public void printUserList(ArrayList<User> users, boolean reversed) {
        if (reversed) users.reversed().forEach(System.out::println);
        else users.forEach(System.out::println);
        promptEnterKey();
    }
    private void printUserListInTableView(ArrayList<User> users) {
        String leftAlignFormat = "| %-15s | %-4d |%n";

        System.out.format("+-----------------+------+%n");
        System.out.format("| ID              | ID   |%n");
        System.out.format("+-----------------+------+%n");
        for (int i = 0; i < 5; i++) {
            System.out.format(leftAlignFormat, "some data" + i, i * i);
        }
        System.out.format("+-----------------+------+%n");
        for (User user : users) {
            System.out.println(user);
        }
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
        return scanner.next().trim().toLowerCase();
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
     * Will continue on "ENTER"
     * @return boolean true for yes, false for no
     */
    public boolean promptYesOrNo(){
        System.out.println("\n" + f.setColor("blue","> Continue? (Y/n): "));
        String cmd = scanner.nextLine();
        scanner.nextLine();
        return cmd.equals("yes") || cmd.equals("y") || cmd.isEmpty();
    }
    public boolean promptYesOrNo(String string){
        System.out.print("\n" + f.setColor("blue", "> " + string + " (Y/n): "));
        String cmd = scanner.next().trim().toLowerCase();
        return cmd.equals("yes") || cmd.equals("y");
    }
    /* --x-- PROMPT --x-- */

}
