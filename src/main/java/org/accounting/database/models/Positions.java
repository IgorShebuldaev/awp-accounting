package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Positions {
    public String position;

    Positions (String position) {
        this.position = position;
    }

    private ArrayList<Positions> getPositions() {
        ArrayList<Positions> arrayList = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT positions.position FROM positions";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                arrayList.add(new Positions(
                   resultSet.getString("positions")
                ));

            connection.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
        return arrayList;
    }
}
