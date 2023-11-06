package org.example;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;

public class UserList {

    Utilities u = new Utilities();
    ArrayList<User> users;

    public UserList(ArrayList<User> users) { this.users = users; }

    public ArrayList<User> sortById() {
        users.sort(Comparator.comparing(User::getId));
        return users;
    }
    public ArrayList<User> sortByFirstname() {
        users.sort(Comparator.comparing(User::getFirstName));
        return users;
    }
    public ArrayList<User> sortByLastname() {
        users.sort(Comparator.comparing(User::getLastName));
        return users;
    }
    public ArrayList<User> sortByDateOfBirth() {
        users.sort(Comparator.comparing(User::getDateOfBirth));
        return users;
    }
    public ArrayList<User> sortByMembership() {
        users.sort(Comparator.comparing(User::getCreatedAt));
        return users;
    }
    public ArrayList<User> sortByNextBirthday() {
        users.sort(Comparator.comparing(user -> u.getMMddString(user.getDateOfBirth())));
        String today = u.getMMddString(new Date(System.currentTimeMillis()));
        for (int i = 0; i < users.size() - 1; i++) {
            String birthday = u.getMMddString(users.get(i).getDateOfBirth());
            if (birthday.compareTo(today) < 0) users.add(users.remove(0));
        }
        return users;
    }

    public ArrayList<User> searchById(int id) {
        return new ArrayList<>(users.stream().filter(user -> user.getId() == id).toList());
    }
    public ArrayList<User> searchByFirstName(String firstName) {
        return new ArrayList<>(users.stream().filter(user -> user.getFirstName().equalsIgnoreCase(firstName)).toList());
    }
    public ArrayList<User> searchByLastName(String lastName) {
        return new ArrayList<>(users.stream().filter(user -> user.getLastName().equalsIgnoreCase(lastName)).toList());
    }
    public ArrayList<User> searchByEmail(String email) {
        return new ArrayList<>(users.stream().filter(user -> user.getEmail().contains(email)).toList());
    }
    public ArrayList<User> searchByDateOfBirth(Date date) {
        return new ArrayList<>(users.stream().filter(user -> user.getDateOfBirth().equals(date)).toList());
    }
    public ArrayList<User> searchByMembership(Date date) {
        return new ArrayList<>(users.stream().filter(user -> user.getCreatedAt().equals(date)).toList());
    }

}
