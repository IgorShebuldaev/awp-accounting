package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private int positionId;
    private int noteID;
    private int userId;
    private Position position;
    private Note note;
    private User user;

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
            this.positionId = resultSet.getInt("position_id");
            this.userId = resultSet.getInt("user_id");
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    private Worker(int id, String fullName, Date dateOfBirth, int positionId, int noteID, int userId) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.positionId = positionId;
        this.noteID = noteID;
        this.userId = userId;
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

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public Position getPosition() {
        if (this.position != null) {
            return position;
        }

        return this.position = new Position(this.positionId);
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
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public User getUser() {
        if (this.user != null) {
            return user;
        }

        return this.user = new User(this.userId);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isValid() {
        getValidator().validatePresence(fullName, "Full name");
        getValidator().validatePresence(dateOfBirth, "Date of birth");
        getValidator().validatePresence(positionId, "Position");

        return getErrors().isEmpty();
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                setNoteID(new Note().save().getId());
                query = String.format("INSERT INTO workers VALUES(null,'%s','%s',%d, %d, %d)",
                        fullName, dateFormat.format(dateOfBirth), positionId, noteID, userId);
            } else {
                query = String.format("UPDATE workers SET full_name='%s', date_of_birth='%s', position_id=%d WHERE id=%d",
                        fullName, dateFormat.format(dateOfBirth), positionId, id);
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException e) {
            writeLog(e);

            return false;
        }

        return true;
    }

    @Override
    protected String getTableName() {
        return "workers";
    }
}
