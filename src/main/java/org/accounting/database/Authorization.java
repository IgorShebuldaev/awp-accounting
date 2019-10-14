package org.accounting.database;

import org.accounting.user.CurrentUser;
import org.accounting.database.models.User;

public class Authorization {
    public boolean isAuthorized(String login, String password) {
        User user = new User(login);

        if (!user.isNewRecord() && user.getPassword().equals(password)) {
            CurrentUser.setCurrentUser(user);

            return true;
        }

        return false;
    }
}
