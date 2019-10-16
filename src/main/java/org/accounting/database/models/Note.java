package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;

public class Note extends Base {
    private String note;

    public Note() {}

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setNoteCurrentUser(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("select n.id, n.note from workers w join notes n on w.note_id = n.id where user_id=%d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            note = resultSet.getString("note");
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
        builder.append("note = ? where id = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(builder.toString());
        preparedStatement.setString(1, note);
        preparedStatement.setInt(2,id);

        return preparedStatement;
    }
}
