package libs.Database;

import org.accounting.config.Config;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Create {
    public static void main(String[] args) {
        try {
            Connection connection = Database.getConnectionWithoutDB();
            Properties properties = new Config().properties;

            String database = properties.getProperty("database");

            connection.createStatement().execute(String.format("create database %s;", database));

            System.out.println("Database was successfully created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
