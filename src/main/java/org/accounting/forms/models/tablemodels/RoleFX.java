package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Role;

public class RoleFX extends AdapterFX {

    public RoleFX() {
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
            default:
                return "";
        }
    }

    @Override
    public Role getRecord(int rowIndex) {
        return (Role) super.getRecord(rowIndex);
    }
}
