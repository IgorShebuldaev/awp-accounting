package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Position;

public class PositionFX extends AdapterFX {

    public PositionFX() {
        columnNames = new String[]{"Position"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Position position = (Position) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return position.getName();
            default:
                return "";
        }
    }

    @Override
    public Position getRecord(int rowIndex) {
        return (Position) super.getRecord(rowIndex);
    }
}
