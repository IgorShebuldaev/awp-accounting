package org.accounting.forms.models.tablemodels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.accounting.database.models.Base;

import javax.swing.table.AbstractTableModel;

public abstract class AdapterFX extends AbstractTableModel {
  String[] columnNames = new String[0];
  ObservableList<Base> data = FXCollections.observableArrayList();;

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
  }

  public void removeRow(int row) {
    data.remove(row);
  }

  public void setValueAt(Base record, int rowIndex) {
    data.set(rowIndex, record);
  }

}
