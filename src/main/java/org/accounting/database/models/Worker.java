package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Worker {
    public int id;
    public String fullName;
    public Date dob;
    public String position;

    public Worker(int id, String fullName, Date dob, String position) {
        this.id = id;
        this.fullName = fullName;
        this.dob = dob;
        this.position = position;
    }

    public static ArrayList<Worker> getWorkers() {
        ArrayList<Worker> arrayList = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT wo.id, wo.full_name, wo.dob, po.position FROM " +
                    "workers wo INNER JOIN positions po ON wo.position_id = po.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                arrayList.add(new Worker(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getDate("dob"),
                        resultSet.getString("position")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }
}
