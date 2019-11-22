package org.accounting.forms;

import javafx.stage.Stage;
import org.accounting.ControllerManager;
import org.accounting.database.models.User;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.user.CurrentUser;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AuthorizationForm extends BaseController {
    @FXML private TextField textFieldEmail;
    @FXML private PasswordField passField;

    @FXML
    private void handleBtnSignIn(){
        validateUserAuthorization(textFieldEmail, passField);
    }

    @FXML
    private void handleBtnExit() {
        System.exit(0);
    }

    private boolean isAuthorized(String login, String password) {
        User user = new User(login);

        if (!user.isNewRecord() && user.getPassword().equals(password)) {
            CurrentUser.setCurrentUser(user);

            return true;
        }

        return false;
    }

    private void validateUserAuthorization(TextField login, PasswordField password) {
        if (isAuthorized(login.getText(), password.getText())) {
            ControllerManager.getInstance().getStage(AuthorizationForm.class).close();
            ControllerManager.getInstance().getStage(MainForm.class).show();
        } else {
            new AlertMessage("Message","Invalid login or password! Try again.").showErrorMessage();
        }
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Sign in");
    }
}
