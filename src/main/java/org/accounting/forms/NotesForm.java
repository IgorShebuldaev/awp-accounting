package org.accounting.forms;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import org.accounting.database.models.Note;
import org.accounting.user.CurrentUser;

import java.net.URL;
import java.util.ResourceBundle;

public class NotesForm implements Initializable {
    @FXML private TextArea taNotes;
    private Note note;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(note != null) {
            taNotes.setText(note.getMessage());
        }

        note = new Note();
        note.setNoteCurrentUser(CurrentUser.getUser().getId());

        //TODO:mb onCreatForm
//        setOnCloseRequest(windowEvent -> {
//            note.setMessage(taNotes.getText());
//            note.save();
//        });

    }



}
