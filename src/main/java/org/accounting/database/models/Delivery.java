package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
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

    public static void insertDelivery(Delivery delivery) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO deliveries " +
                    "VALUES(null,'%s',(SELECT id FROM suppliers WHERE company_name='%s'),'%s','%s'," +
                    "(SELECT id FROM workers WHERE full_name='%s'))", dateFormat.format(delivery.deliveryDate), delivery.supplier, delivery.product, delivery.price, delivery.worker);
            statement.execute(query);
            delivery.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteDelivery(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM deliveries WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateDelivery(Delivery delivery) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE deliveries SET " +
                    "delivery_date='%s', supplier_id=(SELECT id FROM suppliers WHERE company_name='%s'), " +
                    "product='%s', price='%s', worker_id=(SELECT id FROM workers WHERE full_name='%s') " +
                    "WHERE id=%d", dateFormat.format(delivery.deliveryDate), delivery.supplier, delivery.product, delivery.price, delivery.worker, delivery.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
