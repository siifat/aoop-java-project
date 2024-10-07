package game;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import lms.controller.dictionary.Controller;
import lms.controller.dictionary.DictionaryHomepage;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ChooseItemController extends Controller {
    private ChooseItem chooseItem;
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Label questionBox;
    private Item correctItem;
    private Item[] items;
    @FXML
    private Label correctLabel = new Label();
    private int numberOfQuestions = 0;
    @FXML
    private Label scoreBox;

    public ChooseItemController() throws FileNotFoundException {
        items = new Item[4];
        chooseItem = ChooseItem.getChooseItem();
    }

    public void correct() {
        correctLabel.setText("Correct!");
        chooseItem.increaseHighscore();
        scoreBox.setText("Score: " + chooseItem.getScore());

        // Call setQuestion() after a short delay to allow the user to see the "Correct!" message
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3)); // 1-second delay
        pause.setOnFinished(e -> {
            setQuestionByAction();  // Calling setQuestion() to load the next question
        });
        pause.play();
    }

    private boolean checkIfArrayIsNotFull(Item[] items) {
        for (int i = 0; i < 4; i++) {
            if (items[i] == null) {
                return true;
            }
        }
        return false;
    }

    @FXML
    public void initializeQuestion(ActionEvent event) {
        correctLabel.setText("Choose the correct item!");
        correctItem = chooseItem.returnRandomItem();
        correctItem.setChosen(true);

        int randomIndex = (int) (Math.random() * 4);
        items[randomIndex] = correctItem;

        while (checkIfArrayIsNotFull(items)) {
            randomIndex = (int) (Math.random() * 4);
            Item temp = chooseItem.returnRandomItem();
            if (items[randomIndex] == null && (!temp.isChosen())) {
                items[randomIndex] = temp;
                temp.setChosen(true);
            }
        }

        imageView1.setImage(items[0].getImage());
        imageView2.setImage(items[1].getImage());
        imageView3.setImage(items[2].getImage());
        imageView4.setImage(items[3].getImage());

        questionBox.setText(correctItem.getQuestion());
    }

    @FXML
    public void setQuestionByAction() {
        numberOfQuestions++;
        for (int i = 0; i < 4; ++i) {
            items[i].setChosen(false);
            items[i] = null;
        }

        correctItem = chooseItem.returnRandomItem();
        correctItem.setChosen(true);

        int randomIndex = (int) (Math.random() * 4);
        items[randomIndex] = correctItem;

        while (checkIfArrayIsNotFull(items)) {
            randomIndex = (int) (Math.random() * 4);
            Item temp = chooseItem.returnRandomItem();

            if (items[randomIndex] == null && (!temp.isChosen())) {
                items[randomIndex] = temp;
                temp.setChosen(true);
            }
        }

        imageView1.setImage(items[0].getImage());
        imageView2.setImage(items[1].getImage());
        imageView3.setImage(items[2].getImage());
        imageView4.setImage(items[3].getImage());

        questionBox.setText(correctItem.getQuestion());
    }


    public void clickImageView1(MouseEvent event) {
        if (items[0] == correctItem) {
            correct();
        } else {
            correctLabel.setText("Incorrect");
        }
    }

    public void clickImageView2(MouseEvent event) {
        if (items[1] == correctItem) {
            correct();
        } else {
            correctLabel.setText("Incorrect");
        }
    }

    public void clickImageView3(MouseEvent event) {
        if (items[2] == correctItem) {
            correct();
        } else {
            correctLabel.setText("Incorrect");
        }
    }

    public void clickImageView4(MouseEvent event) {
        if (items[3] == correctItem) {
            correct();
        } else {
            correctLabel.setText("Incorrect");
        }
    }

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

    @FXML
    public void end(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game/ChooseItemEnd.fxml"));
        root = loader.load();

        ChooseItemEndController chooseItemEndController = loader.getController();
        chooseItemEndController.displayScore(chooseItem.getScore(), numberOfQuestions);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}