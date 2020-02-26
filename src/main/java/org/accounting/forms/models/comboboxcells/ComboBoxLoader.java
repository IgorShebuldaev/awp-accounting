package org.accounting.forms.models.comboboxcells;

import org.accounting.database.models.Base;

import java.util.ArrayList;
import java.util.HashMap;

public class ComboBoxLoader {
    private static ComboBoxLoader instance = null;

    private HashMap<String, ArrayList<?>> mapping = new HashMap<>();

    public static ComboBoxLoader getInstance() {
        if (instance == null) {
            instance = new ComboBoxLoader();
        }

        return instance;
    }

    public <T extends Base> ArrayList<T> getData(Class<T> model) {
        return get(model, false);
    }

    public <T extends Base> ArrayList<T> getUpdatedData(Class<T> comboBoxCell) {
        return get(comboBoxCell, true);
    }

    private <T extends Base> ArrayList<T> get(Class<T> model, boolean reload) {
        ArrayList<?> list = getComboBoxCellFor(model);

        if (list == null || reload) {

            //TODO:need new implementation method getALl
        }

        return (ArrayList<T>) list;
    }

    private <T extends Base> ArrayList<T> getComboBoxCellFor(Class<T> comboBoxCell) {
        return (ArrayList<T>) mapping.get(comboBoxCell.getSimpleName());
    }

    private <T extends Base> void setComboBoxCellFor(Class<T> comboBoxCell, ArrayList<T> list) {
        mapping.put(comboBoxCell.getSimpleName(), list);
    }
}
