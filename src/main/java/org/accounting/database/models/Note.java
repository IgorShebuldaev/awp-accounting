package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;
import org.apache.logging.log4j.LogManager;

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

    public void createNewNotCurrentUser() {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "insert into notes values(null, null)";

            statement.execute(query);

            id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException e) {
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Note.class).error(e);
        }
    }

    public void setNoteCurrentUser(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("select w.user_id, n.id, n.note from workers w join notes n on w.note_id = n.id where user_id=%d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            note = resultSet.getString("note");
            } catch (SQLException e) {
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Note.class).error(e);
        }
    }

    public void updateNoteCurrentUser() {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("update notes set note='%s' where id=%d", note, id);
            statement.execute(query);
        } catch (SQLException e) {
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Note.class).error(e);
        }
    }

    @Override
    protected String getTableName() {
        return "notes";
    }
}
