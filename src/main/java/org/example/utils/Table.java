package org.example.utils;

import org.example.User;

import java.util.ArrayList;


public class Table {

    public void printUsersInTableView(ArrayList<User> users) {
        String header = "";
        header += formatDiv("a-----b--------------b--------------b--------------------------b--------------b--------------c\n");
        header += formatRow("| ID  | First name   | Last name    | Email                    | Birthdate    | Member since |\n");
        header += formatDiv("d-----e--------------e--------------e--------------------------e--------------e--------------f\n");
        System.out.print(header);

        String rowFormat = "| %-3s | %-12s | %-12s | %-24s | %-12s | %-12s |";
        for (User user : users) {
            String id = user.getId() > 0 ? Integer.toString(user.getId()) : "n/a";
            String row = String.format(rowFormat, id, user.getFirstName(), user.getLastName(), user.getEmail(), user.getDateOfBirth(), user.getCreatedAt());
            System.out.println(formatRow(row));
        }

        System.out.println(formatDiv("g-----h--------------h--------------h--------------------------h--------------h--------------i"));
    }
    private String formatRow(String str) {
        return str.replace('|', '\u2502');
    }
    private String formatDiv(String str) {
        return str.replace('a', '\u250c')
                .replace('b', '\u252c')
                .replace('c', '\u2510')
                .replace('d', '\u251c')
                .replace('e', '\u253c')
                .replace('f', '\u2524')
                .replace('g', '\u2514')
                .replace('h', '\u2534')
                .replace('i', '\u2518')
                .replace('-', '\u2500');
    }
}
