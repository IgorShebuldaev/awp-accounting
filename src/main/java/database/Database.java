package database;

import java.sql.*;

public class Database {
    private static String url = "jdbc:mysql://localhost:3306/accounting";
    private static String user = "wannaasbird";
    private static String password = "db6234";

    public static Connection getDBConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return DriverManager.getConnection(url, user, password);
    }

    public static boolean login(String login, String password) {
        try {
            Connection connection = Database.getDBConnection();
            PreparedStatement st = connection.prepareStatement("select email, password from users where email = ?");
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();

            if (!resultSet.next()){
                return false;
            }
            if (resultSet.getString(2).equals(password)) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }

        return false;
    }
}

