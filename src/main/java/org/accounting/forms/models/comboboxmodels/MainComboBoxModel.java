package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;

import javax.swing.*;
import java.util.ArrayList;

public abstract class MainComboBoxModel extends AbstractListModel implements ComboBoxModel {

    protected ArrayList<Base> records = new ArrayList<>();
    protected Base selection = null;

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (Base) anItem;
    }

    @Override
    public Object getSelectedItem() {
        selection = getSelection();
        if (selection == null) { return ""; }

        return selection;
    }

    @Override
    public int getSize() {
        return records.size();
    }

    @Override
    public Object getElementAt(int index) {
        return records.get(index);
    }

    public void addRecord(Base record) {
        records.add(record);
    }

    public Base getSelection() {
        if (selection != null) return selection;

        return records.get(0);
    }

    public void removeAllElements() {
        records.clear();
    }
}
