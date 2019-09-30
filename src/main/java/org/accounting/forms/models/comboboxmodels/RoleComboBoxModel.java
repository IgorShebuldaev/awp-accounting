package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Role;

public class RoleComboBoxModel extends MainComboBoxModel {

    @Override
    public void setSelectedItem(Object anItem) {
        selection = records.stream().filter(r -> ((Role)r).role.equals(anItem)).findFirst().get();
    }

    @Override
    public Object getSelectedItem() {
        return ((Role)super.getSelectedItem()).role;
    }

    public Object getElementAt(int index) {
        return ((Role)records.get(index)).role;
    }
}
