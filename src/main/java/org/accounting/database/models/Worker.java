package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Worker extends Base {
    public static ArrayList<Worker> getAll() {
        ArrayList<Worker> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "select * from workers w " +
            "join positions p on w.position_id = p.id " +
            "left join users u on w.user_id = u.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Worker(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getInt("position_id"),
                        resultSet.getInt("note_id"),
                        resultSet.getInt("user_id")
                ));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private String fullName;
    private Date dateOfBirth;
    private int positionID;
    private Integer noteID;
    private int userID;
    private Position position;
    private Note note;
    private User user;

    @Override
    void beforeCreate() {
        Note note = new Note();
        note.save();
        setNote(note);
        noteID = note.getId();

        getUser().save();
        userID = getUser().getId();
    }

    @Override
    void beforeDelete() {
        getUser().delete();
        getNote().delete();
    }

    public Worker() {}

    public Worker(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM workers where id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            this.fullName = resultSet.getString("full_name");
            this.dateOfBirth = resultSet.getDate("date_of_birth");
            this.positionID = resultSet.getInt("position_id");
            this.userID = resultSet.getInt("user_id");
            this.noteID = resultSet.getInt("note_id");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    private Worker(int id, String fullName, Date dateOfBirth, int positionID, int noteID, int userID) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.positionID = positionID;
        this.noteID = noteID;
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public Position getPosition() {
        if (this.position != null) {
            return position;
        }

        return this.position = new Position(this.positionID);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public Note getNote() {
        if (note != null) {
            return note;
        }
        try {
        return note = new Note(noteID);
        } catch (IllegalStateException e) {
            System.exit(2);
            return null;
        }
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public User getUser() {
        if (this.user != null) {
            return user;
        }

        return this.user = new User(this.userID);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        getValidator().validatePresence(fullName, "Full name");
        getValidator().validatePresence(dateOfBirth, "Date of birth");
        getValidator().validatePresence(positionID, "Position");

        return getErrors().isEmpty();
    }

    @Override
    protected String getTableName() {
        return "workers";
    }

    @Override
    protected PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        String query = String.format("insert into %s(full_name, date_of_birth, position_id, note_id, user_id) values(?,?,?,?,?);", getTableName());

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, fullName);
        preparedStatement.setString(2, dateFormat.format(dateOfBirth));
        preparedStatement.setInt(3, positionID);
        preparedStatement.setInt(4, noteID);
        preparedStatement.setInt(5, userID);

        return preparedStatement;
    }

    @Override
    protected PreparedStatement getUpdateStatement(Connection connection) throws SQLException {
        StringBuilder builder = new StringBuilder(String.format("update %s set ", getTableName()));
        builder.append("full_name = ?, date_of_birth = ?, position_id = ?, note_id = ? where id = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(builder.toString());
        preparedStatement.setString(1, fullName);
        preparedStatement.setString(2, dateFormat.format(dateOfBirth));
        preparedStatement.setInt(3, positionID);
        preparedStatement.setInt(4, noteID);
        preparedStatement.setInt(5, id);

        return preparedStatement;
    }
}
