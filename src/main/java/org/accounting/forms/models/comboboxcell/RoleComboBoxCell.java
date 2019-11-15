package org.accounting.forms.models.comboboxcell;

import org.accounting.database.models.Role;
import org.accounting.forms.models.tablemodels.RoleFX;
import org.accounting.forms.models.tablemodels.UserFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class RoleComboBoxCell extends TableCell<UserFX, String> {
    private ComboBox<RoleFX> comboBox;
    private ObservableList<RoleFX> items = FXCollections.observableArrayList();

    public RoleComboBoxCell() {
        ArrayList<Role> results = Role.getAll();

        for (Role role: results) {
            items.add(new RoleFX(role));
        }
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

        comboBox.setCellFactory(new Callback<ListView<RoleFX>, ListCell<RoleFX>>() {
            @Override
            public ListCell<RoleFX> call(ListView<RoleFX> roleFXistView) {
                return new ListCell<RoleFX>() {
                    @Override
                    protected void updateItem(RoleFX item, boolean empty) {
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

        comboBox.setConverter(new StringConverter<RoleFX>() {
            @Override
            public String toString(RoleFX roleFX) {
                if (roleFX == null){
                    return null;
                } else {
                    return roleFX.getName();
                }
            }

            @Override
            public RoleFX fromString(String str) {
                return null;
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem();
    }

}
