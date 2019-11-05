package org.accounting.database;

import org.accounting.config.DbConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static boolean includeDatabase = true;
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection != null) return connection;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        DbConfigReader.DbConfig config = DbConfigReader.getDbConfig();

        StringBuilder connectionUrl = new StringBuilder();
        connectionUrl.append("jdbc:mysql://");
        connectionUrl.append(String.format("%s:%s/", config.getHost(), config.getPort()));

        if (includeDatabase) {
            connectionUrl.append(config.getDatabase());
        }

        return connection = DriverManager.getConnection(connectionUrl.toString(), config.getUser(), config.getPassword());
    }

    public static Connection getConnectionWithoutDB() throws SQLException {
        includeDatabase = false;
        Connection tmp_connection = getConnection();
        includeDatabase = true;

        return tmp_connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
