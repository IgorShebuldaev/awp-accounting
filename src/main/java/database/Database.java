package database;

import java.sql.*;

public class Database{
    private static String url = "jdbc:mysql://localhost:3306/accounting";
    private static String user = "wannaasbird";
    private static String password = "db6234";

    private static Connection getDBConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }catch (ClassNotFoundException ex){
            System.out.println(ex);
        }
        return DriverManager.getConnection(url,user,password);
    }
}