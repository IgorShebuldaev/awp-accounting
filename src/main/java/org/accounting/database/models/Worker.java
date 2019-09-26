package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Worker extends Base {
    public String fullName;
    public Date dateOfBirth;
    public String position;

    public Worker(int id, String fullName, Date dateOfBirth, String position) {
        this.id = id;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.position = position;
    }

    public static ArrayList<Worker> getAll() {
        ArrayList<Worker> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT wo.id, wo.full_name, wo.date_of_birth, po.position FROM " +
                    "workers wo INNER JOIN positions po ON wo.position_id = po.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Worker(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("date_of_birth"),
                        resultSet.getString("position")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    public static void insertWorker(Worker worker) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO workers VALUES(null,'%s','%s',(SELECT id FROM positions where position='%s'), null)",
                    worker.fullName, dateFormat.format(worker.dateOfBirth), worker.position);
            statement.execute(query);
            worker.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteWorker(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM workers WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateWorker(Worker worker) {
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
}
