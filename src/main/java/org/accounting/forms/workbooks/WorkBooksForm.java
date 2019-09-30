package org.accounting.forms.workbooks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.accounting.database.models.Supplier;
import org.accounting.database.models.Worker;
import org.accounting.forms.PositionsForm;
import org.accounting.forms.models.tablemodels.SupplierTable;
import org.accounting.forms.models.tablemodels.WorkerTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class WorkBooksForm extends JDialog implements ActionListener {
    private JPanel panelWorkBooks;
    private JPanel panelSuppliers;
    private JPanel panelWorkers;
    private JTabbedPane tabbedPaneWorkBooks;
    private JScrollPane scrollPaneWorkers;
    private JScrollPane scrollPaneSuppliers;
    private JTable tableWorkers;
    private JTable tableSuppliers;
    private JButton btnAddWorkers;
    private JButton btnEditWorkers;
    private JButton btnSaveWorkers;
    private JButton btnCancelWorkers;
    private JButton btnDeleteWorkers;
    private JButton btnAddSuppliers;
    private JButton btnEditSuppliers;
    private JButton btnSaveSuppliers;
    private JButton btnCancelSuppliers;
    private JButton btnDeleteSuppliers;
    private JButton btnShowPositionsForm;
    private JLabel labelWorkersFullName;
    private JLabel labelWorkersDateOfBirth;
    private JLabel labelWorkersPosition;
    private JLabel labelSuppliersCompanyName;
    private JTextField textFieldWorkersFullName;
    private JTextField textFieldSuppliersCompanyName;
    private JSpinner spinnerWorkersDateOfBirth;
    private JComboBox<String> comboBoxWorkersPositions;
    private SupplierImpl supplier;
    private WorkerImpl worker;
    private SupplierTable supplierTableModel;
    private WorkerTable workerTableModel;

    public WorkBooksForm() {
        createWorkBooksForm();
    }

    private void createWorkBooksForm() {
        setContentPane(panelWorkBooks);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Work Books");
        setModalityType(ModalityType.APPLICATION_MODAL);

        supplierTableModel = new SupplierTable();
        supplier = new SupplierImpl();
        supplier.fillTable(supplierTableModel);
        tableSuppliers.setModel(supplierTableModel);

        workerTableModel = new WorkerTable();
        worker = new WorkerImpl();
        worker.fillTable(workerTableModel);
        tableWorkers.setModel(workerTableModel);

        spinnerWorkersDateOfBirth.setModel(worker.setCurrentDateSpinner());
        spinnerWorkersDateOfBirth.setEditor(new JSpinner.DateEditor(spinnerWorkersDateOfBirth, "dd.MM.yyyy"));
        comboBoxWorkersPositions.setModel(worker.addItemComboBoxPosition());

        btnAddSuppliers.addActionListener(this);
        btnDeleteSuppliers.addActionListener(this);
        btnEditSuppliers.addActionListener(this);
        btnSaveSuppliers.addActionListener(this);
        btnCancelSuppliers.addActionListener(this);

        btnAddWorkers.addActionListener(this);
        btnDeleteWorkers.addActionListener(this);
        btnEditWorkers.addActionListener(this);
        btnSaveWorkers.addActionListener(this);
        btnCancelWorkers.addActionListener(this);
        btnShowPositionsForm.addActionListener(this);
    }

    private void setValuesComponentsSupplier() {
        int rowIndex = tableSuppliers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldSuppliersCompanyName.setText(supplierTableModel.getRecord(rowIndex).companyName);
        setEditModeSuppliers();
    }

    private void setValuesComponentsWorker() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldWorkersFullName.setText(workerTableModel.getRecord(rowIndex).fullName);
        spinnerWorkersDateOfBirth.setValue((workerTableModel.getRecord(rowIndex).dateOfBirth));
        comboBoxWorkersPositions.setSelectedItem(workerTableModel.getRecord(rowIndex).position);
        setEditModeWorkers();
    }

    private boolean isAnyEmptyFieldSuppliers() {
        if (textFieldSuppliersCompanyName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private boolean isAnyEmptyFieldWorkers() {
        String[] values = new String[]{
                textFieldWorkersFullName.getText(),
                 spinnerWorkersDateOfBirth.getValue().toString(),
                (String) comboBoxWorkersPositions.getSelectedItem()};

        if (Arrays.stream(values).anyMatch(String::isEmpty)) {
            JOptionPane.showMessageDialog(this, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private void setDefaultModeSuppliers() {
        btnAddSuppliers.setEnabled(true);
        btnEditSuppliers.setEnabled(true);
        btnDeleteSuppliers.setEnabled(true);
        tableSuppliers.setEnabled(true);
        btnCancelSuppliers.setEnabled(false);
        btnSaveSuppliers.setEnabled(false);
        textFieldSuppliersCompanyName.setText("");
    }

    private void setEditModeSuppliers() {
        btnAddSuppliers.setEnabled(false);
        btnEditSuppliers.setEnabled(false);
        btnDeleteSuppliers.setEnabled(false);
        tableSuppliers.setEnabled(false);
        btnCancelSuppliers.setEnabled(true);
        btnSaveSuppliers.setEnabled(true);
    }


    private void setDefaultModeWorkers() {
        btnAddWorkers.setEnabled(true);
        btnEditWorkers.setEnabled(true);
        btnSaveWorkers.setEnabled(false);
        btnCancelWorkers.setEnabled(false);
        btnDeleteWorkers.setEnabled(true);
        btnShowPositionsForm.setEnabled(true);
        tableWorkers.setEnabled(true);
        textFieldWorkersFullName.setText("");
    }

    private void setEditModeWorkers() {
        btnAddWorkers.setEnabled(false);
        btnEditWorkers.setEnabled(false);
        btnSaveWorkers.setEnabled(true);
        btnCancelWorkers.setEnabled(true);
        btnDeleteWorkers.setEnabled(false);
        btnShowPositionsForm.setEnabled(false);
        tableWorkers.setEnabled(false);
    }

    private boolean showAskDialog() {
        Object[] options = {"Yes", "No"};
        int n = JOptionPane.showOptionDialog(this,
                "Are you sure you want to delete the record?",
                "Message",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        return n == JOptionPane.YES_OPTION;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            // Suppliers <------------------------------------------------------------------------------------------------------------------------>
            case "btnAddSupplier":
                if (isAnyEmptyFieldSuppliers()) {
                    supplier.insertData(supplierTableModel, new Supplier(
                            0,
                            textFieldSuppliersCompanyName.getText()));
                    textFieldSuppliersCompanyName.setText("");
                }
                break;
            case "btnEditSupplier":
                setValuesComponentsSupplier();
                break;
            case "btnSaveSupplier":
                if (isAnyEmptyFieldSuppliers()) {
                    supplier.updateData(supplierTableModel, tableSuppliers.getSelectedRow(),
                            new Supplier(
                                    supplierTableModel.getRecord(tableSuppliers.getSelectedRow()).id,
                                    textFieldSuppliersCompanyName.getText()));
                    textFieldSuppliersCompanyName.setText("");
                    setDefaultModeSuppliers();
                }
                break;
            case "btnCancelSupplier":
                setDefaultModeSuppliers();
                break;
            case "btnDeleteSupplier":
                if (tableSuppliers.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(this, "Select an entry in the table!");
                    return;
                } else if (showAskDialog()) {
                    supplier.deleteData(supplierTableModel, tableSuppliers.getSelectedRow(), supplierTableModel.getRecord(tableSuppliers.getSelectedRow()).id);
                }
                break;
            // Workers <------------------------------------------------------------------------------------------------------------------------>
            case "btnAddWorker":
                if (isAnyEmptyFieldWorkers()) {
                    worker.insertData(workerTableModel, new Worker(
                            0,
                            textFieldWorkersFullName.getText(),
                            (Date) spinnerWorkersDateOfBirth.getValue(),
                            (String) comboBoxWorkersPositions.getSelectedItem()));
                    textFieldWorkersFullName.setText("");
                }
                break;
            case "btnEditWorker":
                setValuesComponentsWorker();
                break;
            case "btnSaveWorker":
                if (isAnyEmptyFieldWorkers()) {
                    worker.updateData(workerTableModel, tableWorkers.getSelectedRow(),
                            new Worker(
                                    workerTableModel.getRecord(tableWorkers.getSelectedRow()).id,
                                    textFieldWorkersFullName.getText(),
                                    (Date) spinnerWorkersDateOfBirth.getValue(),
                                    (String) comboBoxWorkersPositions.getSelectedItem()));
                    textFieldWorkersFullName.setText("");
                    setDefaultModeWorkers();
                }
                break;
            case "btnCancelWorker":
                setDefaultModeWorkers();
                break;
            case "btnDeleteWorker":
                if (tableWorkers.getSelectedRow() < 0) {
                    JOptionPane.showMessageDialog(this, "Select an entry in the table!");
                    return;
                }
                if (showAskDialog()) {
                    worker.deleteData(workerTableModel, tableWorkers.getSelectedRow(), workerTableModel.getRecord(tableWorkers.getSelectedRow()).id);
                }
                break;
            case "btnShowPositionsForm":
                new PositionsForm().setVisible(true);
                comboBoxWorkersPositions.setModel(worker.addItemComboBoxPosition());
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
        panelWorkBooks = new JPanel();
        panelWorkBooks.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        tabbedPaneWorkBooks = new JTabbedPane();
        panelWorkBooks.add(tabbedPaneWorkBooks, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        panelSuppliers = new JPanel();
        panelSuppliers.setLayout(new GridLayoutManager(4, 8, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneWorkBooks.addTab("Suppliers", panelSuppliers);
        scrollPaneSuppliers = new JScrollPane();
        panelSuppliers.add(scrollPaneSuppliers, new GridConstraints(0, 0, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableSuppliers = new JTable();
        scrollPaneSuppliers.setViewportView(tableSuppliers);
        textFieldSuppliersCompanyName = new JTextField();
        panelSuppliers.add(textFieldSuppliersCompanyName, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelSuppliersCompanyName = new JLabel();
        labelSuppliersCompanyName.setText("Company name");
        panelSuppliers.add(labelSuppliersCompanyName, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAddSuppliers = new JButton();
        btnAddSuppliers.setActionCommand("btnAddSupplier");
        btnAddSuppliers.setText("Add");
        panelSuppliers.add(btnAddSuppliers, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEditSuppliers = new JButton();
        btnEditSuppliers.setActionCommand("btnEditSupplier");
        btnEditSuppliers.setText("Edit");
        panelSuppliers.add(btnEditSuppliers, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSaveSuppliers = new JButton();
        btnSaveSuppliers.setActionCommand("btnSaveSupplier");
        btnSaveSuppliers.setEnabled(false);
        btnSaveSuppliers.setText("Save");
        panelSuppliers.add(btnSaveSuppliers, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCancelSuppliers = new JButton();
        btnCancelSuppliers.setActionCommand("btnCancelSupplier");
        btnCancelSuppliers.setEnabled(false);
        btnCancelSuppliers.setText("Cancel");
        panelSuppliers.add(btnCancelSuppliers, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDeleteSuppliers = new JButton();
        btnDeleteSuppliers.setActionCommand("btnDeleteSupplier");
        btnDeleteSuppliers.setText("Delete");
        panelSuppliers.add(btnDeleteSuppliers, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelSuppliers.add(spacer1, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(22, 11), null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelSuppliers.add(spacer2, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelSuppliers.add(spacer3, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelWorkers = new JPanel();
        panelWorkers.setLayout(new GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneWorkBooks.addTab("Workers", panelWorkers);
        scrollPaneWorkers = new JScrollPane();
        panelWorkers.add(scrollPaneWorkers, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableWorkers = new JTable();
        scrollPaneWorkers.setViewportView(tableWorkers);
        btnAddWorkers = new JButton();
        btnAddWorkers.setActionCommand("btnAddWorker");
        btnAddWorkers.setText("Add");
        panelWorkers.add(btnAddWorkers, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldWorkersFullName = new JTextField();
        panelWorkers.add(textFieldWorkersFullName, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        spinnerWorkersDateOfBirth = new JSpinner();
        panelWorkers.add(spinnerWorkersDateOfBirth, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxWorkersPositions = new JComboBox();
        panelWorkers.add(comboBoxWorkersPositions, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSaveWorkers = new JButton();
        btnSaveWorkers.setActionCommand("btnSaveWorker");
        btnSaveWorkers.setEnabled(false);
        btnSaveWorkers.setText("Save");
        panelWorkers.add(btnSaveWorkers, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEditWorkers = new JButton();
        btnEditWorkers.setActionCommand("btnEditWorker");
        btnEditWorkers.setText("Edit");
        panelWorkers.add(btnEditWorkers, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkersFullName = new JLabel();
        labelWorkersFullName.setText("Full name");
        panelWorkers.add(labelWorkersFullName, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkersDateOfBirth = new JLabel();
        labelWorkersDateOfBirth.setText("Date of birth");
        panelWorkers.add(labelWorkersDateOfBirth, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkersPosition = new JLabel();
        labelWorkersPosition.setText("Position");
        panelWorkers.add(labelWorkersPosition, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnShowPositionsForm = new JButton();
        btnShowPositionsForm.setActionCommand("btnShowPositionsForm");
        btnShowPositionsForm.setText(". . .");
        panelWorkers.add(btnShowPositionsForm, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDeleteWorkers = new JButton();
        btnDeleteWorkers.setActionCommand("btnDeleteWorker");
        btnDeleteWorkers.setText("Delete");
        panelWorkers.add(btnDeleteWorkers, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCancelWorkers = new JButton();
        btnCancelWorkers.setActionCommand("btnCancelWorker");
        btnCancelWorkers.setEnabled(false);
        btnCancelWorkers.setText("Cancel");
        panelWorkers.add(btnCancelWorkers, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelWorkBooks;
    }
}
