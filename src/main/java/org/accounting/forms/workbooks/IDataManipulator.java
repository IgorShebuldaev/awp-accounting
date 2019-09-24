package org.accounting.forms.workbooks;

import org.accounting.database.models.Base;
import org.accounting.forms.models.MainTableModel;

 public interface IDataManipulator  {
    void fillTable(MainTableModel tableModel);
    void insertData(MainTableModel tableModel, Base base);
    void deleteData(MainTableModel tableModel, int rowIndex, int id);
    void updateData(MainTableModel tableModel, int rowIndex, Base base);
}
