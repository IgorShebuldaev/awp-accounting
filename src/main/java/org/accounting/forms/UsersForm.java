package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.accounting.database.models.Role;
import org.accounting.database.models.User;
import org.accounting.forms.models.UserTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsersForm extends JDialog implements ActionListener {
    private JPanel panelUsersForm;
    private JScrollPane scrollPaneTableUsers;
    private JTextField textFieldEmail;
    private JLabel labelEmail;
    private JTable tableUsers;
    private JTextField textFieldPassword;
    private JComboBox<String> comboBoxRole;
    private JLabel labelPassword;
    private JLabel labelRole;
    private JButton addButtonRoles;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton editButton;
    private UserTable model;

    UsersForm() {
      createUsersForm();
    }

    private void createUsersForm() {
        setContentPane(panelUsersForm);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Users");
        setModalityType(ModalityType.APPLICATION_MODAL);

        model = new UserTable();
        fillTableUsers();
        tableUsers.setModel(model);
        addItemComboBoxRole();

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);
        addButtonRoles.addActionListener(this);
    }

    private void fillTableUsers() {
        ArrayList<User> results = User.getAll();
        for (User user : results) {
            model.addRecord(user);
        }
    }

    private void addUser() {
        if (checkEmptyFields()) {
            User user = new User(0, textFieldEmail.getText(), textFieldPassword.getText(), (String) comboBoxRole.getSelectedItem(), 0);
            User.insertUser(user);
            model.addRecord(user);
            textFieldEmail.setText("");
            textFieldPassword.setText("");
        }
    }

    private void deleteUser() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(this,
                "Are you sure you want to delete the record?",
                "Message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        if (n == JOptionPane.YES_OPTION) {
            User.deleteUser(model.getRecord(rowIndex).id);
            model.removeRow(rowIndex);
        }
    }

    private void saveUser() {
        int rowIndex = tableUsers.getSelectedRow();
        if (checkEmptyFields()) {
            User user = new User(
                model.getRecord(rowIndex).id,
                textFieldEmail.getText(),
                textFieldPassword.getText(),
                (String) comboBoxRole.getSelectedItem(),
                model.getRecord(rowIndex).timeInProgram);

            User.updateUser(user);
            model.setValueAt(user, rowIndex);
            turnComponents(true);
        }
    }

    private void setTextFields() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldEmail.setText(model.getRecord(rowIndex).email);
        textFieldPassword.setText(model.getRecord(rowIndex).password);
        comboBoxRole.setSelectedItem(model.getRecord(rowIndex).role);
        turnComponents(false);
    }

    private boolean checkEmptyFields() {
        if (textFieldEmail.getText().equals("") || textFieldPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private void addItemComboBoxRole() {
        comboBoxRole.removeAllItems();
        ArrayList<Role> results = Role.getAll();
        for (Role role : results) {
            comboBoxRole.addItem(role.role);
        }
    }

    private void turnComponents(Boolean turn) {
        if (!turn) {
            addButton.setEnabled(false);
            editButton.setEnabled(false);
            deleteButton.setEnabled(false);
            tableUsers.setEnabled(false);
            addButtonRoles.setEnabled(false);
            cancelButton.setEnabled(true);
            saveButton.setEnabled(true);
        } else {
            addButton.setEnabled(true);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            tableUsers.setEnabled(true);
            addButtonRoles.setEnabled(true);
            cancelButton.setEnabled(false);
            saveButton.setEnabled(false);
            textFieldEmail.setText("");
            textFieldPassword.setText("");
        }
    }

    private void showRolesForm() {
        new RolesForm().setVisible(true);
        addItemComboBoxRole();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                addUser();
                break;
            case "delete":
                deleteUser();
                break;
            case "edit":
                setTextFields();
                break;
            case "save":
                saveUser();
                break;
            case "cancel":
                turnComponents(true);
                break;
            case "addRoles":
                showRolesForm();
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
        labelEmail = new JLabel();
        labelEmail.setText("Email");
        panelUsersForm.add(labelEmail, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(32, 26), null, 0, false));
        textFieldEmail = new JTextField();
        panelUsersForm.add(textFieldEmail, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelPassword = new JLabel();
        labelPassword.setText("Password");
        panelUsersForm.add(labelPassword, new GridConstraints(2, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldPassword = new JTextField();
        panelUsersForm.add(textFieldPassword, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
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
        labelRole = new JLabel();
        labelRole.setText("Role");
        panelUsersForm.add(labelRole, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("add");
        addButton.setText("Add");
        panelUsersForm.add(addButton, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxRole = new JComboBox();
        panelUsersForm.add(comboBoxRole, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        addButtonRoles = new JButton();
        addButtonRoles.setActionCommand("addRoles");
        addButtonRoles.setHorizontalTextPosition(11);
        addButtonRoles.setMargin(new Insets(0, 0, 0, 0));
        addButtonRoles.setText("...");
        addButtonRoles.setToolTipText("");
        addButtonRoles.setVerticalTextPosition(0);
        panelUsersForm.add(addButtonRoles, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelUsersForm;
    }
}
