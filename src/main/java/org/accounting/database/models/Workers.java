package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Workers {
    public String fullName;
    public Date dob;
    public String position;

    Workers(String fullName, Date dob, String position) {
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

            connection.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
        return arrayList;
    }
}
