package org.accounting.forms.WorkBooks;

import org.accounting.database.models.Worker;
import org.accounting.forms.models.MainTableModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class WorkerImpl implements IDataManipulator {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public MainTableModel fillTable(MainTableModel tableModel) {
        tableModel.setColumnIdentifiers(new String[]{"Full Name", "Date of birth", "Position"});
        ArrayList<Worker> results = Worker.getWorkers();
        for (Worker workers : results) {
            tableModel.addRow(new Object[]{workers.id, workers.fullName, dateFormat.format(workers.dateOfBirth), workers.position});
        }
        return tableModel;
    }

    @Override
    public MainTableModel insertData(Object aValue, MainTableModel tableModel) {
        Worker.insertWorker((Worker) aValue);
        tableModel.addRow(new Object[]{((Worker) aValue).id, ((Worker) aValue).fullName, dateFormat.format(((Worker) aValue).dateOfBirth), ((Worker) aValue).position});
        return tableModel;
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
