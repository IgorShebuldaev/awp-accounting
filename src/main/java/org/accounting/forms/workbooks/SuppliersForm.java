package org.accounting.forms.workbooks;

import org.accounting.database.models.Supplier;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.tablemodels.SupplierFX;

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

public class SuppliersForm implements Initializable {
    @FXML private TableView<SupplierFX> tableSuppliers;
    @FXML private TableColumn<SupplierFX, String> columnCompanyName;
    @FXML private TextField tfCompanyName;
    private ObservableList<SupplierFX> data;

    public SuppliersForm() {
        data = FXCollections.observableArrayList();
        ArrayList<Supplier> results = Supplier.getAll();

        for(Supplier supplier: results) {
            data.add(new SupplierFX(supplier));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnCompanyName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        columnCompanyName.setCellFactory(TextFieldTableCell.forTableColumn());

        tableSuppliers.setItems(data);
    }

    @FXML
    private void handleBtnAdd() {
        Supplier supplier = new Supplier();
        supplier.setName(tfCompanyName.getText());

        if (!supplier.save()) {
            new AlertMessage("Error", supplier.getErrors().fullMessages()).showErrorMessage();
            return;
        }

        data.add(new SupplierFX(supplier));
    }

    @FXML
    private void handleBtnAdd(Supplier supplier) {
        if (!supplier.save()) {
            new AlertMessage("Error", supplier.getErrors().fullMessages()).showErrorMessage();
        }
    }

    @FXML
    private void handleBtnDelete() {
        SupplierFX supplierFX = tableSuppliers.getSelectionModel().getSelectedItem();

        if (new AlertMessage("Message","Are you sure you want to delete the record?").showConfirmationMessage()) {
            supplierFX.getSupplier().delete();
            data.remove(supplierFX);
        }
    }

    @FXML
    private void onEditCommitCompanyName(TableColumn.CellEditEvent<SupplierFX, String> supplierFXStringCellEditEvent) {
        SupplierFX supplierFX = tableSuppliers.getSelectionModel().getSelectedItem();
        supplierFX.setName(supplierFXStringCellEditEvent.getNewValue());
        supplierFX.getSupplier().setName(supplierFXStringCellEditEvent.getNewValue());
        handleBtnAdd(supplierFX.getSupplier());
    }

}
