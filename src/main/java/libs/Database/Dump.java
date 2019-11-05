package libs.Database;

import org.accounting.config.DbConfigReader;
import org.accounting.database.Database;

import java.io.*;
import java.sql.SQLException;

public class Dump {
    public static void main(String[] args) {
        try {
            Database.getConnection(); // validate if database is here
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        DbConfigReader.DbConfig dbConfig = DbConfigReader.getDbConfig();

        String dumpFile = "src/main/resources/database_schema.sql";

        Runtime rt = Runtime.getRuntime();
        String command = String.format("mysqldump --no-data -h%s -u %s -p%s -P%s %s",
                dbConfig.getHost(), dbConfig.getUser(), dbConfig.getPassword(), dbConfig.getPort(), dbConfig.getDatabase());
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
