package lms.controller.admin;

import io.github.palexdev.materialfx.controls.MFXListView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import lms.util.ChangeScene;
import lms.util.UserService;

import java.sql.*;

public class AdminHome {

    public Pane homep1Pane;
    public Pane ump1Pane;
    public Pane ump2Pane;
    public Pane ump3Pane;
    public Pane rlp1Pane;
    public Pane sconfigp1Pane;
    public Circle proPicCircle;
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
    @FXML
    private TableView<TeachApproveData> tapprovalTable;
    @FXML
    private TableColumn<TeachApproveData, String> tinitialCol;
    @FXML
    private TableColumn<TeachApproveData, String> tnameCol;
    @FXML
    private TableColumn<TeachApproveData, String> temailCol;
    @FXML
    private TableColumn<TeachApproveData, String> tmobileCol;
    @FXML
    private TableColumn<TeachApproveData, String> troleCol;
    @FXML
    private TableColumn<TeachApproveData, String> tappCol;

    @FXML
    private TableView<StApproveData> sapprovalTable;
    @FXML
    private TableColumn<StApproveData, String> sidCol;
    @FXML
    private TableColumn<StApproveData, String> snameCol;
    @FXML
    private TableColumn<StApproveData, String> semailCol;
    @FXML
    private TableColumn<StApproveData, String> smobileCol;
    @FXML
    private TableColumn<StApproveData, String> sroleCol;
    @FXML
    private TableColumn<StApproveData, String> sregCol;
    @FXML
    private TableColumn<StApproveData, String> sappCol;

    @FXML
    private Pane chartContainer;

    @FXML
    private MFXListView<String> updateLogLV;

    private ObservableList<TeachApproveData> adt1 = FXCollections.observableArrayList();
    private ObservableList<StApproveData> adt = FXCollections.observableArrayList();

    @FXML
    private TableColumn<ComplainData, String> cNameCol;

    @FXML
    private TableColumn<ComplainData, String> cIDCol;

    @FXML
    private TableColumn<ComplainData, String> cEmailCol;

    @FXML
    private TableColumn<ComplainData, String> cRoleCol;

    @FXML
    private TableColumn<ComplainData, String> cComTitleCol;

    @FXML
    private TableColumn<ComplainData, String> cDescripCol;

    @FXML
    private MFXTextField searchField2;
    @FXML
    private TableView<ComplainData> complainTV;
    @FXML
    private TableColumn<ComplainData, String> nameCol;
    @FXML
    private TableColumn<ComplainData, String> idCol;
    @FXML
    private TableColumn<ComplainData, String> emailCol;
    @FXML
    private TableColumn<ComplainData, String> selectCol;

    private ObservableList<ComplainData> complains = FXCollections.observableArrayList();

    public void initialize() {
        // Load the image from the resources folder
//        Image proPic = new Image(getClass().getResource("/images/admin.jpg").toExternalForm());
//        proPicCircle.setFill(new ImagePattern(proPic));

        sidCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("id"));
        snameCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("name"));
        semailCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("email"));
        smobileCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("mobile"));
        sroleCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("role"));
        sregCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("registration"));
        sappCol.setCellValueFactory(new PropertyValueFactory<StApproveData, String>("approved"));

        tinitialCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("initial"));
        tnameCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("name"));
        temailCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("email"));
        tmobileCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("mobile"));
        troleCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("role"));
        tappCol.setCellValueFactory(new PropertyValueFactory<TeachApproveData, String>("approved"));

        sapprovalTable.getItems().clear();
        loadItemsIntoTable();

        tapprovalTable.getItems().clear();
        loadItemsIntoTeacherTable();

        //homePage -Graph
        displayBarGraph();

        //report -search
        cNameCol.setCellValueFactory(new PropertyValueFactory<ComplainData, String>("cName"));
        cIDCol.setCellValueFactory(new PropertyValueFactory<ComplainData, String>("cId"));
        cEmailCol.setCellValueFactory(new PropertyValueFactory<ComplainData, String>("cEmail"));
        cRoleCol.setCellValueFactory(new PropertyValueFactory<ComplainData, String>("cRole"));
        cComTitleCol.setCellValueFactory(new PropertyValueFactory<ComplainData, String>("cComplainTile"));
        cDescripCol.setCellValueFactory(new PropertyValueFactory<ComplainData, String>("cDescription"));

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

                if (student.getcName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name
                } else if (student.getcId().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches id
                } else if (student.getcEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches email
                }

                return false; // Does not match
            });
        });

        // Bind the filtered list to the complainTV
        complainTV.setItems(filteredData);

    }

    public void homeClicked(ActionEvent actionEvent) {
        homep1Pane.setVisible(true);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(false);
    }

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

    public void p2backClicked() {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(true);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(false);
    }

    public void manageClicked(ActionEvent actionEvent) {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(true);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(false);
    }

    public void reportClicked(ActionEvent actionEvent) {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(true);
        sconfigp1Pane.setVisible(false);
    }

    public void configClicked(ActionEvent actionEvent) {
        homep1Pane.setVisible(false);
        ump1Pane.setVisible(false);
        ump2Pane.setVisible(false);
        ump3Pane.setVisible(false);
        rlp1Pane.setVisible(false);
        sconfigp1Pane.setVisible(true);
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


    //last update----------------------------------------------------------------------------------------------

    public void displayBarGraph() {
        BarGraph barGraph = new BarGraph();
        BarChart<String, Number> chart = barGraph.createBarChart();

        // Clear previous content if necessary
        homep1Pane.getChildren().clear();
        homep1Pane.getChildren().add(chart);
    }

    public void showChartButtonClicked(ActionEvent event) {
        displayBarGraph();
    }
    //end of last update-----------------------------------------------------------------------------------------


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

            stf1.setText(id);
            stf2.setText(name);
            stf3.setText(email);
            stf4.setText(mobile);
        }
    }

    public void tapprovalTableClicked(MouseEvent mouseEvent) {
        TeachApproveData approveData = tapprovalTable.getSelectionModel().getSelectedItem();

        if (approveData != null) {
            String initial = approveData.getInitial();
            String name = approveData.getName();
            String email = approveData.getEmail();
            String mobile = approveData.getMobile();

            ttf1.setText(initial);
            ttf2.setText(name);
            ttf3.setText(email);
            ttf4.setText(mobile);
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

                stmt.executeUpdate(query);
                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);

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

                stmt.executeUpdate(query);
                stmt.executeUpdate(query1);
                stmt.executeUpdate(query2);

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

    public void signoutClicked(MouseEvent mouseEvent) {

        ChangeScene.change("/general/login.fxml", mouseEvent);
    }


    //complain

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
}
