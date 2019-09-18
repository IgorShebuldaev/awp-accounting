package org.accounting.forms;

import org.accounting.database.models.Workers;
import org.accounting.forms.models.MainTableModel;

import java.util.ArrayList;

public class Worker implements DataManipulation {

    @Override
    public MainTableModel fillTable(MainTableModel tableModel) {
        tableModel.setColumnIdentifiers(new String[]{"Full Name", "Date of birth", "Position"});
        ArrayList<Workers> arrayList = Workers.getWorkers();
        for (Workers workers : arrayList) {
            tableModel.addRow(new Object[]{workers.id, workers.fullName, workers.dob, workers.position});
        }
        return tableModel;
    }

    @Override
    public MainTableModel insertData(Object aValue, MainTableModel tableModel, int rowIndex) {
        return null;
    }

    @Override
    public MainTableModel deleteData(Object aValue, MainTableModel tableModel, int rowIndex) {
        return null;
    }

    @Override
    public MainTableModel updateData(Object aValue, MainTableModel tableModel, int rowIndex) {
        return null;
    }
}
