package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;

public class Note extends Base {
    private String message;

    public Note() {}

    public Note(int id) throws IllegalStateException {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("select * from notes where id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);

            // TODO: I need more general version of this. It's time to write another hierarchy - finders.
            if (!resultSet.next()) {
                throw new IllegalStateException(String.format("No such record: Note(%d)", id));
            }

            this.id = resultSet.getInt("id");
            message = resultSet.getString("message");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNoteCurrentUser(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("select n.id, n.message from workers w join notes n on w.note_id = n.id where user_id=%d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            message = resultSet.getString("message");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    @Override
    protected String getTableName() {
        return "notes";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(String.format("insert into %s values()", getTableName()));
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        StringBuilder builder = new StringBuilder(String.format("update %s set ", getTableName()));
        builder.append("message = ? where id = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(builder.toString());
        preparedStatement.setString(1, message);
        preparedStatement.setInt(2, id);

        return preparedStatement;
    }
}
