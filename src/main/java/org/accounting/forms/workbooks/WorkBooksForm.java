package org.accounting.forms.workbooks;

import org.accounting.forms.BaseController;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class WorkBooksForm extends BaseController {
    @FXML private AnchorPane paneSuppliers;
    @FXML private AnchorPane paneWorkers;

    @Override
    public void postInitializable() {
        stage.setTitle("Work books");
    }
}
