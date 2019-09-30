package org.accounting.forms.workbooks;

import org.accounting.database.models.Base;
import org.accounting.database.models.Supplier;
import org.accounting.forms.models.tablemodels.MainTableModel;

import javax.swing.*;
import java.util.ArrayList;

public class SupplierImpl extends JDialog implements IDataManipulator {

    @Override
    public void fillTable(MainTableModel tableModel) {
        ArrayList<Supplier> results = Supplier.getAll();
        for (Supplier supplier: results) {
            tableModel.addRecord(supplier);
        }
    }

    @Override
    public void insertData(MainTableModel tableModel, Base base) {
        Supplier.insertData((Supplier) base);
        tableModel.addRecord(base);
    }

    @Override
    public void deleteData(MainTableModel tableModel, int rowIndex, int id) {
        Supplier.deleteData(id);
        tableModel.removeRow(rowIndex);
    }

    @Override
    public void updateData(MainTableModel tableModel, int rowIndex, Base base) {
        Supplier.updateData((Supplier) base);
        tableModel.setValueAt(base, rowIndex);
    }
}
