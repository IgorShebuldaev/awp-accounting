package forms;

import database.Database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm implements ActionListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private JFrame authorization_frame = new JFrame("Log in");
    private JFrame main_frame = new JFrame("Accounting");

    private JTextField textField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);

    public void createUserAuthorizationForm() {
        JPanel jPanel = new JPanel();

        JButton okButton = new JButton("Ok");
        okButton.setActionCommand("ok");

        JButton exitButton = new JButton("Exit");
        exitButton.setActionCommand("exit");

        jPanel.add(textField);
        jPanel.add(passwordField);
        jPanel.add(okButton);
        jPanel.add(exitButton);

        authorization_frame.setSize(250, 130);
        authorization_frame.setLocationRelativeTo(null);
        authorization_frame.add(jPanel);
        authorization_frame.setVisible(true);
        authorization_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        okButton.addActionListener(this::actionPerformed);
        exitButton.addActionListener(this::actionPerformed);
    }

    private void createMainForm() {
        main_frame.setJMenuBar(creatMenuBar());
        main_frame.setSize(WIDTH, HEIGHT);
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);

        main_frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int x = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to exit?",
                        "Confirm Exit", JOptionPane.YES_NO_OPTION);

                if (x == JOptionPane.YES_OPTION) {
                    main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else {
                    main_frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    private JMenuBar creatMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");

        menu.add(new JMenuItem("Settings"));
        menuBar.add(menu);

        return menuBar;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equals(e.getActionCommand()))
            validateUserAuthorization(textField, passwordField);
        if ("exit".equals(e.getActionCommand()))
            System.exit(0);
    }

    private void validateUserAuthorization(JTextField login, JPasswordField password) {
        if (Database.login(login.getText(), String.valueOf(password.getPassword()))) {
            authorization_frame.dispose();
            createMainForm();
        } else {
            JOptionPane.showMessageDialog(authorization_frame, "Invalid login or password! Try again.");
            return;
        }
    }
}