package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Role extends Base {
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

    public static int getRoleIdByLookupCode(String lookupCode) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("select id from roles where lookup_code = '%s'", lookupCode);
            ResultSet resultSet = statement.executeQuery(query);
            if (!resultSet.next()) {
                throw new SQLException("No records in roles table");
            }
            return resultSet.getInt("id");
        } catch (SQLException se) {
            se.printStackTrace();
            return 0;
        }
    }

    private String role;
    private String lookupCode;
    private final String ADMIN_LOOKUP_CODE = "admin";

    public Role() {}

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

    private Role(int id, String role, String lookupCode) {
        this.id = id;
        this.role = role;
        this.lookupCode = lookupCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLookupCode() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode = lookupCode;
    }

    public String getADMIN_LOOKUP_CODE() {
        return ADMIN_LOOKUP_CODE;
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO roles VALUES(null,'%s', '%s')", role, lookupCode);

            } else {
                query = String.format("UPDATE roles SET role='%s', lookup_code='%s' where id=%d", role, lookupCode, id);
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException se) {
            se.printStackTrace();

            return false;
        }

        return true;
    }

    public boolean isAdmin() {
        return lookupCode.equals(ADMIN_LOOKUP_CODE);
    }

    @Override
    protected String getTableName() {
        return "roles";
    }
}
