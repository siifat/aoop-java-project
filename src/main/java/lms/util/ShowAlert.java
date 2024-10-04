package lms.util;

import javafx.scene.control.Alert;

public class ShowAlert {

    public static void show(String title, String message, Alert.AlertType type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
