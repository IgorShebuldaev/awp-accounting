package org.accounting.forms.models;

import org.accounting.database.models.Delivery;

public class DeliveryTable extends MainTableModel {

    public DeliveryTable() {
        columnNames = new String[]{"Date delivery", "Company name", "Product", "Price", "Worker"};
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object result = null;
        Delivery delivery = (Delivery) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                result = delivery.deliveryDate;
                break;
            case 1:
                result = delivery.supplier;
                break;
            case 2:
                result = delivery.product;
                break;
            case 3:
                result = delivery.price;
                break;
            case 4:
                result = delivery.worker;
                break;
        }
        return result;
    }
}
