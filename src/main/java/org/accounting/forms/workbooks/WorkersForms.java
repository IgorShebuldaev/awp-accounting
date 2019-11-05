package org.accounting.forms.workbooks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import org.accounting.database.models.*;
import org.accounting.forms.PositionsForm;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.comboboxmodels.PositionComboBoxModel;
import org.accounting.forms.models.tablemodels.WorkerFX;
import org.accounting.forms.partials.UserFields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class WorkersForms extends JPanel implements ActionListener {
    private JTable tableWorkers;
    private JButton addButton;
    private JTextField textFieldFullName;
    private JSpinner spinnerDateOfBirth;
    private JComboBox comboBoxPositions;
    private JButton saveButton;
    private JButton editButton;
    private JButton showPositionsFormButton;
    private JPanel panelWorkers;
    private JScrollPane scrollPaneWorkers;
    private JLabel labelFullName;
    private JLabel labelDateOfBirth;
    private JLabel labelPosition;
    private UserFields userFieldsPanel;
    private JButton cancelButton;
    private JButton deleteButton;
    private WorkerFX workerFXModel;
    private PositionComboBoxModel positionComboBoxModel;

    WorkersForms() {
        super();

        workerFXModel = new WorkerFX();

        Worker.getAll().forEach(workerFXModel::addRecord);
        //tableWorkers.setModel(workerFXModel);
        tableWorkers.getTableHeader().setReorderingAllowed(false);

        positionComboBoxModel = new PositionComboBoxModel();
        addItemComboBoxPosition();

        spinnerDateOfBirth.setModel(new SpinnerDateModel());
        spinnerDateOfBirth.setEditor(new JSpinner.DateEditor(spinnerDateOfBirth, "dd.MM.yyyy"));
        spinnerDateOfBirth.setValue(new Date());

        tableWorkers.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    setValues();
                }
            }
        });

        createForm();
    }

    private void createForm() {
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        deleteButton.addActionListener(this);
        showPositionsFormButton.addActionListener(this);

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
        comboBoxPositions.setModel(positionComboBoxModel);
    }

    private void insertRecord() {
        User user = userFieldsPanel.buildUser();

        if (!user.isValid()) {
            JOptionPane.showMessageDialog(this, user.getErrors().fullMessages("\n"));
            return;
        }

        Worker worker = new Worker();
        worker.setFullName(textFieldFullName.getText());
        worker.setDateOfBirth((Date) spinnerDateOfBirth.getValue());
        worker.setPositionID(positionComboBoxModel.getSelection().map(Base::getId).orElse(0));

        if (!worker.isValid()) {
            JOptionPane.showMessageDialog(this, worker.getErrors().fullMessages("\n"));
            return;
        }

        worker.setUser(user);
        worker.save();

        workerFXModel.addRecord(worker);
        textFieldFullName.setText("");
        userFieldsPanel.textFieldEmail.setText("");
        userFieldsPanel.textFieldPassword.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableWorkers.getSelectedRow();
        Worker worker = workerFXModel.getRecord(rowIndex);

        worker.setFullName(textFieldFullName.getText());
        worker.setDateOfBirth((Date) spinnerDateOfBirth.getValue());
        worker.setPositionID(positionComboBoxModel.getSelection().map(Base::getId).orElse(0));

        if (!worker.save()) {
            JOptionPane.showMessageDialog(this, worker.getErrors().fullMessages("\n"));
            return;
        }

        workerFXModel.setValueAt(worker, rowIndex);
        textFieldFullName.setText("");
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            workerFXModel.getRecord(rowIndex).delete();
            workerFXModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

        Worker worker = workerFXModel.getRecord(rowIndex);
        textFieldFullName.setText(worker.getFullName());
        spinnerDateOfBirth.setValue(worker.getDateOfBirth());
        comboBoxPositions.setSelectedItem(worker.getPosition().getName());
        setEditMode();
    }

    private void setDefaultMode() {
        addButton.setEnabled(true);
        editButton.setEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        deleteButton.setEnabled(true);
        showPositionsFormButton.setEnabled(true);
        tableWorkers.setEnabled(true);
        textFieldFullName.setText("");
    }

    private void setEditMode() {
        addButton.setEnabled(false);
        editButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        deleteButton.setEnabled(false);
        showPositionsFormButton.setEnabled(false);
        tableWorkers.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "add":
                insertRecord();
                break;
            case "edit":
                setValues();
                break;
            case "save":
                saveRecord();
                break;
            case "cancel":
                setDefaultMode();
                break;
            case "delete":
                deleteRecord();
                break;
            case "showPositionsForm":
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
        panelWorkers.setLayout(new GridLayoutManager(9, 6, new Insets(5, 5, 5, 5), -1, -1));
        panel1.add(panelWorkers, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        scrollPaneWorkers = new JScrollPane();
        panelWorkers.add(scrollPaneWorkers, new GridConstraints(0, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tableWorkers = new JTable();
        scrollPaneWorkers.setViewportView(tableWorkers);
        addButton = new JButton();
        addButton.setActionCommand("add");
        addButton.setText("Add");
        panelWorkers.add(addButton, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("save");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelWorkers.add(saveButton, new GridConstraints(8, 2, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("edit");
        editButton.setText("Edit");
        panelWorkers.add(editButton, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelFullName = new JLabel();
        labelFullName.setText("Full name");
        panelWorkers.add(labelFullName, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelDateOfBirth = new JLabel();
        labelDateOfBirth.setText("Date of birth");
        panelWorkers.add(labelDateOfBirth, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPosition = new JLabel();
        labelPosition.setText("Position");
        panelWorkers.add(labelPosition, new GridConstraints(6, 2, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancel");
        cancelButton.setText("Cancel");
        panelWorkers.add(cancelButton, new GridConstraints(8, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("delete");
        deleteButton.setText("Delete");
        panelWorkers.add(deleteButton, new GridConstraints(8, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldFullName = new JTextField();
        panelWorkers.add(textFieldFullName, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        spinnerDateOfBirth = new JSpinner();
        panelWorkers.add(spinnerDateOfBirth, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        comboBoxPositions = new JComboBox();
        panelWorkers.add(comboBoxPositions, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        showPositionsFormButton = new JButton();
        showPositionsFormButton.setActionCommand("showPositionsForm");
        showPositionsFormButton.setText(". . .");
        panelWorkers.add(showPositionsFormButton, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panelWorkers.add(spacer1, new GridConstraints(1, 1, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panelWorkers.add(spacer2, new GridConstraints(1, 2, 5, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panelWorkers.add(spacer3, new GridConstraints(1, 0, 5, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        userFieldsPanel = new UserFields();
        panelWorkers.add(userFieldsPanel.$$$getRootComponent$$$(), new GridConstraints(1, 4, 7, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }
}
