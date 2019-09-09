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

    Roles(String role) {
        this.role = role;
    }

    public static ArrayList<Roles> getRoles() {
        ArrayList<Roles> arrayList = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT roles.role FROM roles";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                arrayList.add(new Roles(
                        resultSet.getString("role")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }
}
