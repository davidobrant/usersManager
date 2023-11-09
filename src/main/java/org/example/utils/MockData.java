package org.example.utils;

import org.example.Database;
import org.example.User;

import java.sql.SQLException;

public class MockData {
    Database db = new Database();
    Utilities u = new Utilities();
    Printer p = new Printer();

    public MockData() throws SQLException {
        if(db.getUsers().isEmpty()) {
            if(p.promptYesOrNo("No users in DB. Populate DB with dummy data?")) populateDb();
        };
    }

    public void populateDb() throws SQLException {
        db.addUser(new User("Karl", "XII", "kalle12@kula.ex", u.createSqlDate("16820617")));
        db.addUser(new User("Gustav", "II Adolf", "gurra2@grodan.ex", u.createSqlDate("15941209")));
        db.addUser(new User("Kristina", "Augusta", "kicki@vatikanen.ex", u.createSqlDate("16261218")));
        db.addUser(new User("Gustav", "Vasa", "gurkan@monostav.ex", u.createSqlDate("14960512")));
        db.addUser(new User("Erik", "XIV", "ricken@isoppan.ex", u.createSqlDate("15331213")));
        db.addUser(new User("Karin", "Månsdotter", "kankan@asteroiden.ex", u.createSqlDate("15501106")));
        db.addUser(new User("Victoria", "Bernadotte", "vickie@framtiden.ex", u.createSqlDate("19770714")));
        db.addUser(new User("Margareta", "Eriksdotter", "maggan@leijonhufvud.ex", u.createSqlDate("15160101")));
        db.addUser(new User("Sigismund", "III Vasa", "siggen@gripsholm.ex", u.createSqlDate("15660620")));
    }
}
