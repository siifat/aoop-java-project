package lms.controller.admin;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Calendar extends Application {

    private VBox calendarPane;
    private LocalDate currentDate;
    private Label monthYearLabel;

    @Override
    public void start(Stage primaryStage) {
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
        primaryStage.setTitle("Calender");
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

    public static void main(String[] args) {
        launch(args);
    }
}