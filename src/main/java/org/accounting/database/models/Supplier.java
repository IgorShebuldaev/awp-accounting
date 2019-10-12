package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;
import org.apache.logging.log4j.LogManager;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    private String companyName;

    public Supplier() {}

    public Supplier(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("SELECT * FROM suppliers where id = %d", id);
            ResultSet resultSet = statement.executeQuery(query);

            if (!resultSet.next()) {
                return;
            }

            this.id = resultSet.getInt("id");
            this.companyName = resultSet.getString("company_name");
        } catch (SQLException e) {
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Supplier.class).error(e);
        }
    }

    private Supplier(int id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isValid() {
        getValidator().validatePresence(companyName, "Company name");

        return getErrors().isEmpty();
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO suppliers VALUES(null,'%s')", companyName);

            } else {
                query = String.format("UPDATE suppliers SET company_name='%s' WHERE id=%d", companyName, getId());
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException e) {
            getErrors().addError("Error. Contact the software developer.");
            LogManager.getLogger(Supplier.class).error(e);

            return false;
        }

        return true;
    }

    @Override
    protected String getTableName() {
        return "suppliers";
    }
}
