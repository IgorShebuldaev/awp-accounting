package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Supplier;

public class SupplierTable extends MainTableModel {

    public SupplierTable() {
        columnNames = new String[]{"Company name"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Supplier supplier = (Supplier) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return supplier.getCompanyName();
        }
        return "";
    }

    @Override
    public Supplier getRecord(int rowIndex) {
        return (Supplier) super.getRecord(rowIndex);
    }
}
