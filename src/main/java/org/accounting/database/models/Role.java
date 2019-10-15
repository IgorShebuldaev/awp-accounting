package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;

public class Role extends Base {
    public static ArrayList<Role> getAll() {
        ArrayList<Role> results = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM roles";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Role(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("lookup_code")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    private String name;
    private String lookupCode;
    private final String ADMIN_LOOKUP_CODE = "admin";
    private final String MANAGER_LOOKUP_CODE = "manager";

    public Role() {}

    public Role(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM roles where id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.lookupCode = resultSet.getString("lookup_code");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    private Role(int id, String name, String lookupCode) {
        this.id = id;
        this.name = name;
        this.lookupCode = lookupCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getADMIN_LOOKUP_CODE() {
        return ADMIN_LOOKUP_CODE;
    }

    public boolean isValid() {
        getValidator().validatePresence(name, "Name");
        getValidator().validatePresence(lookupCode, "Lookup code");

        return getErrors().isEmpty();
    }

    public int getRoleIdByLookupCode() {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM roles where lookup_code = '%s'", MANAGER_LOOKUP_CODE);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                throw new SQLException();
            }

            this.id = resultSet.getInt("id");
            this.name = resultSet.getString("name");
            this.lookupCode = resultSet.getString("lookup_code");
        } catch (SQLException e) {
            writeLog(e);
        }

        return id;
    }

    public boolean isAdmin() {
        return lookupCode.equals(ADMIN_LOOKUP_CODE);
    }

    @Override
    protected String getTableName() {
        return "roles";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into roles values(null,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lookupCode);

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update roles set " +
                "name=?, lookup_code=? where id=?");
        preparedStatement.setInt(3,id);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, lookupCode);

        return preparedStatement;
    }
}
