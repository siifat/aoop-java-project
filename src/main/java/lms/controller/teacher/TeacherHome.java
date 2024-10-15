package lms.controller.teacher;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lms.App;
import lms.controller.general.Login;
import lms.util.UserService;
import org.controlsfx.control.Rating;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

public class TeacherHome {

    @FXML private Label totalStu;
    @FXML private Label ex;
    @FXML private Label strug;

    public Label dashboardLabel;
    public Label editClassesLabel;
    public Label manageStudsLabel;
    public Label myProfileLabel;
    public Label settingsLabel;
    public Label privateFilesLabel;
    public Circle profilePicCircle;

    @FXML private MFXComboBox<String> firstScreenCB;

    @FXML private MFXPasswordField privateFilePassField;

    @FXML private MFXTextField sNameField;
    @FXML private MFXTextField sEmailField;
    @FXML private MFXPasswordField sPassField;
    @FXML private TextArea teacherBioField;

    @FXML private TextField pNameField;
    @FXML private TextField pIDField;
    @FXML private TextField pEmailField;
    @FXML private TextField pInitialField;
    @FXML private TextField pMobileField;


    @FXML private MFXComboBox<String> sectionSelectionCB;

    // panes
    @FXML private AnchorPane dashboardPane;
    @FXML private AnchorPane privateFilesLoginPane;
    @FXML private AnchorPane privateFilesPane;
    @FXML private AnchorPane myProfilePane;
    @FXML private AnchorPane settingsPane;
    @FXML private AnchorPane editClassesPane;


    // Tables
    @FXML private TableView<CourseElement> courseMaterialTableView;
    @FXML private TableColumn<CourseElement, String> cTitle;
    @FXML private TableColumn<CourseElement, String> cDesc;
    @FXML private TableColumn<CourseElement, String> cUploadDate;
    @FXML private TableColumn<CourseElement, String> cSec;


    @FXML private TableView<Assignment> assignmentTableView;
    @FXML private TableColumn<Assignment, String> aTitle;
    @FXML private TableColumn<Assignment, String> aDesc;
    @FXML private TableColumn<Assignment, String> aDueDate;
    @FXML private TableColumn<Assignment, String> aSec;


    @FXML private MFXToggleButton wantEmailTB;
    @FXML private Circle cameraCircle;
    @FXML private Circle myProfileCircle;


    @FXML
    private Rating rating;
    @FXML
    private Label ratingLabel;
    @FXML
    private AnchorPane root;

    @FXML private PieChart pieChart;

    private Label selectedLabel;

    @FXML private ListView<File> l;

    // Method to open the file using the system's default application
    private void openFile(File file) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException e) {
                System.err.println("Unable to open file: " + e.getMessage());
            }
        } else {
            System.err.println("Desktop is not supported on this platform.");
        }
    }

    private ObservableList<File> fileList = FXCollections.observableArrayList();

    private void updateFileList(File folder) {
        fileList.clear(); // Clear the old list
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                fileList.addAll(Arrays.asList(files)); // Add new files to the list
            }
        }
        l.setItems(fileList); // Set the updated file list to the ListView
    }

    private void watchDirectory(Path path) {
        Thread watcherThread = new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE);
                while (true) {
                    WatchKey key;
                    try {
                        key = watchService.take(); // Wait for a key to be available
                    } catch (InterruptedException e) {
                        return; // Exit if interrupted
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        if (kind == StandardWatchEventKinds.OVERFLOW) {
                            continue; // Skip if there was an overflow
                        }

                        // Update the ListView on the JavaFX Application Thread
                        Platform.runLater(() -> updateFileList(new File(path.toString())));
                    }

                    boolean valid = key.reset(); // Reset the key and exit if it's no longer valid
                    if (!valid) {
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        watcherThread.setDaemon(true); // Allow the thread to exit when the application closes
        watcherThread.start(); // Start watching the directory
    }


    public void initialize() {

        String folderPath = "C:\\Users\\sifat\\Documents\\aoop-java-project\\src\\main\\resources\\privateFiles\\0112320247";
        File folder = new File(folderPath);

        // Get list of files from the folder
        ObservableList<File> fileList = FXCollections.observableArrayList();

        // Get list of files from the folder
        updateFileList(folder);

        // Set how file names will appear in the ListView
        l.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(File file, boolean empty) {
                super.updateItem(file, empty);
                if (empty || file == null) {
                    setText(null);
                } else {
                    setText(file.getName());
                }
            }
        });

        /// Handle double-click to open file
        l.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                File selectedFile = l.getSelectionModel().getSelectedItem();
                if (selectedFile != null) {
                    openFile(selectedFile);
                }
            }
        });

        // Watch for changes in the directory
        watchDirectory(folder.toPath());

        calculateAndPrintStudentStats();

        loadPieChart();

        loadTopAndBottomStudents();

        loadAssignmentListView();

        sNameField.setText(Login.currentLoggedInTeacher.getName());
        sEmailField.setText(Login.currentLoggedInTeacher.getEmail());
        sPassField.setText(Login.currentLoggedInTeacher.getPassword());
        teacherBioField.setText(Login.currentLoggedInTeacher.getBio());

        pNameField.setText(Login.currentLoggedInTeacher.getName());
        pIDField.setText(Login.currentLoggedInTeacher.getId());
        pEmailField.setText(Login.currentLoggedInTeacher.getEmail());
        pInitialField.setText(Login.currentLoggedInTeacher.getInitial());
        pMobileField.setText(Login.currentLoggedInTeacher.getMobile());

        String camLoc = "/images/teacher/photo.png";
        Image cam = new Image(getClass().getResource(camLoc).toExternalForm());
        cameraCircle.setFill(new ImagePattern(cam));


        // SET PRO PIC When APP Launch

//        setProfilePic(Login.currentLoggedInTeacher.getProfilePicture());

        if(Login.currentLoggedInTeacher.getWantEmail().equalsIgnoreCase("yes")){
            wantEmailTB.setSelected(true);
        } else {
            wantEmailTB.setSelected(false);
        }

        setSelectedLabel(dashboardLabel);
        initializeSectionSelectionCB();
        initCourseAndAssignmentTableColumns();



        wantEmailTB.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                wantEmailTB.setText("I want email notifications");

                Connection conn = null;
                Statement statement = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    statement = conn.createStatement();

                    String query = "UPDATE teachers SET wantEmail='yes' WHERE id= '" +
                            Login.currentLoggedInTeacher.getId()+ "'";

                    statement.executeUpdate(query);

                    conn.close();
                    statement.close();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            } else {
                wantEmailTB.setText("I don't want any email notifications");

                Connection conn = null;
                Statement statement = null;

                try {
                    conn = DriverManager.getConnection(UserService.URL);
                    statement = conn.createStatement();

                    String query = "UPDATE teachers SET wantEmail='no' WHERE id= '" +
                            Login.currentLoggedInTeacher.getId()+ "'";

                    statement.executeUpdate(query);

                    conn.close();
                    statement.close();

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        loadCourseElementTableView();
        loadAssignmentTableView();

        // Add a listener for double-clicking a row in the table
        courseMaterialTableView.setRowFactory(tv -> {
            TableRow<CourseElement> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CourseElement selectedCourseElement = row.getItem();
                    openFile(selectedCourseElement.getPath());
                }
            });
            return row;
        });


        // Add a listener for double-clicking a row in the table
        assignmentTableView.setRowFactory(tv -> {
            TableRow<Assignment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Assignment selectedAssignment = row.getItem();
                    openFile(selectedAssignment.getPath());
                }
            });
            return row;
        });
    }

    @FXML private Label ad;
    @FXML private Label in;
    @FXML private Label ba;
    @FXML private Label pro;

    private void loadPieChart() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();

            // Get the total number of students
            String totalStudentsQuery = "SELECT COUNT(*) as total FROM marks";
            rs = stmt.executeQuery(totalStudentsQuery);
            int totalStudents = rs.getInt("total");

            // Initialize count for each category
            int above90 = 0, above80 = 0, above60 = 0, above50 = 0;

            // Get the number of students for each mark range
            String marksQuery = "SELECT `TotalMarks` FROM marks";
            rs = stmt.executeQuery(marksQuery);

            while (rs.next()) {
                int totalMarks = rs.getInt("TotalMarks");

                if (totalMarks > 90) {
                    above90++;
                } else if (totalMarks > 80) {
                    above80++;
                } else if (totalMarks > 60) {
                    above60++;
                } else if (totalMarks > 50) {
                    above50++;
                }
            }

            // Calculate percentages
            double percentAbove90 = ((double) above90 / totalStudents) * 100;
            double percentAbove80 = ((double) above80 / totalStudents) * 100;
            double percentAbove60 = ((double) above60 / totalStudents) * 100;
            double percentAbove50 = ((double) above50 / totalStudents) * 100;

            ad.setText(String.format("%.1f", percentAbove90));
            in.setText(String.format("%.1f", percentAbove80));
            ba.setText(String.format("%.1f", percentAbove60));
            pro.setText(String.format("%.1f", percentAbove50));
            // Create PieChart data
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                    new PieChart.Data("Above 90%", percentAbove90),
                    new PieChart.Data("Above 80%", percentAbove80),
                    new PieChart.Data("Above 60%", percentAbove60),
                    new PieChart.Data("Above 50%", percentAbove50)
            );


            // Set the data to the PieChart
            pieChart.setData(pieChartData);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void calculateAndPrintStudentStats() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(DB_URL);
            stmt = conn.createStatement();

            // Get the total number of students
            String totalStudentsQuery = "SELECT COUNT(*) as total FROM marks";
            rs = stmt.executeQuery(totalStudentsQuery);
            int totalStudents = rs.getInt("total");

            // Variables to count students above and below/equal to 70 marks
            int above70 = 0, belowOrEqual70 = 0;

            // Get all the total marks for students
            String marksQuery = "SELECT TotalMarks FROM marks";
            rs = stmt.executeQuery(marksQuery);

            while (rs.next()) {
                double totalMarks = Double.parseDouble(rs.getString("TotalMarks"));

                if (totalMarks > 70) {
                    above70++;
                } else {
                    belowOrEqual70++;
                }
            }

            totalStu.setText(String.valueOf(totalStudents));
            ex.setText(String.valueOf(above70));
            strug.setText(String.valueOf(belowOrEqual70));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    public void setProfilePic(String picLocation) {
        try {
            // Load the image from the given location
            Image profileImage = new Image("file:" + picLocation);

            // Set the image in the Circle shape as an ImagePattern
            myProfileCircle.setFill(new ImagePattern(profileImage));
            profilePicCircle.setFill(new ImagePattern(profileImage));

        } catch (IllegalArgumentException e) {
            // Handle cases where the image could not be loaded
            System.out.println("Error: Could not load the image from " + picLocation);
        }
    }

    public void dashboardClicked(MouseEvent mouseEvent) {
        setSelectedLabel(dashboardLabel);

        privateFilesLoginPane.setVisible(false);
        editClassesPane.setVisible(false);
        privateFilesPane.setVisible(false);
        myProfilePane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(true);
    }

    public void editClassClicked(MouseEvent mouseEvent) {
        setSelectedLabel(editClassesLabel);

        privateFilesLoginPane.setVisible(false);
        privateFilesPane.setVisible(false);
        myProfilePane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(false);
        editClassesPane.setVisible(true);
    }

    public void manageStudsClicked(MouseEvent mouseEvent) {
        setSelectedLabel(manageStudsLabel);
    }

    public void myProfileClicked(MouseEvent mouseEvent) {
        setSelectedLabel(myProfileLabel);

        privateFilesLoginPane.setVisible(false);
        editClassesPane.setVisible(false);
        privateFilesPane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(false);
        myProfilePane.setVisible(true);
    }

    public void settingsClicked(MouseEvent mouseEvent) {
        setSelectedLabel(settingsLabel);

        privateFilesLoginPane.setVisible(false);
        editClassesPane.setVisible(false);
        privateFilesPane.setVisible(false);
        myProfilePane.setVisible(false);
        dashboardPane.setVisible(false);
        settingsPane.setVisible(true);
    }

    public void privateFilesClicked(MouseEvent mouseEvent) {
        setSelectedLabel(privateFilesLabel);

        privateFilesPane.setVisible(false);
        editClassesPane.setVisible(false);
        myProfilePane.setVisible(false);
        settingsPane.setVisible(false);
        dashboardPane.setVisible(false);
        privateFilesLoginPane.setVisible(true);
    }

    private void setSelectedLabel(Label label) {
        // Remove the 'selected-label' class from all labels
        dashboardLabel.getStyleClass().remove("selected-label");
        editClassesLabel.getStyleClass().remove("selected-label");
        manageStudsLabel.getStyleClass().remove("selected-label");
        myProfileLabel.getStyleClass().remove("selected-label");
        settingsLabel.getStyleClass().remove("selected-label");
        privateFilesLabel.getStyleClass().remove("selected-label");

        // Add the 'selected-label' class to the clicked label
        label.getStyleClass().add("selected-label");
        // Store this label inside a variable so that we can access it later using this
        selectedLabel = label;
    }

    public void chatIconClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/LoginView.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage loginStage = new Stage();
            loginStage.setScene(scene);

            // Don't block the main window (Modality.NONE)
            loginStage.initModality(Modality.NONE);

            // Show the login window
            loginStage.show();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }


    }

    @FXML
    private void createGroupBtnClicked(ActionEvent actionEvent) {

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/teacher/sendLinkDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            throw new RuntimeException(e);
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
    }

    @FXML
    private void handleRating(MouseEvent event) {
        System.out.println(rating.getRating());
        String labelString = "User Rating: " + rating.getRating();
        if (rating.getRating() == 5) labelString += " Thank you";
        else labelString += "   Nothing but 5 stars please 😊";
        if (rating.getRating() != 5) rating.setRating(5);
        ratingLabel.setText(labelString);
    }

    @FXML
    private void addComplaintClicked(ActionEvent e){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(root.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/fxml/teacher/sendComplaintDialog.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e1) {
            System.out.println("Couldn't load the dialog");
            throw new RuntimeException(e1);
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        Optional<ButtonType> result = dialog.showAndWait();
    }

    private void initializeSectionSelectionCB(){

        ObservableList<String> sections = FXCollections.observableArrayList();

        sections.addAll("Section A", "Section B", "Section C");
        sectionSelectionCB.setItems(sections);
        sectionSelectionCB.selectFirst();

        System.out.println(sectionSelectionCB.getValue());

//        AND ALSO Another combo box
        firstScreenCB.setItems(sections);
        firstScreenCB.selectFirst();
    }


    private ObservableList<CourseElement> courses = FXCollections.observableArrayList();
    private ObservableList<Assignment> assignments = FXCollections.observableArrayList();
    @FXML
    private void addCourseElementClicked() {

        try {
            // Load the DialogPane from the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/addCourseDialog.fxml"));
            DialogPane dialogPane = loader.load();

            AddCourseDialogController controller = loader.getController();

            // Create a Dialog and set the DialogPane
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add Course");

            // Handle the result when the user clicks OK or Cancel
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    String title = controller.getTitleField().getText();
                    String description = controller.getDescField().getText();

                    // Get today's date
                    Date today = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String uploadDate = formatter.format(today);

                    String section = sectionSelectionCB.getValue();

                    File selectedFile = controller.getSelectedFile();
                    String originalPath = selectedFile.getAbsolutePath();

                    // Define the destination folder
                    String destinationFolder = "C:/Users/sifat/Documents/aoop-java-project/src/main/resources/courseMaterials/"
                            + Login.currentLoggedInTeacher.getId() + "/" + section;

                    // Ensure the destination folder exists
                    File destDir = new File(destinationFolder);
                    if (!destDir.exists()) {
                        destDir.mkdirs();  // Create directories if they don't exist
                    }

                    // Define the destination file path
                    String destinationPath = destinationFolder + "/" + selectedFile.getName();
                    File destinationFile = new File(destinationPath);

                    try {
                        // Copy the file to the new location
                        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        // Now set the path to the new location
                        CourseElement course = new CourseElement(title, description, uploadDate, section, destinationPath);

                        Connection conn = null;

                        try {
                            conn = DriverManager.getConnection(UserService.COURSE_URL);
                            String query = "INSERT INTO courseElements VALUES(?, ?, ?, ?, ?, ?)";

                            PreparedStatement ps = conn.prepareStatement(query);
                            ps.setString(1, Login.currentLoggedInTeacher.getId());
                            ps.setString(2, course.getTitle());
                            ps.setString(3, course.getDescription());
                            ps.setString(4, course.getUploadDate());
                            ps.setString(5, course.getSection());
                            ps.setString(6, course.getPath());

                            ps.executeUpdate();

                            conn.close();

                            Platform.runLater(this::loadCourseElementTableView);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (result == ButtonType.CANCEL) {
                    System.out.println("Cancelled");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void addNewAssignmentClicked(){
        try {
            // Load the DialogPane from the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teacher/addAssignmentDialog.fxml"));
            DialogPane dialogPane = loader.load();

            AddAssignmentDialogController controller = loader.getController();

            // Create a Dialog and set the DialogPane
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Add Assignment");

            // Handle the result when the user clicks OK or Cancel
            dialog.showAndWait().ifPresent(result -> {
                if (result == ButtonType.OK) {

                    String title = controller.getTitleField().getText();
                    String description = controller.getDescField().getText();

                    LocalDate localDate = controller.getDatePicker().getValue();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String dueDate = localDate.format(formatter);

                    String section = sectionSelectionCB.getValue();

                    File selectedFile = controller.getSelectedFile();
                    String originalPath = selectedFile.getAbsolutePath();

                    // Define the destination folder
                    String destinationFolder = "C:/Users/sifat/Documents/aoop-java-project/src/main/resources/assignments/"
                            + Login.currentLoggedInTeacher.getId() + "/" + section;

                    // Ensure the destination folder exists
                    File destDir = new File(destinationFolder);
                    if (!destDir.exists()) {
                        destDir.mkdirs();  // Create directories if they don't exist
                    }

                    // Define the destination file path
                    String destinationPath = destinationFolder + "/" + selectedFile.getName();
                    File destinationFile = new File(destinationPath);

                    try {
                        // Copy the file to the new location
                        Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        // Now set the path to the new location
                        Assignment assignment = new Assignment(title, description, dueDate, section, destinationPath);

                        Connection conn = null;

                        try {
                            conn = DriverManager.getConnection(UserService.COURSE_URL);
                            String query = "INSERT INTO assignments VALUES(?, ?, ?, ?, ?, ?)";

                            PreparedStatement ps = conn.prepareStatement(query);
                            ps.setString(1, Login.currentLoggedInTeacher.getId());
                            ps.setString(2, assignment.getTitle());
                            ps.setString(3, assignment.getDescription());
                            ps.setString(4, assignment.getDueDate());
                            ps.setString(5, assignment.getSection());
                            ps.setString(6, assignment.getPath());

                            ps.executeUpdate();

                            conn.close();

                            Platform.runLater(this::loadAssignmentTableView);

                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (result == ButtonType.CANCEL) {
                    System.out.println("Cancelled");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCourseAndAssignmentTableColumns(){
        cTitle.setCellValueFactory(new PropertyValueFactory<CourseElement, String>("title"));
        cDesc.setCellValueFactory(new PropertyValueFactory<CourseElement, String>("description"));
        cUploadDate.setCellValueFactory(new PropertyValueFactory<CourseElement, String>("uploadDate"));
        cSec.setCellValueFactory(new PropertyValueFactory<CourseElement, String>("section"));

        aTitle.setCellValueFactory(new PropertyValueFactory<Assignment, String>("title"));
        aDesc.setCellValueFactory(new PropertyValueFactory<Assignment, String>("description"));
        aDueDate.setCellValueFactory(new PropertyValueFactory<Assignment, String>("dueDate"));
        aSec.setCellValueFactory(new PropertyValueFactory<Assignment, String>("section"));
    }

    private void loadCourseElementTableView(){

        System.out.println("Courses:" + courses);
        courses.clear();
        courseMaterialTableView.getItems().clear();

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.COURSE_URL);
            statement = conn.createStatement();

            String query = "SELECT * FROM courseElements WHERE teacherID = '" + Login.currentLoggedInTeacher.getId() + "'";

            rs = statement.executeQuery(query);

            while(rs.next()){
                CourseElement course = new CourseElement(rs.getString("title"),
                        rs.getString("description"), rs.getString("uploadDate"),
                        rs.getString("section"), rs.getString("pathToFile"));
                courses.add(course);
            }
            courseMaterialTableView.setItems(courses);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private void loadAssignmentTableView(){
        assignments.clear();
        assignmentTableView.getItems().clear();

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.COURSE_URL);
            statement = conn.createStatement();

            String query = "SELECT * FROM assignments WHERE teacherID = '" + Login.currentLoggedInTeacher.getId() + "'";

            rs = statement.executeQuery(query);

            while(rs.next()){
                Assignment assignment = new Assignment(rs.getString("title"),
                        rs.getString("description"), rs.getString("dueDate"),
                        rs.getString("section"), rs.getString("pathToFile"));
                assignments.add(assignment);
            }
            assignmentTableView.setItems(assignments);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML private ListView<String> myListView;

    private void loadAssignmentListView() {
        assignments.clear();
        myListView.getItems().clear();

        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(UserService.COURSE_URL);
            statement = conn.createStatement();

            String query = "SELECT * FROM assignments WHERE teacherID = '" + Login.currentLoggedInTeacher.getId() + "'";

            rs = statement.executeQuery(query);

            while (rs.next()) {
                Assignment assignment = new Assignment(
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("dueDate"),
                        rs.getString("section"),
                        rs.getString("pathToFile")
                );
                assignments.add(assignment);
            }

            // Populate ListView with assignment titles
            for (Assignment assignment : assignments) {
                myListView.getItems().add(assignment.getTitle());
            }

            // Add event handler for double-click
            myListView.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // Double click detected
                    int selectedIndex = myListView.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                        Assignment selectedAssignment = assignments.get(selectedIndex);
                        openFile(selectedAssignment.getPath());
                    }
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void openFile(String filePath) {
        // Convert the file path into a File object
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist!");
            return;
        }

        // Open the file using the default system application
        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(file);
            } else {
                System.out.println("Opening files is not supported on this platform.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method is triggered when "cameraCircle" is clicked
    @FXML
    public void editProfilePictureClicked() {
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
            profilePicCircle.setFill(new ImagePattern(profileImage));

            Connection conn = null;
            Statement statement = null;

            try {
                conn = DriverManager.getConnection(UserService.URL);
                statement = conn.createStatement();

                String query = "UPDATE teachers SET proPic='"+ selectedFile.getAbsolutePath() +"' WHERE id= '" +
                        Login.currentLoggedInTeacher.getId()+ "'";

                statement.executeUpdate(query);

                conn.close();
                statement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void saveChangesClicked(){

        String name = sNameField.getText();
        String email = sEmailField.getText();
        String bio = teacherBioField.getText();

        Connection conn = null;
        Statement statement = null;

        try {
            conn = DriverManager.getConnection(UserService.URL);
            statement = conn.createStatement();

            String query = "UPDATE teachers SET name='" + name + "'," +
                    "email='" + email + "', bio='"
                    + bio + "' WHERE id= '" +
                    Login.currentLoggedInTeacher.getId() + "'";

            statement.executeUpdate(query);

            conn.close();
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void enterVaultClicked(){
        if(privateFilePassField.getText().equals(Login.currentLoggedInTeacher.getPassword())){
            dashboardPane.setVisible(false);
            myProfilePane.setVisible(false);
            settingsPane.setVisible(false);
            editClassesPane.setVisible(false);
            privateFilesLoginPane.setVisible(false);
            privateFilesPane.setVisible(true);
        }
    }


    // Path to your SQLite database file
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/database/Marksheet.db";

    @FXML
    private ListView<String> topView;

    @FXML
    private ListView<String> botView;

    public void loadTopAndBottomStudents() {
        ObservableList<String> topStudents = FXCollections.observableArrayList();
        ObservableList<String> botStudents = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            // Query to get top 3 students with the highest total marks
            String topQuery = "SELECT Name FROM marks ORDER BY `TotalMarks` DESC LIMIT 3";

            // Query to get bottom 3 students with the lowest total marks
            String bottomQuery = "SELECT Name FROM marks ORDER BY `TotalMarks` ASC LIMIT 3";

            // Fetch top 3 students
            ResultSet rsTop = stmt.executeQuery(topQuery);
            while (rsTop.next()) {
                topStudents.add(rsTop.getString("Name"));
            }

            // Fetch bottom 3 students
            ResultSet rsBottom = stmt.executeQuery(bottomQuery);
            while (rsBottom.next()) {
                botStudents.add(rsBottom.getString("Name"));
            }

            // Set the data into the ListViews
            topView.setItems(topStudents);
            botView.setItems(botStudents);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String DESTINATION_FOLDER = "C:\\Users\\sifat\\Documents\\aoop-java-project\\src\\main\\resources\\privateFiles\\0112320247";

    @FXML
    private void bClicked(ActionEvent event) {
        // Open FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a File");

        // Let the user pick a file
        File selectedFile = fileChooser.showOpenDialog(null);

        // If a file is chosen, proceed with copying
        if (selectedFile != null) {
            try {
                // Get the file name
                String fileName = selectedFile.getName();

                // Define the destination path for the copied file
                Path destinationPath = Paths.get(DESTINATION_FOLDER, fileName);

                // Copy the file to the destination folder
                Files.copy(selectedFile.toPath(), destinationPath);

                // Print the new absolute path of the copied file
                System.out.println("File copied to: " + destinationPath.toAbsolutePath().toString());



            } catch (IOException e) {
                // Handle errors during file copying
                System.out.println("Error copying the file: " + e.getMessage());
            }
        } else {
            System.out.println("No file was selected.");
        }
    }
}
