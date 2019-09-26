package org.accounting.database.models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class Base {
    public int id;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static ArrayList<?> getAll() { return null; }
}
