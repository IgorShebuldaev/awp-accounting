package org.accounting.database.models.utils;

import java.util.Date;
import java.util.regex.Pattern;

public class Validator {

    private Errors errors;

    public Validator(Errors errors) {
        this.errors = errors;
    }

    public void validatePresence(int value, String fieldName) {
        validatePresence(value, fieldName, String.format("%s cannot be empty", fieldName));
    }

    public void validatePresence(String value, String fieldName) {
        validatePresence(value, fieldName, String.format("%s cannot be empty", fieldName));
    }

    public void validatePresence(Date value, String fieldName) {
        validatePresence(value, fieldName, String.format("%s cannot be empty", fieldName));
    }

    public void validatePresence(int value, String fieldName, String message) {
        if (value == 0) { errors.addError(message); }
    }

    public void validatePresence(String value, String fieldName, String message) {
        if (value.isEmpty()) { errors.addError(message); }
    }

    public void validatePresence(Date value, String fieldName, String message) {
        if (value == null) { errors.addError(message); }
    }

    public void validateForeignKey(int value, String fieldName) {
        if (value <= 0) {
            errors.addError(String.format("%d is an invalid foreign key for %s", value, fieldName));
        }
    }

    public void validateEmail(String value) {
        validateMatch(value, Pattern.compile("^\\S+@\\S+$"), "Invalid email");
    }

    public void validateMatch(String value, String fieldName, Pattern pattern) {
        if (!pattern.matcher(value).matches()) {
            errors.addError(String.format("%s does not match pattern %s for %s", value, pattern.pattern(), fieldName));
        }
    }

    public void validateMatch(String value, Pattern pattern, String message) {
        if (!pattern.matcher(value).matches()) {
            errors.addError(message);
        }
    }

    public <T> void validateCustom(String message, T value,  CustomValidator<T> validator) {
        if (!validator.isValid(value)) {
            errors.addError(message);
        }
    }

    public interface CustomValidator<T> {
        public boolean isValid(T object);
    }
}
