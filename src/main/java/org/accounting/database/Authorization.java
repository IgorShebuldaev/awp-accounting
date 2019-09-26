package org.accounting.database;

import org.accounting.user.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authorization {

    public boolean isAuthorized(String login, String password) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT u.id, u.email, u.password, r.role, u.time_in_program " +
                    "FROM users u INNER JOIN roles r ON u.role_id = r.id WHERE email = ?");
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString(3).equals(password)){
                CurrentUser.setCurrentUser(resultSet.getInt("id"), resultSet.getString("email"),
                        resultSet.getString("role"), resultSet.getInt("time_in_program"));
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
