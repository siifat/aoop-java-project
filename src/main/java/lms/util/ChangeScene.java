package lms.util;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ChangeScene {
    public static void change(String sceneName, Event event) {
        Parent root;
        try {
            root = FXMLLoader.load(new File("src/main/resources/fxml"
                    + sceneName).toURI().toURL());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void change(Scenes scenes, Event event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(new File("src/main/resources/fxml"
                    + scenes.getResourceLocation()).toURI().toURL());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            ;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
}
