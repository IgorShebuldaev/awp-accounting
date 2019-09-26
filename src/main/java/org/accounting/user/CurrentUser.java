package org.accounting.user;

public class CurrentUser {
    public static int id;
    public static String email;
    public static String role;
    public static int timeInProgram;

    public static void setCurrentUser(int id, String email, String role, int timeInProgram){
        CurrentUser.id = id;
        CurrentUser.email = email;
        CurrentUser.role = role;
        CurrentUser.timeInProgram = timeInProgram;
    }
}
