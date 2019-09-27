package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Role extends Base {
    private final String ADMIN_LOOKUP_CODE = "admin";

    public static ArrayList<Role> getAll() {
        ArrayList<Role> results = new ArrayList<>();

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM roles";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Role(
                    resultSet.getInt("id"),
                    resultSet.getString("role"),
                    resultSet.getString("lookup_code")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    public static void insertData(Role role) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO roles VALUES(null,'%s', '%s')", role.role, role.lookupCode);
            statement.execute(query);
            role.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateData(Role role) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE roles SET role='%s', lookup_code='%s' where id=%d", role.role, role.lookupCode, role.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM roles where id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public String role;
    public String lookupCode;

    public Role(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM roles where id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            this.role = resultSet.getString("role");
            this.lookupCode = resultSet.getString("lookup_code");
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public Role(int id, String role, String lookupCode) {
        this.id = id;
        this.role = role;
        this.lookupCode = lookupCode;
    }

    public boolean isAdmin() {
        return lookupCode.equals(ADMIN_LOOKUP_CODE);
    }
}
