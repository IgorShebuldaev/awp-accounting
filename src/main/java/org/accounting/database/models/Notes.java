package org.accounting.database.models;

import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

class Notes {
   String note;

    private Notes(String note) {
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
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return arrayList;
    }
}
