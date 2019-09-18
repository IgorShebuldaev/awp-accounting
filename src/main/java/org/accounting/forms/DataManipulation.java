package org.accounting.forms;

import org.accounting.forms.models.MainTableModel;

public interface DataManipulation {
    MainTableModel fillTable(MainTableModel tableModel);
    MainTableModel insertData(Object aValue, MainTableModel tableModel, int rowIndex);
    MainTableModel deleteData(Object aValue, MainTableModel tableModel, int rowIndex);
    MainTableModel updateData(Object aValue, MainTableModel tableModel, int rowIndex);
}
