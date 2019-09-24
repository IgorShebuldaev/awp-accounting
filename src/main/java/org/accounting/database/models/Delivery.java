package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Delivery extends Base {
    public Date deliveryDate;
    public String supplier;
    public String product;
    public String price;
    public String worker;

    public Delivery(int id, Date deliveryDate, String supplier, String product, String price, String worker) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.supplier = supplier;
        this.product = product;
        this.price = price;
        this.worker = worker;
    }

    public static ArrayList<Delivery> getAll() {
        ArrayList<Delivery> results = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT de.id, de.delivery_date, su.company_name, de.product, de.price, wo.full_name " +
                    "FROM deliveries de " +
                    "INNER JOIN suppliers su ON de.supplier_id = su.id " +
                    "INNER JOIN workers wo ON de.worker_id = wo.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Delivery(
                        resultSet.getInt("id"),
                        resultSet.getDate("delivery_date"),
                        resultSet.getString("company_name"),
                        resultSet.getString("product"),
                        resultSet.getString("price"),
                        resultSet.getString("full_name")
                ));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }
}
