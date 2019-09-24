package org.accounting.forms.models;

import org.accounting.database.models.Worker;

public class WorkerTable extends MainTableModel {

    public WorkerTable() {
        columnNames = new String[]{"Full Name", "Date of birth", "Position"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Worker worker = (Worker) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = worker.fullName;
                break;
            case 1:
                result = worker.dateOfBirth;
                break;
            case 2:
                result = worker.position;
                break;
        }
        return result;
    }
}
