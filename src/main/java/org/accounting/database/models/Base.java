package org.accounting.database.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
}
