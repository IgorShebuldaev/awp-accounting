package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Role;

public class RoleComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (selection == null) return "";

        Role role = (Role) selection;
        return role.role;
    }

    @Override
    public void addRecord(Base record) {
        Role role = (Role) record;
        records.put(role.role, record);
    }

    public Object getElementAt(int index) {
        return ((Role)super.getElementAt(index)).role;
    }
}
