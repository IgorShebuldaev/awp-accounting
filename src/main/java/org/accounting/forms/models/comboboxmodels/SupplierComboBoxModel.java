package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Supplier;

public class SupplierComboBoxModel extends MainComboBoxModel {
    @Override
    public void setSelectedItem(Object anItem) {
        selection = records.stream().filter(r -> ((Supplier)r).companyName.equals(anItem)).findFirst().get();
    }

    @Override
    public Object getSelectedItem() {
        return ((Supplier)super.getSelectedItem()).companyName;
    }

    public Object getElementAt(int index) {
        return ((Supplier)records.get(index)).companyName;
    }
}
