package lms.controller.general;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lms.controller.student.DashboardController;
import lms.util.ChangeScene;
import lms.util.Scenes;
import lms.util.UserAttribute;
import lms.util.UserService;

import java.io.IOException;
import java.sql.*;

import static lms.util.UserService.getPassword;
import static lms.util.UserService.userExists;

public class Login {

    public static CurrentLoggedInTeacher currentLoggedInTeacher;
    public static CurrentLoggedInStudent currentLoggedInStudent;


    public static final String CREATE_ACC = "/general/createAcc.fxml";

    public MFXTextField idField;
    public MFXPasswordField passField;
    public MFXRadioButton rbStudent;
    public MFXRadioButton rbTeacher;
    public AnchorPane root;


    public void initialize() {
        // Define the key combination: CTRL + ALT + K
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.K, KeyCombination.CONTROL_DOWN, KeyCombination.ALT_DOWN);

        // Add the key combination and the action to the scene
        root.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getAccelerators().put(keyCombination, this::switchToAdminHome);
            }
        });
    }

    // This method will be called when CTRL + ALT + K is pressed
    private void switchToAdminHome() {
        try {
            // Load the new scene (adminHome.fxml)
            Parent adminRoot = FXMLLoader.load(getClass().getResource("/fxml/admin/adminHome.fxml"));

            // Get the current stage (window)
            Stage stage = (Stage) root.getScene().getWindow();

            // Create a new scene with the loaded FXML and set it to the stage
            Scene adminScene = new Scene(adminRoot);
            stage.setScene(adminScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loginClicked(ActionEvent event) throws IOException {
        String typedID = idField.getText();
        String pass = passField.getText();

        if (typedID.isBlank() || pass.isBlank()) {
            System.out.println("You cannot leave any field blank");
            return;
        }

        if (!isValidUserID(typedID)) {
            System.out.println("ID can only contain digits (0-9) and has to be of length 10.");
            return;
        }

        if (!userExists(typedID, UserAttribute.ID, rbStudent.isSelected())) {
            System.out.println("No such user found. Please check your " + (rbStudent.isSelected() ? "Student ID" : "Teacher ID") + " again.");
            return;
        }

        if (isPasswordCorrect(typedID)) {

            if (rbStudent.isSelected()) {

                setCurrentLoggedInStudent(typedID);

                System.out.println("\nWelcome to your new HOMEPAGE!\n");

                // Load the FXML file for the dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/student/dashboard.fxml"));
                System.out.println(Scenes.STUDENT_DASHBOARD.getResourceLocation());
                Parent dashboardRoot = loader.load();

                // Get the controller for the dashboard scene
                DashboardController dashboardController = loader.getController();

                // Set the IDLabel
                dashboardController.setIDLabel(currentLoggedInStudent.getId());

                // Change the scene
                Scene dashboardScene = new Scene(dashboardRoot);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.show();

            } else if (rbTeacher.isSelected()) {
                setCurrentLoggedInTeacher(typedID);

                System.out.println(currentLoggedInTeacher.getId());
                System.out.println(currentLoggedInTeacher.getName());

                // Load the FXML file for the dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/teacherHome.fxml"));
                Parent dashboardRoot = loader.load();

                // Change the scene
                Scene dashboardScene = new Scene(dashboardRoot);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(dashboardScene);
                stage.show();
            }

        } else {
            System.out.println("Password did not match.");
        }
    }

    private boolean isValidUserID(String id) {
        return id.matches("\\d{10}");
    }

    private boolean isPasswordCorrect(String id) {
        return passField.getText().equals(getPassword(id, UserAttribute.ID, rbStudent.isSelected()));
    }

    public void studentRBselected() {
        idField.setFloatingText("Student ID");
    }

    public void teacherRBselected() {
        idField.setFloatingText("Teacher ID");
    }

    public void forgotPassClicked(MouseEvent mouseEvent) {
        ChangeScene.change(Scenes.FORGOT_PASS, mouseEvent);
    }

    public void createAccClicked(MouseEvent mouseEvent) {
        ChangeScene.change(Scenes.CREATE_ACC, mouseEvent);
    }


    private void setCurrentLoggedInTeacher(String id) {
        try {

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            conn = DriverManager.getConnection(UserService.URL);
            stmt = conn.createStatement();

            String query = "SELECT * FROM teachers WHERE id = '" + id + "'";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String teacher_id = rs.getString("id");
                System.out.println("TEACHEEE : " + teacher_id);
                String password = rs.getString("password");
                String initial = rs.getString("initial");
                String mobile = rs.getString("mobile");
                String role = rs.getString("role");
                String approve = rs.getString("approve");
                String wantEmail = rs.getString("wantEmail");
                String profilePicture = rs.getString("proPic");
                String bio = rs.getString("bio");

                currentLoggedInTeacher = new CurrentLoggedInTeacher(name, email, teacher_id, password, initial, mobile,
                        role, approve, wantEmail, profilePicture, bio);
            }

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentLoggedInStudent(String id) {
        try {

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

            conn = DriverManager.getConnection(UserService.URL);
            stmt = conn.createStatement();

            String query = "SELECT * FROM students WHERE id = '" + id + "'";
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String student_id = rs.getString("id");
                String password = rs.getString("password");
                String approved = rs.getString("approved");
                String mobile = rs.getString("mobile");
                String registration = rs.getString("registration");
                String role = rs.getString("role");
                String wantEmail = rs.getString("wantEmail");
                String profilePicture = rs.getString("proPic");
                String bio = rs.getString("bio");

                currentLoggedInStudent = new CurrentLoggedInStudent(name, email, student_id, password, approved,
                        registration, mobile, role, wantEmail, profilePicture, bio);
            }

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
