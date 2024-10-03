package lms.controller.cat;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lms.controller.api.CatAPI;
import lms.controller.dictionary.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class CatController extends Controller implements Initializable {
    @FXML
    private AnchorPane rootAnchor;

    @FXML
    private ImageView catView;
    @FXML
    private Label catGeneratorLabel;

    private CatAPI catAPI;

    private CatImage catImage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        String source = "https://http.cat/200";
        Image catImage = new Image(source);
        catView.setImage(catImage);
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

}
