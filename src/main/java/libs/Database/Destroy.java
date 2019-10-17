package libs.Database;

import org.accounting.config.Config;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class Destroy {
    public static void main(String[] args) {
        try {
            Connection connection = Database.getConnectionWithoutDB();
            Properties properties = new Config().properties;

            String database = properties.getProperty("database");

            connection.createStatement().execute(String.format("drop database %s;", database));

            System.out.println("Database was successfully destroyed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
