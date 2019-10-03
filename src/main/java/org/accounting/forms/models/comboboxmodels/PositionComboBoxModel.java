package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Position;

public class PositionComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (selection == null) return "";

        Position p = (Position) selection;
        return p.position;
    }

    @Override
    public void addRecord(Base record) {
        Position p = (Position) record;
        records.put(p.position, record);
    }

    public Object getElementAt(int index) {
        return ((Position)super.getElementAt(index)).position;
    }
}
