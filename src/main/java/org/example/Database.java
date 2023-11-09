package org.example;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private Connection connection;
    private final String DB_URL = "jdbc:sqlite:users.db";

    public Database() throws SQLException {
        connection = DriverManager.getConnection(DB_URL);
        createTablesIfNotExists();
    }

    private void createTablesIfNotExists() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY," +
                "firstName TEXT NOT NULL," +
                "lastName TEXT NOT NULL," +
                "email TEXT NOT NULL UNIQUE," +
                "dateOfBirth DATE," +
                "createdAt DATE ); ";
        stmt.execute(sql);
    }

    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (firstName, lastName, email, dateOfBirth, createdAt) VALUES(?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getFirstName());
        pstmt.setString(2, user.getLastName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, user.getDateOfBirth());
        pstmt.setDate(5, user.getCreatedAt());
        return pstmt.executeUpdate() > 0;
    }

    public ArrayList<User> getUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        return executeSelectUsersBySql(sql);
    }

    /**
     * Method for retrieving users sorted by given parameters
     * @param attribute - The column on which to sort list
     * @param order - boolean true == ASC, false == DESC
     * @return ArrayList<Users> - Users in arraylist
     * @throws SQLException - Throws exception to be handled
     */
    public ArrayList<User> getUsersSortedBy(String attribute, boolean order) throws SQLException {
        String orderString = order ? " ASC" : " DESC";
        String sql = "SELECT * FROM users ORDER BY " + attribute + orderString ;
        return executeSelectUsersBySql(sql);
    }

    private ArrayList<User> executeSelectUsersBySql(String sql) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            users.add(getUserFromResultSet(rs));
        }
        return users;
    }

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        return getUserFromResultSet(rs);
    }

    public boolean updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET firstName = ?, lastName = ?, email = ?, dateOfBirth = ? WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getFirstName());
        pstmt.setString(2, user.getLastName());
        pstmt.setString(3, user.getEmail());
        pstmt.setDate(4, user.getDateOfBirth());
        pstmt.setInt(5, user.getId());
        return pstmt.executeUpdate() > 0;
    }

    public boolean deleteUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, id);
        return pstmt.executeUpdate() > 0;
    }

    public void deleteAllUsers() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM users");
    }

    public User getUserFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String firstName = rs.getString("firstName");
        String lastName = rs.getString("lastName");
        String email = rs.getString("email");
        Date dateOfBirth = rs.getDate("dateOfBirth");
        Date createdAt = rs.getDate("createdAt");
        return new User(id, firstName, lastName, email, dateOfBirth, createdAt);
    }
}
