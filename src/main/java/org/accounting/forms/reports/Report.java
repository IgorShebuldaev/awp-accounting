package org.accounting.forms.reports;

public class Report {

    private StringBuilder document = new StringBuilder();

    public StringBuilder getDocument() {
        return document;
    }

    private void insertStyle() {
        document.append("<style> table {width: 100%;} th, td {border: solid black;} th {height: 50px;} </style>");
    }

    private void insertTableName(String tableName) {
        document.append("<caption>").append(tableName).append("</caption>");
    }

    private void insertOpenTable() {
        document.append("<table>");
    }

    public void insertCloseTable() {
        document.append("</table>");
    }

    public void insertOpenTr() {
        document.append("<tr>");
    }

    public void insertCloseTr() {
        document.append("</tr>");
    }

    private void insertColumn(String header) {
        document.append("<th>").append(header).append("</th>");
    }

    public void insertRow(String row) {
        document.append("<td>").append(row).append("</td>");
    }

    public void addHeader(String tableName, String[] header) {
        insertStyle();
        insertTableName(tableName);

        insertOpenTable();
        insertOpenTr();

        for (String column : header) {
            insertColumn(column);
        }

        insertCloseTr();
    }
}
