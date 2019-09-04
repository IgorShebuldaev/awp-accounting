package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Suppliers {
    public String supplier;

    Suppliers (String supplier) {
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

            connection.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
        return arrayList;
    }
}
