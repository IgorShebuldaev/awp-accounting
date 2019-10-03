package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.accounting.database.models.Role;
import org.accounting.database.models.User;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.UserTable;
import org.accounting.forms.partials.UserFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class UsersForm extends JDialog implements ActionListener {
    private JPanel panelUsersForm;
    private JScrollPane scrollPaneTableUsers;
    private JTable tableUsers;

    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton editButton;
    public JPanel userFieldsPanel;
    private UserTable userTableModel;
    UserFields userFields;

    UsersForm() {
        createUsersForm();
    }

    private void createUsersForm() {
        setContentPane(panelUsersForm);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Users");
        setModalityType(ModalityType.APPLICATION_MODAL);

        userTableModel = new UserTable();
        fillTable();
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.setModel(userTableModel);


        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        userFields = new UserFields();

        userFieldsPanel.add(userFields.userFieldsPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(400, 300), null, null, 1, false));
    }

    private void fillTable() {
        ArrayList<User> results = User.getAll();
        for (User user : results) {
            userTableModel.addRecord(user);
        }
    }

    private void insertData() {
        if (isAnyEmptyField()) {
            User user = new User(0, userFields.textFieldEmail.getText(), userFields.textFieldPassword.getText(), (int) userFields.comboBoxRoles.getSelectedItem(), 0);
            User.insertData(user);
            userTableModel.addRecord(user);
            userFields.textFieldEmail.setText("");
            userFields.textFieldPassword.setText("");
        }
    }

    private void deleteData() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            User.deleteData(userTableModel.getRecord(rowIndex).id);
            userTableModel.removeRow(rowIndex);
        }
    }

    private void updateData() {
        int rowIndex = tableUsers.getSelectedRow();
        User user = userTableModel.getRecord(rowIndex);

        user.email = userFields.textFieldEmail.getText();
        user.password = userFields.textFieldPassword.getText();
        user.roleId = ((Role) userFields.roleModel.getSelection()).id;

        if (!user.save()) {
            JOptionPane.showMessageDialog(this, user.getErrors().fullMessages());
            return;
        }

        userTableModel.fireTableRowsUpdated(rowIndex, rowIndex);
        setDefaultMode();
    }

    private void setValuesComponents() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        userFields.textFieldEmail.setText(userTableModel.getRecord(rowIndex).email);
        userFields.textFieldPassword.setText(userTableModel.getRecord(rowIndex).password);
        userFields.comboBoxRoles.setSelectedItem(userTableModel.getRecord(rowIndex).getRole().role);
        setEditMode();
    }

    private boolean isAnyEmptyField() {
        String[] values = new String[]{
            userFields.textFieldEmail.getText(),
            userFields.textFieldPassword.getText(),
            (String) userFields.comboBoxRoles.getSelectedItem()};

        if (Arrays.stream(values).anyMatch(String::isEmpty)) {
            JOptionPane.showMessageDialog(this, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private void setDefaultMode() {
        addButton.setEnabled(true);
        editButton.setEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        deleteButton.setEnabled(true);
        tableUsers.setEnabled(true);
        userFields.textFieldEmail.setText("");
        userFields.textFieldPassword.setText("");
    }

    private void setEditMode() {
        addButton.setEnabled(false);
        editButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        deleteButton.setEnabled(false);
        tableUsers.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                insertData();
                break;
            case "delete":
                deleteData();
                break;
            case "edit":
                setValuesComponents();
                break;
            case "save":
                updateData();
                break;
            case "cancel":
                setDefaultMode();
                break;
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelUsersForm = new JPanel();
        panelUsersForm.setLayout(new GridLayoutManager(12, 3, new Insets(5, 5, 5, 5), -1, -1));
        scrollPaneTableUsers = new JScrollPane();
        scrollPaneTableUsers.setEnabled(true);
        panelUsersForm.add(scrollPaneTableUsers, new GridConstraints(0, 0, 12, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableUsers = new JTable();
        scrollPaneTableUsers.setViewportView(tableUsers);
        final Spacer spacer1 = new Spacer();
        panelUsersForm.add(spacer1, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 15), null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("save");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelUsersForm.add(saveButton, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("delete");
        deleteButton.setText("Delete");
        panelUsersForm.add(deleteButton, new GridConstraints(11, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("add");
        addButton.setText("Add");
        panelUsersForm.add(addButton, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelUsersForm.add(spacer2, new GridConstraints(10, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("edit");
        editButton.setText("Edit");
        panelUsersForm.add(editButton, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancel");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelUsersForm.add(cancelButton, new GridConstraints(9, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userFieldsPanel = new JPanel();
        userFieldsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelUsersForm.add(userFieldsPanel, new GridConstraints(0, 1, 6, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelUsersForm;
    }

}
