package libs.Database;

import org.accounting.config.DbConfigReader;
import org.accounting.database.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class Destroy {
    public static void main(String[] args) {
        try {
            Connection connection = Database.getConnectionWithoutDB();
            DbConfigReader.DbConfig dbConfig = DbConfigReader.getDbConfig();

            connection.createStatement().execute(String.format("drop database %s;", dbConfig.getDatabase()));

            System.out.println("Database was successfully destroyed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
