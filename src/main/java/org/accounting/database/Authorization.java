package org.accounting.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorization {
    public boolean isAuthorized(String login, String password) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select email, password from users where email = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString(2).equals(password)){
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
