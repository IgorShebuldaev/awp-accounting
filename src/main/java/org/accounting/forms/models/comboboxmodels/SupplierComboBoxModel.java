package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Supplier;

public class SupplierComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (selection == null) return "";

        Supplier supplier = (Supplier) selection;
        return supplier.companyName;
    }

    @Override
    public void addRecord(Base record) {
        Supplier supplier = (Supplier) record;
        records.put(supplier.companyName, record);
    }

    public Object getElementAt(int index) {
        return ((Supplier)super.getElementAt(index)).companyName;
    }
}
