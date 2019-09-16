package org.accounting.forms;

import org.accounting.database.models.Roles;
import org.accounting.forms.models.MainTableModel;

import javax.swing.*;
import java.util.ArrayList;

public class RolesForm extends JFrame{
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
        setVisible(true);

        model = new MainTableModel();
        fillTableRoles();
        tableRoles.setModel(model);
    }

    private void fillTableRoles() {
        model.setColumnIdentifiers(new String[]{"Role"});
        ArrayList<Roles> results = Roles.getRoles();
        for (Roles roles : results) {
            model.addRow(new Object[]{roles.id, roles.role});
        }
    }
}