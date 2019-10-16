package org.accounting.database.models;

import com.mysql.cj.jdbc.StatementImpl;
import org.apache.logging.log4j.LogManager;

import org.accounting.database.Database;
import org.accounting.database.models.utils.Errors;
import org.accounting.database.models.utils.Validator;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class  Base {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static ArrayList<?> getAll() { return null; }

    protected Integer id;
    private boolean isNewRecord;
    private Errors errors;
    private Validator validator;

    public Integer getId() {
        return id;
    }

    public Errors getErrors() {
        if (errors == null) { errors = new Errors(); }

        return errors;
    }

    public boolean isNewRecord() {
        return this.getId() == null || this.isNewRecord;
    }

    public boolean isValid() {
        if (isNewRecord()) { return true; }

        getValidator().validateCustom("Incorrect id", getId(), object -> object > 0);

        return getErrors().isEmpty();
    }

    public boolean save() {
        if (!isValid()) { return false; }

        try {
            Connection connection = Database.getConnection();
            PreparedStatement preparedStatement;

            if (isNewRecord()) {
                beforeCreate();
                preparedStatement = getInsertStatement(connection);
            } else {
                beforeUpdate();
                preparedStatement = getUpdateStatement(connection);
            }
            beforeSave();
            preparedStatement.execute();

            if (isNewRecord()) {
                setId((int)((StatementImpl) preparedStatement).getLastInsertID());
            }
        } catch (SQLException e) {
            writeLog(e);

            return false;
        }

        return true;
    }

    public void delete() {
        beforeDelete();

        if (isNewRecord()) { return; }

        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM %s WHERE id=%d", getTableName(), getId());
            statement.execute(query);
        } catch (SQLException e) {
            writeLog(e);
        }
    }

    Validator getValidator() {
        if (validator == null) { validator = new Validator(getErrors()); };

        return validator;
    }

    protected abstract String getTableName();
    protected abstract PreparedStatement getInsertStatement(Connection connection) throws SQLException;
    protected abstract PreparedStatement getUpdateStatement(Connection connection) throws SQLException;

    void beforeCreate() {}
    void beforeUpdate() {}
    void beforeSave() {}
    void beforeDelete() {}

    void writeLog(SQLException e) {
        getErrors().addError("Error. Contact the software developer.");
        LogManager.getLogger(this.getClass()).error(e);
    }

    // TODO: Make it completely(!) private. Deal with User.java call
    void setId(Integer id) {
        this.id = id;
        this.isNewRecord = false;
    }

    public String toString() {
        return String.format("%s: %s", this.getClass().getSimpleName(), String.valueOf(this.getId()));
    }
}
