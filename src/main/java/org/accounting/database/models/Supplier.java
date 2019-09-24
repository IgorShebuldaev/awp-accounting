package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Supplier extends Base {
    String supplier;

    private Supplier(String supplier) {
        this.supplier = supplier;
    }
    public static ArrayList<Supplier> getAll() {
        ArrayList<Supplier> arrayList = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT suppliers.supplier FROM suppliers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                arrayList.add(new Supplier(
                        resultSet.getString("supplier")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }
}
