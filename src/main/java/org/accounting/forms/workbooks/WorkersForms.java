package org.accounting.forms.workbooks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Worker;
import org.accounting.forms.PositionsForm;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.WorkerTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

public class WorkersForms extends JPanel implements ActionListener {
    private JTable tableWorkers;
    private JButton btnAddWorkers;
    private JTextField textFieldWorkersFullName;
    private JSpinner spinnerWorkersDateOfBirth;
    private JComboBox comboBoxWorkersPositions;
    private JButton btnSaveWorkers;
    private JButton btnEditWorkers;
    private JButton btnShowPositionsForm;
    private JTextField textFieldWorkersEmail;
    private JTextField textFieldWorkersPassword;
    private JButton btnDeleteWorkers;
    private JButton btnCancelWorkers;
    private JPanel panelWorkers;
    private JScrollPane scrollPaneWorkers;
    private JLabel labelWorkersFullName;
    private JLabel labelWorkersDateOfBirth;
    private JLabel labelWorkersPosition;
    private JLabel labelWorkersPassword;
    private JLabel labelWorkersEmail;

    private WorkerImpl worker;
    private WorkerTable workerTableModel;

    public WorkersForms() {
        super();

        workerTableModel = new WorkerTable();
        worker = new WorkerImpl();
        worker.fillTable(workerTableModel);
        tableWorkers.setModel(workerTableModel);

        spinnerWorkersDateOfBirth.setModel(worker.setCurrentDateSpinner());
        spinnerWorkersDateOfBirth.setEditor(new JSpinner.DateEditor(spinnerWorkersDateOfBirth, "dd.MM.yyyy"));

        comboBoxWorkersPositions.setModel(worker.addItemComboBoxPosition());

        btnAddWorkers.addActionListener(this);
        btnDeleteWorkers.addActionListener(this);
        btnEditWorkers.addActionListener(this);
        btnSaveWorkers.addActionListener(this);
        btnCancelWorkers.addActionListener(this);
        btnShowPositionsForm.addActionListener(this);

        add(panelWorkers);
    }

    JPanel getPanel() {
        return panelWorkers;
    }

    private void setValues() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }
        textFieldWorkersFullName.setText(workerTableModel.getRecord(rowIndex).fullName);
        spinnerWorkersDateOfBirth.setValue((workerTableModel.getRecord(rowIndex).dateOfBirth));
        comboBoxWorkersPositions.setSelectedItem(workerTableModel.getRecord(rowIndex).position);
        textFieldWorkersEmail.setText(workerTableModel.getRecord(rowIndex).email);
        setEditMode();
    }

    private boolean isAnyEmptyField() {
        String[] values = new String[]{
            textFieldWorkersFullName.getText(),
            spinnerWorkersDateOfBirth.getValue().toString(),
            (String) comboBoxWorkersPositions.getSelectedItem(),
            textFieldWorkersPassword.getText(),
            textFieldWorkersPassword.getText()};

        if (Arrays.stream(values).anyMatch(String::isEmpty)) {
            JOptionPane.showMessageDialog(null, "Field cannot be empty!");
            return false;
        } else {
            return true;
        }
    }

    private void setDefaultMode() {
        btnAddWorkers.setEnabled(true);
        btnEditWorkers.setEnabled(true);
        btnSaveWorkers.setEnabled(false);
        btnCancelWorkers.setEnabled(false);
        btnDeleteWorkers.setEnabled(true);
        btnShowPositionsForm.setEnabled(true);
        tableWorkers.setEnabled(true);
        textFieldWorkersFullName.setText("");
    }

    private void setEditMode() {
        btnAddWorkers.setEnabled(false);
        btnEditWorkers.setEnabled(false);
        btnSaveWorkers.setEnabled(true);
        btnCancelWorkers.setEnabled(true);
        btnDeleteWorkers.setEnabled(false);
        btnShowPositionsForm.setEnabled(false);
        tableWorkers.setEnabled(false);
    }

    private void showPositionsForm() {
        new PositionsForm().setVisible(true);
        comboBoxWorkersPositions.setModel(worker.addItemComboBoxPosition());
    }

    private void deleteRecord() {
        if (tableWorkers.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }
        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            worker.deleteData(workerTableModel, tableWorkers.getSelectedRow(), workerTableModel.getRecord(tableWorkers.getSelectedRow()).id);
        }
    }

    private void saveRecord() {
        if (isAnyEmptyField()) {
            worker.updateData(workerTableModel, tableWorkers.getSelectedRow(),
                new Worker(
                    workerTableModel.getRecord(tableWorkers.getSelectedRow()).id,
                    textFieldWorkersFullName.getText(),
                    (Date) spinnerWorkersDateOfBirth.getValue(),
                    (String) comboBoxWorkersPositions.getSelectedItem()));
            textFieldWorkersFullName.setText("");
            setDefaultMode();
        }
    }

    private void addRecord() {
        if (isAnyEmptyField()) {
            worker.insertData(workerTableModel, new Worker(
                    0,
                    textFieldWorkersFullName.getText(),
                    (Date) spinnerWorkersDateOfBirth.getValue(),
                    (String) comboBoxWorkersPositions.getSelectedItem(),
                    textFieldWorkersEmail.getText()),
                textFieldWorkersPassword.getText());
            textFieldWorkersFullName.setText("");
            textFieldWorkersEmail.setText("");
            textFieldWorkersPassword.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnAddWorker":
                addRecord();
                break;
            case "btnEditWorker":
                setValues();
                break;
            case "btnSaveWorker":
                saveRecord();
                break;
            case "btnCancelWorker":
                setDefaultMode();
                break;
            case "btnDeleteWorker":
                deleteRecord();
                break;
            case "btnShowPositionsForm":
                showPositionsForm();
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panelWorkers = new JPanel();
        panelWorkers.setLayout(new GridLayoutManager(4, 6, new Insets(5, 5, 5, 5), -1, -1));
        panel1.add(panelWorkers, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPaneWorkers = new JScrollPane();
        panelWorkers.add(scrollPaneWorkers, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
        panelWorkers.add(comboBoxWorkersPositions, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnSaveWorkers = new JButton();
        btnSaveWorkers.setActionCommand("btnSaveWorker");
        btnSaveWorkers.setEnabled(false);
        btnSaveWorkers.setText("Save");
        panelWorkers.add(btnSaveWorkers, new GridConstraints(3, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
        panelWorkers.add(labelWorkersPosition, new GridConstraints(1, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnShowPositionsForm = new JButton();
        btnShowPositionsForm.setActionCommand("btnShowPositionsForm");
        btnShowPositionsForm.setText(". . .");
        panelWorkers.add(btnShowPositionsForm, new GridConstraints(2, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldWorkersEmail = new JTextField();
        panelWorkers.add(textFieldWorkersEmail, new GridConstraints(2, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldWorkersPassword = new JTextField();
        panelWorkers.add(textFieldWorkersPassword, new GridConstraints(2, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        btnDeleteWorkers = new JButton();
        btnDeleteWorkers.setActionCommand("btnDeleteWorker");
        btnDeleteWorkers.setText("Delete");
        panelWorkers.add(btnDeleteWorkers, new GridConstraints(3, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnCancelWorkers = new JButton();
        btnCancelWorkers.setActionCommand("btnCancelWorker");
        btnCancelWorkers.setEnabled(false);
        btnCancelWorkers.setText("Cancel");
        panelWorkers.add(btnCancelWorkers, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkersPassword = new JLabel();
        labelWorkersPassword.setText("Password");
        panelWorkers.add(labelWorkersPassword, new GridConstraints(1, 5, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelWorkersEmail = new JLabel();
        labelWorkersEmail.setText("Email");
        panelWorkers.add(labelWorkersEmail, new GridConstraints(1, 4, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }
}
