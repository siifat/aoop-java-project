package lms.controller.teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lms.App;

import java.io.IOException;
import java.util.Optional;

public class TeacherHome {

    public Label dashboardLabel;
    public Label editClassesLabel;
    public Label manageStudsLabel;
    public Label myProfileLabel;
    public Label settingsLabel;
    public Label privateFilesLabel;
    public Circle profilePicCircle;

    @FXML
    private AnchorPane root;

    private Label selectedLabel;


    public void initialize() {
        // Load the image from the resources folder
        setProfilePic("/images/general/user_2.png");

        setSelectedLabel(dashboardLabel);
    }

    private void setProfilePic(String picLocation) {
        Image proPic = new Image(getClass().getResource(picLocation).toExternalForm());
        profilePicCircle.setFill(new ImagePattern(proPic));
    }

    public void dashboardClicked(MouseEvent mouseEvent) {
        setSelectedLabel(dashboardLabel);
    }

    public void editClassClicked(MouseEvent mouseEvent) {
        setSelectedLabel(editClassesLabel);
    }

    public void manageStudsClicked(MouseEvent mouseEvent) {
        setSelectedLabel(manageStudsLabel);
    }

    public void myProfileClicked(MouseEvent mouseEvent) {
        setSelectedLabel(myProfileLabel);
    }

    public void settingsClicked(MouseEvent mouseEvent) {
        setSelectedLabel(settingsLabel);
    }

    public void privateFilesClicked(MouseEvent mouseEvent) {
        setSelectedLabel(privateFilesLabel);
    }

    private void setSelectedLabel(Label label) {
        // Remove the 'selected-label' class from all labels
        dashboardLabel.getStyleClass().remove("selected-label");
        editClassesLabel.getStyleClass().remove("selected-label");
        manageStudsLabel.getStyleClass().remove("selected-label");
        myProfileLabel.getStyleClass().remove("selected-label");
        settingsLabel.getStyleClass().remove("selected-label");
        privateFilesLabel.getStyleClass().remove("selected-label");

        // Add the 'selected-label' class to the clicked label
        label.getStyleClass().add("selected-label");
        // Store this label inside a variable so that we can access it later using this
        selectedLabel = label;
    }

    public void chatIconClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/LoginView.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);

            // Don't block the main window (Modality.NONE)
            loginStage.initModality(Modality.NONE);

            // Show the login window
            loginStage.show();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    public void createGroupBtnClicked(ActionEvent actionEvent) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/teacher/sendLinkDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            throw new RuntimeException(e);
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
    }
}
