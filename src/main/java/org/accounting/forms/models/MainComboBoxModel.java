package org.accounting.forms.models;

import javax.swing.*;

public class MainComboBoxModel extends AbstractListModel implements ComboBoxModel {

    protected String[] items;
    protected String selection = null;

    public MainComboBoxModel(String[] items) {
        this.items = items;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return selection;
    }

    @Override
    public int getSize() {
        return items.length;
    }

    @Override
    public Object getElementAt(int index) {
        return items[index];
    }
}
