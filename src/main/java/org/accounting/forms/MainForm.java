package org.accounting.forms;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import org.accounting.database.models.Delivery;
import org.accounting.forms.components.DateTableCell;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.tablemodels.DeliveryFX;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class MainForm implements Initializable {

    @FXML
    private Label labelStatusBar;
    @FXML
    private TableView<DeliveryFX> tableDeliveries;
    @FXML
    private TableColumn<DeliveryFX, Date> columnDeliveryDate;
    @FXML
    private TableColumn<DeliveryFX, String> columnSupplier;
    @FXML
    private TableColumn<DeliveryFX, String> columnProduct;
    @FXML
    private TableColumn<DeliveryFX, String> columnPrice;
    @FXML
    private TableColumn<DeliveryFX, String> columnWorker;

    private ObservableList<DeliveryFX> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        ArrayList<Delivery> results = Delivery.getAll();

        for (Delivery delivery : results) {
            data.add(new DeliveryFX(delivery));
        }

        columnDeliveryDate.setCellValueFactory(cellData -> cellData.getValue().deliveryDateProperty());
        columnSupplier.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        columnProduct.setCellValueFactory(cellData -> cellData.getValue().productProperty());
        columnPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        columnWorker.setCellValueFactory(cellData -> cellData.getValue().workerProperty());
        tableDeliveries.setItems(data);

        tableDeliveries.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        columnDeliveryDate.setCellFactory((TableColumn<DeliveryFX, Date> param) -> new DateTableCell());
        columnSupplier.setCellFactory(TextFieldTableCell.forTableColumn());
        columnProduct.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPrice.setCellFactory(TextFieldTableCell.forTableColumn());
        columnWorker.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    public MainForm() {

    }

   public void showForm() {
        try {
            Parent root = FXMLLoader.load((getClass()).getResource("MainForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Accounting");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void updateStatusBar() {
//        User currentUser = CurrentUser.getUser();
//
//        currentUser.incrementTimeInProgram(1);
//        labelStatusBar.setText(
//                String.format("User: %s. Role: %s. Time in program: %s",
//                        currentUser.getEmail(),
//                        currentUser.getRole().getName(),
//                        currentUser.getFormattedTimeInProgram()
//                )
//        );
//    }


    @FXML
    private void handleMiWorkBooks() {
    }

    @FXML
    private void handleMiNotes() {
    }

    @FXML
    private void handleMiLogOut() {
    }

    @FXML
    private void handleMiExit() {
        if (new AlertMessage("Are you sure you want to exit?", "Confirm Exit").confirmationMessage()) {
            Platform.exit();
        }
    }

    @FXML
    private void handleMiDeliveries() {
    }

    @FXML
    private void handleMiTimeInProgram() {
    }

    @FXML
    private void handleMiUsers() {
    }

    @FXML
    private void handleMiRoles() {
    }

    @FXML
    private void handleMiAbout() {
    }

    @FXML
    private void handleBtnAdd() {
        data.add(new DeliveryFX(new Delivery()));
        int row = data.size() - 1;
        tableDeliveries.getSelectionModel().select(row);
        tableDeliveries.getSelectionModel().focus(row);
    }

    @FXML
    private void handleBtnSave() {
        DeliveryFX deliveryFX  = tableDeliveries.getSelectionModel().getSelectedItem();
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate(deliveryFX.getDeliveryDate());
        delivery.setSupplierId(Integer.parseInt(deliveryFX.getSupplier()));
        delivery.setProduct(deliveryFX.getProduct());
        delivery.setPrice(deliveryFX.getPrice());
        delivery.setWorkerId(Integer.parseInt(deliveryFX.getWorker()));

        if (!delivery.save()) {
        new AlertMessage("Error", delivery.getErrors().fullMessages("\n"));
        }
    }

    @FXML
    private void handleBtnCancel() {
    }

    @FXML
    private void handleBtnDelete() {
    }

    @FXML
    private void onEditCommitDeliveryDate(TableColumn.CellEditEvent<DeliveryFX, Date> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setDeliveryDate(deliveryFXStringCellEditEvent.getNewValue());
    }

    @FXML
    private void onEditCommitSupplier(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setSupplier(deliveryFXStringCellEditEvent.getNewValue());
    }

    @FXML
    private void onEditCommitProduct(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setProduct(deliveryFXStringCellEditEvent.getNewValue());
    }

    @FXML
    private void onEditCommitPrice(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setPrice(deliveryFXStringCellEditEvent.getNewValue());
    }

    @FXML
    private void onEditCommitWorker(TableColumn.CellEditEvent<DeliveryFX, String> deliveryFXStringCellEditEvent) {
        DeliveryFX deliveryFX = tableDeliveries.getSelectionModel().getSelectedItem();
        deliveryFX.setWorker(deliveryFXStringCellEditEvent.getNewValue());
    }


//    private void addItemComboBoxSupplier() {
//        supplierComboBoxModel.removeAllElements();
//        Supplier.getAll().forEach(supplierComboBoxModel::addRecord);
//        comboBoxSuppliers.setModel(supplierComboBoxModel);
//    }
//
//    private void addItemComboBoxWorker() {
//        workerComboBoxModel.removeAllElements();
//        Worker.getAll().forEach(workerComboBoxModel::addRecord);
//        comboBoxWorkers.setModel(workerComboBoxModel);
//    }
//
//    private void saveRecord() {
//        Delivery delivery;
//
//        if (isNewRecord) {
//            delivery = getNewValues(new Delivery());
//        } else {
//            delivery = getNewValues(deliveryTableModel.getRecord(getRowIndex()));
//        }
//
//        if (!delivery.save()) {
//            JOptionPane.showMessageDialog(this, delivery.getErrors().fullMessages("\n"));
//            return;
//        }
//
//        if (isNewRecord) {
//            deliveryTableModel.addRecord(delivery);
//            textFieldProduct.setText("");
//            textFieldPrice.setText("");
//        } else {
//            deliveryTableModel.setValueAt(delivery, getRowIndex());
//            setDefaultMode();
//            isNewRecord = true;
//        }
//    }
//
//    private void deleteRecord() {
//        if (getRowIndex() < 0) {
//            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
//            return;
//        }
//
//        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
//            deliveryTableModel.getRecord(getRowIndex()).delete();
//            deliveryTableModel.removeRow(getRowIndex());
//        }
//    }
//
//    private void setValues() {
//        if (getRowIndex() < 0) {
//            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
//            return;
//        }
//
//        Delivery delivery = deliveryTableModel.getRecord(getRowIndex());
//        spinnerDeliveriesDeliveryDate.setValue(delivery.getDeliveryDate());
//        comboBoxSuppliers.setSelectedItem(delivery.getSupplier().getName());
//        textFieldProduct.setText(delivery.getProduct());
//        textFieldPrice.setText(delivery.getPrice());
//        comboBoxWorkers.setSelectedItem(delivery.getWorker().getFullName());
//        setEditMode();
//        isNewRecord = false;
//    }
//
//    private Delivery getNewValues(Delivery delivery) {
//        if (delivery == null) {
//            delivery = new Delivery();
//        }
//        delivery.setDeliveryDate((Date) spinnerDeliveriesDeliveryDate.getValue());
//        delivery.setSupplierId(supplierComboBoxModel.getSelection().map(Base::getId).orElse(0));
//        delivery.setProduct(textFieldProduct.getText());
//        delivery.setPrice(textFieldPrice.getText());
//        delivery.setWorkerId(workerComboBoxModel.getSelection().map(Base::getId).orElse(0));
//
//        return delivery;
//    }
////
//    private int getRowIndex() {
//        return tableDeliveries.getSelectionModel().get
//    }
//
//    private void setDefaultMode() {
//        addButton.setEnabled(true);
//        editButton.setEnabled(true);
//        saveButton.setEnabled(false);
//        cancelButton.setEnabled(false);
//        deleteButton.setEnabled(true);
//        tableDeliveries.setEnabled(true);
//        textFieldProduct.setText("");
//        textFieldPrice.setText("");
//    }
//
//    private void setEditMode() {
//        addButton.setEnabled(false);
//        editButton.setEnabled(false);
//        saveButton.setEnabled(true);
//        cancelButton.setEnabled(true);
//        deleteButton.setEnabled(false);
//        tableDeliveries.setEnabled(false);
//    }
//
//    @Override
//    public void valueChanged(ListSelectionEvent e) {
//        setValues();
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent event) {
//        switch (event.getActionCommand()) {
//            case "workBooks":
//                new WorkBooksForm().setVisible(true);
//                addItemComboBoxWorker();
//                addItemComboBoxSupplier();
//                break;
//            case "notes":
//                new NotesForm().setVisible(true);
//                break;
//            case "logout":
//                dispose();
//
//                break;
//            case "exit":
//                dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
//                break;
//            case "users":
//                new UsersForm().setVisible(true);
//                break;
//            case "roles":
//                new RolesForm().setVisible(true);
//                break;
//            case "reports":
//                new ReportsForm().setVisible(true);
//                break;
//            case "chart":
//                new ChartForm().setVisible(true);
//                break;
//            case "about":
//                JOptionPane.showMessageDialog(null, "Copyright © 2019 devTeam ");
//                break;
//            case "addButton":
//            case "saveButton":
//                saveRecord();
//                break;
//            case "editButton":
//                setValues();
//                break;
//            case "cancelButton":
//                setDefaultMode();
//                break;
//            case "deleteButton":
//                deleteRecord();
//                break;
//        }
//    }

}
