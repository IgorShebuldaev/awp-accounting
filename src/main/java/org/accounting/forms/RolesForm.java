package org.accounting.forms;

import javafx.stage.Stage;
import org.accounting.database.models.Role;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.tablemodels.RoleFX;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RolesForm extends BaseController implements Initializable {
    @FXML private TableView<RoleFX> tableRoles;
    @FXML private TableColumn<RoleFX, String> columnRoleName;
    @FXML private TableColumn<RoleFX, String> columnLookupCode;
    @FXML private TextField tfNameRole;
    @FXML private TextField tfLookupCode;
    private ObservableList<RoleFX> data;

    public RolesForm() {
        data = FXCollections.observableArrayList();
        ArrayList<Role> results = Role.getAll();

        for(Role role: results) {
            data.add(new RoleFX(role));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnRoleName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnLookupCode.setCellValueFactory(cellData -> cellData.getValue().lookupCodeProperty());

        columnRoleName.setCellFactory(TextFieldTableCell.forTableColumn());

        tableRoles.setItems(data);
    }

    @FXML
    private void handleBtnAdd() {
        Role role = new Role();
        role.setName(tfNameRole.getText());
        role.setLookupCode(tfLookupCode.getText());

        if (!role.save()) {
            new AlertMessage("Error", role.getErrors().fullMessages()).showErrorMessage();
            return;
        }

        data.add(new RoleFX(role));
    }

    @FXML
    private void handleBtnAdd(Role role) {
        if (!role.save()) {
            new AlertMessage("Error", role.getErrors().fullMessages()).showErrorMessage();
        }
    }

    @FXML
    private void handleBtnDelete() {
        RoleFX roleFX = tableRoles.getSelectionModel().getSelectedItem();

        if (new AlertMessage("Message","Are you sure you want to delete the record?").showConfirmationMessage()) {
            roleFX.getRole().delete();
            data.remove(roleFX);
        }
    }

    @FXML
    private void onEditCommitName(TableColumn.CellEditEvent<RoleFX, String> roleFXStringCellEditEvent) {
        RoleFX roleFX = tableRoles.getSelectionModel().getSelectedItem();
        roleFX.setName(roleFXStringCellEditEvent.getNewValue());
        roleFX.getRole().setName(roleFXStringCellEditEvent.getNewValue());
        handleBtnAdd(roleFX.getRole());
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Roles");
    }
}
