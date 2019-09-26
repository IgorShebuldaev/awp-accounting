package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.Authorization;
import org.accounting.database.Database;
import org.accounting.database.models.Delivery;
import org.accounting.database.models.User;
import org.accounting.forms.workbooks.WorkBooksForm;
import org.accounting.forms.models.DeliveryTable;
import org.accounting.user.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainForm extends JFrame implements ActionListener {
    private JFrame authorizationFrame = new JFrame("Log in");
    private JTextField textField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    private JTable tableDelivery;
    private JPanel panelMain;
    private JScrollPane scrollPaneMain;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JSpinner spinnerDelivery;
    private JComboBox comboBoxSupplier;
    private JTextField textFieldProduct;
    private JTextField textFieldPrice;
    private JComboBox comboBoxWorker;
    private JLabel labelBar;

    private DeliveryTable deliveryTableModel;

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

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateUserAuthorization(textField, passwordField);
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
        setJMenuBar(creatMenuBar());
        setContentPane(panelMain);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        deliveryTableModel = new DeliveryTable();
        fillTableDeliveries();
        tableDelivery.setModel(deliveryTableModel);

        this.addWindowListener(new WindowAdapter() {
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
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        Timer timer = new Timer(1000, e -> {
            CurrentUser.timeInProgram += 1;
            User.updateTimeUser(CurrentUser.id, CurrentUser.email, CurrentUser.timeInProgram);
            int seconds = CurrentUser.timeInProgram % 60;
            int minutes = CurrentUser.timeInProgram / 60 % 60;
            int days = CurrentUser.timeInProgram / 86400;
            labelBar.setText("User: " + CurrentUser.email + ". Role: " + CurrentUser.role + ". Time in program = " + days + ":" + minutes + ":" + seconds);
        });
        timer.start();
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
        ArrayList<Delivery> results = Delivery.getAll();
        for (Delivery delivery : results) {
            deliveryTableModel.addRecord(delivery);
        }
        tableDelivery.setModel(deliveryTableModel);
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
            case "notes":
                new NotesForm().setVisible(true);
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
        Authorization currentUser = new Authorization();
        if (currentUser.isAuthorized(login.getText(), String.valueOf(password.getPassword()))) {
            authorizationFrame.dispose();
            createMainForm();
        } else {
            JOptionPane.showMessageDialog(authorizationFrame, "Invalid login or password! Try again.");
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
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        scrollPaneMain = new JScrollPane();
        panelMain.add(scrollPaneMain, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableDelivery = new JTable();
        scrollPaneMain.setViewportView(tableDelivery);
        labelBar = new JLabel();
        labelBar.setText("Label");
        panelMain.add(labelBar, new GridConstraints(3, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setText("Add");
        panelMain.add(addButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setText("Edit");
        panelMain.add(editButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelMain.add(saveButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelMain.add(cancelButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setText("Delete");
        panelMain.add(deleteButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerDelivery = new JSpinner();
        panelMain.add(spinnerDelivery, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxSupplier = new JComboBox();
        panelMain.add(comboBoxSupplier, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldProduct = new JTextField();
        panelMain.add(textFieldProduct, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldPrice = new JTextField();
        panelMain.add(textFieldPrice, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBoxWorker = new JComboBox();
        panelMain.add(comboBoxWorker, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
