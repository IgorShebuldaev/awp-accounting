package org.accounting.forms.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertMessage {
    private final String title;
    private String header = null;
    private final String message;

    public AlertMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public AlertMessage(String title, String header,String message) {
        this.title = title;
        this.header = header;
        this.message = message;
    }

    public void showErrorMessage() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean confirmationMessage() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        return alert.showAndWait().get() == ButtonType.OK;
    }

}
