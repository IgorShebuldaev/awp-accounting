package org.accounting;

import org.accounting.forms.AuthorizationForm;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ControllerManager.getInstance().getStage(AuthorizationForm.class).show();
    }
}
