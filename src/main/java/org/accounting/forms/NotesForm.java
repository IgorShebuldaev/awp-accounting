package org.accounting.forms;

import org.accounting.database.models.Note;
import org.accounting.user.CurrentUser;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesForm extends BaseController implements Initializable {
    @FXML private TextArea taNotes;
    private Note note;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        note = new Note();
        note.setNoteCurrentUser(CurrentUser.getUser().getId());

        if(note != null) {
            taNotes.setText(note.getMessage());
        }
    }

    @Override
    public void postInitializable() {
        stage.setTitle("Notes");

        stage.setOnCloseRequest(windowEvent -> {
            note.setMessage(taNotes.getText());
            note.save();
        });
    }
}
