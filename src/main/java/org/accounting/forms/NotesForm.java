package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Note;
import org.accounting.user.CurrentUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class NotesForm extends JDialog {
    private JTextArea textAreaNotes;
    private JScrollPane scrollPaneNotes;
    private JPanel panelNotes;

    NotesForm() {
        createNotesForm();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                Note.updateNoteCurrentUser(CurrentUser.id, textAreaNotes.getText());
            }
        });
    }

    private void createNotesForm() {
        setContentPane(panelNotes);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Notes");

        textAreaNotes.setText(Note.getNoteCurrentUser(CurrentUser.id).note);
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
        panelNotes = new JPanel();
        panelNotes.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        scrollPaneNotes = new JScrollPane();
        panelNotes.add(scrollPaneNotes, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textAreaNotes = new JTextArea();
        scrollPaneNotes.setViewportView(textAreaNotes);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelNotes;
    }
}