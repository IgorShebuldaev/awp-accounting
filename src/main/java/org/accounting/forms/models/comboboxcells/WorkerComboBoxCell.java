package org.accounting.forms.models.comboboxcells;

import javafx.collections.FXCollections;
import org.accounting.database.models.Worker;
import org.accounting.forms.models.tablemodels.DeliveryFX;
import org.accounting.forms.models.tablemodels.WorkerFX;

import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;

public class WorkerComboBoxCell extends BaseComboBoxCell<DeliveryFX, WorkerFX> {

    public WorkerComboBoxCell() {
        items = FXCollections.observableArrayList();
        ArrayList<Worker> results = Worker.getAll();
        for (Worker worker : results) {
            items.add(new WorkerFX(worker));
            values.put(worker.getFullName(), worker.getId());
        }
    }

    protected void createComboBox() {
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
}
