package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.models.User;
import com.siliconsquad.siliconsquadmoviebooking.services.UserManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class RegisterController {

    private static final DateTimeFormatter DOB_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    @FXML
    private Button backbutton;

    @FXML
    private Button signupButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField dateOfBirthField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void initialize() {
        dateOfBirthField.textProperty().addListener(
                (observable, oldValue, newValue) -> {

                    String digits = newValue.replaceAll("[^0-9]", "");

                    if (digits.length() > 8) {
                        digits = digits.substring(0, 8);
                    }

                    StringBuilder formatted = new StringBuilder();

                    for (int i = 0; i < digits.length(); i++) {

                        if (i == 2 || i == 4) {
                            formatted.append("/");
                        }

                        formatted.append(digits.charAt(i));
                    }

                    String formattedText = formatted.toString();

                    if (!formattedText.equals(newValue)) {
                        dateOfBirthField.setText(formattedText);
                        dateOfBirthField.positionCaret(formattedText.length());
                    }
                }
        );
    }

    @FXML
    protected void backToMain() throws Exception {
        openPage("loginPage.fxml", backbutton);
    }

    @FXML
    protected void signupUser() throws Exception {

        String name = nameField.getText().trim();
        String username = usernameField.getText().trim();
        String dateOfBirth = dateOfBirthField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        messageLabel.setStyle("-fx-text-fill: #DD0808;");

        if (name.isEmpty()
                || username.isEmpty()
                || dateOfBirth.isEmpty()
                || password.isEmpty()
                || confirmPassword.isEmpty()) {

            messageLabel.setText("Please complete every field.");
            return;
        }

        if (!isValidDate(dateOfBirth)) {
            messageLabel.setText("Enter a valid date using MM/DD/YYYY.");
            dateOfBirthField.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setText("Passwords do not match.");
            confirmPasswordField.clear();
            confirmPasswordField.requestFocus();
            return;
        }

        if (password.length() < 6) {
            messageLabel.setText("Password must contain at least 6 characters.");
            passwordField.requestFocus();
            return;
        }

        if (UserManager.usernameExists(username)) {
            messageLabel.setText("That username already exists.");
            usernameField.requestFocus();
            return;
        }

        User user = new User(
                name,
                username,
                dateOfBirth,
                password
        );

        UserManager.saveUser(user);

        openPage("loginPage.fxml", signupButton);
    }

    private boolean isValidDate(String dateOfBirth) {
        try {
            LocalDate enteredDate =
                    LocalDate.parse(dateOfBirth, DOB_FORMATTER);

            LocalDate today = LocalDate.now();

            return !enteredDate.isAfter(today)
                    && enteredDate.getYear() >= 1900;

        } catch (DateTimeException e) {
            return false;
        }
    }

    private void openPage(String pageName, Button sourceButton)
            throws Exception {

        FXMLLoader loader = new FXMLLoader(
                BookingApplication.class.getResource(pageName)
        );

        Scene scene = new Scene(loader.load(), 1080, 800);

        Stage stage =
                (Stage) sourceButton.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
