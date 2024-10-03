package lms.controller.general;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import lms.util.ChangeScene;
import lms.util.ShowDesktopNotification;
import lms.util.UserService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static lms.util.UserService.TABLE_STUDENTS;
import static lms.util.UserService.TABLE_TEACHERS;

public class CreateAcc {

    public MFXTextField nameField;
    public MFXTextField emailField;
    public MFXTextField idField;
    public MFXPasswordField passField;
    public MFXRadioButton rbStudent;
    public MFXRadioButton rbTeacher;

    public void createAcc() {

        // Insert the user info into Database
        String name = nameField.getText();
        String email = emailField.getText();
        String id = idField.getText();
        String pass = passField.getText();

        if(!isValidEmail(email)){
            new Alert(Alert.AlertType.WARNING, "Invalid email").showAndWait();
            return;
        }

        if (!isValidUserID(id)) {
            new Alert(Alert.AlertType.WARNING, "ID can only contain digits (0-9) and has to be of " +
                    "length 10.").showAndWait();
            return;
        }

        try {
            Connection conn;
            conn = DriverManager.getConnection(UserService.URL);

            String query = "INSERT INTO " + (rbStudent.isSelected() ? TABLE_STUDENTS : TABLE_TEACHERS) + " VALUES(?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, id);
            ps.setString(4, pass);

            ps.executeUpdate();

            ShowDesktopNotification.show("Success", "Account creation successful. Wait for admin approval");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isValidEmail(String email) {

        return (!email.isBlank() && email.contains("@") && email.contains("."));
    }

    private boolean isValidUserID(String id) {
        return id.matches("\\d{10}");
    }

    public void signInClicked(MouseEvent mouseEvent) throws Exception {
        ChangeScene.change("/general/login.fxml", mouseEvent);
    }

    public boolean rbTeacherIsSelected() {
        return rbTeacher.isSelected();
    }

    public boolean rbStudentIsSelected() {
        return rbStudent.isSelected();
    }

    public String getPassFieldText() {
        return passField.getText();
    }

    public String getIdFieldText() {
        return idField.getText();
    }

    public String getEmailFieldText() {
        return emailField.getText();
    }

    public String getNameFieldText() {
        return nameField.getText();
    }
}
