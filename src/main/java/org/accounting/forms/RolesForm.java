package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Role;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.RoleTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public RolesForm() {
        createRolesForm();
    }

    private void createRolesForm() {
        setContentPane(panelRolesForm);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setTitle("Roles");
        setModalityType(ModalityType.APPLICATION_MODAL);

        roleTableModel = new RoleTable();

        Role.getAll().forEach(roleTableModel::addRecord);
        tableRoles.getTableHeader().setReorderingAllowed(false);
        tableRoles.setModel(roleTableModel);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }

    private void insertRecord() {
        Role role = new Role();
        role.setName(textFieldRole.getText());
        role.setLookupCode(lookupCode.getText());

        if (!role.save()) {
            JOptionPane.showMessageDialog(this, role.getErrors().fullMessages("\n"));
            return;
        }

        roleTableModel.addRecord(role);
        textFieldRole.setText("");
        lookupCode.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableRoles.getSelectedRow();
        Role role = roleTableModel.getRecord(rowIndex);
        role.setName(textFieldRole.getText());
        role.setLookupCode(lookupCode.getText());

        if (!role.save()) {
            JOptionPane.showMessageDialog(this, role.getErrors().fullMessages("\n"));
            return;
        }

        roleTableModel.setValueAt(role, rowIndex);
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tableRoles.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            roleTableModel.getRecord(rowIndex).delete();
            roleTableModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableRoles.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        Role role = roleTableModel.getRecord(rowIndex);
        textFieldRole.setText(role.getName());
        lookupCode.setText(role.getLookupCode());
        setEditMode();
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
                insertRecord();
                break;
            case "editButton":
                setValues();
                break;
            case "saveButton":
                saveRecord();
                break;
            case "cancelButton":
                setDefaultMode();
                break;
            case "deleteButton":
                deleteRecord();
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
