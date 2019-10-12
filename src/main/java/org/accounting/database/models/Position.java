package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;
import org.apache.logging.log4j.LogManager;

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
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Position.class).error(e);
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

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO positions VALUES(null,'%s')", name);

            } else {
                query = String.format("UPDATE positions SET name='%s' where id=%d", name, id);
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException e) {
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Position.class).error(e);

            return false;
        }

        return true;
    }

    @Override
    protected String getTableName() {
        return "positions";
    }
}
