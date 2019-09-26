package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class User extends Base {
    public String email;
    public String password;
    public String role;
    public int timeInProgram;

    public User(int id, String email, String password, String role, int timeInProgram) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.timeInProgram = timeInProgram;
    }

    public static ArrayList<User> getAll() {
        ArrayList<User> results = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT u.id, u.email, u.password, r.role, u.time_in_program FROM users u INNER JOIN roles r ON u.role_id = r.id";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                results.add(new User(
                    resultSet.getInt("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("role"),
                    resultSet.getInt("time_in_program")
                ));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    public static void insertData(User user) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO users VALUES(null,'%s','%s', 0, (SELECT id FROM roles WHERE role='%s'))", user.email, user.password, user.role);
            statement.execute(query);
            user.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM users WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateData(User user) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET email='%s', password='%s', time_in_program=%d, " +
                    "role_id=(SELECT id from roles where role='%s') " +
                    "where id=%d", user.email, user.password, user.timeInProgram, user.role, user.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateDataTimeInProgram(int id, String email, int timeInProgram) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET email='%s', time_in_program=%d where id=%d", email, timeInProgram, id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
