package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Position;

public class PositionTable extends MainTableModel {

    public PositionTable() {
        columnNames = new String[]{"Position"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Position position = (Position) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = position.position;
                break;
        }
        return result;
    }

    @Override
    public Position getRecord(int rowIndex) {
        return (Position) super.getRecord(rowIndex);
    }
}
