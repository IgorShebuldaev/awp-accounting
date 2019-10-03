package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Position;

public class PositionComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (selection == null) return "";

        Position position = (Position) selection;
        return position.position;
    }

    @Override
    public void addRecord(Base record) {
        Position position = (Position) record;
        records.put(position.position, record);
    }

    public Object getElementAt(int index) {
        return ((Position)super.getElementAt(index)).position;
    }
}
