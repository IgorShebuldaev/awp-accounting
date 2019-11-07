package org.accounting.forms.components;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import org.accounting.forms.models.tablemodels.DeliveryFX;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

public class DateTableCell extends TableCell<DeliveryFX, Date> {

    private DatePicker datePicker;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public DateTableCell() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();

            createDatePicker();
            setText(null);
            setGraphic(datePicker);
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getDate().toString());
        setGraphic(null);
    }

    @Override
    public void updateItem(Date item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue(getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(dateFormat.format(getDate()));
                setGraphic(null);
            }
        }
    }

    private void createDatePicker() {
        datePicker = new DatePicker(Instant.ofEpochMilli(getDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
        datePicker.setOnAction((e) -> {
            commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        });
    }

    private Date getDate() {
        return getItem() == null ? new Date() : getItem();
    }

}
