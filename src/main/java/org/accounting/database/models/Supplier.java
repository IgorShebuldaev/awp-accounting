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
                        resultSet.getString("company_name")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    private String companyName;

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
            this.companyName = resultSet.getString("company_name");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    private Supplier(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isValid() {
        getValidator().validatePresence(companyName, "Company name");

        return getErrors().isEmpty();
    }

    @Override
    protected String getTableName() {
        return "suppliers";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        String query = String.format("insert into %s(company_name) values(?)", getTableName());
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, companyName);

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        String query = String.format("update %s set company_name = ? where id = ?", getTableName());
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, companyName);
        preparedStatement.setInt(2,id);

        return preparedStatement;
    }
}
