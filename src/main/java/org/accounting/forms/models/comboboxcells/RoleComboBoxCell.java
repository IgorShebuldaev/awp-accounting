package org.accounting.forms.models.comboboxcells;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.accounting.database.models.Role;
import org.accounting.forms.models.tablemodels.RoleFX;
import org.accounting.forms.models.tablemodels.UserFX;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class RoleComboBoxCell extends BaseComboBoxCell<UserFX, RoleFX> {
    private ObservableList<RoleFX> items = FXCollections.observableArrayList();

    public RoleComboBoxCell() {
        ArrayList<Role> results = Role.getAll();

        for (Role role: results) {
            items.add(new RoleFX(role));
        }
    }

    protected void createComboBox() {
        comboBox = new ComboBox<>(items);

        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction((e) -> {
            commitEdit(comboBox.getSelectionModel().getSelectedItem().getName());
        });

        comboBox.setCellFactory(new Callback<ListView<RoleFX>, ListCell<RoleFX>>() {
            @Override
            public ListCell<RoleFX> call(ListView<RoleFX> roleFXListView) {
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

}
