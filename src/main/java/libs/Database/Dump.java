package libs.Database;

import org.accounting.config.Config;
import org.accounting.database.Database;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

public class Dump {
    public static void main(String[] args) {
        try {
            Database.getConnection(); // validate if database is here
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        Properties properties = new Config().properties;
        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");
        String user = properties.getProperty("user");
        String password = properties.getProperty("pass");

        String dumpFile = "src/main/resources/database_schema.sql";

        Runtime rt = Runtime.getRuntime();
        String command = String.format("mysqldump --no-data -h%s -u %s -p%s -P%s %s",
            host, user, password, port, database);
        try {
            Process p = rt.exec(command);

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new FileWriter(dumpFile));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.write("\n");
            }
            writer.flush();

            reader.close();
            writer.close();

            p.destroy();

            System.out.println("Dump was successfully created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
