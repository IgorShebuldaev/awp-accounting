package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Role;

import javafx.beans.property.SimpleStringProperty;

public class RoleFX {
    private SimpleStringProperty name;
    private SimpleStringProperty lookupCode;
    private Role role;

    public RoleFX (Role role) {
        name = new SimpleStringProperty(role.getName());
        lookupCode = new SimpleStringProperty(role.getLookupCode());
        this.role = role;
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

    public String getLookupCode() {
        return lookupCode.get();
    }

    public SimpleStringProperty lookupCodeProperty() {
        return lookupCode;
    }

    public void setLookupCode(String lookupCode) {
        this.lookupCode.set(lookupCode);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
