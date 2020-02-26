package org.accounting.forms.models.comboboxcells;

import org.accounting.database.models.Position;
import org.accounting.database.models.Supplier;
import org.accounting.forms.models.tablemodels.DeliveryFX;
import org.accounting.forms.models.tablemodels.SupplierFX;

import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class SupplierComboBoxCell extends BaseComboBoxCell<DeliveryFX, SupplierFX> {

    public SupplierComboBoxCell() {
        //ArrayList<Supplier> results = Supplier.getAll();

        ArrayList<Supplier> results = ComboBoxLoader.getInstance().getData(Supplier.class);
        for (Supplier supplier : results) {
            items.add(new SupplierFX(supplier));
            values.put(supplier.getName(), supplier.getId());
        }
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
