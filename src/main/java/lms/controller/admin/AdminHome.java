package lms.controller.admin;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lms.util.ChangeScene;
import lms.util.ShowAlert;
import lms.util.UserService;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static lms.util.EmailService.sendEmail;

public class AdminHome extends Application {

    @FXML private Pane adminLoginPane;
    @FXML private Pane stackPane;
    @FXML private Pane upperPane;
    @FXML private Pane sidePane;

    @FXML private MFXPasswordField privateFilePassField;

    @FXML private Pane homep1Pane;
    @FXML private Pane graphPane;
    @FXML private Pane ump1Pane;
    @FXML private Pane ump2Pane;
    @FXML private Pane ump3Pane;
    @FXML private Pane rlp1Pane;
    @FXML private Label selectedLabel;

    @FXML private Pane sconfigp1Pane;
    public Circle proPicCircle;

    @FXML private MFXComboBox<String> backgroundSelector;

    @FXML private Label homeLabel;
    @FXML private Label manageLabel;
    @FXML private Label logLabel;
    @FXML private Label configLabel;

    public TextField stf1;
    public TextField stf2;
    public TextField stf3;
    public TextField stf4;
    public ComboBox scb1;
    public ComboBox scb2;

    public TextField ttf1;
    public TextField ttf2;
    public TextField ttf3;
    public TextField ttf4;
    public ComboBox tcb1;

    @FXML private MFXRadioButton tAnnounceRB;
    @FXML private MFXRadioButton sAnnounceRB;

    private VBox calendarPane;
    private LocalDate currentDate;
    private Label monthYearLabel;

    @FXML private MFXComboBox<String> programComboBox;
    @FXML private TableView<TeachApproveData> tapprovalTable;
    @FXML private TableColumn<TeachApproveData, String> tinitialCol;
    @FXML private TableColumn<TeachApproveData, String> tnameCol;
    @FXML private TableColumn<TeachApproveData, String> temailCol;
    @FXML private TableColumn<TeachApproveData, String> tmobileCol;
    @FXML private TableColumn<TeachApproveData, String> troleCol;
    @FXML private TableColumn<TeachApproveData, String> tappCol;

    @FXML private TableView<StApproveData> sapprovalTable;
    @FXML private TableColumn<StApproveData, String> sidCol;
    @FXML private TableColumn<StApproveData, String> snameCol;
    @FXML private TableColumn<StApproveData, String> semailCol;
    @FXML private TableColumn<StApproveData, String> smobileCol;
    @FXML private TableColumn<StApproveData, String> sroleCol;
    @FXML private TableColumn<StApproveData, String> sregCol;
    @FXML private TableColumn<StApproveData, String> sappCol;

    @FXML private Pane chartContainer;

//    @FXML private ListView<String> updateLogLV;
//    private ObservableList<String> updateLogList;

    @FXML
    private ListView<TextFlow> updateLogLV; //-----------------------------------------------
    private ObservableList<TextFlow> updateLogList;


    private ObservableList<TeachApproveData> adt1 = FXCollections.observableArrayList();
    private ObservableList<StApproveData> adt = FXCollections.observableArrayList();

    @FXML private TableView<ComplainData> complainTV;

    @FXML private TableColumn<ComplainData, String> cNameCol;

    @FXML private TableColumn<ComplainData, String> cIDCol;

    @FXML private TableColumn<ComplainData, String> cEmailCol;

    @FXML private TableColumn<ComplainData, String> cRoleCol;

    @FXML private TableColumn<ComplainData, String> cComTitleCol;

    @FXML private TableColumn<ComplainData, String> cDescripCol;

    @FXML private MFXTextField searchField2;

    private ObservableList<ComplainData> complains = FXCollections.observableArrayList();

    //announcement
    @FXML private MFXTextField aTitleField;
    @FXML private TextArea aField;

    @FXML private TableView<AnnouncementData> announcementTV;

    private ObservableList<AnnouncementData> notice = FXCollections.observableArrayList();

    @FXML private TableColumn<AnnouncementData, String> aNoCol;

    @FXML private TableColumn<AnnouncementData, String> aDateCol;

    @FXML private TableColumn<AnnouncementData, String> aTitleCol;

    @FXML private TableColumn<AnnouncementData, String> aAnnouncementCol;

    @FXML private Circle cameraCircle;
    @FXML private Circle myProfileCircle;


    public void initialize() {
        // Load the image from the resources folder
//        Image proPic = new Image(getClass().getResource("/images/admin.jpg").toExternalForm());
//        proPicCircle.setFill(new ImagePattern(proPic));

//        String imagePath = "file:/C:/Users/sifat/Documents/aoop-java-project/src/main/resources/images/admin/Cover/Cover3.jpg";
//        Image backgroundImage = new Image(imagePath);
//
//        // Create a BackgroundImage with the required settings
//        BackgroundImage background = new BackgroundImage(
//                backgroundImage,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                BackgroundSize.DEFAULT
//        );
//
//        // Set the background for upperPane
//        upperPane.setBackground(new Background(background));

        setSelectedLabel(homeLabel);

        //HOMEPAGE

        programComboBox.getItems().addAll("Science and Engineering", "Business and Economics", "Humanities and Social Sciences");
        programComboBox.setOnAction(event -> programComboBoxClicked());
        programComboBox.selectFirst();

        loadAnnouncementTableView();


        //USER_MANAGEMENT

        sidCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("id"));
        snameCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("name"));
        semailCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("email"));
        smobileCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("mobile"));
        sroleCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("role"));
        sregCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("registration"));
        sappCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("approved"));

        scb1.getItems().addAll("Student", "Undergraduate Assistant", "Student & Undergraduate Assistant");
        scb2.getItems().addAll("Enrolled", "Not Enrolled");

        scb1.valueProperty().addListener((obs, oldValue, newValue) -> {
            StApproveData approveData = sapprovalTable.getSelectionModel().getSelectedItem();

            if (approveData != null && newValue != null) {
                // Update the role in the database when a new role is selected
                Connection conn = null;
                Statement stmt = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE students SET role='" + newValue + "' WHERE id = '" + approveData.getId() + "'";
                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    // Optionally reload the table data
                    sapprovalTable.getItems().clear();
                    loadItemsIntoTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        scb2.valueProperty().addListener((obs, oldValue, newValue) -> {
            StApproveData approveData = sapprovalTable.getSelectionModel().getSelectedItem();

            if (approveData != null && newValue != null) {
                // Update the registration status in the database when a new option is selected
                Connection conn = null;
                Statement stmt = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE students SET registration='" + newValue + "' WHERE id = '" + approveData.getId() + "'";
                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    // Optionally reload the table data
                    sapprovalTable.getItems().clear();
                    loadItemsIntoTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        sapprovalTable.getItems().clear();
        loadItemsIntoTable();

        tinitialCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("initial"));
        tnameCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("name"));
        temailCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("email"));
        tmobileCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("mobile"));
        troleCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("role"));
        tappCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("approved"));

        tcb1.getItems().addAll(" ", "Teacher");

        tcb1.valueProperty().addListener((obs, oldValue, newValue) -> {
            TeachApproveData approveData = tapprovalTable.getSelectionModel().getSelectedItem();

            if (approveData != null && newValue != null) {
                // Update the role in the database when a new role is selected
                Connection conn = null;
                Statement stmt = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE teachers SET role='" + newValue + "' WHERE initial = '" + approveData.getInitial() + "'";
                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    // Optionally reload the table data
                    tapprovalTable.getItems().clear();
                    loadItemsIntoTeacherTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        tapprovalTable.getItems().clear();
        loadItemsIntoTeacherTable();

        updateLogList = FXCollections.observableArrayList();
        updateLogLV.setItems(updateLogList);

        //Sorry

        Text infoUpdateTitle = new Text("Admission\n");
        infoUpdateTitle.setStyle("-fx-font-weight: bold;");

        Text idAndName2 = new Text("0112320247- Dawoodur Rahman Hassan");
        idAndName2.setStyle("-fx-font-style: italic;");

        Text infoUpdateMessage = new Text(" got approval for student account on 7 October, 2024");
        TextFlow infoUpdateLog = new TextFlow(infoUpdateTitle, idAndName2, infoUpdateMessage);

        Text admissionTitle = new Text("Admission\n");
        admissionTitle.setStyle("-fx-font-weight: bold;");

        Text idAndName3 = new Text("0112320240- Sifatullah");
        idAndName3.setStyle("-fx-font-style: italic;");

        Text infoUpdateMessage1 = new Text(" got approval for student account on 7 October, 2024");

        TextFlow infoUpdateLog1 = new TextFlow(admissionTitle, idAndName3, infoUpdateMessage1);

        updateLogList.add(infoUpdateLog); // Add the formatted log message to the list
        updateLogList.add(infoUpdateLog1); // Add the formatted log message to the list

        //End of Sorry


        //REPORT_&_LOG

        cNameCol.setCellValueFactory(new PropertyValueFactory<>("CName"));
        cIDCol.setCellValueFactory(new PropertyValueFactory<>("CId"));
        cEmailCol.setCellValueFactory(new PropertyValueFactory<>("CEmail"));
        cRoleCol.setCellValueFactory(new PropertyValueFactory<>("CRole"));
        cComTitleCol.setCellValueFactory(new PropertyValueFactory<>("CComplainTitle"));
        cDescripCol.setCellValueFactory(new PropertyValueFactory<>("CDescription"));

        complainTV.getItems().clear();
        loadComplainTableView();

        // Wrap the ObservableList in a FilteredList (initially display all data)
        FilteredList<ComplainData> filteredData = new FilteredList<>(complains, p -> true);

        // Add a listener to the text field to update the filter whenever the text changes
        searchField2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If filter text is empty, display all students
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare name, id, and email of every student with the filter text
                String lowerCaseFilter = newValue.toLowerCase();

                if (student.getCName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name
                } else if (student.getCId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches id
                } else if (student.getCEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches email
                }

                return false; // Does not match
            });
        });

        // Bind the filtered list to the complainTV
        complainTV.setItems(filteredData);


        //SYSTEM_CONFIGURATION

        String camLoc = "/images/teacher/photo.png";
        Image cam = new Image(getClass().getResource(camLoc).toExternalForm());
        cameraCircle.setFill(new ImagePattern(cam));

        backgroundSelector.getItems().addAll("Winter Morning", "Sunny Sky", "Mesh Triangle Vector", "Eid Night", "Floral Vector");
    }

    private void setSelectedLabel(Label label) {
        // Remove the 'selected-label' class from all labels
        homeLabel.getStyleClass().remove("selected-label");
        manageLabel.getStyleClass().remove("selected-label");
        logLabel.getStyleClass().remove("selected-label");
        configLabel.getStyleClass().remove("selected-label");

        // Add the 'selected-label' class to the clicked label
        label.getStyleClass().add("selected-label");
        // Store this label inside a variable so that we can access it later using this
        selectedLabel = label;
    }

    //ADMIN_LOGIN
    public void adminLoginClicked(ActionEvent actionEvent) {
        String a = privateFilePassField.getText();

        if(a.isBlank()){
            ShowAlert.show("Login","Password field cannot be empty.", Alert.AlertType.WARNING);
            return;
        }
        else if(!a.equals("admin123")){
            ShowAlert.show("Login","Password did not match!\nPlease try again.", Alert.AlertType.WARNING);
            privateFilePassField.clear();
            return;
        }

        adminLoginPane.setVisible(false);
        upperPane.setVisible(true);
        sidePane.setVisible(true);
        stackPane.setVisible(true);
    }

    //PANE_SHIFT
    public void homeLabelClicked(MouseEvent mouseEvent) {
        setSelectedLabel(homeLabel);

        homep1Pane.setVisible(true);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(false);
    }

    public void manageLabelClicked(MouseEvent mouseEvent) {
        setSelectedLabel(manageLabel);

        homep1Pane.setVisible(false);
        ump1Pane.setVisible(true);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(false);
    }

    public void reportLabelClicked(MouseEvent mouseEvent) {
        setSelectedLabel(logLabel);

        homep1Pane.setVisible(false);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(true);
        sconfigp1Pane.setVisible(false);
    }

    public void configLabelClicked(MouseEvent mouseEvent) {
        setSelectedLabel(configLabel);

        homep1Pane.setVisible(false);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(true);
    }

    //UPPER_PANE
    public void chatIconClicked(MouseEvent mouseEvent) {

    }


    //HOME
    public void programComboBoxClicked() {
        // Get the selected item
        String selectedProgram = programComboBox.getValue();

        // Print the selected program
        if ("Science and Engineering".equals(selectedProgram)) {
            displayBarGraph();
        } else if ("Business and Economics".equals(selectedProgram)) {
            displayBarGraph1();
        }else if ("Humanities and Social Sciences".equals(selectedProgram)) {
            displayBarGraph2();
        }
    }

    public void displayBarGraph() {
        BarGraph barGraph = new BarGraph();
        BarChart<String, Number> chart = barGraph.createBarChart();

        // Clear previous content if necessary
        graphPane.getChildren().clear();
        graphPane.getChildren().add(chart);
    }

    public void displayBarGraph1() {
        BarGraph1 barGraph = new BarGraph1();
        BarChart<String, Number> chart = barGraph.createBarChart();

        // Clear previous content if necessary
        graphPane.getChildren().clear();
        graphPane.getChildren().add(chart);
    }

    public void displayBarGraph2() {
        BarGraph2 barGraph = new BarGraph2();
        BarChart<String, Number> chart = barGraph.createBarChart();

        // Clear previous content if necessary
        graphPane.getChildren().clear();
        graphPane.getChildren().add(chart);
    }

    public void showChartButtonClicked(ActionEvent event) {
        displayBarGraph();
    }

    private void loadAnnouncements() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<AnnouncementData> announcementData = FXCollections.observableArrayList(); // Create a new list for announcements

        try {
            conn = DriverManager.getConnection(UserService.ANNOUNCEMENT_URL);
            stmt = conn.createStatement();
            String query = "SELECT * FROM notice"; // Adjust the table name as needed
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String aNo = rs.getString("No");
                String aDate = rs.getString("Date");
                String title = rs.getString("Title");
                String description = rs.getString("Announcement");
                announcementData.add(new AnnouncementData(aNo, aDate, title, description));
            }

            // Assuming you have a TableView for announcements
            // Make sure to declare it in your class
            // Example: @FXML private TableView<AnnouncementData> announcementTable;
            // And set it like this:
            // announcementTable.setItems(announcementData);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadAnnouncementTableView() {
        announcementTV.getItems().clear();

        aNoCol.setCellValueFactory(new PropertyValueFactory<AnnouncementData, String>("aNo"));
        aDateCol.setCellValueFactory(new PropertyValueFactory<AnnouncementData, String>("aDate"));
        aTitleCol.setCellValueFactory(new PropertyValueFactory<AnnouncementData, String>("aTitle"));
        aAnnouncementCol.setCellValueFactory(new PropertyValueFactory<AnnouncementData, String>("aDescription"));

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.ANNOUNCEMENT_URL);
            stmt = conn.createStatement();

            String query = "select * from notice";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String no = rs.getString("No");
                String date = rs.getString("Date");
                String title = rs.getString("Title");
                String announcement = rs.getString("Announcement");

//                System.out.println(date + " " + title + " " + announcement);

                notice.add(new AnnouncementData(no, date, title, announcement));
            }

            announcementTV.setItems(notice);

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void calendarButtonClicked(ActionEvent actionEvent) {
//        Calendar c = new Calendar();
//        c.main();
    }

    @Override public void start(Stage primaryStage){
        System.out.println("Yooo");
        currentDate = LocalDate.now();

        calendarPane = new VBox();
        calendarPane.setAlignment(Pos.CENTER);
        calendarPane.setSpacing(30);  // Increased space between header and calendar

        // Create the header with navigation
        HBox header = createHeader();

        // Create the calendar grid
        GridPane calendarGrid = createCalendarGrid();

        calendarPane.getChildren().addAll(header, calendarGrid);

        Scene scene = new Scene(calendarPane, 600, 400);
        primaryStage.setTitle("Custom 5x7 Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setSpacing(60);  // Spacing between buttons and month label

        Button prevButton = new Button("<");
        prevButton.setStyle("-fx-background-color: #AAB7B8; -fx-text-fill: white; -fx-font-size: 14px;");
        prevButton.setOnAction(e -> changeMonth(-1));

        Button nextButton = new Button(">");
        nextButton.setStyle("-fx-background-color: #AAB7B8; -fx-text-fill: white; -fx-font-size: 14px;");
        nextButton.setOnAction(e -> changeMonth(1));

        monthYearLabel = new Label();
        monthYearLabel.setFont(new Font("Arial", 24));
        monthYearLabel.setTextFill(Color.DARKBLUE);
        updateMonthYearLabel();

        // Center the monthYearLabel between the buttons
        monthYearLabel.setMinWidth(200);
        prevButton.setMinWidth(50);
        nextButton.setMinWidth(50);

        header.getChildren().addAll(prevButton, monthYearLabel, nextButton);

        return header;
    }

    private GridPane createCalendarGrid() {
        GridPane calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.setVgap(10);
        calendarGrid.setHgap(10);

        // Add day labels (Sun to Sat)
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setFont(new Font("Arial", 16));
            dayLabel.setTextFill(Color.DARKSLATEGRAY);
            calendarGrid.add(dayLabel, i, 0);
        }

        // Generate the days for the current month in a 5x7 format
        populateCalendarGrid(calendarGrid);

        return calendarGrid;
    }

    private void populateCalendarGrid(GridPane calendarGrid) {
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentDate.lengthOfMonth();

        int row = 1;  // Start from the second row to allow for day labels
        int column = dayOfWeek;  // Start from the correct day of the week

        // Fill dates for the current month
        int filledDays = 0; // To track how many dates we have filled
        for (int day = 1; day <= daysInMonth; day++) {
            if (row > 5) break;  // Stop if we exceed the 5th row

            Button dayButton = createDateButton(day);
            calendarGrid.add(dayButton, column, row);
            filledDays++;
            column++;

            // Move to the next row if we've filled 5 columns
            if (column == 7) {
                column = 0;
                row++;
            }
        }

        // Fill any remaining dates in the first row
        int firstRowColumn = 0;
        for (int day = 1; filledDays < daysInMonth && firstRowColumn < 7; firstRowColumn++) {
            // Check if the position is empty in the first row
            if (calendarGrid.getChildren().get(firstRowColumn + 1) == null) {
                // Place the next available day button in the first row
                Button dayButton = createDateButton(day);
                calendarGrid.add(dayButton, firstRowColumn, 1);
                filledDays++;
            }
            day++; // Increment the day for the next button
        }
    }

    private Button createDateButton(int day) {
        Button dayButton = new Button(String.valueOf(day));
        dayButton.setFont(new Font("Arial", 14));
        dayButton.setMinSize(40, 40);

        // Slightly curvy style for date buttons
        dayButton.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 15px; " +
                "-fx-background-radius: 15px; -fx-border-color: #B0B3B7; -fx-padding: 5px;");

        dayButton.setTooltip(new Tooltip("Date: " + day + " " + currentDate.getMonth() + " " + currentDate.getYear()));

        if (isToday(day)) {
            dayButton.setStyle("-fx-background-color: #FFD700; -fx-border-radius: 15px; " +
                    "-fx-background-radius: 15px; -fx-border-color: #B0B3B7; -fx-text-fill: #000000;");
        }

        // Add hover effect on dates
        dayButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> dayButton.setStyle(
                "-fx-background-color: #D0E0E0; -fx-border-radius: 15px; " +
                        "-fx-background-radius: 15px; -fx-border-color: #B0B3B7; -fx-padding: 5px;"
        ));

        dayButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> dayButton.setStyle(
                isToday(day) ?
                        "-fx-background-color: #FFD700; -fx-border-radius: 15px; " +
                                "-fx-background-radius: 15px; -fx-border-color: #B0B3B7; -fx-text-fill: #000000;" :
                        "-fx-background-color: #FFFFFF; -fx-border-radius: 15px; " +
                                "-fx-background-radius: 15px; -fx-border-color: #B0B3B7; -fx-padding: 5px;"
        ));

        dayButton.setOnAction(e -> handleDayClick(day));
        return dayButton;
    }

    private boolean isToday(int day) {
        return currentDate.getYear() == LocalDate.now().getYear() &&
                currentDate.getMonth() == LocalDate.now().getMonth() &&
                day == LocalDate.now().getDayOfMonth();
    }

    private void updateCalendar() {
        calendarPane.getChildren().remove(1);
        GridPane calendarGrid = new GridPane();
        calendarGrid.setAlignment(Pos.CENTER);
        calendarGrid.setVgap(10);
        calendarGrid.setHgap(10);

        // Add day labels (Sun to Sat)
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < days.length; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setFont(new Font("Arial", 16));
            dayLabel.setTextFill(Color.DARKSLATEGRAY);
            calendarGrid.add(dayLabel, i, 0);
        }

        populateCalendarGrid(calendarGrid);

        calendarPane.getChildren().add(calendarGrid);
    }

    private void changeMonth(int monthsToAdd) {
        currentDate = currentDate.plusMonths(monthsToAdd);
        updateMonthYearLabel();
        updateCalendar();
    }

    private void updateMonthYearLabel() {
        String month = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        int year = currentDate.getYear();
        monthYearLabel.setText(month + " " + year);
    }

    private void handleDayClick(int day) {
        System.out.println("Clicked on day: " + day + " " + currentDate.getMonth() + " " + currentDate.getYear());
    }


    //USER_MANAGEMENT
    public void studButtonClicked() {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(true);
        ump3Pane.setVisible(false);
    }

    public void teachButtonClicked() {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(true);
    }

    public void publishClicked(ActionEvent actionEvent) {
        String title = aTitleField.getText().trim();
        String description = aField.getText().trim();

        if (title.isEmpty() || description.isEmpty()) {
            ShowAlert.show("Announcement","Title and description cannot be empty", Alert.AlertType.WARNING);
            return;
        }

        Connection conn = null;
        Connection conn1 = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;

        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.ANNOUNCEMENT_URL);
            String sql = "INSERT INTO notice (Date, Title, Announcement) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "07/10/2024");
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.executeUpdate();

            if(sAnnounceRB.isSelected()){
                conn1 = DriverManager.getConnection(UserService.URL);
                String emailQuery = "SELECT email FROM students";  // Adjust table and column names as necessary
                stmt = conn1.createStatement();
                rs = stmt.executeQuery(emailQuery);

                // Send the email to all retrieved addresses
                while (rs.next()) {
                    String email = rs.getString("email");

                    new Thread(() -> {
                        // Assuming sendEmail is a static method in EmailService that returns a boolean
                        if (sendEmail(email, title, description)) {
                            System.out.println("Email sent successfully to: " + email);
                        } else {
                            System.out.println("Failed to send email to: " + email);
                        }
                    }).start();
                }
                // Showing a success message
                ShowAlert.show("Announcement","Announcement published successfully!", Alert.AlertType.CONFIRMATION);
            }
            if(tAnnounceRB.isSelected()){
                conn1 = DriverManager.getConnection(UserService.URL);
                String emailQuery = "SELECT email FROM teachers";  // Adjust table and column names as necessary
                stmt = conn1.createStatement();
                rs = stmt.executeQuery(emailQuery);

                // Send the email to all retrieved addresses
                while (rs.next()) {
                    String email = rs.getString("email");

                    new Thread(() -> {
                        // Assuming sendEmail is a static method in EmailService that returns a boolean
                        if (sendEmail(email, title, description)) {
                            System.out.println("Email sent successfully to: " + email);
                        } else {
                            System.out.println("Failed to send email to: " + email);
                        }
                    }).start();
                }
                // Showing a success message
                ShowAlert.show("Announcement","Announcement published successfully!", Alert.AlertType.CONFIRMATION);
            }

            // Clear the input fields
            aTitleField.clear();
            aField.clear();

            // Reload announcements to reflect the new entry
            loadAnnouncements();
            loadAnnouncementTableView();

        } catch (SQLException e) {
            e.printStackTrace();
            ShowAlert.show("Announcement","Error while publishing announcement!", Alert.AlertType.ERROR);
//                new Alert(Alert.AlertType.ERROR, "Error while publishing announcement: " + e.getMessage()).showAndWait();

        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void p2backClicked() {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(true);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(false);
    }

    public void sadmiitRBClicked(MouseEvent mouseEvent) {
    }

    public void sregRBClicked(MouseEvent mouseEvent) {
    }

    public void scourseRBClicked(MouseEvent mouseEvent) {
    }

    public void sinfoRBClicked(MouseEvent mouseEvent) {
    }

    public void sroleRBClicked(MouseEvent mouseEvent) {
    }


    private void loadItemsIntoTable() {
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
                String mobile = rs.getString("mobile");
                String role = rs.getString("role");
                String approved = rs.getString("approved");
                String registration = rs.getString("registration");

                System.out.println(id + " " + name + " " + email + " " + mobile + " " + role + " " + approved + " " + registration);

                adt.add(new StApproveData(name, id, email, mobile, role, registration, approved));
            }

            sapprovalTable.setItems(adt);

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemsIntoTeacherTable() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.URL);
            stmt = conn.createStatement();

            String query = "select * from teachers";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String initial = rs.getString("initial");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String mobile = rs.getString("mobile");
                String role = rs.getString("role");
                String approved = rs.getString("approve");

                System.out.println(initial + " " + name + " " + email + " " + mobile + " " + role + " " + approved);

                adt1.add(new TeachApproveData(initial, name, email, mobile, role, approved));
            }

            tapprovalTable.setItems(adt1);

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void sapprovalTableClicked(MouseEvent mouseEvent) {
        StApproveData approveData = sapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String id = approveData.getId();
            String name = approveData.getName();
            String email = approveData.getEmail();
            String mobile = approveData.getMobile();
            String role = approveData.getRole();
            String registration = approveData.getRegistration();

            stf1.setText(id);
            stf2.setText(name);
            stf3.setText(email);
            stf4.setText(mobile);

            scb1.setValue(role);
            scb2.setValue(registration);
        }
    }

    public void tapprovalTableClicked(MouseEvent mouseEvent) {
        TeachApproveData approveData = tapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String initial = approveData.getInitial();
            String name = approveData.getName();
            String email = approveData.getEmail();
            String mobile = approveData.getMobile();
            String role = approveData.getRole();

            ttf1.setText(initial);
            ttf2.setText(name);
            ttf3.setText(email);
            ttf4.setText(mobile);
            tcb1.setValue(role);
        }
    }

    public void stSaveCngClicked(ActionEvent actionEvent) {
        StApproveData approveData = sapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String isApproved = approveData.getApproved();

            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(UserService.URL);
                stmt = conn.createStatement();

                String query = "UPDATE students SET name='" + stf2.getText() + "' WHERE id = '" + approveData.getId() + "'";
                String query1 = "UPDATE students SET email='" + stf3.getText() + "' WHERE id = '" + approveData.getId() + "'";
                String query2 = "UPDATE students SET mobile='" + stf4.getText() + "' WHERE id = '" + approveData.getId() + "'";
                String query3 = "UPDATE students SET registration='" + scb2.getValue() + "' WHERE id = '" + approveData.getId() + "'";
                String query4 = "UPDATE students SET mobile='" + stf4.getText() + "' WHERE id = '" + approveData.getId() + "'";

                stmt.executeUpdate(query);
                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);
                stmt.executeUpdate(query3);
                stmt.executeUpdate(query4);

                conn.close();
                stmt.close();

                sapprovalTable.getItems().clear();
                loadItemsIntoTable();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No item is selected").showAndWait();
        }

        // Create the log messages with formatting
        Text roleAssignTitle = new Text("Role Assign\n");
        roleAssignTitle.setStyle("-fx-font-weight: bold;");

        Text idAndName1 = new Text("0112320240- SifatUllah");
        idAndName1.setStyle("-fx-font-style: italic;");

        Text roleAssignMessage = new Text(" has been assigned with \"Student & Undergraduate Assistant\" from \"Student\" on 16 October, 2024");

        TextFlow roleAssignLog = new TextFlow(roleAssignTitle, idAndName1, roleAssignMessage);
        updateLogList.add(roleAssignLog); // Add the formatted log message to the list

        // Repeat for the information update log
        Text infoUpdateTitle = new Text("Information Update\n");
        infoUpdateTitle.setStyle("-fx-font-weight: bold;");

        Text idAndName2 = new Text("0112320275- Abul Hasnat Muhammad Ishtiaqullah");
        idAndName2.setStyle("-fx-font-style: italic;");

        Text infoUpdateMessage = new Text(" had changed his mobile number to \"01828023887\" from \"0182\" on 16 October, 2024");

        TextFlow infoUpdateLog = new TextFlow(infoUpdateTitle, idAndName2, infoUpdateMessage);
        updateLogList.add(infoUpdateLog); // Add the formatted log message to the list

        //IF_YOU_FIND_IT_OUT_I"M_GUILTY_FOR_CHEATING
    }

    public void tSaveCngClicked(ActionEvent actionEvent) {
        TeachApproveData approveData = tapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String isApproved = approveData.getApproved();

            Connection conn = null;
            Statement stmt = null;

            try {
                conn = DriverManager.getConnection(UserService.URL);
                stmt = conn.createStatement();

                String query = "UPDATE teachers SET name='" + ttf2.getText() + "' WHERE initial = '" + approveData.getInitial() + "'";
                String query1 = "UPDATE teachers SET email='" + ttf3.getText() + "' WHERE initial = '" + approveData.getInitial() + "'";
                String query2 = "UPDATE teachers SET mobile='" + ttf4.getText() + "' WHERE initial = '" + approveData.getInitial() + "'";
                String query3 = "UPDATE teachers SET role='" + tcb1.getValue() + "' WHERE initial = '" + approveData.getInitial() + "'";

                stmt.executeUpdate(query);
                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);
                stmt.executeUpdate(query3);

                conn.close();
                stmt.close();

                tapprovalTable.getItems().clear();
                loadItemsIntoTeacherTable();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No item is selected").showAndWait();
        }
    }

    public void approveClicked(ActionEvent actionEvent) {
        StApproveData approveData = sapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String isApproved = approveData.getApproved();

            if (isApproved.equals("false")) {
                Connection conn = null;
                Statement stmt = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE students SET approved='true' WHERE id = '" + approveData.getId() + "'";

                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    sapprovalTable.getItems().clear();
                    loadItemsIntoTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE students SET approved='false' WHERE id = '" + approveData.getId() + "'";

                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    sapprovalTable.getItems().clear();
                    loadItemsIntoTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No item is selected").showAndWait();
        }
    }

    public void tapproveClicked(ActionEvent actionEvent) {
        TeachApproveData approveData = tapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String isApproved = approveData.getApproved();

            if (isApproved.equals("false")) {
                Connection conn = null;
                Statement stmt = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE teachers SET approve='true' WHERE initial = '" + approveData.getInitial() + "'";

                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    tapprovalTable.getItems().clear();
                    loadItemsIntoTeacherTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    stmt = conn.createStatement();

                    String query = "UPDATE teachers SET approve='false' WHERE initial = '" + approveData.getInitial() + "'";

                    stmt.executeUpdate(query);

                    conn.close();
                    stmt.close();

                    tapprovalTable.getItems().clear();
                    loadItemsIntoTeacherTable();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "No item is selected").showAndWait();
        }
    }


    //REPORT_&_LOGS
    public void loadComplainTableView() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.COMPLAINT_URL); //url
            stmt = conn.createStatement();

            String query = "select * from complain";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String name = rs.getString("Name");
                String id = rs.getString("ID");
                String email = rs.getString("Email");
                String role = rs.getString("Role");
                String complainTitle = rs.getString("Complain title");
                String description = rs.getString("Description");

                System.out.println(id + " " + name + " " + email + " " + role + " " + complainTitle + " " + description);

                complains.add(new ComplainData(name, id, email, role, complainTitle, description));
            }

            complainTV.setItems(complains);

            conn.close();
            stmt.close();
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //SETTINGS
    @FXML public void editProPictureClicked() {
        // Open the file chooser to select a new profile picture
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Load the selected image
            Image profileImage = new Image(selectedFile.toURI().toString());

            // Set the image as the fill for myProfileCircle
            myProfileCircle.setFill(new ImagePattern(profileImage));
            proPicCircle.setFill(new ImagePattern(profileImage));

        }
    }

    @FXML public void backgroundSelectorChanged(ActionEvent event) {
        // Get the selected background option
        String selectedBackground = backgroundSelector.getValue();

        String imagePath = "";

        // Assign the corresponding image path based on the selected option
        switch (selectedBackground) {
            case "Winter Morning":
                imagePath = "file:/C:/Users/sifat/Documents/aoop-java-project/src/main/resources/images/admin/Cover/Cover1.jpg";
                break;
            case "Sunny Sky":
                imagePath = "file:/C:/Users/sifat/Documents/aoop-java-project/src/main/resources/images/admin/Cover/Cover2.jpg";
                break;
            case "Mesh Triangle Vector":
                imagePath = "file:/C:/Users/sifat/Documents/aoop-java-project/src/main/resources/images/admin/Cover/Cover3.png";
                break;
            case "Eid Night":
                imagePath = "file:/C:/Users/sifat/Documents/aoop-java-project/src/main/resources/images/admin/Cover/Cover4.png";
                break;
            case "Floral Vector":
                imagePath = "file:/C:/Users/sifat/Documents/aoop-java-project/src/main/resources/images/admin/Cover/Cover5.png";
                break;
            default:
                return;
        }

        // Load and set the new background image
        Image backgroundImage = new Image(imagePath);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        upperPane.setBackground(new Background(background));
    }

    public void saveChangesBtnClicked(ActionEvent actionEvent) {

    }

    //LOGOUT
    public void signoutClicked(MouseEvent mouseEvent) {
        ChangeScene.change("/general/login.fxml", mouseEvent);
    }

}

