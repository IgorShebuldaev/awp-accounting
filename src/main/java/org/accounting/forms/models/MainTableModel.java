package org.accounting.forms.models;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


public class MainTableModel extends AbstractTableModel {
  private String[] columnNames = new String[0];
  private ArrayList<Object[]> data = new ArrayList<>();

  public void setColumnIdentifiers(String[] columns) {
    this.columnNames = columns;
  }

  public Object getValueAt(int row, int column) {
    return data.get(row)[column + 1];
  }

  public Object getRawValueAt(int row, int column) {
    return this.getValueAt(row, column - 1);
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

  public void addRow(Object[] row) {
    data.add(row);
    fireTableRowsInserted(data.size()-1, data.size()-1);
  }

  public void removeRow(int row) {
    data.remove(row);
    fireTableRowsDeleted(row,row);
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return true;
  }

  public void setValueAt(Object[] aValue, int rowIndex, int columnIndex) {
      this.data.set(rowIndex,aValue);
      fireTableCellUpdated(rowIndex, columnIndex);
  }
}
