package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Position;

public class PositionComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (!selection.isPresent()) return "";

        Position position = (Position) selection.get();
        return position.getName();
    }

    @Override
    public void addRecord(Base record) {
        Position position = (Position) record;
        records.put(position.getName(), record);
    }

    public Object getElementAt(int index) {
        return ((Position)super.getElementAt(index)).getName();
    }
}
