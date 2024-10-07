package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lms.controller.dictionary.Controller;

import java.io.IOException;

public class GameSceneController extends Controller {
    @FXML
    private Button numberOfQuestionsButton;
    @FXML
    private Button closeButton;

    @FXML
    public void switchToMultipleChoiceScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game/multipleChoiceScene.fxml"));
        root = loader.load();

        MultipleChoiceController multipleChoiceController = loader.getController();
        multipleChoiceController.setQuestion(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToChooseItemGameScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game/ChooseItem.fxml"));
        root = loader.load();

        ChooseItemController chooseItemController = loader.getController();
        chooseItemController.initializeQuestion(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
