package org.accounting.database;

import org.accounting.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
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
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, database);

        return connection = DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) connection.close();
    }
}

