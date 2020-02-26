package org.accounting.forms.models.comboboxcells;

import org.accounting.database.models.Position;
import org.accounting.forms.models.tablemodels.PositionFX;
import org.accounting.forms.models.tablemodels.WorkerFX;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class PositionComboBoxCell extends BaseComboBoxCell<WorkerFX, PositionFX> {

    public PositionComboBoxCell() {

        ArrayList<Position> results = Position.getAll();

        for (Position position : results) {
            items.add(new PositionFX(position));
            values.put(position.getName(), position.getId());
        }
    }

    protected void createComboBox() {
        comboBox = new ComboBox<>(items);

        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        comboBox.getSelectionModel().selectFirst();

        comboBox.setOnAction((e) -> commitEdit(comboBox.getSelectionModel().getSelectedItem().getName()));

        comboBox.setCellFactory(new Callback<ListView<PositionFX>, ListCell<PositionFX>>() {
            @Override
            public ListCell<PositionFX> call(ListView<PositionFX> positionFXListView) {
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
}
