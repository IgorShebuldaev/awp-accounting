package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Roles {
    public int id;
    public String role;

    public Roles(int id,String role) {
        this.id = id;
        this.role = role;
    }

    public static ArrayList<Roles> getRoles() {
        ArrayList<Roles> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT r.id, r.role FROM roles r";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Roles(
                        resultSet.getInt("id"),
                        resultSet.getString("role")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }
}
