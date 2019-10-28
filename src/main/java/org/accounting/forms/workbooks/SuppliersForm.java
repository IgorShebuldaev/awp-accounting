package org.accounting.forms.workbooks;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import org.accounting.database.models.Supplier;
import org.accounting.forms.helpers.YesNoDialog;
import org.accounting.forms.models.tablemodels.SupplierTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SuppliersForm extends JPanel implements ActionListener {

    private JPanel panelSuppliers;
    private JScrollPane scrollPaneSuppliers;
    private JTable tableSuppliers;
    private JTextField textFieldSuppliersCompanyName;
    private JLabel labelSuppliersCompanyName;
    private JButton btnAddSuppliers;
    private JButton btnEditSuppliers;
    private JButton btnSaveSuppliers;
    private JButton btnCancelSuppliers;
    private JButton btnDeleteSuppliers;
    private SupplierTable supplierTableModel;

    SuppliersForm() {
        super();

        supplierTableModel = new SupplierTable();

        Supplier.getAll().forEach(supplierTableModel::addRecord);
        tableSuppliers.setModel(supplierTableModel);
        tableSuppliers.getTableHeader().setReorderingAllowed(false);

        tableSuppliers.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    setValues();
                }
            }
        });

        createForm();
    }

    private void createForm() {
        btnAddSuppliers.addActionListener(this);
        btnDeleteSuppliers.addActionListener(this);
        btnEditSuppliers.addActionListener(this);
        btnSaveSuppliers.addActionListener(this);
        btnCancelSuppliers.addActionListener(this);

        add(panelSuppliers);
    }

    JPanel getPanel() {
        return panelSuppliers;
    }

    private void insertRecord() {
        Supplier supplier = new Supplier();
        supplier.setName(textFieldSuppliersCompanyName.getText());

        if (!supplier.save()) {
            JOptionPane.showMessageDialog(this, supplier.getErrors().fullMessages("\n"));
            return;
        }

        supplierTableModel.addRecord(supplier);
        textFieldSuppliersCompanyName.setText("");
    }

    private void saveRecord() {
        int rowIndex = tableSuppliers.getSelectedRow();
        Supplier supplier = supplierTableModel.getRecord(rowIndex);
        supplier.setName(textFieldSuppliersCompanyName.getText());

        if (!supplier.save()) {
            JOptionPane.showMessageDialog(this, supplier.getErrors().fullMessages("\n"));
            return;
        }

        supplierTableModel.setValueAt(supplier, rowIndex);
        textFieldSuppliersCompanyName.setText("");
        setDefaultMode();
    }

    private void deleteRecord() {
        int rowIndex = tableSuppliers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

        if (new YesNoDialog("Are you sure you want to delete the record?", "Message").isPositive()) {
            supplierTableModel.getRecord(rowIndex).delete();
            supplierTableModel.removeRow(rowIndex);
        }
    }

    private void setValues() {
        int rowIndex = tableSuppliers.getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "Select an entry in the table!");
            return;
        }

        textFieldSuppliersCompanyName.setText(supplierTableModel.getRecord(rowIndex).getName());
        setEditMode();
    }

    private void setDefaultMode() {
        btnAddSuppliers.setEnabled(true);
        btnEditSuppliers.setEnabled(true);
        btnDeleteSuppliers.setEnabled(true);
        tableSuppliers.setEnabled(true);
        btnCancelSuppliers.setEnabled(false);
        btnSaveSuppliers.setEnabled(false);
        textFieldSuppliersCompanyName.setText("");
    }

    private void setEditMode() {
        btnAddSuppliers.setEnabled(false);
        btnEditSuppliers.setEnabled(false);
        btnDeleteSuppliers.setEnabled(false);
        tableSuppliers.setEnabled(false);
        btnCancelSuppliers.setEnabled(true);
        btnSaveSuppliers.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "btnAddSupplier":
                insertRecord();
                break;
            case "btnEditSupplier":
                setValues();
                break;
            case "btnSaveSupplier":
                saveRecord();
                break;
            case "btnCancelSupplier":
                setDefaultMode();
                break;
            case "btnDeleteSupplier":
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1, true, true));
        panelSuppliers = new JPanel();
        panelSuppliers.setLayout(new GridLayoutManager(4, 8, new Insets(5, 5, 5, 5), -1, -1));
        panel1.add(panelSuppliers, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
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
    }
}
