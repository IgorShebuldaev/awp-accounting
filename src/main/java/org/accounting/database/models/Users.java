package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Users {
    public String email;
    public String password;
    public String role;
    public int timeInProgram;

    Users (String email, String password, String role, int timeInProgram) {
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
            String query = "SELECT users.email, users.password, roles.role, users.time_in_program FROM users INNER JOIN roles ON users.role_id = roles.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                arrayList.add(new Users(
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        resultSet.getString("role"),
                        resultSet.getInt("time_in_program")
                ));
            }
            connection.close();
        } catch (SQLException se) {
            System.out.println(se);
        }
        return arrayList;
    }
}
