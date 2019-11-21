package org.accounting.forms.models.comboboxcells;

import org.accounting.forms.models.tablemodels.DeliveryFX;
import org.accounting.forms.models.tablemodels.SupplierFX;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class SupplierComboBoxCell extends BaseComboBoxCell<DeliveryFX, SupplierFX> {

    public SupplierComboBoxCell(ObservableList<SupplierFX> items) {
        this.items = items;
    }

    protected void createComboBox() {
        comboBox = new ComboBox<>(items);

        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction((e) -> {
                commitEdit(comboBox.getSelectionModel().getSelectedItem().getName());
        });

        comboBox.setCellFactory(new Callback<ListView<SupplierFX>, ListCell<SupplierFX>>() {
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

        comboBox.setConverter(new StringConverter<SupplierFX>() {
            @Override
            public String toString(SupplierFX supplierFX) {
                if (supplierFX == null){
                    return null;
                } else {
                    return supplierFX.getName();
                }
            }

            @Override
            public SupplierFX fromString(String str) {
                return null;
            }
        });

    }

}
