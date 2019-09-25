package org.accounting.forms.models;

import org.accounting.database.models.Supplier;

public class SupplierTable extends MainTableModel {

    public SupplierTable() {
        columnNames = new String[]{"Company name"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Supplier supplier = (Supplier) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = supplier.companyName;
                break;
        }
        return result;
    }

    @Override
    public Supplier getRecord(int rowIndex) {
        return (Supplier) super.getRecord(rowIndex);
    }
}
