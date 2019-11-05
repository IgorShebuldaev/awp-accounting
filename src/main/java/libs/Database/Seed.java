package libs.Database;

import org.accounting.database.Database;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class Seed {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = Database.getConnection(); // validate if database is here
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String dumpFile = "src/main/resources/seed.sql";

        try {
            Load.importSQL(connection, (new FileInputStream(dumpFile)));

            System.out.println("Dump was successfully created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
