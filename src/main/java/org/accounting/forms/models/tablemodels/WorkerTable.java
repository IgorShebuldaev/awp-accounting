package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Worker;

import java.text.SimpleDateFormat;

public class WorkerTable extends MainTableModel {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public WorkerTable() {
        columnNames = new String[]{"Full Name", "Date of birth", "Position", "Email"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Worker worker = (Worker) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return worker.fullName;
            case 1:
                return dateFormat.format(worker.dateOfBirth);
            case 2:
                return worker.position;
            case 3:
                return worker.email;
        }
        return "";
    }

    @Override
    public Worker getRecord(int rowIndex) {
        return (Worker) super.getRecord(rowIndex);
    }
}
