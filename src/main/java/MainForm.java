import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainForm {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    private JFrame authorization_frame = new JFrame("Log in");
    private JFrame main_frame = new JFrame("Accounting");

    public void createUserAuthorizationForm(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;

        authorization_frame.setSize(300, 150);
        authorization_frame.setLocationRelativeTo(null);
        authorization_frame.add(createLoginFiled());
        authorization_frame.add(createPasswordFiled());
        authorization_frame.setVisible(true);
        authorization_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createMainForm() {
        main_frame.setJMenuBar(creatMenuBar());
        main_frame.setSize(WIDTH, HEIGHT);
        main_frame.setLocationRelativeTo(null);
       // main_frame.setVisible(true);

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

    private JTextField createLoginFiled(){
        JTextField textField = new JTextField(20);
        return textField;
    }

    private JPasswordField createPasswordFiled(){
        JPasswordField passwordField = new JPasswordField(10);
        return passwordField;
    }

}