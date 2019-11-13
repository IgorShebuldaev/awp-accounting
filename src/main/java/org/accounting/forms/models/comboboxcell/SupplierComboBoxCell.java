package org.accounting.forms.models.comboboxcell;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.accounting.forms.models.tablemodels.DeliveryFX;
import org.accounting.forms.models.tablemodels.SupplierFX;

public class SupplierComboBoxCell extends TableCell<DeliveryFX, String> {
    private ComboBox<SupplierFX> comboBox;
    private ObservableList<SupplierFX> items;

    public SupplierComboBoxCell(ObservableList<SupplierFX> items) {
        this.items = items;
    }

    @Override
    public void startEdit() {
        super.startEdit();

        if (comboBox == null) {
            createComboBox();
        }

        setGraphic(comboBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(String.valueOf(getItem()));
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (comboBox != null) {
                    comboBox.setValue(comboBox.getSelectionModel().getSelectedItem());
                }
                setGraphic(comboBox);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(getString());
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }

    private void createComboBox() {
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

    private String getString() {
        return getItem() == null ? "" : getItem();
    }
}
