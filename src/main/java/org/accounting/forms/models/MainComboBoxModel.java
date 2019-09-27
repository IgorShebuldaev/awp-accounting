package org.accounting.forms.models;

import org.accounting.database.models.Base;
import org.accounting.database.models.Role;

import javax.swing.*;
import java.util.ArrayList;

public class MainComboBoxModel extends AbstractListModel implements ComboBoxModel {

    protected ArrayList<Base> records = new ArrayList<>();
    protected Base selection = null;

    @Override
    public void setSelectedItem(Object anItem) {
        Base tmp = records.stream().filter(r -> ((Role)r).role.equals((String)anItem)).findFirst().get();
        selection = tmp;
    }

    @Override
    public Object getSelectedItem() {
        selection = getSelection();
        if (selection == null) { return ""; }

        return ((Role)selection).role;
    }

    @Override
    public int getSize() {
        return records.size();
    }

    @Override
    public Object getElementAt(int index) {
        return ((Role)records.get(index)).role;
    }

    public void addRecord(Base record) {
        records.add(record);
    }

    public Base getSelection() {
        if (selection != null) return selection;

        return records.get(0);
    }
}
