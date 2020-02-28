package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;

public class Supplier extends Base {
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
                        resultSet.getString("name")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    private String name;

    public Supplier() {}

    public Supplier(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM suppliers where id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    private Supplier(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        getValidator().validatePresence(name, "Company name");

        return getErrors().isEmpty();
    }

    @Override
    protected String getTableName() {
        return "suppliers";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        String query = String.format("insert into %s(name) values(?)", getTableName());
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        String query = String.format("update %s set name = ? where id = ?", getTableName());
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setInt(2,id);

        return preparedStatement;
    }
}
