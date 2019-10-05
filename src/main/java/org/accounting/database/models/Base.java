package org.accounting.database.models;

import org.accounting.database.Database;
import org.accounting.database.models.utils.Errors;
import org.accounting.database.models.utils.Validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class  Base {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    protected abstract String getTableName();

    public static ArrayList<?> getAll() { return null; }
    protected Integer id;
    protected boolean isNewRecord;
    protected Errors errors;
    protected Validator validator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Errors getErrors() {
        if (errors == null) { errors = new Errors(); }

        return errors;
    }

    protected Validator getValidator() {
        if (validator == null) { validator = new Validator(getErrors()); };

        return validator;
    }

    public boolean isNewRecord() {
        return this.getId() == null || this.isNewRecord;
    }

    public boolean isValid() {
        if (isNewRecord()) { return true; }

        getValidator().validateCustom("Incorrect id", getId(), new Validator.CustomValidator<Integer>() {
            @Override
            public boolean isValid(Integer object) {
                return object > 0;
            }
        });

        return getErrors().isEmpty();
    }

    public void delete() {
        try {
            Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();
            String query = String.format("DELETE FROM %s WHERE id=%d", getTableName(), getId());
            statement.execute(query);
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public String toString() {
        return String.format("%s: %s", this.getClass().getSimpleName(), String.valueOf(this.getId()));
    }
}
