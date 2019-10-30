package org.accounting.libs;

import javax.swing.table.DefaultTableModel;

public class Report {
    private StringBuilder column = new StringBuilder();
    private StringBuilder table = new StringBuilder();
    private StringBuilder document = new StringBuilder();

    public String getDocument () {
        return document.toString();
    }

    public void setStyle(String style) {
        document.append("<style>").append(style).append("</style>");
    }

    public void setTableName(String tableName) {
        document.append("<caption>").append(tableName).append("</caption>");
    }

    private void insertTable() {
        document.append("<table>").append(table).append("</table>");
    }

    private void insertRow() {
        table.append("<tr>").append(column).append("</tr>");
        column.setLength(0);
    }

    private void insertHeaderColumn(String column) {
        this.column.append("<th>").append(column).append("</th>");
    }

    private void insertColumn(String column) {
        this.column.append("<td>").append(column).append("</td>");
    }

    private void setHeaderTable(DefaultTableModel model) {
        for (int i = 0; i < model.getColumnCount(); i++) {
            insertHeaderColumn(model.getColumnName(i));
        }
        insertRow();
    }

    public void addTable(DefaultTableModel model) {
        setHeaderTable(model);

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                insertColumn(model.getValueAt(i, j).toString());
            }
            insertRow();
        }
        insertTable();
    }
}
