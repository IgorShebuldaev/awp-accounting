package org.accounting.forms.components;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import org.accounting.forms.models.tablemodels.DeliveryFX;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

        setText(dateFormat.format(getDate()));
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
                    datePicker.setValue(convertDateToLocaleDate(getDate()));
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
        datePicker = new DatePicker(convertDateToLocaleDate(getDate()));

        datePicker.setOnAction((e) -> {
            if (datePicker.getValue() != null) {
                commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            }
        });

        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private LocalDate convertDateToLocaleDate(Date date) {
        if (date != null) return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        return null;
    }

    private Date getDate() {
        return getItem() == null ? new Date() : getItem();
    }

}
