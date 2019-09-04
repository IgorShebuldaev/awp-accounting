package org.accounting.forms;

import org.accounting.database.models.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class UsersForm {
    private JFrame usersFrame = new JFrame("Users");
    private JTable tableUsers = new JTable();
    private JScrollPane scrollPaneTableUsers = new JScrollPane(tableUsers);

    public void createUsersForm() {
        usersFrame.setSize(800, 600);
        usersFrame.setLocationRelativeTo(null);
        usersFrame.setVisible(true);
        usersFrame.add(scrollPaneTableUsers);
        fillTableUser();

    }

    private void fillTableUser() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Email","Password","Role","Time in program"});
        ArrayList<Users> arrayList = Users.getUsers();
        for (Users users : arrayList){
            model.addRow(new Object[]{users.email, users.password, users.role, users.timeInProgram});
        }
        tableUsers.setModel(model);
    }

}
