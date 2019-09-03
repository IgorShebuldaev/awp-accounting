package org.accounting.forms;

import org.accounting.database.Authorization;
import org.accounting.database.models.Users;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainForm implements ActionListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private JFrame authorizationFrame = new JFrame("Log in");
    private JFrame mainFrame = new JFrame("Accounting");

    private JTextField textField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    private JTable tableUsers = new JTable();
    private JScrollPane scrollPaneTableUsers = new JScrollPane(tableUsers);

    public void createUserAuthorizationForm() {
        JPanel jPanel = new JPanel();

        JButton okButton = new JButton("Ok");
        okButton.setActionCommand("ok");

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");

        jPanel.add(textField);
        jPanel.add(passwordField);
        jPanel.add(okButton);
        jPanel.add(exitButton);

        authorizationFrame.setSize(250, 130);
        authorizationFrame.setLocationRelativeTo(null);
        authorizationFrame.add(jPanel);
        authorizationFrame.setVisible(true);
        authorizationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        okButton.addActionListener(this::actionPerformed);
        exitButton.addActionListener(this::actionPerformed);
    }

    private void createMainForm() {
        mainFrame.setJMenuBar(creatMenuBar());
        fillTableUser();
        mainFrame.add(scrollPaneTableUsers);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int x = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);

                if (x == JOptionPane.YES_OPTION) {
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private JMenuBar creatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        menu.add(new JMenuItem("Settings"));
        menuBar.add(menu);

        return menuBar;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equals(e.getActionCommand()))
            validateUserAuthorization(textField, passwordField);
        if ("exit".equals(e.getActionCommand()))
            System.exit(0);
    }

    private void validateUserAuthorization(JTextField login, JPasswordField password) {
        if (new Authorization().isAuthorized(login.getText(), String.valueOf(password.getPassword()))) {
            authorizationFrame.dispose();
            createMainForm();
        } else {
            JOptionPane.showMessageDialog(authorizationFrame, "Invalid login or password! Try again.");
        }
    }
}