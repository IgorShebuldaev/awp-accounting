package org.accounting.forms;

import org.accounting.ControllerManager;
import org.accounting.database.models.Delivery;
import org.accounting.database.models.Supplier;
import org.accounting.database.models.User;
import org.accounting.database.models.Worker;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.DateTableCell;
import org.accounting.forms.models.comboboxcells.SupplierComboBoxCell;
import org.accounting.forms.models.comboboxcells.WorkerComboBoxCell;
import org.accounting.forms.models.tablemodels.DeliveryFX;
import org.accounting.forms.models.tablemodels.SupplierFX;
import org.accounting.forms.models.tablemodels.WorkerFX;
import org.accounting.forms.workbooks.WorkBooksForm;
import org.accounting.user.CurrentUser;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MainForm extends BaseController implements Initializable {
    @FXML private TableView<DeliveryFX> tableDeliveries;
    @FXML private TableColumn<DeliveryFX, Date> columnDeliveryDate;
    @FXML private TableColumn<DeliveryFX, String> columnSupplier;
    @FXML private TableColumn<DeliveryFX, String> columnProduct;
    @FXML private TableColumn<DeliveryFX, String> columnPrice;
    @FXML private TableColumn<DeliveryFX, String> columnWorker;
    @FXML private DatePicker dpDeliveryDate;
    @FXML private ComboBox<SupplierFX> cbSupplier;
    @FXML private TextField tfProduct;
    @FXML private TextField tfPrice;
    @FXML private ComboBox<WorkerFX> cbWorker;
    @FXML private Label labelStatusBar;
    private ObservableList<DeliveryFX> data;

    public MainForm() {
        data = FXCollections.observableArrayList();
        ArrayList<Delivery> resultsDelivery = Delivery.getAll();

        for (Delivery delivery : resultsDelivery) {
            data.add(new DeliveryFX(delivery));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startTimer();

        columnDeliveryDate.setCellValueFactory(cellData -> cellData.getValue().deliveryDateProperty());
        columnSupplier.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        columnProduct.setCellValueFactory(cellData -> cellData.getValue().productProperty());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        columnWorker.setCellValueFactory(cellData -> cellData.getValue().workerProperty());

        columnDeliveryDate.setCellFactory((TableColumn<DeliveryFX, Date> param) -> new DateTableCell<>());
        columnSupplier.setCellFactory((TableColumn<DeliveryFX, String> param) -> new SupplierComboBoxCell(getItemsComboBoxSupplier()));
        columnProduct.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        columnWorker.setCellFactory((TableColumn<DeliveryFX, String> param) -> new WorkerComboBoxCell(getItemsComboBoxWorker()));

        tableDeliveries.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableDeliveries.setItems(data);

        cbSupplier.setItems(getItemsComboBoxSupplier());
        cbWorker.setItems(getItemsComboBoxWorker());
        setPropertiesComboBox();
    }

    @Override
    public void postInitializable() {
        stage.setTitle("Accounting");

        stage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            handleMiExit();
        });
    }

    @FXML
    private void handleMiWorkBooks() {
        ControllerManager.getInstance().getStage(WorkBooksForm.class).show();
    }

    @FXML
    private void handleMiNotes() {
        ControllerManager.getInstance().getStage(NotesForm.class).show();

    }

    @FXML
    private void handleMiLogOut() {
        ControllerManager.getInstance().getStage(MainForm.class).close();
        ControllerManager.getInstance().getStageReloaded(AuthorizationForm.class).show();
    }

    @FXML
    private void handleMiExit() {
        exitMessage();
    }

    @FXML
    private void handleMiDeliveries() {
        ControllerManager.getInstance().getStage(ReportsForm.class).show();
    }

    @FXML
    private void handleMiTimeInProgram() {
        ControllerManager.getInstance().getStage(ChartsForm.class).show();
    }

    @FXML
    private void handleMiUsers() {
        ControllerManager.getInstance().getStage(UsersForm.class).show();
    }

    @FXML
    private void handleMiRoles() {
        ControllerManager.getInstance().getStage(RolesForm.class).show();
    }

    @FXML
    private void handleMiAbout() {
        new AlertMessage("About", "Copyright © 2019 Dev Team").showInformationMessage();
    }

    private ObservableList<SupplierFX> getItemsComboBoxSupplier() {
        ObservableList<SupplierFX> suppliers = FXCollections.observableArrayList();
        ArrayList<Supplier> results = Supplier.getAll();

        for (Supplier supplier : results) {
            suppliers.add(new SupplierFX(supplier));
        }

        return suppliers;
    }

    private ObservableList<WorkerFX> getItemsComboBoxWorker() {
        ObservableList<WorkerFX> workers = FXCollections.observableArrayList();
        ArrayList<Worker> results = Worker.getAll();

        for (Worker worker : results) {
            workers.add(new WorkerFX(worker));
        }

        return workers;
    }

    private void setPropertiesComboBox() {
        cbSupplier.setCellFactory(new Callback<ListView<SupplierFX>, ListCell<SupplierFX>>() {
            @Override
            public ListCell<SupplierFX> call(ListView<SupplierFX> supplierFXListView) {
                return new ListCell<SupplierFX>() {
                    @Override
                    protected void updateItem(SupplierFX item, boolean empty) {
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

        cbSupplier.setConverter(new StringConverter<SupplierFX>() {
            @Override
            public String toString(SupplierFX supplierFX) {
                if (supplierFX == null){
                    return null;
                } else {
                    return supplierFX.getName();
                }
            }

            @Override
            public SupplierFX fromString(String name) {
                return null;
            }
        });

        cbWorker.setCellFactory(new Callback<ListView<WorkerFX>, ListCell<WorkerFX>>() {
            @Override
            public ListCell<WorkerFX> call(ListView<WorkerFX> workerFXListView) {
                return new ListCell<WorkerFX>() {
                    @Override
                    protected void updateItem(WorkerFX item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getFullName());
                        }
                    }
                };
            }
        });

        cbWorker.setConverter(new StringConverter<WorkerFX>() {
            @Override
            public String toString(WorkerFX workerFX) {
                if (workerFX == null){
                    return null;
                } else {
                    return workerFX.getFullName();
                }
            }

            @Override
            public WorkerFX fromString(String fullName) {
                return null;
            }
        });
    }

    private void startTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> updateStatusBar()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateStatusBar() {
        User currentUser = CurrentUser.getUser();
        if (currentUser == null) { return; }

        currentUser.incrementTimeInProgram(1);
        labelStatusBar.setText(
                String.format("User: %s. Role: %s. Time in program: %s",
                        currentUser.getEmail(),
                        currentUser.getRole().getName(),
                        currentUser.getFormattedTimeInProgram()
                )
        );
    }

    @FXML
    private void handleBtnAdd() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(convertToDate(dpDeliveryDate.getValue()));
        delivery.setSupplierId(cbSupplier.getSelectionModel().getSelectedItem().getSupplier().getId());
        delivery.setProduct(tfProduct.getText());
        delivery.setPrice(tfPrice.getText());
        delivery.setWorkerId(cbWorker.getSelectionModel().getSelectedItem().getWorker().getId());

        if (!delivery.save()) {
            new AlertMessage("Error", delivery.getErrors().fullMessages()).showErrorMessage();
            return;
        }

        data.add(new DeliveryFX(delivery));
        clearComponents();
    }

    @FXML
    private void handleBtnAdd(Delivery delivery) {
        if (!delivery.save()) {
            new AlertMessage("Error", delivery.getErrors().fullMessages()).showErrorMessage();
        }
    }

    @FXML
    private void handleBtnDelete() {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();

        if (new AlertMessage("Message","Are you sure you want to delete the record?").showConfirmationMessage()) {
            deliveryFX.getDelivery().delete();
            data.remove(deliveryFX);
        }

    }

    private void clearComponents() {
        dpDeliveryDate.setValue(null);
        cbSupplier.getSelectionModel().clearSelection();
        tfProduct.clear();
        tfPrice.clear();
        cbWorker.getSelectionModel().clearSelection();
    }

    private Date convertToDate(LocalDate dateToConvert) {
        if (dateToConvert != null) {
            return Date.from(dateToConvert.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }
        return null;
    }

    private void exitMessage() {
        if (new AlertMessage("Confirm Exit", "Are you sure you want to exit?").showConfirmationMessage()) {
            Platform.exit();
        }
    }

    @FXML
    private void onEditCommitDeliveryDate(TableColumn.CellEditEvent<DeliveryFX, Date> deliveryFXStringCellEditEvent) {
            DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
            deliveryFX.setDeliveryDate(deliveryFXStringCellEditEvent.getNewValue());
            deliveryFX.getDelivery().setDeliveryDate(deliveryFXStringCellEditEvent.getNewValue());
            handleBtnAdd(deliveryFX.getDelivery());
    }

    @FXML
    private void onEditCommitSupplier(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        //TODO:save value from comboboxcells
    }

    @FXML
    private void onEditCommitProduct(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setProduct(deliveryFXStringCellEditEvent.getNewValue());
        deliveryFX.getDelivery().setProduct(deliveryFXStringCellEditEvent.getNewValue());
        handleBtnAdd(deliveryFX.getDelivery());
    }

    @FXML
    private void onEditCommitPrice(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setPrice(deliveryFXStringCellEditEvent.getNewValue());
        deliveryFX.getDelivery().setPrice(deliveryFXStringCellEditEvent.getNewValue());
        handleBtnAdd(deliveryFX.getDelivery());
    }

    @FXML
    private void onEditCommitWorker(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        //TODO:save value from comboboxcells
    }
}
