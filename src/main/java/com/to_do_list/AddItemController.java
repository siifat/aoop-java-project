package com.to_do_list;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

public class AddItemController {
    @FXML
    private TextArea details;
    @FXML
    private DatePicker deadline;
    @FXML
    private TextField description;
    @FXML
    private Button cancelButton;
    @FXML
    private Button OkButton;

    private String newItemDescription;
    private String newItemDetails;
    private String newItemDate;
    private String desc;
    private String det;
    private LocalDate date;

    private boolean okClicked = false;


    public String getNewItemDescription() {
        return newItemDescription;
    }

    public String getNewItemDetails() {
        return newItemDetails;
    }

    public String getNewItemDate() {
        return newItemDate;
    }

    public void handleAddItem() {

        newItemDescription = desc;
        newItemDetails = det;
        newItemDate = date.toString();
    }

    public AddItemController() {
    }

    public void cancelButtonClicked(ActionEvent actionEvent)
    {
        // Get the stage associated with the Cancel button
        Stage stage = (Stage) cancelButton.getScene().getWindow();

        // Close only the current stage (Add-item-view)
        stage.close();
    }

    public void OkButtonClicked(ActionEvent actionEvent)
    {
        desc = description.getText();
        det = details.getText();
        date = deadline.getValue();
        if(desc.isEmpty() || det.isEmpty() || date == null) return;
        ToDoItem toDoItems = new ToDoItem(desc, det, date);

        writeToTextFile(toDoItems);
        okClicked = true;
        Stage stage = (Stage) OkButton.getScene().getWindow();
        stage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private void writeToTextFile(ToDoItem toDoItem) {
        try
        {
            // Get the resource path
            String separator = File.separator;
            String filePathString = "src" + separator + "main" + separator + "resources" + separator + "data.txt";
            Path filePath = Paths.get(filePathString);

            // Create the parent directories if they don't exist
            Files.createDirectories(filePath.getParent());

            // Create the file if it doesn't exist
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
                // Append a newline character if the file is not empty
                if (Files.size(filePath) > 0) {
                    writer.newLine();
                }
                writer.write(toDoItem.shortDescription()+",");
                writer.write(toDoItem.getDetails()+",");
                writer.write(toDoItem.getDeadline().toString());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
