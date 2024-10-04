package lms.controller.teacher;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import lms.controller.general.Login;
import lms.util.ShowAlert;
import lms.util.UserService;

import java.sql.*;

public class SendComplaintDialogController {

    @FXML
    private MFXToggleButton anonymousTB;
    @FXML
    private MFXTextField titleField;
    @FXML
    private TextArea detailArea;


    public void initialize(){
        // Add a listener to the selected property
        anonymousTB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                anonymousTB.setText("Keep my identity hidden");
            } else {
                anonymousTB.setText("Keep my identity disclosed");
            }
        });
    }


    @FXML
    private void sendButtonPressed(ActionEvent actionEvent) {

        String complaintTitle = titleField.getText();
        String complaintDetail = detailArea.getText();


        Connection conn = null;

        try {

            conn = DriverManager.getConnection(UserService.COMPLAINT_URL);

            String query = "INSERT INTO complain VALUES(?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);

            if(anonymousTB.isSelected()){

                ps.setString(1, "Anonymous");
                ps.setString(2, "Anonymous");
                ps.setString(3, "Anonymous");
                ps.setString(4, "Teacher");
                ps.setString(5, complaintTitle);
                ps.setString(6, complaintDetail);
            } else {
                ps.setString(1, Login.currentLoggedInTeacher.getName());
                ps.setString(2, Login.currentLoggedInTeacher.getId());
                ps.setString(3, Login.currentLoggedInTeacher.getEmail());
                ps.setString(4, "Teacher");
                ps.setString(5, complaintTitle);
                ps.setString(6, complaintDetail);
            }

            ps.executeUpdate();

            conn.close();
            ShowAlert.show("Complaint", "We've received your complaint.", Alert.AlertType.CONFIRMATION);

        } catch (SQLException e) {
            ShowAlert.show("Error", "Error sending complaint to Admin", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
}
