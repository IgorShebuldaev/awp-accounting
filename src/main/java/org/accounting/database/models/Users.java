package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Users {
    public int id;
    public String email;
    public String password;
    public String role;
    public int timeInProgram;

     public Users(String email, String password, String role, int timeInProgram) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.timeInProgram = timeInProgram;
    }

    public static ArrayList<Users> getUsers() {
        ArrayList<Users> arrayList = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT u.email, u.password, r.role, u.time_in_program FROM users u INNER JOIN roles r ON u.role_id = r.id";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                arrayList.add(new Users(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getInt("time_in_program")
                ));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }

    public static void insertUsers(Users users) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO users VALUES(null,'%s','%s', 0, (SELECT id from roles where role='%s'))", users.email, users.password, users.role);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteUsers(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM users WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateUsers(Users users) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET email='%s', password='%s', role_id=(SELECT id from roles where role='%s') where id='%d'", users.email, users.password, users.role, users.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }




}
