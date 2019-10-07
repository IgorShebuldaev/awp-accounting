package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Role;

public class RoleTable extends MainTableModel {

    public RoleTable() {
        columnNames = new String[]{"Role", "Lookup Code"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Role role = (Role) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return role.getName();
            case 1:
                return role.getLookupCode();
        }
        return "";
    }

    @Override
    public Role getRecord(int rowIndex) {
        return (Role) super.getRecord(rowIndex);
    }
}
