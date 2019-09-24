package org.accounting.forms.WorkBooks;

import org.accounting.database.models.Base;
import org.accounting.forms.models.MainTableModel;

 public interface IDataManipulator  {
    void fillTable(MainTableModel tableModel);
    void insertData(Base base, MainTableModel tableModel);
    void deleteData(Base base, MainTableModel tableModel, int rowIndex);
    void updateData(Base base, MainTableModel tableModel, int rowIndex);
}
