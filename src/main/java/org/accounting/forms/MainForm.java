package org.accounting.forms;

import org.accounting.database.Authorization;
import org.accounting.database.Database;
import org.accounting.database.models.Deliveries;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        JMenuItem jMenuItemSuppliers = new JMenuItem("Suppliers");
        JMenuItem jMenuItemWorkers = new JMenuItem("Workers");
        JMenuItem jMenuItemPositions = new JMenuItem("Positions");
        JMenuItem jMenuItemNotes = new JMenuItem("Notes");

        JMenuItem jMenuItemUsers = new JMenuItem("Users");

        jMenuItemSuppliers.setActionCommand("suppliers");
        jMenuItemWorkers.setActionCommand("workers");
        jMenuItemPositions.setActionCommand("positions");
        jMenuItemNotes.setActionCommand("notes");

        jMenuItemUsers.setActionCommand("users");

        jMenuItemSuppliers.addActionListener(this);
        jMenuItemWorkers.addActionListener(this);
        jMenuItemPositions.addActionListener(this);
        jMenuItemNotes.addActionListener(this);

        jMenuItemUsers.addActionListener(this);

        menu.add(jMenuItemSuppliers);
        menu.addSeparator();
        menu.add(jMenuItemWorkers);
        menu.addSeparator();
        menu.add(jMenuItemPositions);
        menu.addSeparator();
        menu.add(jMenuItemNotes);

        settings.add(jMenuItemUsers);

        menuBar.add(menu);
        menuBar.add(settings);
        menuBar.add(about);

        return menuBar;
    }

    private void fillTableDeliveries() {
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Date delivery", "Company name", "Product", "Price", "Worker"});
        ArrayList<Deliveries> arrayList = Deliveries.getDeliveries();
        for (Deliveries deliveries : arrayList){
            model.addRow(new Object[]{deliveries.deliveryDate, deliveries.supplier, deliveries.product, deliveries.price, deliveries.worker});
        }
        tableDeliveries.setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equals(e.getActionCommand()))
            validateUserAuthorization(textField, passwordField);
        if ("exit".equals(e.getActionCommand())) {
            try {
                Database.closeConnection();
            } catch (SQLException se) {
                se.printStackTrace();
            }
            System.exit(0);
        }
        if ("users".equals(e.getActionCommand()))
            new UsersForm().createUsersForm();
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