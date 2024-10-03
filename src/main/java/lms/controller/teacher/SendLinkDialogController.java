package lms.controller.teacher;

import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lms.util.UserService;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.ChatInviteLink;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.*;

import static lms.util.EmailService.sendEmail;

public class SendLinkDialogController {

    @FXML
    private MFXToggleButton shareWithEveryoneTBtn;
    @FXML
    private MFXTextField searchField;
    @FXML
    private MFXTextField chatIDField;
    @FXML
    private TableView<StudentData> tableView;
    @FXML
    private TableColumn<StudentData, String> nameCol;
    @FXML
    private TableColumn<StudentData, String> idCol;
    @FXML
    private TableColumn<StudentData, String> emailCol;
    @FXML
    private TableColumn<StudentData, String> selectCol;

    private ObservableList<StudentData> students = FXCollections.observableArrayList();

    private TelegramBot telegramBot;

    private void loadTableView() {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.URL);
            stmt = conn.createStatement();

            String query = "select * from students";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                String email = rs.getString("email");

                System.out.println(id + " " + name + " " + email);

                students.add(new StudentData(name, id, email, new CheckBox()));
            }

            tableView.setItems(students);

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

        // Initialize the Telegram Bot with your bot token
        String botToken = "8180409640:AAEqEWC6veOpiyMmOR7hVQx-xWxgnbpt8AY";  // Replace with your actual API token
        telegramBot = new TelegramBot(botToken);

        try {
            // Register the bot with Telegram
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        shareWithEveryoneTBtn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue)//is selected state
            {
                searchField.setDisable(true);
                tableView.setDisable(true);
            } else {
                searchField.setDisable(false);
                tableView.setDisable(false);
            }
        });

        nameCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("id"));
        emailCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        selectCol.setCellValueFactory(new PropertyValueFactory<StudentData, String>("checkBox"));

        tableView.getItems().clear();
        loadTableView();

        // Wrap the ObservableList in a FilteredList (initially display all data)
        FilteredList<StudentData> filteredData = new FilteredList<>(students, p -> true);

        // Add a listener to the text field to update the filter whenever the text changes
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If filter text is empty, display all students
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare name, id, and email of every student with the filter text
                String lowerCaseFilter = newValue.toLowerCase();

                if (student.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name
                } else if (student.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches id
                } else if (student.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches email
                }

                return false; // Does not match
            });
        });

        // Bind the filtered list to the TableView
        tableView.setItems(filteredData);
    }

    private ObservableList<StudentData> emailList = FXCollections.observableArrayList();

    public void shareLinkClicked(ActionEvent actionEvent) {

        emailList.clear();

        // -4569344123
        String groupChatId = chatIDField.getText(); // Get this ID from the group where bot is added as admin

        try {
            ChatInviteLink inviteLink = telegramBot.createInviteLink(groupChatId);
            String invitation_URL = inviteLink.getInviteLink();

            if (!shareWithEveryoneTBtn.isSelected()) {
                for (StudentData s : students) {
                    if (s.getCheckBox().isSelected()) {

                        String amarID = s.getId();

                        // database
                        Connection conn = null;
                        Statement stmt = null;
                        ResultSet rs = null;

                        try {

                            conn = DriverManager.getConnection(UserService.URL);
                            stmt = conn.createStatement();

                            String query = "select wantEmail from students WHERE id='" + amarID + "'";

                            rs = stmt.executeQuery(query);

                            if(rs.next()) {
                                String wantEmail = rs.getString("wantEmail");
                                if(wantEmail.equalsIgnoreCase("yes")) {
                                    emailList.add(s);
                                }
                            }

                            conn.close();
                            stmt.close();
                            rs.close();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                for (StudentData s : emailList) {
                    System.out.println(s.getEmail());
                    // Send the email
                    new Thread(() -> {
                        if (sendEmail(s.getEmail(), invitation_URL)) {
                            System.out.println("Email send successful");

                        } else {
                            System.out.println("Error in Thread sending email");
                        }
                    }).start();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Share Link");
                alert.setHeaderText(null);
                alert.setContentText("Link has been shared successfully.");
                alert.showAndWait();
            } else {
                for (StudentData s : students) {
                        String amarID = s.getId();
                        // database
                        Connection conn = null;
                        Statement stmt = null;
                        ResultSet rs = null;

                        try {

                            conn = DriverManager.getConnection(UserService.URL);
                            stmt = conn.createStatement();

                            String query = "select wantEmail from students WHERE id='" + amarID + "'";

                            rs = stmt.executeQuery(query);

                            if(rs.next()) {
                                String wantEmail = rs.getString("wantEmail");
                                if(wantEmail.equalsIgnoreCase("yes")) {
                                    emailList.add(s);
                                }
                            }

                            conn.close();
                            stmt.close();
                            rs.close();

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                }

                for (StudentData s : emailList) {

                    System.out.println(s.getEmail());

                    // Send the email
                    new Thread(() -> {
                        if (sendEmail(s.getEmail(), invitation_URL)) {
                            System.out.println("Email send successful");

                        } else {
                            System.out.println("Error in Thread sending email");
                        }
                    }).start();
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Share Link");
                alert.setHeaderText(null);
                alert.setContentText("Link has been shared successfully.");
                alert.showAndWait();
            }

        } catch (TelegramApiException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Generating Link Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to generate link");
            alert.showAndWait();
        }
    }
}

