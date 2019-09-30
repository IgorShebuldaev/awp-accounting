package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Position;

public class PositionComboBoxModel extends MainComboBoxModel {
    @Override
    public void setSelectedItem(Object anItem) {
        selection = records.stream().filter(r -> ((Position)r).position.equals(anItem)).findFirst().get();
    }

    @Override
    public Object getSelectedItem() {
        return ((Position)super.getSelectedItem()).position;
    }

    public Object getElementAt(int index) {
        return ((Position)records.get(index)).position;
    }
}
