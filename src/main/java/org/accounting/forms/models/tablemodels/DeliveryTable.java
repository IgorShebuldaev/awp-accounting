package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Delivery;

import java.text.SimpleDateFormat;

public class DeliveryTable extends MainTableModel {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public DeliveryTable() {
        columnNames = new String[]{"Date delivery", "Company name", "Product", "Price", "Worker"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Delivery delivery = (Delivery) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return dateFormat.format(delivery.deliveryDate);
            case 1:
                return delivery.supplier;
            case 2:
                return delivery.product;
            case 3:
                return delivery.price;
            case 4:
                return delivery.worker;
        }
        return "";
    }

    @Override
    public Delivery getRecord(int rowIndex) {
        return (Delivery) super.getRecord(rowIndex);
    }
}
