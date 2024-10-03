package com.to_do_list;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainWindowController {
    @FXML
    private Label deadlineLabel;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ListView<String> toDoListView;
    @FXML
    private TextArea itemDescriptionTextArea;
    @FXML
    private Menu fileButton;
    @FXML
    private MenuItem NewButton;
    @FXML
    private MenuItem ExitButton;
    private final String separator = File.separator;
    private final String filePathString = "src" + separator + "main" + separator + "resources" + separator + "data.txt";

    private ArrayList<String> details;
    private ArrayList<String> date;
    ArrayList<String> description;

    public void initialize() throws IOException
    {
        BufferedReader myReader = null;
        description = new ArrayList<>();
        details = new ArrayList<>();
        date = new ArrayList<>();

        Path filePath = Paths.get(filePathString);

        // Create the parent directories if they don't exist
        Files.createDirectories(filePath.getParent());

        // Create the file if it doesn't exist
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        try
        {
            String line;
            myReader = new BufferedReader(new FileReader(filePathString));

            while((line = myReader.readLine()) != null)
            {
                String[] data = line.split(",");
                description.add(data[0]);
                details.add(data[1]);
                date.add(data[2]);
            }

        }
        catch(FileNotFoundException e)
        {
            System.err.println("File not exist "+e.getMessage());
            throw e;
        }
        catch (Exception e)
        {
            System.err.println("Error "+e.getMessage());
        }
        finally
        {
            if(myReader != null)
            {
                try
                {
                    myReader.close();
                }
                catch(IOException e)
                {
                    System.err.println("Error closing BufferedReader: " + e.getMessage());
                }
                catch (Exception e)
                {
                    System.err.println("Error "+e.getMessage());
                }
            }
        }

        toDoListView.getItems().addAll(description);
        ContextMenu listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteItem();
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

        // Set the context menu for the list view
        toDoListView.setContextMenu(listContextMenu);
    }

    public void deleteItem() {
        int selectedIndex = toDoListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            String selectedItem = toDoListView.getItems().get(selectedIndex);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete item");
            alert.setHeaderText("Delete item: " + selectedItem);
            alert.setContentText("Are you sure you want to delete this item?");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Remove the item from the lists
                    description.remove(selectedIndex);
                    details.remove(selectedIndex);
                    date.remove(selectedIndex);

                    // Update the ListView
                    toDoListView.getItems().setAll(description);

                    // Update the data file
                    updateDataFile();

                    // Clear the details and deadlineLabel
                    itemDescriptionTextArea.clear();
                    deadlineLabel.setText("");
                }
            });
        }
    }

    private void updateDataFile()
    {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePathString)))
        {
            for (int i = 0; i < description.size(); i++) {
                writer.println(description.get(i) + "," + details.get(i) + "," + date.get(i));
            }
        } catch (IOException e) {
            System.err.println("Error updating data file: " + e.getMessage());
        }
    }

    public void OnNewClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/todolist/Add-item-view.fxml"));
        Parent root = loader.load();

        // Create a new stage
        Stage addStage = new Stage();
        addStage.setTitle("Add Item");
        addStage.setScene(new Scene(root));

        // Get the controller from the FXMLLoader
        AddItemController addItemController = loader.getController();

        // Show the new stage and wait for it to be closed
        addStage.showAndWait();

        // Call the new method in AddItemController to handle adding the item
        if (addItemController.isOkClicked()) {
            addItemController.handleAddItem();

            // Add the new item details to the lists
            description.add(addItemController.getNewItemDescription());
            details.add(addItemController.getNewItemDetails());
            date.add(addItemController.getNewItemDate());

            // Refresh the ListView
            toDoListView.getItems().setAll(description);
        }
    }


    public void OnExitClicked(ActionEvent actionEvent)
    {
        Platform.exit();
    }

    public void handleClickListView(MouseEvent mouseEvent)
    {
        int selectedIndex = toDoListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && selectedIndex < date.size() && selectedIndex < details.size())
        {
            String selectedDateString = date.get(selectedIndex);
            String detailsString = details.get(selectedIndex);

            try
            {
                // Assuming the date format is "yyyy-MM-dd", adjust it based on your actual format
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate selectedDate = LocalDate.parse(selectedDateString, inputFormatter);

                // Format the date in "dd MMMM yyyy" and set it to the label
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
                String formattedDate = selectedDate.format(outputFormatter);

                deadlineLabel.setText(formattedDate);
                itemDescriptionTextArea.setText(detailsString);
            }
            catch (Exception e)
            {
                // Handle parsing exception
                System.err.println("Error parsing date: " + e.getMessage());
                deadlineLabel.setText("Invalid date format");
            }
        }
        else
        {
            // Handling the case when no item is selected or the selected index is out of bounds
            deadlineLabel.setText("No date available");
        }
    }

}