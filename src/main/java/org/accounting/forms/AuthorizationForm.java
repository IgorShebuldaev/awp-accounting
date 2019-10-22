package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.User;
import org.accounting.user.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AuthorizationForm extends JFrame implements ActionListener {
    private JPasswordField passwordField;
    private JPanel panelAuthorization;
    private JTextField textFieldEmail;
    private JButton emailButton;
    private JButton exitButton;
    private JLabel labelEmail;
    private JLabel labelPassword;

    public AuthorizationForm() {
        createAuthorizationForm();

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
    }

    private void createAuthorizationForm() {
        setContentPane(panelAuthorization);
        setTitle("Log In");
        setSize(250, 170);
        setLocationRelativeTo(null);

        emailButton.addActionListener(this);
        exitButton.addActionListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private boolean isAuthorized(String login, String password) {
        User user = new User(login);

        if (!user.isNewRecord() && user.getPassword().equals(password)) {
            CurrentUser.setCurrentUser(user);

            return true;
        }

        return false;
    }

    private void validateUserAuthorization(JTextField login, JPasswordField password) {
        if (isAuthorized(login.getText(), String.valueOf(password.getPassword()))) {
            new MainForm().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Invalid login or password! Try again.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case "signIn":
                validateUserAuthorization(textFieldEmail, passwordField);
                break;
            case "exit":
                System.exit(0);
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
        panelAuthorization = new JPanel();
        panelAuthorization.setLayout(new GridLayoutManager(5, 2, new Insets(5, 5, 5, 5), -1, -1));
        passwordField = new JPasswordField();
        panelAuthorization.add(passwordField, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        textFieldEmail = new JTextField();
        panelAuthorization.add(textFieldEmail, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        emailButton = new JButton();
        emailButton.setActionCommand("signIn");
        emailButton.setText("Sign in");
        panelAuthorization.add(emailButton, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        exitButton = new JButton();
        exitButton.setActionCommand("exit");
        exitButton.setText("Exit");
        panelAuthorization.add(exitButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelEmail = new JLabel();
        labelEmail.setText("Email");
        panelAuthorization.add(labelEmail, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        labelPassword = new JLabel();
        labelPassword.setText("Password");
        panelAuthorization.add(labelPassword, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelAuthorization;
    }

}