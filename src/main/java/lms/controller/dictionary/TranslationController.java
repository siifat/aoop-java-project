package lms.controller.dictionary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lms.controller.api.TranslateAPI;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class TranslationController extends Controller implements Initializable {
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

    public void init() {
        sourceLabel.setText("English");
        targetLabel.setText("Bangla");
        numOfCharLabel.setText("0/5000");
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader sidePaneLoader = new FXMLLoader(getClass().getResource("SidePane.fxml"));
        try {
            Parent sidePaneLoaded = sidePaneLoader.load();
            rootAnchor.getChildren().addAll(sidePaneLoaded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sourceLanguage.setCellValueFactory(new PropertyValueFactory<Record, String>("sourceLanguage"));
        targetLanguage.setCellValueFactory(new PropertyValueFactory<Record, String>("targetLanguage"));
        sourceText.setCellValueFactory(new PropertyValueFactory<Record, String>("sourceText"));
        targetText.setCellValueFactory(new PropertyValueFactory<Record, String>("targetText"));

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
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

    @FXML
    public void countChar() {
        numOfCharLabel.setText(inputArea.getText().length() + "/5000");
    }

    @FXML
    public void deleteHistory() {
        Statement statement = null;
        try {
            Connection connection = DriverManager.getConnection(Utilities.PATH_TO_DATABASE);
            statement = connection.createStatement();
            statement.executeUpdate("delete from translationHistory");
            history.clear();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}