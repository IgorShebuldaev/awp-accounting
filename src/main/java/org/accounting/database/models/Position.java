package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Position {
    public int id;
    public String position;

    public Position(int id, String position) {
        this.id = id;
        this.position = position;
    }

    public static ArrayList<Position> getPositions() {
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
}
