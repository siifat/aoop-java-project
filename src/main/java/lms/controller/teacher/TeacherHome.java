package lms.controller.teacher;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class TeacherHome {

    public Label dashboardLabel;
    public Label editClassesLabel;
    public Label manageStudsLabel;
    public Label myProfileLabel;
    public Label settingsLabel;
    public Label privateFilesLabel;
    public Circle profilePicCircle;
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

    private Label selectedLabel;


    public void initialize() {

        String camLoc = "/images/teacher/photo.png";
        Image cam = new Image(getClass().getResource(camLoc).toExternalForm());
        cameraCircle.setFill(new ImagePattern(cam));

//        setProfilePic(Login.currentLoggedInTeacher.getProfilePicture());

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

//    private void setProfilePic(String picLocation) {
//        Image proPic = new Image(picLocation);
//        Image profileImage = new Image(selectedFile.toURI().toString());
//
//        // Set the image as the fill for myProfileCircle
//        myProfileCircle.setFill(new ImagePattern(profileImage));
//        profilePicCircle.setFill(new ImagePattern(profileImage));
//        profilePicCircle.setFill(new ImagePattern(proPic));
//        myProfileCircle.setFill(new ImagePattern(proPic));
//    }

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
}
