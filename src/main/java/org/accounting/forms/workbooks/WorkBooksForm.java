package org.accounting.forms.workbooks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import org.accounting.database.models.Position;
import org.accounting.database.models.Worker;
import org.accounting.forms.models.WorkerTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WorkBooksForm extends JDialog implements ActionListener {
    private JTabbedPane tabbedPaneWorkBooks;
    private JTable tableWorker;
    private JTable tableSupplier;
    private JPanel panelWorker;
    private JPanel panelSupplier;
    private JScrollPane scrollPaneWorker;
    private JScrollPane scrollPaneSupplier;
    private JPanel panelWorkBooks;
    private JButton btnAddWorker;
    private JButton btnEditWorker;
    private JButton btnSaveWorker;
    private JButton btnCancelWorker;
    private JButton btnDeleteWorker;
    private JTextField textFieldWorkerFullName;
    private JSpinner spinnerWorkerDateOfBirth;
    private JComboBox<String> comboBoxWorkerPosition;
    private JButton btnShowPositionsForm;
    private JLabel labelWorkerFullName;
    private JLabel labelWorkerDateOfBirth;
    private JLabel labelWorkerPosition;
    private JTextField textFieldSupplierCompanyName;
    private JButton btnAddSupplier;
    private JButton btnEditSupplier;
    private JButton btnSaveSupplier;
    private JButton btnCancelSupplier;
    private JButton btnDeleteSupplier;
    private JLabel labelSupplierCompanyName;
    private WorkerTable workerModel;
    private WorkerImpl worker;

    public WorkBooksForm() {
        setContentPane(panelWorkBooks);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Work Books");

        workerModel = new WorkerTable();
        worker = new WorkerImpl();
        worker.fillTable(workerModel);
        tableWorker.setModel(workerModel);

        setCurrentDateWorkerSpinner();
        addItemComboBoxPosition();

        btnAddWorker.addActionListener(this);
        btnDeleteWorker.addActionListener(this);
        btnEditWorker.addActionListener(this);
        btnSaveWorker.addActionListener(this);
        btnCancelWorker.addActionListener(this);
    }

    private void setCurrentDateWorkerSpinner() {
        Calendar calendar = Calendar.getInstance();
        Date initDate = calendar.getTime();
        calendar.add(Calendar.YEAR, -100);
        Date earliestDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 200);
        Date latestDate = calendar.getTime();
        SpinnerModel dateModel = new SpinnerDateModel(initDate, earliestDate, latestDate, Calendar.YEAR);
        spinnerWorkerDateOfBirth.setModel(dateModel);
        spinnerWorkerDateOfBirth.setEditor(new JSpinner.DateEditor(spinnerWorkerDateOfBirth, "dd.MM.yyyy"));
    }

    private void addItemComboBoxPosition() {
        comboBoxWorkerPosition.removeAllItems();
        ArrayList<Position> results = Position.getAll();
        for (Position positions : results) {
            comboBoxWorkerPosition.addItem(positions.position);
        }
    }

    private void setValueFieldsWorker() {
        int rowIndex = tableWorker.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }
        textFieldWorkerFullName.setText(workerModel.getRecord(rowIndex).fullName);
        spinnerWorkerDateOfBirth.setValue((workerModel.getRecord(rowIndex).dateOfBirth));
        comboBoxWorkerPosition.setSelectedItem(workerModel.getRecord(rowIndex).position);
        turnComponentsWorker(false);
    }

    private void turnComponentsWorker(Boolean turn) {
        if (!turn) {
            btnAddWorker.setEnabled(false);
            btnEditWorker.setEnabled(false);
            btnDeleteWorker.setEnabled(false);
            tableWorker.setEnabled(false);
            btnShowPositionsForm.setEnabled(false);
            btnCancelWorker.setEnabled(true);
            btnSaveWorker.setEnabled(true);
        } else {
            btnAddWorker.setEnabled(true);
            btnEditWorker.setEnabled(true);
            btnDeleteWorker.setEnabled(true);
            tableWorker.setEnabled(true);
            btnShowPositionsForm.setEnabled(true);
            btnCancelWorker.setEnabled(false);
            btnSaveWorker.setEnabled(false);
            textFieldWorkerFullName.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnAddWorker":
                worker.insertData(workerModel, new Worker(
                        0,
                        textFieldWorkerFullName.getText(),
                        (Date) spinnerWorkerDateOfBirth.getValue(),
                        (String) comboBoxWorkerPosition.getSelectedItem()));
                break;
            case "btnEditWorker":
                setValueFieldsWorker();
                turnComponentsWorker(false);
                break;
            case "btnSaveWorker":
                worker.updateData(workerModel, tableWorker.getSelectedRow(),
                        new Worker(
                                workerModel.getRecord(tableWorker.getSelectedRow()).id,
                                textFieldWorkerFullName.getText(),
                                (Date) spinnerWorkerDateOfBirth.getValue(),
                                (String) comboBoxWorkerPosition.getSelectedItem()));
                turnComponentsWorker(true);
                break;
            case "btnCancelWorker":
                turnComponentsWorker(true);
                break;
            case "btnDeleteWorker":
                worker.deleteData(workerModel, tableWorker.getSelectedRow(), workerModel.getRecord(tableWorker.getSelectedRow()).id);
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
        panelWorkBooks.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneWorkBooks = new JTabbedPane();
        panelWorkBooks.add(tabbedPaneWorkBooks, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        panelSupplier = new JPanel();
        panelSupplier.setLayout(new GridLayoutManager(4, 8, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneWorkBooks.addTab("Suppliers", panelSupplier);
        scrollPaneSupplier = new JScrollPane();
        panelSupplier.add(scrollPaneSupplier, new GridConstraints(0, 0, 1, 8, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableSupplier = new JTable();
        scrollPaneSupplier.setViewportView(tableSupplier);
        textFieldSupplierCompanyName = new JTextField();
        panelSupplier.add(textFieldSupplierCompanyName, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelSupplierCompanyName = new JLabel();
        labelSupplierCompanyName.setText("Company name");
        panelSupplier.add(labelSupplierCompanyName, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnAddSupplier = new JButton();
        btnAddSupplier.setActionCommand("addSupplier");
        btnAddSupplier.setText("Add");
        panelSupplier.add(btnAddSupplier, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEditSupplier = new JButton();
        btnEditSupplier.setActionCommand("editSupplier");
        btnEditSupplier.setText("Edit");
        panelSupplier.add(btnEditSupplier, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSaveSupplier = new JButton();
        btnSaveSupplier.setActionCommand("saveSupplier");
        btnSaveSupplier.setText("Save");
        panelSupplier.add(btnSaveSupplier, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCancelSupplier = new JButton();
        btnCancelSupplier.setActionCommand("cancelSupplier");
        btnCancelSupplier.setText("Cancel");
        panelSupplier.add(btnCancelSupplier, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDeleteSupplier = new JButton();
        btnDeleteSupplier.setActionCommand("deleteSupplier");
        btnDeleteSupplier.setText("Delete");
        panelSupplier.add(btnDeleteSupplier, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelSupplier.add(spacer1, new GridConstraints(3, 7, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelSupplier.add(spacer2, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelSupplier.add(spacer3, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelWorker = new JPanel();
        panelWorker.setLayout(new GridLayoutManager(4, 5, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPaneWorkBooks.addTab("Workers", panelWorker);
        scrollPaneWorker = new JScrollPane();
        panelWorker.add(scrollPaneWorker, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableWorker = new JTable();
        scrollPaneWorker.setViewportView(tableWorker);
        btnAddWorker = new JButton();
        btnAddWorker.setActionCommand("btnAddWorker");
        btnAddWorker.setText("Add");
        panelWorker.add(btnAddWorker, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldWorkerFullName = new JTextField();
        panelWorker.add(textFieldWorkerFullName, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        spinnerWorkerDateOfBirth = new JSpinner();
        panelWorker.add(spinnerWorkerDateOfBirth, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxWorkerPosition = new JComboBox();
        panelWorker.add(comboBoxWorkerPosition, new GridConstraints(2, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSaveWorker = new JButton();
        btnSaveWorker.setActionCommand("btnSaveWorker");
        btnSaveWorker.setEnabled(false);
        btnSaveWorker.setText("Save");
        panelWorker.add(btnSaveWorker, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnEditWorker = new JButton();
        btnEditWorker.setActionCommand("btnEditWorker");
        btnEditWorker.setText("Edit");
        panelWorker.add(btnEditWorker, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkerFullName = new JLabel();
        labelWorkerFullName.setText("Full name");
        panelWorker.add(labelWorkerFullName, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkerDateOfBirth = new JLabel();
        labelWorkerDateOfBirth.setText("Date of birth");
        panelWorker.add(labelWorkerDateOfBirth, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkerPosition = new JLabel();
        labelWorkerPosition.setText("Position");
        panelWorker.add(labelWorkerPosition, new GridConstraints(1, 2, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnShowPositionsForm = new JButton();
        btnShowPositionsForm.setText(". . .");
        panelWorker.add(btnShowPositionsForm, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnDeleteWorker = new JButton();
        btnDeleteWorker.setActionCommand("btnDeleteWorker");
        btnDeleteWorker.setText("Delete");
        panelWorker.add(btnDeleteWorker, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCancelWorker = new JButton();
        btnCancelWorker.setActionCommand("btnCancelWorker");
        btnCancelWorker.setEnabled(false);
        btnCancelWorker.setText("Cancel");
        panelWorker.add(btnCancelWorker, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelWorkBooks;
    }

}
