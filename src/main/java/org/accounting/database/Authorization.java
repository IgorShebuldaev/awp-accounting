package org.accounting.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorization {
    public boolean isAuthorized(String login, String password) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement st = connection.prepareStatement("select email, password from users where email = ?");
            st.setString(1, login);
            ResultSet resultSet = st.executeQuery();

            if (resultSet.next() && resultSet.getString(2).equals(password)){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
