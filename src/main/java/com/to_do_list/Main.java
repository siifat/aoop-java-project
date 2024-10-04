package com.to_do_list;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/todolist/main-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("ToDo List -> Plan Execute!!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();

//        URL url = new URL("https://api.api-ninjas.com/v1/quotes?category=happiness");
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestProperty("accept", "application/json");
//        InputStream responseStream = connection.getInputStream();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode root = mapper.readTree(responseStream);
//        System.out.println(root.path("fact").asText());
    }
}