package org.accounting.forms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.accounting.database.models.User;
import org.accounting.forms.helpers.AlertMessage;
import org.accounting.forms.models.comboboxcell.RoleComboBoxCell;
import org.accounting.forms.models.tablemodels.UserFX;
import org.accounting.forms.partials.UserFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersForm implements Initializable {
    @FXML private AnchorPane paneUserFields;
    @FXML private UserFields userFields;
    @FXML private TableView<UserFX> tableUsers;
    @FXML private TableColumn<UserFX, String> columnEmail;
    @FXML private TableColumn<UserFX, String> columnPassword;
    @FXML private TableColumn<UserFX, String> columnRole;
    @FXML private TableColumn<UserFX, Integer> columnTimeInProgram;
    @FXML private Label labelRole;
    private ObservableList<UserFX> data;

    public UsersForm() {
        data = FXCollections.observableArrayList();
        ArrayList<User> results = User.getAll();

        for (User user : results) {
            data.add(new UserFX(user));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        columnEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        columnPassword.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
        columnRole.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        columnTimeInProgram.setCellValueFactory(cellData -> cellData.getValue().timeInProgramProperty());

        columnEmail.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPassword.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRole.setCellFactory((TableColumn<UserFX, String> param) -> new RoleComboBoxCell());

        tableUsers.setItems(data);
    }

    public void showForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UsersForm.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Users");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBtnAdd() {
        User user = userFields.buildUser();

        if (!user.save()) {
            new AlertMessage("Error", user.getErrors().fullMessages()).showErrorMessage();
            return;
        }

        data.add(new UserFX(user));
    }

    @FXML
    private void handleBtnDelete() {
        UserFX userFX = tableUsers.getSelectionModel().getSelectedItem();

        if (new AlertMessage("Message","Are you sure you want to delete the record?").confirmationMessage()) {
            userFX.getUser().delete();
        }

        data.remove(userFX);
    }

    @FXML
    private void handleEditCommitEmail(TableColumn.CellEditEvent cellEditEvent) {
    }

    @FXML
    private void handleEditCommitPassword(TableColumn.CellEditEvent cellEditEvent) {
    }

    @FXML
    private void handleEditCommitRole(TableColumn.CellEditEvent cellEditEvent) {
    }

    @FXML
    private void handleEditCommitTimeInProgram(TableColumn.CellEditEvent cellEditEvent) {
    }

    public void setController(UserFields controller) {
        this.userFields = controller;
    }

}
