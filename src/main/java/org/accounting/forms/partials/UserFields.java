package org.accounting.forms.partials;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.accounting.database.models.Role;
import org.accounting.database.models.User;
import org.accounting.forms.models.tablemodels.RoleFX;
import org.accounting.user.CurrentUser;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserFields implements Initializable {
    @FXML private TextField tfEmail;
    @FXML private TextField tfPassword;
    @FXML private Label labelRole;
    @FXML private ComboBox<RoleFX> cbRole;
    @FXML private Button btnShowRolesForm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(CurrentUser.getUser().getRole().isAdmin())) {
            labelRole.setVisible(false);
            cbRole.setVisible(false);
            btnShowRolesForm.setVisible(false);
        }

        addItemComboBoxRole();


    }

    public UserFields() {

    }

    private void addItemComboBoxRole() {
        ObservableList<RoleFX> items = FXCollections.observableArrayList();
        ArrayList<Role> results = Role.getAll();
        for (Role role: results) {
            items.add(new RoleFX(role));
        }

        cbRole.setCellFactory(new Callback<ListView<RoleFX>, ListCell<RoleFX>>() {
            @Override
            public ListCell<RoleFX> call(ListView<RoleFX> roleFXListView) {
                return new ListCell<RoleFX>() {
                    @Override
                    protected void updateItem(RoleFX item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getName());
                        }
                    }
                };
            }
        });

        cbRole.setConverter(new StringConverter<RoleFX>() {
            @Override
            public String toString(RoleFX roleFX) {
                if (roleFX == null){
                    return null;
                } else {
                    return roleFX.getName();
                }
            }

            @Override
            public RoleFX fromString(String name) {
                return null;
            }
        });

        cbRole.setItems(items);
        cbRole.getSelectionModel().selectFirst();
    }

    public User buildUser() {
        User user = new User();
        user.setEmail(tfEmail.getText());
        user.setPassword(tfPassword.getText());
        user.setRoleId(cbRole.getSelectionModel().getSelectedItem().getRole().getId());

        return user;
    }

    public void handleBtnShowRolesForm(javafx.event.ActionEvent actionEvent) {

    }

}
