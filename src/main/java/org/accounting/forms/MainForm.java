package org.accounting.forms;

import org.accounting.database.Authorization;
import org.accounting.database.Database;
import org.accounting.database.models.Delivery;
import org.accounting.forms.WorkBooks.WorkBooksForm;
import org.accounting.forms.models.DeliveryTable;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainForm implements ActionListener {
    private JFrame authorizationFrame = new JFrame("Log in");
    private JFrame mainFrame = new JFrame("Accounting");

    private JTextField textField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    private JTable tableDeliveries = new JTable();
    private JScrollPane scrollPaneTableDeliveries = new JScrollPane(tableDeliveries);

    void createUserAuthorizationForm() {
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

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateUserAuthorization(textField,passwordField);
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });

        okButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    private void createMainForm() {
        mainFrame.setJMenuBar(creatMenuBar());
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.add(scrollPaneTableDeliveries);
        fillTableDeliveries();

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int x = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);

                if (x == JOptionPane.YES_OPTION) {
                    try {
                        Database.closeConnection();
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private JMenuBar creatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenu settings = new JMenu("Settings");
        JMenu about = new JMenu("About");

        JMenuItem jMenuItemWorkBooks = new JMenuItem("Work Books");
        JMenuItem jMenuItemNotes = new JMenuItem("Notes");

        JMenuItem jMenuItemUsers = new JMenuItem("Users");
        JMenuItem jMenuItemRoles = new JMenuItem("Roles");

        jMenuItemWorkBooks.setActionCommand("workBooks");
        jMenuItemNotes.setActionCommand("notes");

        jMenuItemUsers.setActionCommand("users");
        jMenuItemRoles.setActionCommand("roles");

        jMenuItemWorkBooks.addActionListener(this);
        jMenuItemNotes.addActionListener(this);

        jMenuItemUsers.addActionListener(this);
        jMenuItemRoles.addActionListener(this);

        menu.add(jMenuItemWorkBooks);
        menu.addSeparator();
        menu.add(jMenuItemNotes);

        settings.add(jMenuItemUsers);
        settings.addSeparator();
        settings.add(jMenuItemRoles);

        menuBar.add(menu);
        menuBar.add(settings);
        menuBar.add(about);

        return menuBar;
    }

    private void fillTableDeliveries() {
        DeliveryTable model = new DeliveryTable();
        ArrayList<Delivery> results = Delivery.getAll();
        for (Delivery delivery : results){
            model.addRecord(delivery);
        }
        tableDeliveries.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ok":
                validateUserAuthorization(textField, passwordField);
                break;
            case "exit":
                try {
                    Database.closeConnection();
                } catch (SQLException se) {
                    se.printStackTrace();
                }
                System.exit(0);
                break;
            case "workBooks":
                new WorkBooksForm().setVisible(true);
                break;
            case "users":
                new UsersForm().setVisible(true);
                break;
            case "roles":
                new RolesForm().setVisible(true);
                break;
        }
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
