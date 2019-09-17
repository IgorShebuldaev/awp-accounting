package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Roles;
import org.accounting.forms.models.MainTableModel;

import javax.swing.*;
import java.awt.*;
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

    public void createRolesForm() {
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

    private void setTextFields() {
        int row = tableRoles.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldRole.setText((String) model.getRawValueAt(row, 1));
        editButton.setEnabled(false);
        addButton.setEnabled(false);
        deleteButton.setEnabled(false);
        tableRoles.setEnabled(false);
        cancelButton.setEnabled(true);
        saveButton.setEnabled(true);
    }

    private boolean checkEmptyFields() {
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
        panaelRolesForm = new JPanel();
        panaelRolesForm.setLayout(new GridLayoutManager(4, 5, new Insets(5, 5, 5, 5), -1, -1));
        scrolPaneTableRoles = new JScrollPane();
        panaelRolesForm.add(scrolPaneTableRoles, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableRoles = new JTable();
        scrolPaneTableRoles.setViewportView(tableRoles);
        textFieldRole = new JTextField();
        panaelRolesForm.add(textFieldRole, new GridConstraints(2, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelRole = new JLabel();
        labelRole.setText("Role");
        panaelRolesForm.add(labelRole, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("add");
        addButton.setText("Add");
        panaelRolesForm.add(addButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("edit");
        editButton.setText("Edit");
        panaelRolesForm.add(editButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("save");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panaelRolesForm.add(saveButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancel");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panaelRolesForm.add(cancelButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("delete");
        deleteButton.setText("Delete");
        panaelRolesForm.add(deleteButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panaelRolesForm;
    }
}