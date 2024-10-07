package lms.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneChanger {

    // Method to switch scenes
    public static void switchScene(Stage stage, String fxmlFile) throws Exception {
        // Load the new FXML file
        Parent root = FXMLLoader.load(SceneChanger.class.getResource(fxmlFile));

        // Create a new scene from the loaded FXML
        Scene newScene = new Scene(root);

        // Set the new scene to the stage
        stage.setScene(newScene);
        stage.show(); // Ensure the stage is displayed
    }
}
