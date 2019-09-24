package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Note extends Base {
    public String note;

    public Note(int id, String note) {
        this.id = id;
        this.note = note;
    }

    public static ArrayList<Note> getAll() {
        ArrayList<Note> results = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM notes";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results.add(new Note(
                        resultSet.getInt("id"),
                        resultSet.getString("note")
                ));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }
}
