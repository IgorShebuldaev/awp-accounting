package org.accounting.user;

import org.accounting.database.models.User;

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

        user.save();
    }
}
