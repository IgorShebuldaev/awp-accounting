package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.Authorization;
import org.accounting.database.Database;
import org.accounting.database.models.Delivery;
import org.accounting.database.models.Supplier;
import org.accounting.database.models.User;
import org.accounting.database.models.Worker;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.comboboxmodels.SupplierComboBoxModel;
import org.accounting.forms.models.comboboxmodels.WorkerComboBoxModel;
import org.accounting.forms.workbooks.WorkBooksForm;
import org.accounting.forms.models.tablemodels.DeliveryTable;
import org.accounting.user.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Date;

public class MainForm extends JFrame implements ActionListener {
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
    private JSpinner spinnerDeliveriesDeliveryDate;
    private JComboBox<String> comboBoxSuppliers;
    private JTextField textFieldProduct;
    private JTextField textFieldPrice;
    private JComboBox<String> comboBoxWorkers;
    private JLabel labelStatusBar;
    private JLabel labelDeliveryDate;
    private JLabel labelSupplier;
    private JLabel labelProduct;
    private JLabel labelPrice;
    private JLabel labelWorker;
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

        Delivery.getAll().forEach(deliveryTableModel::addRecord);
        tableDeliveries.setModel(deliveryTableModel);
        tableDeliveries.getTableHeader().setReorderingAllowed(false);

        spinnerDeliveriesDeliveryDate.setModel(new SpinnerDateModel());
        spinnerDeliveriesDeliveryDate.setEditor(new JSpinner.DateEditor(spinnerDeliveriesDeliveryDate, "dd.MM.yyyy"));
        spinnerDeliveriesDeliveryDate.setValue(new Date());

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (new YesNoDialog("Are you sure you want to exit?", "Confirm Exit").isPositive()) {
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

        updateStatusBar(); //???

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

    private void validateUserAuthorization(JTextField login, JPasswordField password) {
        Authorization currentUser = new Authorization();
        if (currentUser.isAuthorized(login.getText(), String.valueOf(password.getPassword()))) {
            authorizationForm.dispose();
            createMainForm();
        } else {
            JOptionPane.showMessageDialog(authorizationForm, "Invalid login or password! Try again.");
        }
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

    private void updateStatusBar() {
        User currentUser = CurrentUser.getUser();

        currentUser.incrementTimeInProgram(1);
        labelStatusBar.setText(
                String.format("User: %s. Role: %s. Time in program: %s",
                        currentUser.getEmail(),
                        currentUser.getRole().getName(),
                        currentUser.getFormattedTimeInProgram()
                )
        );
    }

    private void addItemComboBoxSupplier() {
        supplierComboBoxModel.removeAllElements();
        Supplier.getAll().forEach(supplierComboBoxModel::addRecord);
        comboBoxSuppliers.setModel(supplierComboBoxModel);
    }

    private void addItemComboBoxWorker() {
        workerComboBoxModel.removeAllElements();
        Worker.getAll().forEach(workerComboBoxModel::addRecord);
        comboBoxWorkers.setModel(workerComboBoxModel);
    }

    private void insertRecord() {
        Delivery delivery = new Delivery();
        delivery.setDeliveryDate((Date) spinnerDeliveriesDeliveryDate.getValue());
        delivery.setSupplier((String) comboBoxSuppliers.getSelectedItem());
        delivery.setProduct(textFieldProduct.getText());
        delivery.setPrice(textFieldPrice.getText());
        delivery.setWorker((String) workerComboBoxModel.getSelectedItem());

        if (!delivery.save()) {
            JOptionPane.showMessageDialog(this, delivery.getErrors().fullMessages("\n"));
            return;
        }

        deliveryTableModel.addRecord(delivery);
        textFieldProduct.setText("");
        textFieldPrice.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableDeliveries.getSelectedRow();
        Delivery delivery = deliveryTableModel.getRecord(rowIndex);
        delivery.setDeliveryDate((Date) spinnerDeliveriesDeliveryDate.getValue());
        delivery.setSupplier((String) comboBoxSuppliers.getSelectedItem());
        delivery.setProduct(textFieldProduct.getText());
        delivery.setPrice(textFieldPrice.getText());
        delivery.setWorker((String) comboBoxWorkers.getSelectedItem());

        if (!delivery.save()) {
            JOptionPane.showMessageDialog(this, delivery.getErrors().fullMessages("\n"));
            return;
        }

        delivery.save();
        deliveryTableModel.setValueAt(delivery, rowIndex);
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tableDeliveries.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            deliveryTableModel.getRecord(rowIndex).delete();
            deliveryTableModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableDeliveries.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        Delivery delivery = deliveryTableModel.getRecord(rowIndex);
        spinnerDeliveriesDeliveryDate.setValue(delivery.getDeliveryDate());
        comboBoxSuppliers.setSelectedItem(delivery.getSupplier());
        textFieldProduct.setText(delivery.getProduct());
        textFieldPrice.setText(delivery.getPrice());
        comboBoxWorkers.setSelectedItem(delivery.getWorker());
        setEditMode();
    }

    private void setDefaultMode() {
        addButton.setEnabled(true);
        editButton.setEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        deleteButton.setEnabled(true);
        tableDeliveries.setEnabled(true);
        textFieldProduct.setText("");
        textFieldPrice.setText("");
    }

    private void setEditMode() {
        addButton.setEnabled(false);
        editButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        deleteButton.setEnabled(false);
        tableDeliveries.setEnabled(false);
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
                addItemComboBoxWorker();
                addItemComboBoxSupplier();
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
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 5, new Insets(5, 5, 5, 5), -1, -1));
        scrollPaneMain = new JScrollPane();
        panelMain.add(scrollPaneMain, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableDeliveries = new JTable();
        scrollPaneMain.setViewportView(tableDeliveries);
        labelStatusBar = new JLabel();
        labelStatusBar.setText("                     ");
        panelMain.add(labelStatusBar, new GridConstraints(4, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        addButton = new JButton();
        addButton.setActionCommand("addButton");
        addButton.setText("Add");
        panelMain.add(addButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("editButton");
        editButton.setText("Edit");
        panelMain.add(editButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("saveButton");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelMain.add(saveButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancelButton");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelMain.add(cancelButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("deleteButton");
        deleteButton.setText("Delete");
        panelMain.add(deleteButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        spinnerDeliveriesDeliveryDate = new JSpinner();
        panelMain.add(spinnerDeliveriesDeliveryDate, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxSuppliers = new JComboBox();
        panelMain.add(comboBoxSuppliers, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldProduct = new JTextField();
        panelMain.add(textFieldProduct, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldPrice = new JTextField();
        panelMain.add(textFieldPrice, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        comboBoxWorkers = new JComboBox();
        panelMain.add(comboBoxWorkers, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelDeliveryDate = new JLabel();
        labelDeliveryDate.setText("Delivery date");
        panelMain.add(labelDeliveryDate, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelSupplier = new JLabel();
        labelSupplier.setText("Supplier");
        panelMain.add(labelSupplier, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelProduct = new JLabel();
        labelProduct.setText("Product");
        panelMain.add(labelProduct, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPrice = new JLabel();
        labelPrice.setText("Price");
        panelMain.add(labelPrice, new GridConstraints(1, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorker = new JLabel();
        labelWorker.setText("Worker");
        panelMain.add(labelWorker, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
