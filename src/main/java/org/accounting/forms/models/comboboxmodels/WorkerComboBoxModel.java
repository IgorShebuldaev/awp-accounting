package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Base;
import org.accounting.database.models.Worker;

public class WorkerComboBoxModel extends MainComboBoxModel {

    @Override
    public Object getSelectedItem() {
        if (selection == null) return "";

        Worker p = (Worker) selection;
        return p.fullName;
    }

    @Override
    public void addRecord(Base record) {
        Worker p = (Worker) record;
        records.put(p.fullName, record);
    }

    public Object getElementAt(int index) {
        return ((Worker)super.getElementAt(index)).fullName;
    }

}
