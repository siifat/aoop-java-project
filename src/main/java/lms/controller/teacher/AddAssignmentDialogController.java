package lms.controller.teacher;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;

public class AddAssignmentDialogController {

    @FXML
    private MFXDatePicker datePicker;
    @FXML
    private MFXTextField titleField;
    @FXML
    private TextArea descField;
    @FXML
    private MFXTextField filePathField;

    private File selectedFile;

    public MFXTextField getTitleField() {
        return titleField;
    }

    public void setTitleField(MFXTextField titleField) {
        this.titleField = titleField;
    }

    public TextArea getDescField() {
        return descField;
    }

    public void setDescField(TextArea descField) {
        this.descField = descField;
    }

    public MFXTextField getFilePathField() {
        return filePathField;
    }

    public void setFilePathField(MFXTextField filePathField) {
        this.filePathField = filePathField;
    }

    public File getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    public MFXDatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(MFXDatePicker datePicker) {
        this.datePicker = datePicker;
    }

    @FXML
    private void browseClicked(ActionEvent actionEvent) {

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File("C:\\Users\\sifat\\Documents\\aoop-java-project\\src\\main\\resources\\assignments\\0112320247\\Section A"));
        selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            filePathField.setText(selectedFile.getName());
        }
    }
}
