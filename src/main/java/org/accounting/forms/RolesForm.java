package org.accounting.forms;

import org.accounting.database.models.Roles;
import org.accounting.forms.models.MainTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RolesForm extends JDialog implements ActionListener {
    private JPanel panaelRolesForm;
    private JTable tableRoles;
    private JTextField textFieldRole;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JLabel labelRole;
    private JScrollPane scrolPaneTableRoles;
    private MainTableModel model;

    public void createRolesForm () {
        setContentPane(panaelRolesForm);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setTitle("Roles");
        setModalityType(ModalityType.APPLICATION_MODAL);

        model = new MainTableModel();
        fillTableRoles();
        tableRoles.setModel(model);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        deleteButton.addActionListener(this);

        setVisible(true);
    }

    private void fillTableRoles() {
        model.setColumnIdentifiers(new String[]{"Role"});
        ArrayList<Roles> results = Roles.getRoles();
        for (Roles role : results) {
            model.addRow(new Object[]{role.id, role.role});
        }
    }

    private void addRole() {
        if (checkEmptyFields()) {
            Roles role = new Roles(0, textFieldRole.getText());
            Roles.insertRole(role);
            model.addRow(new Object[]{role.id, role.role});
            textFieldRole.setText("");
        }
    }

    private void deleteRole() {
        int row = tableRoles.getSelectedRow();
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
            Roles.deleteRole((int) model.getRawValueAt(row, 0));
            model.removeRow(row);
        }
    }

    private void saveRole() {
        int row = tableRoles.getSelectedRow();
        if (checkEmptyFields()) {
            Roles.updateRole(new Roles((int) model.getRawValueAt(row, 0), textFieldRole.getText()));
            model.setValueAt(new Object[]{model.getRawValueAt(row, 0), textFieldRole.getText()}, row);
            addButton.setEnabled(true);
            editButton.setEnabled(true);
            deleteButton.setEnabled(true);
            tableRoles.setEnabled(true);
            saveButton.setEnabled(false);
            cancelButton.setEnabled(false);
            textFieldRole.setText("");
        }
    }

    private void setTextFields () {
        int row = tableRoles.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldRole.setText((String)model.getRawValueAt(row, 1));
        editButton.setEnabled(false);
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        tableRoles.setEnabled(false);
        cancelButton.setEnabled(true);
        saveButton.setEnabled(true);
    }

    private boolean checkEmptyFields () {
        if (textFieldRole.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                addRole();
                break;
            case "delete":
                deleteRole();
                break;
            case "edit":
                setTextFields();
                break;
            case "save":
                saveRole();
                break;
            case "cancel":
                addButton.setEnabled(true);
                editButton.setEnabled(true);
                deleteButton.setEnabled(true);
                tableRoles.setEnabled(true);
                saveButton.setEnabled(false);
                cancelButton.setEnabled(false);
                textFieldRole.setText("");
                break;
        }
    }
}