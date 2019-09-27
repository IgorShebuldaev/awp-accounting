package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Role;
import org.accounting.forms.models.RoleTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;

public class RolesForm extends JDialog implements ActionListener {
    private JPanel panelRolesForm;
    private JTable tableRoles;
    private JTextField textFieldRole;
    private JTextField lookupCode;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JLabel labelRole;
    private JScrollPane scrollPaneTableRoles;
    private RoleTable roleTableModel;

    RolesForm() {
        createRolesForm();
    }

    private void createRolesForm() {
        setContentPane(panelRolesForm);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setTitle("Roles");
        setModalityType(ModalityType.APPLICATION_MODAL);

        roleTableModel = new RoleTable();
        fillTable();
        tableRoles.setModel(roleTableModel);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }

    private void fillTable() {
        ArrayList<Role> results = Role.getAll();
        for (Role role : results) {
            roleTableModel.addRecord(role);
        }
    }

    private void insertData() {
        if (!isAnyEmptyField()) {
            return;
        }

        Role role = new Role(0, textFieldRole.getText(), lookupCode.getText());
        Role.insertData(role);
        roleTableModel.addRecord(role);
        textFieldRole.setText("");
        lookupCode.setText("");
    }

    private void deleteData() {
        int rowIndex = tableRoles.getSelectedRow();
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
            Role.deleteData(roleTableModel.getRecord(rowIndex).id);
            roleTableModel.removeRow(rowIndex);
        }
    }

    private void updateData() {
        int rowIndex = tableRoles.getSelectedRow();
        if (!isAnyEmptyField() || rowIndex < 0) {
            return;
        }

        Role role = new Role(roleTableModel.getRecord(rowIndex).id, textFieldRole.getText(), lookupCode.getText());
        Role.updateData(role);
        roleTableModel.setValueAt(role, rowIndex);
        setDefaultMode();
    }

    private void setValuesComponents() {
        int row = tableRoles.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        textFieldRole.setText(roleTableModel.getRecord(row).role);
        lookupCode.setText(roleTableModel.getRecord(row).lookupCode);
        setEditMode();
    }

    private boolean isAnyEmptyField() {
        String[] values = new String[]{textFieldRole.getText(), lookupCode.getText()};

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
        tableRoles.setEnabled(true);
        textFieldRole.setText("");
        lookupCode.setText("");
    }

    private void setEditMode() {
        addButton.setEnabled(false);
        editButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        deleteButton.setEnabled(false);
        tableRoles.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "addButton":
                insertData();
                break;
            case "editButton":
                setValuesComponents();
                break;
            case "saveButton":
                updateData();
                break;
            case "cancelButton":
                setDefaultMode();
                break;
            case "deleteButton":
                deleteData();
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
        panelRolesForm = new JPanel();
        panelRolesForm.setLayout(new GridLayoutManager(4, 5, new Insets(5, 5, 5, 5), -1, -1));
        scrollPaneTableRoles = new JScrollPane();
        panelRolesForm.add(scrollPaneTableRoles, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableRoles = new JTable();
        scrollPaneTableRoles.setViewportView(tableRoles);
        textFieldRole = new JTextField();
        panelRolesForm.add(textFieldRole, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelRole = new JLabel();
        labelRole.setText("Role");
        panelRolesForm.add(labelRole, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("addButton");
        addButton.setText("Add");
        panelRolesForm.add(addButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("editButton");
        editButton.setText("Edit");
        panelRolesForm.add(editButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("saveButton");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelRolesForm.add(saveButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancelButton");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelRolesForm.add(cancelButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("deleteButton");
        deleteButton.setText("Delete");
        panelRolesForm.add(deleteButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lookupCode = new JTextField();
        panelRolesForm.add(lookupCode, new GridConstraints(2, 3, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelRolesForm;
    }

}
