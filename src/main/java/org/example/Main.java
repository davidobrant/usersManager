package org.example;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {

        try {
            new UI().run();
        } catch (SQLException e) {
            System.out.println("Error... Something went wrong with Database");
        }
    }
}