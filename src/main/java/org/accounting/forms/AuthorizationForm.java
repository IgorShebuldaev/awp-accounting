package org.accounting.forms;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.accounting.database.models.User;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.user.CurrentUser;

import java.io.IOException;

public class AuthorizationForm {
    @FXML private TextField textFieldEmail;
    @FXML private PasswordField passField;

    public void showForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AuthorizationForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Sign in");
        stage.setScene(scene);
        stage.show();
    }

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
            new MainForm().showForm();
        } else {
            new AlertMessage("Message","Invalid login or password! Try again.").showErrorMessage();
        }
    }

}
