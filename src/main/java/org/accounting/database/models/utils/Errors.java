package org.accounting.database.models.utils;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Errors {
    private ArrayList<String> errors = new ArrayList<>();

    public void addError(String error) {
        errors.add(error);
    }

    public boolean isAny() {
        return errors.isEmpty();
    }

    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public String fullMessages() {
        return fullMessages(", ");
    }

    public String fullMessages(CharSequence delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter);
        errors.forEach(joiner::add);

        return joiner.toString();
    }
}
