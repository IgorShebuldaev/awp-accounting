package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;

public class User extends Base {
    public static ArrayList<User> getAll() {
        ArrayList<User> results = new ArrayList<>();
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = "SELECT id from users;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                results.add(new User(resultSet.getInt("id")));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return results;
    }

    private static PreparedStatement buildQuery(String field, Object value, Class valueClass) {
        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                String.format(
                    "SELECT u.id, u.email, u.password, u.role_id, u.time_in_program " +
                    "FROM users u WHERE u.%s = ?", field)
            );

            switch (valueClass.getSimpleName()) {
                case "String":
                    preparedStatement.setString(1, (String) value);
                    break;
                case "Integer":
                    preparedStatement.setInt(1, (int)value);
                    break;
            }

            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String email;
    private String password;
    private int roleId;
    private int timeInProgram;
    private Role role;

    public User() {}

    public User(int id) {
        try {
            PreparedStatement preparedStatement = buildQuery("id", id, Integer.class);
            assert preparedStatement != null;
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) { return; }

            setAttributes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User(String email) {
        try {
            PreparedStatement preparedStatement = buildQuery("email", email, String.class);
            assert preparedStatement != null;
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) { return; }

            setAttributes(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Role getRole() {
        if (this.role != null) {
            return role;
        }

        return this.role = new Role(this.roleId);
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setTimeInProgram(int timeInProgram) {
        this.timeInProgram = timeInProgram;
    }

    public int getTimeInProgram() {
        return this.timeInProgram;
    }

    public boolean isValid() {
        getValidator().validatePresence(email, "Email");
        getValidator().validateEmail(email);
        getValidator().validatePresence(password, "Password");
        getValidator().validateForeignKey(roleId, "Role Id");

        return getErrors().isEmpty();
    }

    private void setAttributes(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.email = resultSet.getString("email");
        this.password = resultSet.getString("password");
        this.roleId = resultSet.getInt("role_id");
        this.timeInProgram = resultSet.getInt("time_in_program");
        this.isNewRecord = false;
    }

    public String getFormattedTimeInProgram() {
        int seconds = timeInProgram % 60;
        int minutes = timeInProgram / 60 % 60;
        int days = timeInProgram / 86400;

        return String.format("%02d:%02d:%02d",
            days,
            minutes,
            seconds
        );
    }

    public int incrementTimeInProgram(Integer step) {
        return this.timeInProgram += step;
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            String query;
            if (isNewRecord()) {
                query = String.format("INSERT INTO users VALUES(null,'%s','%s', 0, %d)", email, password, roleId);

            } else {
                query = String.format("UPDATE users SET email='%s', password='%s', time_in_program=%d, " +
                    "role_id=%d where id=%d", email, password, timeInProgram, roleId, id);
            }

            statement.execute(query);

            if (isNewRecord()) {
                this.id = (int)((StatementImpl) statement).getLastInsertID();
                this.isNewRecord = false;
            }
        } catch (SQLException se) {
            se.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected String getTableName() {
        return "users";
    }
}
