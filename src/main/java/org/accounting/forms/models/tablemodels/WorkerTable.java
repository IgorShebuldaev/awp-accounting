package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Worker;

import java.text.SimpleDateFormat;

public class WorkerTable extends MainTableModel {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

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
                result = dateFormat.format(worker.dateOfBirth);
                break;
            case 2:
                result = worker.position;
                break;
        }
        return result;
    }

    @Override
    public Worker getRecord(int rowIndex) {
        return (Worker) super.getRecord(rowIndex);
    }
}
