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
import org.controlsfx.control.Rating;

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

    // panes
    @FXML private AnchorPane dashboardPane;
    @FXML private AnchorPane privateFilesLoginPane;
    @FXML private AnchorPane privateFilesPane;
    @FXML private AnchorPane myProfilePane;
    @FXML private AnchorPane settingsPane;


    @FXML
    private Rating rating;
    @FXML
    private Label ratingLabel;

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

        privateFilesLoginPane.setVisible(false);
        privateFilesPane.setVisible(false);
        myProfilePane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(true);
    }

    public void editClassClicked(MouseEvent mouseEvent) {
        setSelectedLabel(editClassesLabel);
    }

    public void manageStudsClicked(MouseEvent mouseEvent) {
        setSelectedLabel(manageStudsLabel);
    }

    public void myProfileClicked(MouseEvent mouseEvent) {
        setSelectedLabel(myProfileLabel);

        privateFilesLoginPane.setVisible(false);
        privateFilesPane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(false);
        myProfilePane.setVisible(true);
    }

    public void settingsClicked(MouseEvent mouseEvent) {
        setSelectedLabel(settingsLabel);

        privateFilesLoginPane.setVisible(false);
        privateFilesPane.setVisible(false);
        myProfilePane.setVisible(false);
        dashboardPane.setVisible(false);
        settingsPane.setVisible(true);
    }

    public void privateFilesClicked(MouseEvent mouseEvent) {
        setSelectedLabel(privateFilesLabel);

        privateFilesPane.setVisible(false);
        myProfilePane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(false);
        privateFilesLoginPane.setVisible(true);
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

    @FXML
    private void createGroupBtnClicked(ActionEvent actionEvent) {

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

    @FXML
    private void handleRating(MouseEvent event) {
        System.out.println(rating.getRating());
        String labelString = "User Rating: " + rating.getRating();
        if (rating.getRating() == 5) labelString += " Thank you";
        else labelString += "   Nothing but 5 stars please 😊";
        if (rating.getRating() != 5) rating.setRating(5);
        ratingLabel.setText(labelString);
    }

    @FXML
    private void addComplaintClicked(ActionEvent e){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/teacher/sendComplaintDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e1) {
            System.out.println("Couldn't load the dialog");
            throw new RuntimeException(e1);
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
    }
}
