package org.accounting.forms.workbooks;

import org.accounting.database.models.Base;
import org.accounting.forms.models.tablemodels.MainTableModel;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public interface IDataManipulator  {

    default void fillTable(MainTableModel tableModel) {}
    default void insertData(MainTableModel tableModel, Base base) {}
    default void deleteData(MainTableModel tableModel, int rowIndex, int id) {}
    default void updateData(MainTableModel tableModel, int rowIndex, Base base) {}
    default SpinnerModel setCurrentDateSpinner() {
        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        return new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
    }
}
