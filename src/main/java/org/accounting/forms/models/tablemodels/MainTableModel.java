package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Base;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public abstract class MainTableModel extends AbstractTableModel {
  String[] columnNames = new String[0];
  ArrayList<Base> data = new ArrayList<>();

  public void setColumnIdentifiers(String[] columns) {
    this.columnNames = columns;
  }

  public String getColumnName(int column) {
    return columnNames[column];
  }

  public int getColumnCount() {
    return columnNames.length;
  }

  public int getRowCount() {
    return data.size();
  }

  public Base getRecord(int rowIndex) {
    return data.get(rowIndex);
  }

  public void addRecord(Base record) {
    data.add(record);
    fireTableRowsInserted(data.size()-1, data.size()-1);
  }

  public void removeRow(int row) {
    data.remove(row);
    fireTableRowsDeleted(row,row);
  }

  public void setValueAt(Base record, int rowIndex) {
    data.set(rowIndex, record);
    fireTableRowsUpdated(rowIndex,rowIndex);
  }
}
