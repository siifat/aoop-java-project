package game;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lms.controller.dictionary.Controller;
import lms.controller.dictionary.DictionaryHomepage;

import java.io.IOException;

public class MultipleChoiceEndController extends Controller {
    @FXML
    private Label scoreBox;

    @FXML private ImageView hppy;
    @FXML private ImageView sad;

    @FXML
    public void switchToGameScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dictionary/dictionaryHomepage.fxml"));
        root = loader.load();

        // Load the DialogPane from the FXML file
        DictionaryHomepage controller = loader.getController();

        Label l = controller.gameLabel;
        controller.setSelectedLabel(l);
        controller.homepagePane.setVisible(false);
        controller.addNewWordPane.setVisible(false);
        controller.onlineModePane.setVisible(false);
        controller.translatorPane.setVisible(false);
        controller.homepagePane.setVisible(false);
        controller.gamePane.setVisible(true);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void displayScore(int score, int numberOfQuestions) {
        String res = "You got " + score + " question(s) correct out of " + numberOfQuestions + " question(s).";
        if (score < 5) {
            res += " Seriously?";
            hppy.setVisible(false);
            sad.setVisible(true);
        }
        if (score > 5) {
            res += " Well done!";
            sad.setVisible(false);
            hppy.setVisible(true);
        }
        scoreBox.setText(res);
    }

    @FXML
    public void replay(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game/MultipleChoiceScene.fxml"));
        root = loader.load();

        MultipleChoiceController multipleChoiceController = loader.getController();
        multipleChoiceController.setQuestion(event);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
