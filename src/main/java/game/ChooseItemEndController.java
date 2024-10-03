package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lms.controller.dictionary.Controller;

import java.io.IOException;

public class ChooseItemEndController extends Controller {
    @FXML
    private Label scoreBox;

    @FXML
    public void switchToGameScene(ActionEvent event) throws IOException {
        FXMLLoader gameScene = new FXMLLoader(getClass().getResource("gameScene.fxml"));
        root = gameScene.load();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void displayScore(int score, int numberOfQuestions) {
        numberOfQuestions++;
        String res = "You got " + score + " question(s) correct out of " + numberOfQuestions + " question(s).";
        if (score == 0) {
            res += " Seriously?";
        }
        if (score == numberOfQuestions) {
            res += " Well done!";
        }
        scoreBox.setText(res);
    }

    @FXML
    public void replay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChooseItem.fxml"));
        root = loader.load();

        ChooseItemController chooseItemController = loader.getController();
        chooseItemController.initializeQuestion(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}
