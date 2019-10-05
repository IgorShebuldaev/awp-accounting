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

    private Date deliveryDate;
    private String supplier;
    private String product;
    private String price;
    private String worker;

    public Delivery() { }

    private Delivery(int id, Date deliveryDate, String supplier, String product, String price, String worker) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.supplier = supplier;
        this.product = product;
        this.price = price;
        this.worker = worker;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public boolean isValid() {
        getValidator().validatePresence(deliveryDate, "Delivery date");
        getValidator().validatePresence(supplier, "Supplier");
        getValidator().validatePresence(product, "Product");
        getValidator().validatePresence(price, "Price");
        getValidator().validatePresence(worker, "Worker");

        return getErrors().isEmpty();
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO deliveries " +
                        "VALUES(null,'%s',(SELECT id FROM suppliers WHERE company_name='%s'),'%s','%s'," +
                        "(SELECT id FROM workers WHERE full_name='%s'))",
                        dateFormat.format(deliveryDate), supplier, product, price, worker);

            } else {
                query = String.format("UPDATE deliveries SET " +
                        "delivery_date='%s', supplier_id=(SELECT id FROM suppliers WHERE company_name='%s'), " +
                        "product='%s', price='%s', worker_id=(SELECT id FROM workers WHERE full_name='%s') " +
                        "WHERE id=%d",
                        dateFormat.format(deliveryDate), supplier, product, price, worker, getId());
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException se) {
            se.printStackTrace();

            return false;
        }

        return true;
    }

    @Override
    protected String getTableName() {
        return "deliveries";
    }
}
