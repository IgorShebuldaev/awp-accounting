package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Delivery extends Base {
    public static ArrayList<Delivery> getAll() {
        ArrayList<Delivery> results = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT de.id, de.delivery_date, de.supplier_id, de.product, de.price, de.worker_id " +
                    "FROM deliveries de " +
                    "INNER JOIN suppliers su ON de.supplier_id = su.id " +
                    "INNER JOIN workers wo ON de.worker_id = wo.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Delivery(
                        resultSet.getInt("id"),
                        resultSet.getDate("delivery_date"),
                        resultSet.getInt("supplier_id"),
                        resultSet.getString("product"),
                        resultSet.getString("price"),
                        resultSet.getInt("worker_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    private Date deliveryDate;
    private int supplierId;
    private Supplier supplier;
    private String product;
    private String price;
    private int workerId;
    private Worker worker;

    public Delivery() { }

    private Delivery(int id, Date deliveryDate, int supplierId, String product, String price, int workerId) {
        this.id = id;
        this.deliveryDate = deliveryDate;
        this.supplierId = supplierId;
        this.product = product;
        this.price = price;
        this.workerId = workerId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
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

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public Supplier getSupplier() {
        if (this.supplier != null) {
            return supplier;
        }

        return this.supplier = new Supplier(this.supplierId);
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Worker getWorker() {
        if (this.worker != null) {
            return worker;
        }

        return this.worker = new Worker(this.workerId);
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public boolean isValid() {
        getValidator().validatePresence(deliveryDate, "Delivery date");
        getValidator().validatePresence(supplierId, "Supplier");
        getValidator().validatePresence(product, "Product");
        getValidator().validatePresence(price, "Price");
        getValidator().validatePresence(workerId, "Worker");

        return getErrors().isEmpty();
    }

    @Override
    protected String getTableName() {
        return "deliveries";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        String query = String.format(
                "insert into %s(delivery_date, supplier_id, product, price, worker_id) values(?, ?, ?, ?, ?);",
                getTableName());

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, dateFormat.format(deliveryDate));
        preparedStatement.setInt(2, supplierId);
        preparedStatement.setString(3, product);
        preparedStatement.setString(4, price);
        preparedStatement.setInt(5, workerId);

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        StringBuilder builder = new StringBuilder(String.format("update %s set ", getTableName()));
        builder.append("delivery_date=?, supplier_id=?, product=?, price=?, worker_id=? ");
        builder.append(String.format("where id = %d", getId()));

        PreparedStatement preparedStatement = connection.prepareStatement(builder.toString());
        preparedStatement.setString(1, dateFormat.format(deliveryDate));
        preparedStatement.setInt(2, supplierId);
        preparedStatement.setString(3, product);
        preparedStatement.setString(4, price);
        preparedStatement.setInt(5, workerId);

        return preparedStatement;
    }
}
