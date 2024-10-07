package game;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lms.controller.dictionary.Controller;
import lms.controller.dictionary.DictionaryHomepage;

import java.io.IOException;

public class MultipleChoiceController extends Controller {
    private int numberOfQuestionsUsed = 0;
    private final MultipleChoice multipleChoice = MultipleChoice.getMultipleChoice();
    private String correctAnswer;

    @FXML
    private Label questionBox;
    @FXML
    private Label resultBox;
    @FXML
    private Button AButton;
    @FXML
    private Button BButton;
    @FXML
    private Button CButton;
    @FXML
    private Button DButton;
    @FXML
    private Label scoreBox;

    @FXML
    public void setQuestion(ActionEvent event) throws IOException {
        if (numberOfQuestionsUsed == multipleChoice.getNumberOfQuestions()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game/MultipleChoiceEnd.fxml"));
            root = loader.load();

            MultipleChoiceEndController multipleChoiceEndController = loader.getController();
            multipleChoiceEndController.displayScore(multipleChoice.getScore(), multipleChoice.getNumberOfQuestions());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return;
        }

        MultipleChoiceQuestion currentQuestion = multipleChoice.returnRandomQuestion();

        questionBox.setText(currentQuestion.getQuestion());
        AButton.setText(currentQuestion.getAnswerA());
        BButton.setText(currentQuestion.getAnswerB());
        CButton.setText(currentQuestion.getAnswerC());
        DButton.setText(currentQuestion.getAnswerD());
        correctAnswer = currentQuestion.getCorrectAnswer();

        resultBox.setText("Choose your answer!");
        numberOfQuestionsUsed++;
    }

//    public void correct() {
//        resultBox.setText("Correct!");
//        multipleChoice.increaseHighscore();
//        scoreBox.setText(multipleChoice.getScore() + "");
//    }


    public void correct() {
        resultBox.setText("Correct!");
        multipleChoice.increaseHighscore();
        scoreBox.setText(multipleChoice.getScore() + "");

        // Call setQuestion() after a short delay to allow the user to see the "Correct!" message
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3)); // 1-second delay
        pause.setOnFinished(e -> {
            try {
                setQuestion(null);  // Calling setQuestion() to load the next question
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        pause.play();
    }


    @FXML
    public void choseA() {
        if (AButton.getText().equals(correctAnswer)) {
            correct();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void choseB() {
        if (BButton.getText().equals(correctAnswer)) {
            correct();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void choseC() {
        if (CButton.getText().equals(correctAnswer)) {
            correct();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void choseD() {
        if (DButton.getText().equals(correctAnswer)) {
            correct();
        } else {
            resultBox.setText("Wrong!");
        }
    }

    @FXML
    public void switchBackToGameScene(ActionEvent event) throws IOException {
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
}
