package org.accounting.database;

import org.accounting.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

        Properties properties = new Config().properties;

        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");
        String user = properties.getProperty("user");
        String password = properties.getProperty("pass");

        StringBuilder connectionUrl = new StringBuilder();
        connectionUrl.append("jdbc:mysql://");
        connectionUrl.append(String.format("%s:%s/", host, port));

        if (includeDatabase) {
            connectionUrl.append(database);
        }

        return connection = DriverManager.getConnection(connectionUrl.toString(), user, password);
    }

    public static Connection getConnectionWithoutDB() throws SQLException {
        includeDatabase = false;
        Connection tmp_connection = getConnection();
        includeDatabase = true;

        return tmp_connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) connection.close();
    }
}
