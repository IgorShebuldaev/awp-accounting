package org.accounting.forms.models;

import org.accounting.database.models.Role;

public class RoleTable extends MainTableModel {

    public RoleTable() {
        columnNames = new String[]{"Role"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Role role = (Role) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = role.role;
                break;
        }
        return result;
    }

    @Override
    public Role getRecord(int rowIndex) {
        return (Role) super.getRecord(rowIndex);
    }
}
