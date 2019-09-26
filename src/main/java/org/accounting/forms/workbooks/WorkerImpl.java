package org.accounting.forms.workbooks;

import org.accounting.database.models.Base;
import org.accounting.database.models.Position;
import org.accounting.database.models.Worker;
import org.accounting.forms.models.MainComboBoxModel;
import org.accounting.forms.models.MainTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorkerImpl implements IDataManipulator {

    MainComboBoxModel addItemComboBoxPosition() {
        ArrayList<Position> results = Position.getAll();
        String[] items = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            items[i] = results.get(i).position;
        }
        return new MainComboBoxModel(items);
    }

    @Override
    public void fillTable(MainTableModel tableModel) {
        ArrayList<Worker> results = Worker.getAll();
        for (Worker worker : results) {
            tableModel.addRecord(worker);
        }
    }

    @Override
    public void insertData(MainTableModel tableModel, Base base) {
        Worker.insertWorker((Worker) base);
        tableModel.addRecord(base);
    }

    @Override
    public void deleteData(MainTableModel tableModel, int rowIndex, int id) {
        Worker.deleteWorker(id);
        tableModel.removeRow(rowIndex);
    }

    @Override
    public void updateData(MainTableModel tableModel, int rowIndex, Base base) {
        Worker.updateWorker((Worker) base);
        tableModel.setValueAt(base, rowIndex);
    }
}
