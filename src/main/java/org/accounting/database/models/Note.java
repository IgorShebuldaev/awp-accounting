package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Note extends Base {
    public String note;

    public Note(int id, String note) {
        this.id = id;
        this.note = note;
    }

    public static Note getNoteCurrentUser(int id) {
        Note results = null;
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT n.id, n.note FROM workers w INNER JOIN notes n on w.note_id = n.id where w.id=%d", id);
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                results = new Note(
                        resultSet.getInt("id"),
                        resultSet.getString("note")
                );
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    public static void updateNoteCurrentUser(int id, String note) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE notes SET note='%s' WHERE id=(SELECT n.id FROM workers w INNER JOIN notes n on w.note_id = n.id where w.id=%d)", note, id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
