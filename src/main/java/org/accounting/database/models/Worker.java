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

    public static void insertData(Worker worker, int userId) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO workers VALUES(null,'%s','%s',(SELECT id FROM positions where position='%s'), null, %d)",
                    worker.fullName, dateFormat.format(worker.dateOfBirth), worker.position, userId);
            statement.execute(query);
            worker.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM workers WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateData(Worker worker) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE workers SET full_name='%s', date_of_birth='%s', position_id=(SELECT id FROM positions WHERE position='%s') WHERE id=%d",
                worker.fullName, dateFormat.format(worker.dateOfBirth), worker.position, worker.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public String fullName;
    public Date dateOfBirth;
    public String position;
    public String email;

    public Worker(int id, String fullName, Date dateOfBirth, String position) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
    }

    public Worker(int id, String fullName, Date dateOfBirth, String position, String email) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
        this.email = email;
    }
}
