package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Supplier;

public class SupplierFX extends AdapterFX {

    public SupplierFX() {
        columnNames = new String[]{"Company name"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Supplier supplier = (Supplier) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return supplier.getName();
            default:
                return "";
        }
    }

    @Override
    public Supplier getRecord(int rowIndex) {
        return (Supplier) super.getRecord(rowIndex);
    }
}
