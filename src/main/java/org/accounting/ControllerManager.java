package org.accounting;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.accounting.forms.BaseController;

import java.io.IOException;
import java.util.HashMap;

public class ControllerManager {
    private HashMap<String, Stage> mapping = new HashMap<>();

    private static ControllerManager instance = null;

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }

        return instance;
    }

    public Stage getStage(Class controller) {
        return getStage_(controller, false);
    }

    public Stage getStageReloaded(Class controller) {
        return getStage_(controller, true);
    }

    private Stage getStage_(Class controller, boolean reload) {
        Stage stage = getStageFor(controller);

        if (stage == null || reload) {
            FXMLLoader loader = new FXMLLoader(controller.getResource(controller.getSimpleName() + ".fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);

            stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            setStageFor(controller, stage);

            BaseController baseController = loader.getController();
            baseController.setStage(stage);
            baseController.postInitializable();
        }

        return stage;
    }

    private Stage getStageFor(Class controller) {
        return mapping.get(controller.getSimpleName());
    }

    private void setStageFor(Class controller, Stage stage) {
        mapping.put(controller.getSimpleName(), stage);
    }
}
