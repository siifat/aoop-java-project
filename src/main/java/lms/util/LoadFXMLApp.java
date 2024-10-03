package lms.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lms.App;

import java.io.IOException;

public class LoadFXMLApp {

    private static final String RELATIVE_FXML_LOCATION = "/fxml";

    public static void load(String locationOfFXML){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(
                    RELATIVE_FXML_LOCATION + locationOfFXML));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
