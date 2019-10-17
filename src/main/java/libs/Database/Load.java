package libs.Database;

import org.accounting.database.Database;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Load {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = Database.getConnection(); // validate if database is here
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String dumpFile = "src/main/resources/database_schema.sql";

        try {
            importSQL(connection, (new FileInputStream(dumpFile)));

            System.out.println("Dump was successfully created!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void importSQL(Connection conn, InputStream in) throws SQLException {
        Scanner s = new Scanner(in);
        s.useDelimiter("(;(\r)?\n)|(--\n)");
        try (Statement st = conn.createStatement()) {
            while (s.hasNext()) {
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")) {
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }

                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        }
    }
}

