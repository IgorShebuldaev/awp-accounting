package org.accounting.forms.workbooks;

import org.accounting.database.models.Base;
import org.accounting.database.models.Position;
import org.accounting.database.models.Worker;
import org.accounting.forms.models.MainComboBoxModel;
import org.accounting.forms.models.MainTableModel;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class WorkerImpl implements IDataManipulator {

    MainComboBoxModel addItemComboBoxPosition() {
        MainComboBoxModel model = new MainComboBoxModel();
        Position.getAll().forEach(model::addRecord);

        return model;
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
        Worker.insertData((Worker) base);
        tableModel.addRecord(base);
    }

    @Override
    public void deleteData(MainTableModel tableModel, int rowIndex, int id) {
        Worker.deleteData(id);
        tableModel.removeRow(rowIndex);
    }

    @Override
    public void updateData(MainTableModel tableModel, int rowIndex, Base base) {
        Worker.updateData((Worker) base);
        tableModel.setValueAt(base, rowIndex);
    }
}
