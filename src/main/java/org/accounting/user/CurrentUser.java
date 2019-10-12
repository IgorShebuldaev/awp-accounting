package org.accounting.user;

import org.accounting.database.Database;
import org.accounting.database.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CurrentUser {
    private static User user;

    public static void setCurrentUser(int id){
        CurrentUser.user = new User(id);
    }

    public static void setCurrentUser(User user) {
        CurrentUser.user = user;
    }

    public static User getUser() {
        return CurrentUser.user;
    }

    public static void updateDataTimeInProgram() {
        if (CurrentUser.user == null) {
            return;
        }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET time_in_program=%d where id=%d", user.getTimeInProgram(), user.getId());
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
