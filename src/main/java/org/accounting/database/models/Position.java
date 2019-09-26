package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Position extends Base {
    public String position;

    public Position(int id, String position) {
        this.id = id;
        this.position = position;
    }

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

    public static void insertData(Position position) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO positions VALUES(null,'%s')", position.position);
            statement.execute(query);
            position.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateData(Position position) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE positions SET position='%s' where id=%d", position.position, position.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM positions where id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
