package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Position;

public class PositionTable extends MainTableModel {

    public PositionTable() {
        columnNames = new String[]{"Position"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Position position = (Position) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return position.position;
        }
        return "";
    }

    @Override
    public Position getRecord(int rowIndex) {
        return (Position) super.getRecord(rowIndex);
    }
}
