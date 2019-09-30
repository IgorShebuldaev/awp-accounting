package org.accounting.forms.models.comboboxmodels;

import org.accounting.database.models.Worker;

public class WorkerComboBoxModel extends MainComboBoxModel {
    @Override
    public void setSelectedItem(Object anItem) {
        selection = records.stream().filter(r -> ((Worker)r).position.equals(anItem)).findFirst().get();
    }

    @Override
    public Object getSelectedItem() {
        return ((Worker)super.getSelectedItem()).fullName;
    }

    public Object getElementAt(int index) {
        return ((Worker)records.get(index)).fullName;
    }

}
