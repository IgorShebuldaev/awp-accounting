package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.accounting.database.models.Base;
import org.accounting.database.models.Role;
import org.accounting.database.models.User;
import org.accounting.forms.models.comboboxmodels.MainComboBoxModel;
import org.accounting.forms.models.comboboxmodels.RoleComboBoxModel;
import org.accounting.forms.models.tablemodels.UserTable;

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
    private JComboBox<Base> comboBoxRoles;
    private MainComboBoxModel roleModel;
    private JLabel labelPassword;
    private JLabel labelRole;
    private JButton showRolesFormButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton editButton;
    private UserTable userTableModel;

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
        tableUsers.setModel(userTableModel);
        roleModel = new RoleComboBoxModel();
        addItemComboBoxRole();
        comboBoxRoles.setModel(roleModel);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);
        showRolesFormButton.addActionListener(this);
    }

    private void fillTable() {
        ArrayList<User> results = User.getAll();
        for (User user : results) {
            userTableModel.addRecord(user);
        }
    }

    private void insertData() {
        if (checkEmptyFields()) {
            User user = new User(0, textFieldEmail.getText(), textFieldPassword.getText(), (int) comboBoxRoles.getSelectedItem(), 0);
            User.insertData(user);
            userTableModel.addRecord(user);
            textFieldEmail.setText("");
            textFieldPassword.setText("");
        }
    }

    private void deleteData() {
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
            User.deleteData(userTableModel.getRecord(rowIndex).id);
            userTableModel.removeRow(rowIndex);
        }
    }

    private void updateData() {
        int rowIndex = tableUsers.getSelectedRow();
        if (checkEmptyFields()) {
            User user = new User(
                userTableModel.getRecord(rowIndex).id,
                textFieldEmail.getText(),
                textFieldPassword.getText(),
                ((Role) roleModel.getSelection()).id,
                userTableModel.getRecord(rowIndex).timeInProgram);

            User.updateData(user);
            userTableModel.setValueAt(user, rowIndex);
            turnComponents(true);
        }
    }

    private void setValuesComponents() {
        int rowIndex = tableUsers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldEmail.setText(userTableModel.getRecord(rowIndex).email);
        textFieldPassword.setText(userTableModel.getRecord(rowIndex).password);
        comboBoxRoles.setSelectedItem(userTableModel.getRecord(rowIndex).getRole().role);
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
        ArrayList<Role> results = Role.getAll();
        for (Role role : results) {
            roleModel.addRecord(role);
        }
    }

    private void turnComponents(Boolean turn) {
        if (!turn) {
            addButton.setEnabled(false);
            editButton.setEnabled(false);
            saveButton.setEnabled(true);
            cancelButton.setEnabled(true);
            deleteButton.setEnabled(false);
            showRolesFormButton.setEnabled(false);
            tableUsers.setEnabled(false);
        } else {
            addButton.setEnabled(true);
            editButton.setEnabled(true);
            saveButton.setEnabled(false);
            cancelButton.setEnabled(false);
            deleteButton.setEnabled(true);
            showRolesFormButton.setEnabled(true);
            tableUsers.setEnabled(true);
            textFieldEmail.setText("");
            textFieldPassword.setText("");
        }
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
                turnComponents(true);
                break;
            case "addRoles":
                new RolesForm().setVisible(true);
                addItemComboBoxRole();
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
        comboBoxRoles = new JComboBox();
        panelUsersForm.add(comboBoxRoles, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        showRolesFormButton = new JButton();
        showRolesFormButton.setActionCommand("addRoles");
        showRolesFormButton.setHorizontalTextPosition(11);
        showRolesFormButton.setMargin(new Insets(0, 0, 0, 0));
        showRolesFormButton.setText("...");
        showRolesFormButton.setToolTipText("");
        showRolesFormButton.setVerticalTextPosition(0);
        panelUsersForm.add(showRolesFormButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelUsersForm;
    }
}
