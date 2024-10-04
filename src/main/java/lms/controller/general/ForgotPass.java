package lms.controller.general;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXRadioButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lms.util.ChangeScene;
import lms.util.Scenes;
import lms.util.UserAttribute;
import lms.util.UserService;

import static lms.util.EmailService.generateRandomCode;
import static lms.util.EmailService.sendEmail;
import static lms.util.UserService.*;

public class ForgotPass {

    public MFXTextField emailField;
    public MFXButton nextButton;
    public MFXRadioButton rbStudent;
    public MFXRadioButton rbTeacher;
    public MFXTextField securityCodeField;
    public MFXButton resetButton;
    public Pane sendEmailPane;
    public Pane enterCodePane;
    public Pane updatePassPane;
    public Label countdownLabel;
    public Label emailFoundLabel;
    public Hyperlink resendEmailLink;
    public MFXPasswordField newPassField;
    public MFXPasswordField confirmNewPassField;

    private String sentCode;
    private String recipientEmail;

    public void nextClicked() {

        recipientEmail = emailField.getText();
        System.out.println(recipientEmail);

        // check if email is valid
        if(!isValidEmail(recipientEmail)){
            System.out.println("Invalid Email");
            return;
        }

        // check if the user exists
        if(!userExists(recipientEmail, UserAttribute.EMAIL, rbStudent.isSelected())){
            System.out.println("No such user with this email");
            return;
        }

        String messageBody = getSecurityMailBody(rbStudent.isSelected());

        // Send the email
        new Thread(() -> {
            if(sendEmail(recipientEmail, "Security Code" ,messageBody)){
                System.out.println("Email send successful");

                // Start countdown after sending email
                Platform.runLater(()-> startCountdown(Duration.seconds(60)));

            } else{
                System.out.println("Error");
            }
        }).start();

        emailFoundLabel.setText(recipientEmail);
        sendEmailPane.setVisible(false);
        enterCodePane.setVisible(true);
    }

    private String getSecurityMailBody(boolean isStudent) {

        String table = isStudent ? TABLE_STUDENTS : TABLE_TEACHERS;
        String username = UserService.get(emailField.getText(), UserAttribute.EMAIL, table, UserService.COLUMN_NAME);
        System.out.println("username from GetSecMethode: " + username);

        String securityCode = String.valueOf(generateRandomCode());
        sentCode = securityCode;

        StringBuilder builder = new StringBuilder();
        builder.append("Hi! ").append(username);
        builder.append("\n\nA password reset request was made to your Advanced LMS account. ");
        builder.append("If it was you please enter the following code in the application :\n\n");
        builder.append(securityCode);
        builder.append("\nIf you didn't request this change, you can disregard this email. We have not yet reset your password.\n\n");
        builder.append("Thanks for using our app!");

        String messageBody = builder.toString();
        return messageBody;
    }

    private boolean isValidEmail(String email) {

        return (!email.isBlank() && email.contains("@") && email.contains("."));
    }

    private void startCountdown(Duration duration) {
        resendEmailLink.setVisible(false);
        final int totalSeconds = (int) duration.toSeconds();
        System.out.println("total sec: " + totalSeconds);
        final int[] minutes = {totalSeconds / 60};
        final int[] seconds = {totalSeconds % 60};
        countdownLabel.setText(String.format("Time left: %02d:%02d", minutes[0], seconds[0]));
        countdownLabel.setVisible(true);

        // External counter to track elapsed time
        final int[] elapsedSeconds = {0};

        // Declare the countdown variable
        Timeline countdown = new Timeline();

        // Initialize the Timeline with a KeyFrame that runs every second
        countdown.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {

            // Increment the counter each second
            elapsedSeconds[0]++;

            // Calculate remaining time
            int remainingSeconds = totalSeconds - elapsedSeconds[0];
            minutes[0] = remainingSeconds / 60;
            seconds[0] = remainingSeconds % 60;
            System.out.println("min: " + minutes[0]);
            System.out.println("sec: " + seconds[0]);

            // Update the label with the remaining time
            countdownLabel.setText(String.format("Time left: %02d:%02d", minutes[0], seconds[0]));

            // When time runs out
            if (remainingSeconds <= 0) {
                sentCode = null;
                countdown.stop();
                countdownLabel.setText("Security code expired. Please request a new one.");
                resendEmailLink.setVisible(true);
                System.out.println("Security code expired. Please request a new one.");
            }
        }));

        // Repeat the KeyFrame for each second in the countdown
        countdown.setCycleCount(totalSeconds);
        countdown.play();
    }


    public void resetClicked() {
        if(!securityCodeField.getText().equals(sentCode)){
            System.out.println("Security code didn't match");
            return;
        }

        System.out.println("Successful");
        enterCodePane.setVisible(false);
        updatePassPane.setVisible(true);
    }

    public void resendEmail() {
        countdownLabel.setVisible(false);
        resendEmailLink.setVisible(false);
        String messageBody = getSecurityMailBody(rbStudent.isSelected());

        // Send the email
        new Thread(() -> {
            if(sendEmail(recipientEmail, "Security Code", messageBody)){
                System.out.println("Email send successful");

                // Start countdown after sending email
                Platform.runLater(()-> startCountdown(Duration.seconds(60)));

            } else{
                System.out.println("Error");
            }
        }).start();
    }

    // Stage 3
    public void updatePassClicked() {

    }


    public void signInClicked(MouseEvent mouseEvent) {
        ChangeScene.change(Scenes.LOGIN, mouseEvent);
    }

    public void createAccClicked(MouseEvent mouseEvent) {
        ChangeScene.change(Scenes.CREATE_ACC, mouseEvent);
    }
}
