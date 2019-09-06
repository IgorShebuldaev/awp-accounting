package org.accounting.forms;

import org.accounting.database.models.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import static javax.swing.GroupLayout.Alignment.*;

class UsersForm extends JFrame{
    JTable tableUsers = new JTable();
    JScrollPane scrollPaneTableUsers = new JScrollPane(tableUsers);
    JButton addButton = new JButton("Add");
    JButton deleteButton = new JButton("Delete");
    JButton editButton = new JButton("Edit");
    JButton cancelButton = new JButton("Cancel");

    void createUsersForm() {
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

//        horizontal layout = sequential group { c1, c2, parallel group (LEFT) { c3, c4 } }
//        vertical layout = sequential group { parallel group (BASELINE) { c1, c2, c3 }, c4 }

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(scrollPaneTableUsers)
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(addButton)
                        .addComponent(editButton))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(deleteButton)
                        .addComponent(cancelButton))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, deleteButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(scrollPaneTableUsers)
                        .addComponent(addButton)
                        .addComponent(deleteButton))
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(editButton)
                        .addComponent(cancelButton))

        );

        setTitle("Users");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        fillTableUsers();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void fillTableUsers() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Email","Password","Role","Time in program"});
        ArrayList<Users> arrayList = Users.getUsers();
        for (Users users : arrayList){
            model.addRow(new Object[]{users.email, users.password, users.role, users.timeInProgram});
        }
        tableUsers.setModel(model);
    }
}
