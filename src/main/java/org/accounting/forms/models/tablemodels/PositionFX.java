package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.Position;

import javafx.beans.property.SimpleStringProperty;

public class PositionFX  {
    private SimpleStringProperty name;
    private Position position;

    public PositionFX(Position position) {
        name = new SimpleStringProperty(position.getName());
        this.position = position;
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

}
