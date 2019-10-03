package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Worker;

public class WorkerComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (selection == null) return "";

        Worker worker = (Worker) selection;
        return worker.fullName;
    }

    @Override
    public void addRecord(Base record) {
        Worker worker = (Worker) record;
        records.put(worker.fullName, record);
    }

    public Object getElementAt(int index) {
        return ((Worker)super.getElementAt(index)).fullName;
    }

}
