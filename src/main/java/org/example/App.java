package org.example;

public class App {

    Printer p = new Printer();
    private boolean run = true;
    private boolean runMenu;
    private boolean runSubMenu;

    public boolean runMain() {
        return run;
    }
    public void exit() {
        p.printInfo("\nExiting program...");
        run = false;
    }
    public void runMenu() {
        runMenu = true;
    }
    public boolean menuIsRunning() {
        return runMenu;
    }
    public void exitMenu() {
        p.printInfo("\nReturning...");
        runMenu = false;
    }
    public void runSubMenu() {
        runSubMenu = true;
    }
    public boolean subMenuIsRunning() {
        return runSubMenu;
    }
    public void exitSubMenu() {
        runSubMenu = false;
    }

}
