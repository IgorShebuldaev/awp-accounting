package org.accounting.forms.workbooks;

import org.accounting.database.models.User;
import org.accounting.database.models.Worker;
import org.accounting.forms.PositionsForm;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.WorkerFX;
import org.accounting.forms.partials.UserFields;

import javax.swing.*;
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
    //private PositionComboBoxModel positionComboBoxModel;

    WorkersForms() {
        super();

        //workerFXModel = new WorkerFX();

        //.getAll().forEach(workerFXModel::addRecord);
        //tableWorkers.setModel(workerFXModel);
        tableWorkers.getTableHeader().setReorderingAllowed(false);

       // positionComboBoxModel = new PositionComboBoxModel();
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
//        positionComboBoxModel.removeAllElements();
//        Position.getAll().forEach(positionComboBoxModel::addRecord);
//        comboBoxPositions.setModel(positionComboBoxModel);
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
      //  worker.setPositionID(positionComboBoxModel.getSelection().map(Base::getId).orElse(0));

        if (!worker.isValid()) {
            JOptionPane.showMessageDialog(this, worker.getErrors().fullMessages("\n"));
            return;
        }

        worker.setUser(user);
        worker.save();

       // workerFXModel.addRecord(worker);
        textFieldFullName.setText("");
//        userFieldsPanel.textFieldEmail.setText("");
//        userFieldsPanel.textFieldPassword.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableWorkers.getSelectedRow();
//        Worker worker = workerFXModel.getRecord(rowIndex);
//
//        worker.setFullName(textFieldFullName.getText());
//        worker.setDateOfBirth((Date) spinnerDateOfBirth.getValue());
//        worker.setPositionID(positionComboBoxModel.getSelection().map(Base::getId).orElse(0));

//        if (!worker.save()) {
//            JOptionPane.showMessageDialog(this, worker.getErrors().fullMessages("\n"));
//            return;
//        }

       // workerFXModel.setValueAt(worker, rowIndex);
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
          //  workerFXModel.getRecord(rowIndex).delete();
           // workerFXModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableWorkers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

       // Worker worker = workerFXModel.getRecord(rowIndex);
//        textFieldFullName.setText(worker.getFullName());
//        spinnerDateOfBirth.setValue(worker.getDateOfBirth());
//        comboBoxPositions.setSelectedItem(worker.getPosition().getName());
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

}
