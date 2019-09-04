package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Notes {
   public String note;

    Notes (String note) {
        this.note = note;
    }

    private ArrayList<Notes> getNotes() {
        ArrayList<Notes> arrayList = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT notes.note FROM notes";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                arrayList.add(new Notes(
                        resultSet.getString("note")
                ));
            }

            connection.close();

        } catch (SQLException se) {
            System.out.println(se);
        }
        return arrayList;
    }
}
