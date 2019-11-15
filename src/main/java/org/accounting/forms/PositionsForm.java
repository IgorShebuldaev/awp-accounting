package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Position;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.PositionFX;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PositionsForm extends JDialog implements ActionListener {
    private JTable tablePositions;
    private JButton addButton;
    private JButton editButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton deleteButton;
    private JTextField textFieldPosition;
    private JLabel labelPosition;
    private JScrollPane scrollPanePositions;
    private JPanel panelPositions;
    private PositionFX positionFXModel;

    public PositionsForm() {
        createForm();

        //positionFXModel = new PositionFX();

        //Position.getAll().forEach(positionFXModel::addRecord);
        //tablePositions.setModel(positionFXModel);
        tablePositions.getTableHeader().setReorderingAllowed(false);

        tablePositions.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    setValues();
                }
            }
        });
    }

    private void createForm() {
        setContentPane(panelPositions);
        setSize(450, 300);
        setLocationRelativeTo(null);
        setTitle("Positions");
        setModalityType(ModalityType.APPLICATION_MODAL);

        addButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        cancelButton.addActionListener(this);
        deleteButton.addActionListener(this);
    }

    private void insertRecord() {
        Position position = new Position();
        position.setName(textFieldPosition.getText());

        if (!position.save()) {
            JOptionPane.showMessageDialog(this, position.getErrors().fullMessages("\n"));
            return;
        }

        //positionFXModel.addRecord(position);
        textFieldPosition.setText("");
    }

    private void saveRecord() {
        int rowIndex = tablePositions.getSelectedRow();
        //Position position = positionFXModel.getRecord(rowIndex);
       // position.setName(textFieldPosition.getText());

//        if (!position.save()) {
//            JOptionPane.showMessageDialog(this, position.getErrors().fullMessages("\n"));
//            return;
//        }

       // positionFXModel.setValueAt(position, rowIndex);
        textFieldPosition.setText("");
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tablePositions.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
//            positionFXModel.getRecord(rowIndex).delete();
//            positionFXModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tablePositions.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(this, "Select an entry in the table!");
            return;
        }

        //textFieldPosition.setText(positionFXModel.getRecord(rowIndex).getName());
        setEditMode();
    }

    private void setDefaultMode() {
        addButton.setEnabled(true);
        editButton.setEnabled(true);
        saveButton.setEnabled(false);
        cancelButton.setEnabled(false);
        deleteButton.setEnabled(true);
        tablePositions.setEnabled(true);
        textFieldPosition.setText("");
    }

    private void setEditMode() {
        addButton.setEnabled(false);
        editButton.setEnabled(false);
        saveButton.setEnabled(true);
        cancelButton.setEnabled(true);
        deleteButton.setEnabled(false);
        tablePositions.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
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
        panelPositions = new JPanel();
        panelPositions.setLayout(new GridLayoutManager(4, 5, new Insets(5, 5, 5, 5), -1, -1));
        scrollPanePositions = new JScrollPane();
        panelPositions.add(scrollPanePositions, new GridConstraints(0, 0, 1, 5, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        tablePositions = new JTable();
        scrollPanePositions.setViewportView(tablePositions);
        addButton = new JButton();
        addButton.setActionCommand("addButton");
        addButton.setText("Add");
        panelPositions.add(addButton, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        editButton = new JButton();
        editButton.setActionCommand("editButton");
        editButton.setText("Edit");
        panelPositions.add(editButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveButton = new JButton();
        saveButton.setActionCommand("saveButton");
        saveButton.setEnabled(false);
        saveButton.setText("Save");
        panelPositions.add(saveButton, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancelButton");
        cancelButton.setEnabled(false);
        cancelButton.setText("Cancel");
        panelPositions.add(cancelButton, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        deleteButton = new JButton();
        deleteButton.setActionCommand("deleteButton");
        deleteButton.setText("Delete");
        panelPositions.add(deleteButton, new GridConstraints(3, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textFieldPosition = new JTextField();
        panelPositions.add(textFieldPosition, new GridConstraints(2, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        labelPosition = new JLabel();
        labelPosition.setText("Position");
        panelPositions.add(labelPosition, new GridConstraints(1, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelPositions;
    }

}
