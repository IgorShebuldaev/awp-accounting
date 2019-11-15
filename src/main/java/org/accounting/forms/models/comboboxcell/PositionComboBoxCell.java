package org.accounting.forms.models.comboboxcell;

import org.accounting.forms.models.tablemodels.PositionFX;
import org.accounting.forms.models.tablemodels.WorkerFX;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PositionComboBoxCell extends TableCell<WorkerFX, String> {
    private ComboBox<PositionFX> comboBox;
    private ObservableList<PositionFX> items;

    public PositionComboBoxCell(ObservableList<PositionFX> items) {
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

        comboBox.setCellFactory(new Callback<ListView<PositionFX>, ListCell<PositionFX>>() {
            @Override
            public ListCell<PositionFX> call(ListView<PositionFX> positionFXistView) {
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

        comboBox.setConverter(new StringConverter<PositionFX>() {
            @Override
            public String toString(PositionFX positionFX) {
                if (positionFX == null){
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

    private String getString() {
        return getItem() == null ? "" : getItem();
    }

}
