package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Role {
    public int id;
    public String role;

    public Role(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public static ArrayList<Role> getRoles() {
        ArrayList<Role> results = new ArrayList<>();
        try{
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT r.id, r.role FROM roles r";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next())
                results.add(new Role(
                        resultSet.getInt("id"),
                        resultSet.getString("role")
                ));
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    public static void insertRole(Role role) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("INSERT INTO roles VALUES(null,'%s')", role.role);
            statement.execute(query);
            role.id = (int)((StatementImpl) statement).getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void updateRole(Role role) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE roles SET role='%s' where id=%d", role.role, role.id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteRole(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM roles where id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
