package org.accounting.forms.models.comboboxcell;

import org.accounting.database.models.Base;
import org.accounting.database.models.Role;

public class RoleComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (!selection.isPresent()) return "";

        Role role = (Role) selection.get();
        return role.getName();
    }

    @Override
    public void addRecord(Base record) {
        Role role = (Role) record;
        records.put(role.getName(), record);
    }

    public Object getElementAt(int index) {
        return ((Role)super.getElementAt(index)).getName();
    }
}
