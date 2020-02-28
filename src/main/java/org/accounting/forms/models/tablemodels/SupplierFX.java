package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Supplier;

import javafx.beans.property.SimpleStringProperty;

public class SupplierFX  {
    private SimpleStringProperty name;
    private Supplier supplier;

    public SupplierFX(Supplier supplier) {
        name = new SimpleStringProperty(supplier.getName());
        this.supplier = supplier;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
