package lms.controller.dictionary;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class Controller {
    @FXML
    protected Parent root;
    protected Stage stage;
    protected Scene scene;

    @FXML
    protected AnchorPane rootAnchor;
}