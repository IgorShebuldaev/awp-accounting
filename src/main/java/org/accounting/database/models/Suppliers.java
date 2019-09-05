package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Suppliers {
    String supplier;

    private Suppliers(String supplier) {
        this.supplier = supplier;
    }
    private ArrayList<Suppliers> getSuppliers() {
        ArrayList<Suppliers> arrayList = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT suppliers.supplier FROM suppliers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                arrayList.add(new Suppliers(
                        resultSet.getString("supplier")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }
}
