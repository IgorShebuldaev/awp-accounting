package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;

public class Position extends Base {
    public static ArrayList<Position> getAll() {
        ArrayList<Position> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM positions";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Position(
                   resultSet.getInt("id"),
                   resultSet.getString("name")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    private String name;

    public Position() {}

    public Position(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM positions where id = %d", id);
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

    private Position(int id, String name) {
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
        getValidator().validatePresence(name, "Position");

        return getErrors().isEmpty();
    }

    @Override
    protected String getTableName() {
        return "positions";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into positions values(null,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, name);

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update positions set " +
                "name=? where id=?");
        preparedStatement.setInt(2,id);
        preparedStatement.setString(1, name);

        return preparedStatement;
    }
}
