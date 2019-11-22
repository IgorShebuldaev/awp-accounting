package org.accounting.forms.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertMessage {
    private Alert alert;
    private final String title;
    private final String message;

    public AlertMessage(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public void showErrorMessage() {
        alert = new Alert(Alert.AlertType.ERROR);
        setMessage();
        alert.showAndWait();
    }

    public void showInformationMessage() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        setMessage();
        alert.showAndWait();
    }

    public boolean showConfirmationMessage() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        setMessage();
        return alert.showAndWait().get() == ButtonType.OK;
    }

    private void setMessage() {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
    }

}
