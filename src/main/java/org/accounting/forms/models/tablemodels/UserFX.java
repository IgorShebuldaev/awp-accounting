package org.accounting.forms.models.tablemodels;

import org.accounting.database.models.User;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class UserFX  {
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleStringProperty role;

    public Integer getTimeInProgram() {
        return timeInProgram.getValue();
    }

    public ObservableValue<Integer> timeInProgramProperty() {
        return timeInProgram;
    }

    private ObservableValue<Integer> timeInProgram;
    private User user;

    public UserFX(User user) {
        email = new SimpleStringProperty(user.getEmail());
        password = new SimpleStringProperty(user.getPassword());
        role = new SimpleStringProperty(user.getRole().getName());
        timeInProgram = new SimpleIntegerProperty(user.getTimeInProgram()).asObject();
        this.user = user;
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getRole() {
        return role.get();
    }

    public SimpleStringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
