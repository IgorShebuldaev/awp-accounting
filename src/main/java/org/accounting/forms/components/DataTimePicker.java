package org.accounting.forms.components;

import javax.swing.*;
import java.util.Calendar;
import java.util.Date;

public class DataTimePicker {

    public SpinnerDateModel setCurrentDateSpinner() {
        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();

        return new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
    }
}
