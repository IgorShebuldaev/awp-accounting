package org.accounting.forms.models.comboboxcell;

import org.accounting.forms.models.tablemodels.DeliveryFX;
import org.accounting.forms.models.tablemodels.WorkerFX;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class WorkerComboBoxCell extends TableCell<DeliveryFX, String> {
    private ComboBox<WorkerFX> comboBox;
    private ObservableList<WorkerFX> items;

    public WorkerComboBoxCell(ObservableList<WorkerFX> items) {
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
            commitEdit(comboBox.getSelectionModel().getSelectedItem().getFullName());
        });

        comboBox.setCellFactory(new Callback<ListView<WorkerFX>, ListCell<WorkerFX>>() {
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

        comboBox.setConverter(new StringConverter<WorkerFX>() {
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

    private String getString() {
        return getItem() == null ? "" : getItem();
    }

}
