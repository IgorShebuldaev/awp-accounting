package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Role;

public class RoleTable extends MainTableModel {

    public RoleTable() {
        columnNames = new String[]{"Role", "Lookup Code"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Role role = (Role) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = role.role;
                break;
            case 1:
                return role.lookupCode;
        }
        return result;
    }

    @Override
    public Role getRecord(int rowIndex) {
        return (Role) super.getRecord(rowIndex);
    }
}
