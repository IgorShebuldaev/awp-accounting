package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.forms.models.MainTableModel;
import org.accounting.database.models.Roles;
import org.accounting.database.models.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UsersForm extends JFrame implements ActionListener {
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
    private MainTableModel model;

    void createUsersForm() {
        setContentPane(panelUsersForm);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Users");
        setVisible(true);

        model = new MainTableModel();
        fillTableUsers();
        tableUsers.setModel(model);

        ArrayList<Roles> arrayListRoles = Roles.getRoles();
        for (Roles roles : arrayListRoles) {
            comboBoxRole.addItem(roles.role);
        }

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);
        addButtonRoles.addActionListener(this);
    }

    private void fillTableUsers() {
        model.setColumnIdentifiers(new String[]{"Email", "Password", "Role", "Time in program"});
        ArrayList<Users> arrayList = Users.getUsers();
        for (Users users : arrayList) {
            model.addRow(new Object[]{users.id, users.email, users.password, users.role, users.timeInProgram});
        }
    }

    private void addUser() {
        if (checkEmptyFields()) {
            Users users = new Users(0, textFieldEmail.getText(), textFieldPassword.getText(), (String) comboBoxRole.getSelectedItem(), 0);
            Users.insertUser(users);
            model.addRow(new Object[]{users.id, users.email, users.password, users.role, users.timeInProgram});
            textFieldEmail.setText("");
            textFieldPassword.setText("");
        }
    }

    private void deleteUser() {
        int row = tableUsers.getSelectedRow();
        if (row < 0) {
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
            Users.deleteUser((int) model.getRawValueAt(row, 0));
            model.removeRow(row);
        }
    }

    private void saveUser() {
        int row = tableUsers.getSelectedRow();
        if (checkEmptyFields()) {
            Users.updateUser(new Users(
                (int) model.getRawValueAt(row, 0),
                textFieldEmail.getText(),
                textFieldPassword.getText(),
                (String) comboBoxRole.getSelectedItem(),
                (int) model.getRawValueAt(row, 4)));
            model.setValueAt(new Object[]{
                model.getRawValueAt(row, 0),
                textFieldEmail.getText(),
                textFieldPassword.getText(),
                comboBoxRole.getSelectedItem(),
                model.getRawValueAt(row, 4)}, row);
            addButton.setEnabled(true);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            tableUsers.setEnabled(true);
            addButtonRoles.setEnabled(true);
            saveButton.setEnabled(false);
            cancelButton.setEnabled(false);
            textFieldEmail.setText("");
            textFieldPassword.setText("");
        }
    }

    private void setTextFields () {
        int row = tableUsers.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldEmail.setText((String) model.getRawValueAt(row, 1));
        textFieldPassword.setText((String) model.getRawValueAt(row, 2));
        comboBoxRole.setSelectedItem(model.getRawValueAt(row, 3));
        editButton.setEnabled(false);
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        tableUsers.setEnabled(false);
        addButtonRoles.setEnabled(false);
        cancelButton.setEnabled(true);
        saveButton.setEnabled(true);
    }

    private boolean checkEmptyFields () {
        if (textFieldEmail.getText().equals("") || textFieldPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Email or password cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private void addRolesForm () {
        new RolesForm().createRolesForm();
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
                addButton.setEnabled(true);
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                tableUsers.setEnabled(true);
                addButtonRoles.setEnabled(true);
                saveButton.setEnabled(false);
                cancelButton.setEnabled(false);
                textFieldEmail.setText("");
                textFieldPassword.setText("");
                break;
            case "addRoles":
                addRolesForm();
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
        panelUsersForm.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        scrollPaneTableUsers = new JScrollPane();
        scrollPaneTableUsers.setEnabled(true);
        panelUsersForm.add(scrollPaneTableUsers, new GridConstraints(0, 0, 8, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        comboBoxRole = new JComboBox();
        panelUsersForm.add(comboBoxRole, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelRole = new JLabel();
        labelRole.setText("Role");
        panelUsersForm.add(labelRole, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButtonRoles = new JButton();
        addButtonRoles.setText("Add roles");
        panelUsersForm.add(addButtonRoles, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("add");
        addButton.setText("Add");
        panelUsersForm.add(addButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("delete");
        deleteButton.setText("Delete");
        panelUsersForm.add(deleteButton, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("edit");
        saveButton.setEnabled(true);
        saveButton.setText("Edit");
        panelUsersForm.add(saveButton, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setText("Cancel");
        panelUsersForm.add(cancelButton, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelUsersForm;
    }
}

