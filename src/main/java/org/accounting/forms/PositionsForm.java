package org.accounting.forms;

import org.accounting.database.models.Position;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.tablemodels.PositionFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PositionsForm extends BaseController implements Initializable {
    @FXML private TableView<PositionFX> tablePositions;
    @FXML private TableColumn<PositionFX, String> columnName;
    @FXML private TextField tfPositionName;
    private ObservableList<PositionFX> data;

    public PositionsForm() {
        data = FXCollections.observableArrayList();
        ArrayList<Position> results = Position.getAll();

        for(Position position: results) {
            data.add(new PositionFX(position));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnName.setCellFactory(TextFieldTableCell.forTableColumn());

        tablePositions.setItems(data);
    }

    @Override
    public void postInitialize() {
        stage.setTitle("Positions");
    }

    @FXML
    private void handleBtnAdd() {
        Position position = new Position();
        position.setName(tfPositionName.getText());

        if (!position.save()) {
            new AlertMessage("Error", position.getErrors().fullMessages()).showErrorMessage();
            return;
        }

        data.add(new PositionFX(position));

        tfPositionName.clear();
    }

    @FXML
    private void handleBtnAdd(Position position) {
        if (!position.save()) {
            new AlertMessage("Error", position.getErrors().fullMessages()).showErrorMessage();
        }
    }

    @FXML
    private void handleBtnDelete() {
        PositionFX positionFX = tablePositions.getSelectionModel().getSelectedItem();

        if (new AlertMessage("Message","Are you sure you want to delete the record?").showConfirmationMessage()) {
            positionFX.getPosition().delete();
            data.remove(positionFX);
        }
    }

    @FXML
    private void onEditCommitName(TableColumn.CellEditEvent<PositionFX, String> positionFXStringCellEditEvent) {
        PositionFX positionFX = tablePositions.getSelectionModel().getSelectedItem();
        positionFX.setName(positionFXStringCellEditEvent.getNewValue());
        positionFX.getPosition().setName(positionFXStringCellEditEvent.getNewValue());
        handleBtnAdd(positionFX.getPosition());
    }
}
