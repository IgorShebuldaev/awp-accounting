package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.accounting.database.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class User extends Base {
    private Errors errors;

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

    public static void insertData(User user) {
        try {
            Connection connection = Database.getConnection();
            StatementImpl statement = (StatementImpl)connection.createStatement();
            String query = String.format("INSERT INTO users VALUES(null,'%s','%s', 0, %d)", user.email, user.password, user.roleId);
            statement.execute(query);
            user.id = (int) statement.getLastInsertID();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public static void deleteData(int id) {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM users WHERE id=%d", id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
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

    public String email;
    public String password;
    public int roleId;
    public int timeInProgram;
    private Role role;

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

    public User(int id, String email, String password, int role_id, int timeInProgram) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roleId = role_id;
        this.timeInProgram = timeInProgram;
    }

    public Role getRole() {
        if (this.role != null) {
            return role;
        }

        return this.role = new Role(this.roleId);
    }

    public boolean save() {
        if (!isPositive()) { return false; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("UPDATE users SET email='%s', password='%s', time_in_program=%d, " +
                "role_id=%d where id=%d", email, password, timeInProgram, roleId, id);
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return true;
    }

    public String getTimeInProgram() {
        int seconds = timeInProgram % 60;
        int minutes = timeInProgram / 60 % 60;
        int days = timeInProgram / 86400;

        return String.format("%02d:%02d:%02d",
            days,
            minutes,
            seconds
        );
    }

    public boolean isPositive() {
        boolean tmp = super.isValid();

        if (email.isEmpty()) {
           getErrors().addError("Email cannot be empty!");
        }

        return getErrors().isAny();
    }

    public Errors getErrors() {
        if (errors == null) { errors = new Errors(); }

        return errors;
    }

    private void setAttributes(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.email = resultSet.getString("email");
        this.password = resultSet.getString("password");
        this.roleId = resultSet.getInt("role_id");
        this.timeInProgram = resultSet.getInt("time_in_program");
    }

    public class Errors {
        private ArrayList<String> errors = new ArrayList<>();

        public void addError(String error) {
            errors.add(error);
        }

        public boolean isAny() {
            return !errors.isEmpty();
        }

        public String fullMessages() {
            return errors.stream().collect(Collectors.joining(new CharSequence() {
                @Override
                public int length() {
                    return 1;
                }

                @Override
                public char charAt(int i) {
                    return ',';
                }

                @Override
                public CharSequence subSequence(int i, int i1) {
                    return null;
                }
            }));
        }

    }
}
