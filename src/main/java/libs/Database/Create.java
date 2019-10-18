package libs.Database;

import org.accounting.config.DbConfigReader;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class Create {
    public static void main(String[] args) {
        try {
            Connection connection = Database.getConnectionWithoutDB();
            DbConfigReader.DbConfig dbConfig = DbConfigReader.getDbConfig();

            connection.createStatement().execute(String.format("create database %s;", dbConfig.getDatabase()));

            System.out.println("Database was successfully created!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
