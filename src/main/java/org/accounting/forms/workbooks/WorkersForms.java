package org.accounting.forms.workbooks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Base;
import org.accounting.database.models.Position;
import org.accounting.database.models.Worker;
import org.accounting.forms.PositionsForm;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.comboboxmodels.PositionComboBoxModel;
import org.accounting.forms.models.tablemodels.WorkerTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private WorkerTable workerTableModel;
    private PositionComboBoxModel positionComboBoxModel;

    WorkersForms() {
        super();

        workerTableModel = new WorkerTable();

        Worker.getAll().forEach(workerTableModel::addRecord);
        tableWorkers.setModel(workerTableModel);
        tableWorkers.getTableHeader().setReorderingAllowed(false);

        positionComboBoxModel = new PositionComboBoxModel();
        addItemComboBoxPosition();

        spinnerWorkersDateOfBirth.setModel(new SpinnerDateModel());
        spinnerWorkersDateOfBirth.setEditor(new JSpinner.DateEditor(spinnerWorkersDateOfBirth, "dd.MM.yyyy"));
        spinnerWorkersDateOfBirth.setValue(new Date());

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

    private void showPositionsForm() {
        new PositionsForm().setVisible(true);
        addItemComboBoxPosition();
    }

    private void addItemComboBoxPosition() {
        positionComboBoxModel.removeAllElements();
        Position.getAll().forEach(positionComboBoxModel::addRecord);
        comboBoxWorkersPositions.setModel(positionComboBoxModel);
    }

    private void insertRecord() {
        Worker worker = new Worker();
        worker.setFullName(textFieldWorkersFullName.getText());
        worker.setDateOfBirth((Date) spinnerWorkersDateOfBirth.getValue());
        worker.setPositionId(positionComboBoxModel.getSelection().map(Base::getId).orElse(0));

        if (!worker.save()) {
            JOptionPane.showMessageDialog(this, worker.getErrors().fullMessages("\n"));
            return;
        }

        workerTableModel.addRecord(worker);
        textFieldWorkersFullName.setText("");
        textFieldWorkersEmail.setText("");
        textFieldWorkersPassword.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableWorkers.getSelectedRow();
        Worker worker = workerTableModel.getRecord(rowIndex);

        worker.setFullName(textFieldWorkersFullName.getText());
        worker.setDateOfBirth((Date) spinnerWorkersDateOfBirth.getValue());
        worker.setPositionId(positionComboBoxModel.getSelection().map(Base::getId).orElse(0));

        if (!worker.save()) {
            JOptionPane.showMessageDialog(this, worker.getErrors().fullMessages("\n"));
            return;
        }

        workerTableModel.setValueAt(worker, rowIndex);
        textFieldWorkersFullName.setText("");
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            workerTableModel.getRecord(rowIndex).delete();
            workerTableModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

        Worker worker = workerTableModel.getRecord(rowIndex);
        textFieldWorkersFullName.setText(worker.getFullName());
        spinnerWorkersDateOfBirth.setValue(worker.getDateOfBirth());
        comboBoxWorkersPositions.setSelectedItem(worker.getPositionId());
        setEditMode();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnAddWorker":
                insertRecord();
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
                addItemComboBoxPosition();
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
        panelWorkers.setLayout(new GridLayoutManager(4, 4, new Insets(5, 5, 5, 5), -1, -1));
        panel1.add(panelWorkers, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPaneWorkers = new JScrollPane();
        panelWorkers.add(scrollPaneWorkers, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
    }
}
