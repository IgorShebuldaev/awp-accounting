package org.accounting.forms;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.accounting.database.models.Note;
import org.accounting.user.CurrentUser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class NotesForm extends JDialog {
    private JTextArea textAreaNotes;
    private JScrollPane scrollPaneNotes;
    private JPanel panelNotes;

    NotesForm() {
        setContentPane(panelNotes);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Notes");

        textAreaNotes.setText(Note.getNoteCurrentUser(CurrentUser.id).note);

        textAreaNotes.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Note.updateNote(CurrentUser.id, textAreaNotes.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Note.updateNote(CurrentUser.id, textAreaNotes.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
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
