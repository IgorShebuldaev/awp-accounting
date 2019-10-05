package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Role;

public class RoleComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (!selection.isPresent()) return "";

        Role role = (Role) selection.get();
        return role.getRole();
    }

    @Override
    public void addRecord(Base record) {
        Role role = (Role) record;
        records.put(role.getRole(), record);
    }

    public Object getElementAt(int index) {
        return ((Role)super.getElementAt(index)).getRole();
    }
}
