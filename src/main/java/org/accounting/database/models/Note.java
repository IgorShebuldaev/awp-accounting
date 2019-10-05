package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Note extends Base {
    private String note;

    public Note() {}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void getNoteCurrentUser() {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT n.id, n.note FROM workers w INNER JOIN notes n on w.note_id = n.id where w.id=%d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            id = resultSet.getInt("id");
            note = resultSet.getString("note");
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNoteCurrentUser() {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE notes SET note='%s' WHERE id=(SELECT n.id FROM workers w INNER JOIN notes n on w.note_id = n.id where w.id=%d)", note, id);
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getTableName() {
        return "notes";
    }
}
