package org.accounting.forms.models.tablemodels;

import javafx.beans.property.SimpleStringProperty;
import org.accounting.database.models.Delivery;

public class DeliveryFX {
    private SimpleStringProperty deliveryDate = new SimpleStringProperty();
    private SimpleStringProperty supplier = new SimpleStringProperty();
    private SimpleStringProperty product = new SimpleStringProperty();
    private SimpleStringProperty price = new SimpleStringProperty();
    private SimpleStringProperty worker = new SimpleStringProperty();

    public DeliveryFX(Delivery delivery) {
        deliveryDate.set(toString(delivery.getDeliveryDate()));
        supplier.set(delivery.getSupplier().getName());
        product.set(delivery.getProduct());
        price.set(delivery.getPrice());
        worker.set(delivery.getWorker().getFullName());
    }

    public String getDeliveryDate() {
        return deliveryDate.get();
    }

    public SimpleStringProperty deliveryDateProperty() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate.set(deliveryDate);
    }

    public String getSupplier() {
        return supplier.get();
    }

    public SimpleStringProperty supplierProperty() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier.set(supplier);
    }

    public String getProduct() {
        return product.get();
    }

    public SimpleStringProperty productProperty() {
        return product;
    }

    public void setProduct(String product) {
        this.product.set(product);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }

    public String getWorker() {
        return worker.get();
    }

    public SimpleStringProperty workerProperty() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker.set(worker);
    }

    private String toString(Object object) {
        if (object != null) {
            return object.toString();
        }

        return "";
    }
}
