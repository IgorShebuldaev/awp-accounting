package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Note extends Base {
    String note;

    private Note(String note) {
        this.note = note;
    }

    public static ArrayList<Note> getAll() {
        ArrayList<Note> arrayList = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT notes.note FROM notes";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                arrayList.add(new Note(
                        resultSet.getString("note")
                ));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }
}
