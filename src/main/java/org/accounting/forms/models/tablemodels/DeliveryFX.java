package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Delivery;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;

public class DeliveryFX {
    private ObjectProperty<Date> deliveryDate;
    private SimpleStringProperty supplier;
    private SimpleStringProperty product;
    private SimpleStringProperty price;
    private SimpleStringProperty worker;
    private Delivery delivery;

    public DeliveryFX(Delivery delivery) {
        deliveryDate = new SimpleObjectProperty<>(delivery.getDeliveryDate());
        supplier = new SimpleStringProperty(delivery.getSupplier().getName());
        product = new SimpleStringProperty(delivery.getProduct());
        price = new SimpleStringProperty(delivery.getPrice());
        worker = new SimpleStringProperty(delivery.getWorker().getFullName());
        this.delivery = delivery;
    }

    public Date getDeliveryDate() {
        return deliveryDate.get();
    }

    public ObjectProperty<Date> deliveryDateProperty() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
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

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

}
