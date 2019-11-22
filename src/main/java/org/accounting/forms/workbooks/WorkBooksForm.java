package org.accounting.forms.workbooks;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.accounting.forms.BaseController;

public class WorkBooksForm extends BaseController {
    @FXML private AnchorPane paneSuppliers;
    @FXML private AnchorPane paneWorkers;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Work books");
    }
}
