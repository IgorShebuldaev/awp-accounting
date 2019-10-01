package org.accounting.forms.workbooks;

import org.accounting.database.models.*;
import org.accounting.forms.models.comboboxmodels.PositionComboBoxModel;
import org.accounting.forms.models.tablemodels.MainTableModel;

import java.util.ArrayList;

public class WorkerImpl implements IDataManipulator {

    PositionComboBoxModel addItemComboBoxPosition() {
        PositionComboBoxModel model = new PositionComboBoxModel();
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

    public void insertData(MainTableModel tableModel, Base base, String password) {
        User user = new User(0, ((Worker)base).email, password, Role.getRoleIdByLookupCode("manager"), 0);
        User.insertData(user);
        Worker.insertData((Worker) base, user.id);
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
