package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.accounting.database.models.Base;
import org.accounting.database.models.User;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.UserTable;
import org.accounting.forms.partials.UserFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private UserFields userFields;

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

        User.getAll().forEach(userTableModel::addRecord);
        tableUsers.getTableHeader().setReorderingAllowed(false);
        tableUsers.setModel(userTableModel);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);
    }

    private void insertRecord() {
        User user = new User();
        user.setEmail(userFields.textFieldEmail.getText());
        user.setPassword(userFields.textFieldPassword.getText());
        user.setRoleId(userFields.roleModel.getSelection().map(Base::getId).orElse(0));

        if (!user.save()) {
            JOptionPane.showMessageDialog(this, user.getErrors().fullMessages("\n"));
            return;
        }

        userTableModel.addRecord(user);
        userFields.textFieldEmail.setText("");
        userFields.textFieldPassword.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableUsers.getSelectedRow();
        User user = userTableModel.getRecord(rowIndex);

        user.setEmail(userFields.textFieldEmail.getText());
        user.setPassword(userFields.textFieldPassword.getText());
        user.setRoleId(userFields.roleModel.getSelection().map(Base::getId).orElse(0));

        if (!user.save()) {
            JOptionPane.showMessageDialog(this, user.getErrors().fullMessages());
            return;
        }

        userTableModel.setValueAt(user, rowIndex);
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            userTableModel.getRecord(rowIndex).delete();
            userTableModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        User user = userTableModel.getRecord(rowIndex);

        userFields.textFieldEmail.setText(user.getEmail());
        userFields.textFieldPassword.setText(user.getPassword());
        userFields.comboBoxRoles.setSelectedItem(user.getRole().getRole());
        setEditMode();
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
                insertRecord();
                break;
            case "delete":
                deleteRecord();
                break;
            case "edit":
                setValues();
                break;
            case "save":
                saveRecord();
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
        panelUsersForm.setLayout(new GridLayoutManager(7, 3, new Insets(5, 5, 5, 5), -1, -1));
        scrollPaneTableUsers = new JScrollPane();
        scrollPaneTableUsers.setEnabled(true);
        panelUsersForm.add(scrollPaneTableUsers, new GridConstraints(0, 0, 7, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableUsers = new JTable();
        scrollPaneTableUsers.setViewportView(tableUsers);
        final Spacer spacer1 = new Spacer();
        panelUsersForm.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 15), null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("save");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelUsersForm.add(saveButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("delete");
        deleteButton.setText("Delete");
        panelUsersForm.add(deleteButton, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("add");
        addButton.setText("Add");
        panelUsersForm.add(addButton, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelUsersForm.add(spacer2, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("edit");
        editButton.setText("Edit");
        panelUsersForm.add(editButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancel");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelUsersForm.add(cancelButton, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        userFieldsPanel = new JPanel();
        userFieldsPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelUsersForm.add(userFieldsPanel, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        userFields = new UserFields();
        userFieldsPanel.add(userFields.$$$getRootComponent$$$(), new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelUsersForm;
    }
}
