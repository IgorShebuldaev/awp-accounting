package org.accounting.forms.WorkBooks;

import org.accounting.forms.models.MainTableModel;

public interface DataManipulator {
    MainTableModel fillTable(MainTableModel tableModel);
    MainTableModel insertData(Object aValue, MainTableModel tableModel, int rowIndex);
    MainTableModel deleteData(Object aValue, MainTableModel tableModel, int rowIndex);
    MainTableModel updateData(Object aValue, MainTableModel tableModel, int rowIndex);
}
