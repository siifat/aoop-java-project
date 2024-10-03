package lms;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(true))
                .setDeploy(true)
                .setResolveAssets(true)
                .build()
                .setGlobal();

//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/general/login.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/teacher/teacherHome.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/admin/adminHome.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/dictionary/dictionaryHomepage.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/student/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Advanced LMS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
