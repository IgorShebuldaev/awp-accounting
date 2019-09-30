package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.Authorization;
import org.accounting.database.Database;
import org.accounting.database.models.Delivery;
import org.accounting.database.models.Supplier;
import org.accounting.database.models.User;
import org.accounting.database.models.Worker;
import org.accounting.forms.models.comboboxmodels.SupplierComboBoxModel;
import org.accounting.forms.models.comboboxmodels.WorkerComboBoxModel;
import org.accounting.forms.workbooks.IDataManipulator;
import org.accounting.forms.workbooks.WorkBooksForm;
import org.accounting.forms.models.tablemodels.DeliveryTable;
import org.accounting.user.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class MainForm extends JFrame implements ActionListener, IDataManipulator {
    private JFrame authorizationForm = new JFrame("Log in");
    private JTextField textFieldEmail = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    private JTable tableDeliveries;
    private JPanel panelMain;
    private JScrollPane scrollPaneMain;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JSpinner spinnerDeliveries;
    private JComboBox<String> comboBoxSuppliers;
    private JTextField textFieldProduct;
    private JTextField textFieldPrice;
    private JComboBox<String> comboBoxWorkers;
    private JLabel labelBar;
    private DeliveryTable deliveryTableModel;
    private SupplierComboBoxModel supplierComboBoxModel;
    private WorkerComboBoxModel workerComboBoxModel;

    public void createUserAuthorizationForm() {
        JPanel jPanel = new JPanel();

        JButton okButton = new JButton("Ok");
        okButton.setActionCommand("ok");

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");

        jPanel.add(textFieldEmail);
        jPanel.add(passwordField);
        jPanel.add(okButton);
        jPanel.add(exitButton);

        authorizationForm.setSize(250, 130);
        authorizationForm.setLocationRelativeTo(null);
        authorizationForm.add(jPanel);
        authorizationForm.setVisible(true);
        authorizationForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    validateUserAuthorization(textFieldEmail, passwordField);
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

        deliveryTableModel = new DeliveryTable();
        fillTable();
        tableDeliveries.setModel(deliveryTableModel);

        spinnerDeliveries.setModel(setCurrentDateSpinner());
        spinnerDeliveries.setEditor(new JSpinner.DateEditor(spinnerDeliveries, "dd.MM.yyyy"));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int x = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);

                if (x == JOptionPane.YES_OPTION) {
                    CurrentUser.updateDataTimeInProgram();
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


        updateStatusBar();

        Timer timer = new Timer(1000, e -> updateStatusBar());
        timer.start();

        supplierComboBoxModel = new SupplierComboBoxModel();
        workerComboBoxModel = new WorkerComboBoxModel();
        addItemComboBoxSupplier();
        addItemComboBoxWorker();

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        deleteButton.addActionListener(this);
        cancelButton.addActionListener(this);

        setVisible(true);
    }

    private void updateStatusBar() {
        User currentUser = CurrentUser.getUser();

        currentUser.timeInProgram += 1;
        int seconds = currentUser.timeInProgram % 60;
        int minutes = currentUser.timeInProgram / 60 % 60;
        int days = currentUser.timeInProgram / 86400;
        labelBar.setText(
            String.format("User: %s. Role: %s. Time in program: %02d:%02d:%02d",
                currentUser.email,
                currentUser.getRole().role,
                days,
                minutes,
                seconds
            )
        );
    }

    private JMenuBar creatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenu reports = new JMenu("Reports");
        JMenu graphics = new JMenu("Graphics");
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

        reports.setActionCommand("reports");
        graphics.setActionCommand("graphics");

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
        menuBar.add(reports);
        menuBar.add(graphics);

        if ((CurrentUser.getUser().getRole().isAdmin())) {
            menuBar.add(settings);
        }
        menuBar.add(about);

        return menuBar;
    }

    private void validateUserAuthorization(JTextField login, JPasswordField password) {
        Authorization currentUser = new Authorization();
        if (currentUser.isAuthorized(login.getText(), String.valueOf(password.getPassword()))) {
            authorizationForm.dispose();
            createMainForm();
        } else {
            JOptionPane.showMessageDialog(authorizationForm, "Invalid login or password! Try again.");
        }
    }

    private void fillTable() {
        ArrayList<Delivery> results = Delivery.getAll();
        for (Delivery delivery : results) {
            deliveryTableModel.addRecord(delivery);
        }
        tableDeliveries.setModel(deliveryTableModel);
    }

    private void insertData() {
        if (checkEmptyFields()) {
            Delivery delivery = new Delivery(
                    0,
                    (Date) spinnerDeliveries.getValue(),
                    (String) comboBoxSuppliers.getSelectedItem(),
                    textFieldProduct.getText(),
                    textFieldPrice.getText(),
                    (String) comboBoxWorkers.getSelectedItem());
            Delivery.insertData(delivery);
            deliveryTableModel.addRecord(delivery);
            textFieldProduct.setText("");
            textFieldPrice.setText("");
        }
    }

    private void deleteData() {
        int rowIndex = tableDeliveries.getSelectedRow();
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
            User.deleteData(deliveryTableModel.getRecord(rowIndex).id);
            deliveryTableModel.removeRow(rowIndex);
        }
    }

    private void updateData() {
        int rowIndex = tableDeliveries.getSelectedRow();
        if (checkEmptyFields()) {
            Delivery delivery = new Delivery(
                    deliveryTableModel.getRecord(rowIndex).id,
                    (Date) spinnerDeliveries.getValue(),
                    (String) comboBoxSuppliers.getSelectedItem(),
                    textFieldProduct.getText(),
                    textFieldPrice.getText(),
                    (String) comboBoxWorkers.getSelectedItem());

            Delivery.updateData(delivery);
            deliveryTableModel.setValueAt(delivery, rowIndex);
            turnComponents(true);
        }
    }

    private void setValuesComponents() {
        int rowIndex = tableDeliveries.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        spinnerDeliveries.setValue(deliveryTableModel.getRecord(rowIndex).deliveryDate);
        comboBoxSuppliers.setSelectedItem(deliveryTableModel.getRecord(rowIndex).supplier);
        textFieldProduct.setText(deliveryTableModel.getRecord(rowIndex).product);
        textFieldPrice.setText(deliveryTableModel.getRecord(rowIndex).price);
        comboBoxWorkers.setSelectedItem(deliveryTableModel.getRecord(rowIndex).worker);
        turnComponents(false);
    }

    private boolean checkEmptyFields() {
        if (textFieldProduct.getText().equals("") || textFieldPrice.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private void addItemComboBoxSupplier() {
        ArrayList<Supplier> results = Supplier.getAll();
        for (Supplier supplier : results) {
            supplierComboBoxModel.addRecord(supplier);
        }
        comboBoxSuppliers.setModel(supplierComboBoxModel);
    }

    private void addItemComboBoxWorker() {
        ArrayList<Worker> results = Worker.getAll();
        for (Worker worker : results) {
            workerComboBoxModel.addRecord(worker);
        }
        comboBoxWorkers.setModel(workerComboBoxModel);
    }

    private void turnComponents(Boolean turn) {
        if (!turn) {
            addButton.setEnabled(false);
            editButton.setEnabled(false);
            saveButton.setEnabled(true);
            cancelButton.setEnabled(true);
            deleteButton.setEnabled(false);
            tableDeliveries.setEnabled(false);
        } else {
            addButton.setEnabled(true);
            editButton.setEnabled(true);
            saveButton.setEnabled(false);
            cancelButton.setEnabled(false);
            deleteButton.setEnabled(true);
            tableDeliveries.setEnabled(true);
            textFieldProduct.setText("");
            textFieldPrice.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "ok":
                validateUserAuthorization(textFieldEmail, passwordField);
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
                turnComponents(true);
                break;
            case "deleteData":
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
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        scrollPaneMain = new JScrollPane();
        panelMain.add(scrollPaneMain, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableDeliveries = new JTable();
        scrollPaneMain.setViewportView(tableDeliveries);
        labelBar = new JLabel();
        labelBar.setText("                     ");
        panelMain.add(labelBar, new GridConstraints(3, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("addButton");
        addButton.setText("Add");
        panelMain.add(addButton, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("addButton");
        editButton.setText("Edit");
        panelMain.add(editButton, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("saveButton");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelMain.add(saveButton, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancelButton");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelMain.add(cancelButton, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("deleteButton");
        deleteButton.setText("Delete");
        panelMain.add(deleteButton, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerDeliveries = new JSpinner();
        panelMain.add(spinnerDeliveries, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxSuppliers = new JComboBox();
        panelMain.add(comboBoxSuppliers, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldProduct = new JTextField();
        panelMain.add(textFieldProduct, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldPrice = new JTextField();
        panelMain.add(textFieldPrice, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBoxWorkers = new JComboBox();
        panelMain.add(comboBoxWorkers, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }
}
