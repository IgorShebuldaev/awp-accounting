package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Role;

import javax.swing.*;
import java.util.ArrayList;

public class MainComboBoxModel extends AbstractListModel implements ComboBoxModel {

    private ArrayList<Base> records = new ArrayList<>();
    private Base selection = null;

    @Override
    public void setSelectedItem(Object anItem) {
        selection = records.stream().filter(r -> ((Role)r).role.equals(anItem)).findFirst().get();
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
