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
            String query = "select w.id, w.full_name, w.date_of_birth, p.position, u.email from workers w " +
            "join positions p on w.position_id = p.id " +
            "left join users u on w.user_id = u.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Worker(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getString("position"),
                        resultSet.getString("email")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    private String fullName;
    private Date dateOfBirth;
    private String position;
    private String  email;

    public Worker() {}

    private Worker(int id, String fullName, Date dateOfBirth, String position, String email) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.email = email;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isValid() {
        getValidator().validatePresence(fullName, "Full name");
        getValidator().validatePresence(dateOfBirth, "Date of birth");
        getValidator().validatePresence(position, "Position");
        getValidator().validateEmail(email);

        return getErrors().isEmpty();
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO workers VALUES(null,'%s','%s',(SELECT id FROM positions where position='%s'), null, %d)",
                        fullName, dateFormat.format(dateOfBirth), position, id);

            } else {
                query = String.format("UPDATE workers SET full_name='%s', date_of_birth='%s', position_id=(SELECT id FROM positions WHERE position='%s') WHERE id=%d",
                        fullName, dateFormat.format(dateOfBirth), position, id);
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected String getTableName() {
        return "workers";
    }
}
