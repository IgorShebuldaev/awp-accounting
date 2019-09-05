package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

class Workers {
    String fullName;
    Date dob;
    String position;

    private Workers(String fullName, Date dob, String position) {
        this.fullName = fullName;
        this.dob = dob;
        this.position = position;
    }

    private ArrayList<Workers> getWorkers() {
        ArrayList<Workers> arrayList = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT wo.full_name, wo.dob, po.position FROM workers wo" +
                    "INNER JOIN positions po ON wo.position_id = po.id";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                arrayList.add(new Workers(
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
