package org.accounting.forms.models.tablemodels;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import org.accounting.database.models.Worker;

import java.util.Date;

public class WorkerFX {
    private ObjectProperty<Date> dateOfBirth;
    private SimpleStringProperty fullName;
    private SimpleStringProperty position;
    private SimpleStringProperty userName;
    private Worker worker;

    public WorkerFX(Worker worker) {
        dateOfBirth = new SimpleObjectProperty<>(worker.getDateOfBirth());
        fullName = new SimpleStringProperty(worker.getFullName());
        position = new SimpleStringProperty(worker.getPosition().getName());
        userName = new SimpleStringProperty(worker.getUser().getEmail());
        this.worker = worker;
    }

    public Date getDateOfBirth() {
        return dateOfBirth.get();
    }

    public ObjectProperty<Date> dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    public String getPosition() {
        return position.get();
    }

    public SimpleStringProperty positionProperty() {
        return position;
    }

    public void setPosition(String position) {
        this.position.set(position);
    }

    public String getUserName() {
        return userName.get();
    }

    public SimpleStringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

}
