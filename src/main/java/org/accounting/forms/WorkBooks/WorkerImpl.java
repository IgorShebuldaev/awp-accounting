package org.accounting.forms.WorkBooks;

import org.accounting.database.models.Base;
import org.accounting.database.models.Worker;
import org.accounting.forms.models.MainTableModel;

import java.util.ArrayList;

public class WorkerImpl implements IDataManipulator {

    @Override
    public void fillTable(MainTableModel tableModel) {
        ArrayList<Worker> results = Worker.getAll();
        for (Worker worker : results) {
            tableModel.addRecord(worker);
        }
    }

    @Override
    public void insertData(Base base, MainTableModel tableModel) {
        Worker.insertWorker((Worker) base);
        tableModel.addRecord(base);
    }

    @Override
    public void deleteData(Base base, MainTableModel tableModel, int rowIndex) {

    }

    @Override
    public void updateData(Base base, MainTableModel tableModel, int rowIndex) {

    }
}
