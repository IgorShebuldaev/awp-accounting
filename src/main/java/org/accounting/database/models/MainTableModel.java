package org.accounting.database.models;


import javax.swing.table.DefaultTableModel;


public class MainTableModel extends DefaultTableModel {
    @Override
    public Object getValueAt(int row, int column) {
        return super.getValueAt(row, column);//here will be my implementation
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return super.isCellEditable(row, column);//similarly
    }
}
