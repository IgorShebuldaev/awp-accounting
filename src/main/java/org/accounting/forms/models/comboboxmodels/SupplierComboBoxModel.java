package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Supplier;

public class SupplierComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (!selection.isPresent()) return "";

        Supplier supplier = (Supplier) selection.get();
        return supplier.getName();
    }

    @Override
    public void addRecord(Base record) {
        Supplier supplier = (Supplier) record;
        records.put(supplier.getName(), record);
    }

    public Object getElementAt(int index) {
        return ((Supplier)super.getElementAt(index)).getName();
    }
}
