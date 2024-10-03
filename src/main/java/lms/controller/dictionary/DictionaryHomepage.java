package lms.controller.dictionary;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lms.controller.api.TranslateAPI;
import lms.util.TextToSpeech;
import lms.util.dictionary.InternetConnection;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.model.StyleSpan;
import org.fxmisc.richtext.model.StyleSpans;

import java.sql.*;

public class DictionaryHomepage {

    private static final String databaseLocation = "src/main/resources/database/dictionary.db";
    public static final String URL = "jdbc:sqlite:" + databaseLocation;
    private static final String TABLE_WORDS = "words";
    private static final String TABLE_USER_DEFINED_WORDS = "userDefinedWords";
    private static final String COLUMN_WORD = "word";
    private static final String COLUMN_MEANING = "meaning";
    private static final String COLUMN_ISBOOKMARKED = "isBookmarked";


    // Translation Part
    @FXML
    private TextArea inputArea;
    @FXML
    private TextArea outputArea;
    @FXML
    private Label sourceLabel;
    @FXML
    private Label targetLabel;
    @FXML
    private Label numOfCharLabel;

    private TranslateAPI translateAPI;
    @FXML
    private TableView<Record> records;
    @FXML
    private TableColumn<Record, String> sourceLanguage;
    @FXML
    private TableColumn<Record, String> targetLanguage;
    @FXML
    private TableColumn<Record, String> sourceText;
    @FXML
    private TableColumn<Record, String> targetText;

    private ObservableList<Record> history = FXCollections.observableArrayList();

    public Label homeLabel;
    public Label addNewWordLabel;
    public Label onlineModeLabel;
    public Label gameLabel;
    public Label settingsLabel;
    public Label wordLabel;
    public Label meaningLabel;
    public Label translatorLabel;
    public MFXTextField wordSearchField;
    public MFXTextField searchField;
    public MFXTextField meaningField;
    public ListView<Word> wordListView;
    public ListView<UserDefinedWord> userWordsListView;
    public ListView<Word> bookmarkedList;
    public AnchorPane homepagePane;
    public AnchorPane addNewWordPane;
    public Label wordLabel2;
    public Label meaningLabel2;
    public AnchorPane onlineModePane;
    public AnchorPane translatorPane;

    private Label selectedLabel;

    private Word selectedWord;
    private UserDefinedWord selectedUserWord;
    private Word selectedBookmark;
    private Connection connection;

    @FXML
    private MFXTextField searchBox;
    @FXML
    private InlineCssTextArea result;

    public void pressed() {
        String wordToSearch = searchBox.getText();

        searchDictionary(wordToSearch);
    }

    private void searchDictionary(String wordToSearch){
        // Get formatted text area content from InternetConnection
        InlineCssTextArea formattedContent = InternetConnection.getOnlineData(wordToSearch);

        // Update the result TextArea in the JavaFX Application Thread
        Platform.runLater(() -> {
            // Clear existing content and append new formatted content
            result.clear();
            result.appendText(formattedContent.getText());

            // Apply styles to the result TextArea
            StyleSpans<String> styles = formattedContent.getStyleSpans(0, formattedContent.getLength());
            int start = 0;
            for (StyleSpan<String> span : styles) {
                int end = start + span.getLength();
                result.setStyle(start, end, span.getStyle());
                start = end;
            }

            // Scroll to the top of the text area
            result.scrollYToPixel(0.0);
        });
    }

    public void initialize() {
        setSelectedLabel(homeLabel);

        sourceLabel.setText("English");
        targetLabel.setText("Bangla");
        numOfCharLabel.setText("0/5000");

        loadWordList();
        loadBookmarkedList();

        sourceLanguage.setCellValueFactory(new PropertyValueFactory<Record, String>("sourceLanguage"));
        targetLanguage.setCellValueFactory(new PropertyValueFactory<Record, String>("targetLanguage"));
        sourceText.setCellValueFactory(new PropertyValueFactory<Record, String>("sourceText"));
        targetText.setCellValueFactory(new PropertyValueFactory<Record, String>("targetText"));

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from translationHistory");

            while (resultSet.next()) {
                String sourceLanguage = resultSet.getString("sourceLanguage");
                String targetLanguage = resultSet.getString("targetLanguage");
                String sourceText = resultSet.getString("sourceText");
                String targetText = resultSet.getString("targetText");
                history.add(new Record(sourceLanguage, targetLanguage, sourceText, targetText));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        records.setItems(history);
    }

    // Translation part

    @FXML
    public void translate() throws InterruptedException {
        if (inputArea.getText().isEmpty()) {
            outputArea.setText("Please enter text to translate");
            return;
        }

        outputArea.setText("Translating...");
        translateAPI = new TranslateAPI(inputArea.getText(), sourceLabel.getText(), targetLabel.getText());
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                outputArea.setText(translateAPI.getData());
                Model.addTranslationHistory(sourceLabel.getText(), targetLabel.getText(), inputArea.getText(), outputArea.getText());
                history.add(new Record(sourceLabel.getText(), targetLabel.getText(), inputArea.getText(), outputArea.getText()));
                records.setItems(history);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    @FXML
    public void swapLanguage() {
        String temp = sourceLabel.getText();
        sourceLabel.setText(targetLabel.getText());
        targetLabel.setText(temp);
    }

    @FXML
    public void countChar() {
        numOfCharLabel.setText(inputArea.getText().length() + "/5000");
    }

    @FXML
    public void deleteHistory() {
        Statement statement = null;
        try {
            Connection connection = DriverManager.getConnection(URL);
            statement = connection.createStatement();
            statement.executeUpdate("delete from translationHistory");
            history.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadWordList(){
        ObservableList<Word> words = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_WORDS;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String word = resultSet.getString(COLUMN_WORD);
                String meaning = resultSet.getString(COLUMN_MEANING);
                int isBookmarked = resultSet.getInt(COLUMN_ISBOOKMARKED);
                Word newWord = new Word(word, meaning, isBookmarked);
                words.add(newWord);
            }
            wordListView.setItems(words);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadBookmarkedList() {
        ObservableList<Word> bookmarks = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_WORDS + " WHERE " + COLUMN_ISBOOKMARKED + " = 1";
            ResultSet resultSet;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String word = resultSet.getString(COLUMN_WORD);
                String meaning = resultSet.getString(COLUMN_MEANING);
                int isBookmarked = resultSet.getInt(COLUMN_ISBOOKMARKED);

                Word newWord = new Word(word, meaning, isBookmarked);

                bookmarks.add(newWord);
            }
            bookmarkedList.setItems(bookmarks);

            resultSet.close();
            statement.close();
            connection.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadUserDefinedWords(){
        ObservableList<UserDefinedWord> words = FXCollections.observableArrayList();

        try {
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM " + TABLE_USER_DEFINED_WORDS;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String word = resultSet.getString(COLUMN_WORD);
                String meaning = resultSet.getString(COLUMN_MEANING);
                UserDefinedWord newWord = new UserDefinedWord(word, meaning);
                words.add(newWord);
            }
            userWordsListView.setItems(words);

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void homeClicked() {
        setSelectedLabel(homeLabel);
        addNewWordPane.setVisible(false);
        onlineModePane.setVisible(false);
        translatorPane.setVisible(false);
        homepagePane.setVisible(true);
    }

    public void addNewWordClicked() {
        setSelectedLabel(addNewWordLabel);
        homepagePane.setVisible(false);
        onlineModePane.setVisible(false);
        translatorPane.setVisible(false);
        addNewWordPane.setVisible(true);
        loadUserDefinedWords();
    }

    public void onlineModeClicked() {
        setSelectedLabel(onlineModeLabel);
        homepagePane.setVisible(false);
        translatorPane.setVisible(false);
        addNewWordPane.setVisible(false);
        onlineModePane.setVisible(true);
    }

    public void gameClicked() {
        setSelectedLabel(gameLabel);
    }

    public void settingsClicked() {
        setSelectedLabel(settingsLabel);
    }

    public void translatorClicked(MouseEvent mouseEvent) {
        setSelectedLabel(translatorLabel);
        homepagePane.setVisible(false);
        addNewWordPane.setVisible(false);
        onlineModePane.setVisible(false);
        translatorPane.setVisible(true);

    }

    private void setSelectedLabel(Label label) {
        // Remove the 'selected-label' class from all labels
        homeLabel.getStyleClass().remove("selected-label");
        addNewWordLabel.getStyleClass().remove("selected-label");
        onlineModeLabel.getStyleClass().remove("selected-label");
        gameLabel.getStyleClass().remove("selected-label");
        settingsLabel.getStyleClass().remove("selected-label");
        translatorLabel.getStyleClass().remove("selected-label");

        // Add the 'selected-label' class to the clicked label
        label.getStyleClass().add("selected-label");
        // Store this label inside a variable so that we can access it later using this
        selectedLabel = label;
    }

    public void searchWord() {

        ObservableList<Word> words = FXCollections.observableArrayList();
        String pattern = '*' + wordSearchField.getText() + '*';

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            connection = DriverManager.getConnection(URL);

            // SELECT * FROM words WHERE (word glob ?);
            String query = "SELECT * FROM " + TABLE_WORDS + " WHERE (" +
                    COLUMN_WORD + " glob ?)";
            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, pattern);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String word = rs.getString(COLUMN_WORD);
                String meaning = rs.getString(COLUMN_MEANING);
                int isBookmarked = rs.getInt(COLUMN_ISBOOKMARKED);
                Word newWord = new Word(word, meaning, isBookmarked);
                words.add(newWord);
            }
            wordListView.setItems(words);

            rs.close();
            pstmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayMeaningOfSelectedWord() {
        selectedWord = wordListView.getFocusModel().getFocusedItem();
        wordLabel.setText(selectedWord.getWord());
        meaningLabel.setText(selectedWord.getMeaning());
    }

    public void displayMeaningOfSelectedBookmark() {
        selectedWord = bookmarkedList.getFocusModel().getFocusedItem();
        wordLabel.setText(selectedWord.getWord());
        meaningLabel.setText(selectedWord.getMeaning());
    }

    public void bookmarkWord() {
        if (selectedWord == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a word to bookmark it.");
            alert.showAndWait();
            return;
        }

        if(DictionaryModel.isBookmarked(selectedWord)){
            selectedWord.setBookmarked(false);
            if(DictionaryModel.unBookmark(selectedWord)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Word removed from bookmark list.");
                alert.showAndWait();
            }
        } else {
            selectedWord.setBookmarked(true);
            if(DictionaryModel.Bookmark(selectedWord)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("Word added to bookmark list.");
                alert.showAndWait();
            }
        }

        loadBookmarkedList();
    }

    public void speak() {
        if (selectedWord == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a word.");
            alert.showAndWait();
            return;
        }

        TextToSpeech.read(selectedWord.getWord());
    }

    public void addClicked() {
        String word = searchField.getText();
        String meaning = meaningField.getText();

        if(word.isBlank() || meaning.isBlank()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a word.");
            alert.showAndWait();
            return;
        }

        UserDefinedWord newWord = new UserDefinedWord(word, meaning);

        if(DictionaryModel.addUserDefinedWord(newWord)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Word added.");
            alert.showAndWait();
        }

        loadUserDefinedWords();
    }

    public void deleteClicked() {
        if(selectedUserWord == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Select a word first.");
            alert.showAndWait();
            return;
        }

        if(DictionaryModel.deleteUserDefinedWord(selectedUserWord)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("User defined word removed.");
            alert.showAndWait();
        }

        loadUserDefinedWords();
    }

    public void displayUserDefinedMeaning() {
        selectedUserWord = userWordsListView.getFocusModel().getFocusedItem();
        wordLabel2.setText(selectedUserWord.getWord());
        meaningLabel2.setText(selectedUserWord.getMeaning());
    }


    public void speakUserWord() {
        if (selectedUserWord == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a word.");
            alert.showAndWait();
            return;
        }

        TextToSpeech.read(selectedUserWord.getWord());
    }

    public void hahaClicked() {
    }

}
