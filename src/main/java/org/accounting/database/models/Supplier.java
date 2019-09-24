package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Supplier extends Base {
    public String supplier;

    public Supplier(int id, String supplier) {
        this.id = id;
        this.supplier = supplier;
    }
    public static ArrayList<Supplier> getAll() {
        ArrayList<Supplier> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM suppliers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("supplier")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }
}
