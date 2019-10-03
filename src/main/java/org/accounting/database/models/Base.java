package org.accounting.database.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class Base {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static ArrayList<?> getAll() { return null; }

    public int id;

    public boolean isValid() {
        return id != 0;
    }

    public String toString() {
        return String.format("%s: %s", this.getClass().getSimpleName(), String.valueOf(this.id));
    }

    public class Errors {
        private ArrayList<String> errors = new ArrayList<>();

        public void addError(String error) {
            errors.add(error);
        }

        public boolean isAny() {
            return errors.isEmpty();
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
