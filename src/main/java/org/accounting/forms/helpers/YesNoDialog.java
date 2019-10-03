package org.accounting.forms.helpers;

import javax.swing.*;

public class YesNoDialog {
    private final String message;
    private final String title;
    private int result;
    private boolean showed = false;

    public YesNoDialog(String message, String title) {

        this.message = message;
        this.title = title;
    }

    public boolean isPositive() {
        if (!showed) { show(); }

        return result == JOptionPane.YES_OPTION;
    }

    public boolean isNegative() {
        if (!showed) { show(); }

        return result == JOptionPane.NO_OPTION;
    }

    private void show() {
        Object[] options = {"Yes", "No"};
        result = JOptionPane.showOptionDialog(null,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]);

        showed = true;
    }
}
