package lms.controller.student;

import io.github.palexdev.materialfx.controls.MFXCheckbox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lms.controller.api.CatAPI;
import lms.controller.cat.CatImage;
import lms.controller.dictionary.Controller;
import lms.util.ShowDesktopNotification;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DashboardController extends Controller implements Initializable {
    public MFXCheckbox check2;
    public MFXCheckbox check3;
    public MFXCheckbox check4;
    @FXML
    private MFXCheckbox check1;
    @FXML
    private AnchorPane catPane;

    @FXML
    private AnchorPane basePane;

    @FXML
    private AnchorPane bgbasePane;

    @FXML
    private AnchorPane progressPane;

    public Label IDlabel;

    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;

    public void dashboardClicked() {
        bgbasePane.setVisible(false);
        catPane.setVisible(false);
        progressPane.setVisible(false);
        basePane.setVisible(true);
        phyPane.setVisible(false);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
        allCoursePane.setVisible(false);
    }

    public void enrollButtonClicked() {
        basePane.setVisible(false);
        catPane.setVisible(false);
        progressPane.setVisible(false);
        bgbasePane.setVisible(true);
        phyPane.setVisible(false);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
        allCoursePane.setVisible(false);
    }

    public void enrollNowClicked() {
        if (check1.isSelected()) {
            addToDatabase("EC", IDlabel.getText());
        }
        if (check2.isSelected()) {
            addToDatabase("Physics", IDlabel.getText());
        }
        if (check3.isSelected()) {
            addToDatabase("Vector", IDlabel.getText());
        }
        if (check4.isSelected()) {
            addToDatabase("DLD", IDlabel.getText());
        }

    }
    public void progressbarClicked() {
        progressPane.setVisible(true);
        bgbasePane.setVisible(false);
        catPane.setVisible(false);
        basePane.setVisible(false);
        phyPane.setVisible(false);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
        allCoursePane.setVisible(false);
    }

    public void addToDatabase(String tableName, String IDText) {
        try {
            connection = DriverManager.getConnection("jdbc:sqLite:src/main/resources/database/Course.db");
            statement = connection.createStatement();
            String query = "INSERT INTO " + tableName + " VALUES(?, ?)";
            PreparedStatement sIFAT = connection.prepareStatement(query);
            sIFAT.setString(1, IDText);
            sIFAT.setString(2, "");
            sIFAT.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // A method to set the IDLabel from outside the controller
    public void setIDLabel(String id) {
        IDlabel.setText(id);
    }

    @FXML
    void notificationClicked(MouseEvent event) {
        ShowDesktopNotification.show("Notification","Your teacher called");

    }
    @FXML
    private AnchorPane allCoursePane;
    public void seeallClicked1(MouseEvent mouseEvent) {
        allCoursePane.setVisible(true);
        basePane.setVisible(false);
        bgbasePane.setVisible(false);
        progressPane.setVisible(false);
        catPane.setVisible(false);
        phyPane.setVisible(false);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
    }

    public void seeallClicked2(MouseEvent mouseEvent) {

    }

    @FXML
    private TableColumn<Course, String> subCol;
    @FXML
    private TableColumn<Course, String> midCol;
    @FXML
    private TableColumn<Course, String> finCol;
    @FXML
    private TableColumn<Course, String> ct1Col;

    @FXML
    private TableColumn<Course, String> ct2Col;

    @FXML
    private TableColumn<Course, String> ct3Col;
    @FXML
    private TableColumn<Course, String> ass1Col;

    @FXML
    private TableColumn<Course, String> ass2Col;
    // CAT API
    @FXML
    private ImageView catView;
    @FXML
    private Label catGeneratorLabel;

    private CatAPI catAPI;

    private CatImage catImage;
    @FXML
    private TableView<Course>courseTable;
    @FXML
    private ObservableList<Course> courseObservableList = FXCollections.observableArrayList();
    @FXML
    private WebView webView;
    @FXML
    private WebView webView1;
    @FXML
    private WebView webView2;
    @FXML
    private WebView webView3;
    private  WebEngine webEngine,webEngine1,webEngine2,webEngine3;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//       final WebEngine webEngine = webView.getEngine();
//       String urlweb = "https://www.facebook.com/home.php?paipv=0&eav=Afb93kx16l3kQl6e37IzFLIwknvjDaM1FoGzEhsprIGBC9b2S0Ilfq_mI8CXTmLJnlk&_rdr";
//        webEngine.load(urlweb);
            webEngine = webView.getEngine();
            webEngine1 = webView1.getEngine();
            webEngine2 = webView2.getEngine();
            webEngine3 = webView3.getEngine();
        String source = "https://http.cat/200";
        Image catImage = new Image(source);
        catView.setImage(catImage);

        subCol.setCellValueFactory(new PropertyValueFactory<Course, String>("sub"));
        ct1Col.setCellValueFactory(new PropertyValueFactory<Course, String>("ct1"));
        ct2Col.setCellValueFactory(new PropertyValueFactory<Course, String>("ct2"));
        ct3Col.setCellValueFactory(new PropertyValueFactory<Course, String>("ct3"));
        ass1Col.setCellValueFactory(new PropertyValueFactory<Course, String>("assignment1"));
        ass2Col.setCellValueFactory(new PropertyValueFactory<Course, String>("assignment2"));
        midCol.setCellValueFactory(new PropertyValueFactory<Course, String>("mid"));
        finCol.setCellValueFactory(new PropertyValueFactory<Course, String>("finale"));
        Connection coon = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            coon = DriverManager.getConnection("jdbc:sqLite:src/main/resources/database/Course.db");
            st = coon.createStatement();
            String query = "select * from COURSETABLE";
            rs = st.executeQuery(query);
            while (rs.next()) {
                String sub = rs.getString("Sub");
                String ct1 = rs.getString("CT-01");
                String ct2 = rs.getString("CT-02");
                String ct3 = rs.getString("CT-03");
                String ass1 = rs.getString("Assignment-01");
                String ass2 = rs.getString("Assignment-02");
                String mid = rs.getString("Mid");
                String fin = rs.getString("Final");
                courseObservableList.add(new Course(sub,ct1,ct2,ct3,ass1,ass2,mid,fin));
            }
            System.out.println(courseObservableList);
            courseTable.setItems(courseObservableList);
            coon.close();
            st.close();
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private Pane webPane;
    @FXML
    private Pane webPane1;
    @FXML
    private Pane webPane2;
    @FXML
    private Pane webPane3;

    public void cm1Clicked() {
//        final WebEngine webEngine = webView.getEngine();
//        String urlweb = "https://www.facebook.com/home.php?paipv=0&eav=Afb93kx16l3kQl6e37IzFLIwknvjDaM1FoGzEhsprIGBC9b2S0Ilfq_mI8CXTmLJnlk&_rdr";
        webPane.setVisible(true);
        webEngine.load("https://drive.google.com/drive/folders/1_OereiPeuaUBGIgHUzuxov9aTlaFRACk?usp=drive_link");

    }
    public void closeBClicked(){
        webPane.setVisible(false);
    }
    public void closeB1Clicked(){
        webPane1.setVisible(false);
    }
    public void closeB2Clicked(){
        webPane2.setVisible(false);
    }
    public void closeB3Clicked(){
        webPane3.setVisible(false);
    }


    @FXML
    public void getCat() throws InterruptedException {
        catAPI = new CatAPI();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                catImage = catAPI.getData();
//                System.out.println(catImage.getUrl());
                Image cat = new Image(catImage.getUrl());
                catView.setImage(cat);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
        catGeneratorLabel.setVisible(false);
    }

    public void catIconClicked(MouseEvent mouseEvent) {
        basePane.setVisible(false);
        bgbasePane.setVisible(false);
        progressPane.setVisible(false);
        catPane.setVisible(true);
        phyPane.setVisible(false);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
        allCoursePane.setVisible(false);
    }

    @FXML
    private Pane course41;
    @FXML
    private Pane course31;
    @FXML
    private Pane course21;
    @FXML
    private Pane course11;
    @FXML
    private AnchorPane vectorPane;
    @FXML
    private AnchorPane dldPane;

    @FXML
    private AnchorPane ecPane;
    @FXML
    private AnchorPane phyPane;


    public void physicsClicked(MouseEvent mouseEvent) {
        basePane.setVisible(false);
        bgbasePane.setVisible(false);
        progressPane.setVisible(false);
        catPane.setVisible(false);
        phyPane.setVisible(true);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
        allCoursePane.setVisible(false);
    }

    public void vectorClicked(MouseEvent mouseEvent) {
        basePane.setVisible(false);
        bgbasePane.setVisible(false);
        progressPane.setVisible(false);
        catPane.setVisible(false);
        phyPane.setVisible(false);
        dldPane.setVisible(false);
        vectorPane.setVisible(true);
        allCoursePane.setVisible(false);
    }

    public void dldClicked(MouseEvent mouseEvent) {
        basePane.setVisible(false);
        bgbasePane.setVisible(false);
        progressPane.setVisible(false);
        catPane.setVisible(false);
        phyPane.setVisible(false);
        ecPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(true);
        allCoursePane.setVisible(false);
    }
    public void back1Clicked(){
        phyPane.setVisible(false);
        allCoursePane.setVisible(true);
    }
    public void back2Clicked(){
        ecPane.setVisible(false);
        allCoursePane.setVisible(true);
    }
    public void back3Clicked(){
        vectorPane.setVisible(false);
        allCoursePane.setVisible(true);
    }
    public void back4Clicked(){
        dldPane.setVisible(false);
        allCoursePane.setVisible(true);
    }
    public void pl1Clicked(MouseEvent mouseEvent) {
        webPane.setVisible(true);
        webEngine.load("https://drive.google.com/drive/u/0/folders/10ooKurT1W6BPc1deRw22k6sAVmGWmKiT");
    }
    public void marksClicked(MouseEvent mouseEvent) {
        phyPane.setVisible(false);
        progressPane.setVisible(true);
    }
    public void ecClicked(MouseEvent mouseEvent) {
        basePane.setVisible(false);
        bgbasePane.setVisible(false);
        progressPane.setVisible(false);
        catPane.setVisible(false);
        phyPane.setVisible(false);
        vectorPane.setVisible(false);
        dldPane.setVisible(false);
        ecPane.setVisible(true);
        allCoursePane.setVisible(false);
    }

    public void vecClicked(MouseEvent event) {
        webPane2.setVisible(true);
        webEngine2.load("https://drive.google.com/drive/u/0/folders/1NHIusKo7QVv323qOK0w4-PRM9Y6WW1F0");
    }


    public void vecoutClicked(MouseEvent event) {
        webPane2.setVisible(true);
        webEngine2.load("https://drive.google.com/drive/u/0/folders/1ArIEWsQvWAkqw0agleIvj03FXa79urAW");
    }


    public void marks3clicked(MouseEvent event) {
        vectorPane.setVisible(false);
        progressPane.setVisible(true);
    }

    public void eCClicked(MouseEvent event) {
        webPane1.setVisible(true);
        webEngine1.load("https://drive.google.com/drive/u/0/folders/1ycixMofgAzgcU9w_H3T0eSxQ3tCbxbxc");
    }


    public void ecoutClicked(MouseEvent event) {
        webPane1.setVisible(true);
        webEngine1.load("https://drive.google.com/drive/u/0/folders/1gsEU6Rskngb1rHYAJqbefc_Z_fkEoDl9");
    }


    public void marks2Clicked(MouseEvent event) {
        ecPane.setVisible(false);
        progressPane.setVisible(true);
    }
}
