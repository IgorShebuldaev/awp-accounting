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
        return connection.prepareStatement("insert into notes values(null,null)", Statement.RETURN_GENERATED_KEYS);
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update notes set " +
                "note=? where id=?");
        preparedStatement.setInt(2,id);
        preparedStatement.setString(1, note);

        return preparedStatement;
    }
}
