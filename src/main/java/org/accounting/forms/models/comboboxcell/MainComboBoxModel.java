package org.accounting.forms.models.comboboxcell;

import org.accounting.database.models.Base;

import javax.swing.*;
import java.util.HashMap;
import java.util.Optional;

public abstract class MainComboBoxModel extends AbstractListModel implements ComboBoxModel {

    HashMap<String, Base> records = new HashMap<>();
    Optional<Base> selection = Optional.empty();

    public abstract void addRecord(Base record);

    @Override
    public void setSelectedItem(Object anItem) {
        selection = Optional.of(records.get(anItem));
        fireContentsChanged(anItem, 0, 0);
    }

    @Override
    public int getSize() {
        return records.size();
    }

    @Override
    public Object getElementAt(int index) {
        return records.values().toArray()[index];
    }

    public Optional<Base> getSelection() {
        return selection;
    }

    public void removeAllElements() {
        records.clear();
    }
}
