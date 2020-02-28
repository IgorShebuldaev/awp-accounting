package org.accounting.forms.workbooks;

import org.accounting.ControllerManager;
import org.accounting.database.models.Worker;
import org.accounting.forms.PositionsForm;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.DateTableCell;
import org.accounting.forms.models.comboboxcells.PositionComboBoxCell;
import org.accounting.forms.models.tablemodels.PositionFX;
import org.accounting.forms.models.tablemodels.WorkerFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class WorkersForm implements Initializable {
    @FXML private AnchorPane paneWorkers;
    @FXML private TableView<WorkerFX> tableWorkers;
    @FXML private TableColumn<WorkerFX, String> columnFullName;
    @FXML private TableColumn<WorkerFX, Date> columnDateOfBirth;
    @FXML private TableColumn<WorkerFX, String> columnPosition;
    @FXML private TableColumn<WorkerFX, String> columnEmail;
    @FXML private TextField tfFullName;
    @FXML private DatePicker dpDateOfBirth;
    @FXML private ComboBox<PositionFX> cbPosition;
    private ObservableList<WorkerFX> data;
    private PositionComboBoxCell cbCellPosition;

    public WorkersForm() {
        data = FXCollections.observableArrayList();
        ArrayList<Worker> results = Worker.getAll();

        for(Worker worker: results) {
            data.add(new WorkerFX(worker));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbCellPosition = new PositionComboBoxCell();

        columnFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        columnDateOfBirth.setCellValueFactory(cellData -> cellData.getValue().dateOfBirthProperty());
        columnPosition.setCellValueFactory(cellData -> cellData.getValue().positionProperty());
        columnEmail.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());

        columnFullName.setCellFactory(TextFieldTableCell.forTableColumn());
        columnDateOfBirth.setCellFactory((TableColumn<WorkerFX, Date> param) -> new DateTableCell<>());
        columnPosition.setCellFactory((TableColumn<WorkerFX, String> param) -> cbCellPosition);

        cbPosition.setItems(cbCellPosition.getList());

        tableWorkers.setItems(data);

        setPropertiesCombobox();
    }

    private void setPropertiesCombobox() {
        cbPosition.setCellFactory(new Callback<ListView<PositionFX>, ListCell<PositionFX>>() {
            @Override
            public ListCell<PositionFX> call(ListView<PositionFX> positionFXListView) {
                return new ListCell<PositionFX>() {
                    @Override
                    protected void updateItem(PositionFX item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });

        cbPosition.setConverter(new StringConverter<PositionFX>() {
            @Override
            public String toString(PositionFX positionFX) {
                if (positionFX == null) {
                    return null;
                } else {
                    return positionFX.getName();
                }
            }

            @Override
            public PositionFX fromString(String str) {
                return null;
            }
        });
    }

    private void clearComponents() {
        tfFullName.clear();
        dpDateOfBirth.setValue(null);
        cbPosition.getSelectionModel().clearSelection();
    }

    private Date convertToDate(LocalDate dateToConvert) {
        if (dateToConvert != null) {
            return Date.from(dateToConvert.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }
        return null;
    }

    @FXML
    private void handleBtnAdd() {
        Worker supplier = new Worker();
        //TODO:buildUser();
        supplier.setFullName(tfFullName.getText());
        supplier.setDateOfBirth(convertToDate(dpDateOfBirth.getValue()));
        supplier.setPositionID(cbPosition.getSelectionModel().getSelectedItem().getPosition().getId());

        if (!supplier.save()) {
            new AlertMessage("Error", supplier.getErrors().fullMessages()).showErrorMessage();
            return;
        }

        data.add(new WorkerFX(supplier));
        clearComponents();
    }

    @FXML
    private void handleBtnAdd(Worker worker) {
        if (!worker.save()) {
            new AlertMessage("Error", worker.getErrors().fullMessages()).showErrorMessage();
        }
    }

    @FXML
    private void handleBtnDelete() {
        WorkerFX workerFX = tableWorkers.getSelectionModel().getSelectedItem();

        if (new AlertMessage("Message","Are you sure you want to delete the record?").showConfirmationMessage()) {
            workerFX.getWorker().delete();
            data.remove(workerFX);
        }
    }

    @FXML
    private void onEditCommitFullName(TableColumn.CellEditEvent<WorkerFX, String> workerFXStringCellEditEvent) {
        WorkerFX workerFX = tableWorkers.getSelectionModel().getSelectedItem();
        workerFX.setFullName(workerFXStringCellEditEvent.getNewValue());
        workerFX.getWorker().setFullName(workerFXStringCellEditEvent.getNewValue());
        handleBtnAdd(workerFX.getWorker());
    }

    @FXML
    private void onEditCommitDateOfBirth(TableColumn.CellEditEvent<WorkerFX, Date> workerFXDateCellEditEvent) {
        WorkerFX workerFX = tableWorkers.getSelectionModel().getSelectedItem();
        workerFX.setDateOfBirth(workerFXDateCellEditEvent.getNewValue());
        workerFX.getWorker().setDateOfBirth(workerFXDateCellEditEvent.getNewValue());
        handleBtnAdd(workerFX.getWorker());
    }

    @FXML
    private void onEditCommitPosition(TableColumn.CellEditEvent<WorkerFX, String> workerFXStringCellEditEvent) {
        //TODO:save value from comboboxcells
    }

    @FXML
    private void handlerBtnShowPositionsForm() {
        ControllerManager.getInstance().getStage(PositionsForm.class).showAndWait();
        cbCellPosition = new PositionComboBoxCell();
        cbPosition.setItems(cbCellPosition.getList());
    }
}
