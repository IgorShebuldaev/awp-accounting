package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                   resultSet.getString("position")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    private String position;

    public Position() {}

    private Position(int id, String position) {
        this.id = id;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO positions VALUES(null,'%s')", position);

            } else {
                query = String.format("UPDATE positions SET position='%s' where id=%d", position, id);
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
        return "positions";
    }
}
