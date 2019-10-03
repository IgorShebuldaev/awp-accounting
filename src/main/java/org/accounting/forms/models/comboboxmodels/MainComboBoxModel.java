package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;

import javax.swing.*;
import java.util.HashMap;

public abstract class MainComboBoxModel extends AbstractListModel implements ComboBoxModel {

    protected HashMap<String, Base> records = new HashMap<>();
    protected Base selection = null;

    public abstract void addRecord(Base record);

    @Override
    public void setSelectedItem(Object anItem) {
        selection = records.get(anItem);
    }

    @Override
    public int getSize() {
        return records.size();
    }

    @Override
    public Object getElementAt(int index) {
        return records.values().toArray()[index];
    }

    public Base getSelection() {
        if (selection != null) return selection;

        return null;
    }

    public void removeAllElements() {
        records.clear();
    }
}
