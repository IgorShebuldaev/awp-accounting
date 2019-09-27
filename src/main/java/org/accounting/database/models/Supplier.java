package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Supplier extends Base {
    public static ArrayList<Supplier> getAll() {
        ArrayList<Supplier> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM suppliers";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Supplier(
                        resultSet.getInt("id"),
                        resultSet.getString("company_name")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    public static void insertData(Supplier supplier) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO suppliers VALUES(null,'%s')", supplier.companyName);
            statement.execute(query);
            supplier.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateData(Supplier supplier) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE suppliers SET company_name='%s' WHERE id=%d", supplier.companyName, supplier.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM suppliers WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public String companyName;

    public Supplier(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }
}
