package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.services.UserManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class ForgotPasswordController {

    private static final DateTimeFormatter DOB_FORMATTER =
            DateTimeFormatter.ofPattern("MM/dd/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    @FXML
    private TextField usernameField;

    @FXML
    private TextField dateOfBirthField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private VBox resetPasswordSection;

    @FXML
    private Label messageLabel;

    @FXML
    private Button verifyButton;

    @FXML
    private Button resetPasswordButton;

    @FXML
    private Button backButton;

    private String verifiedUsername;

    @FXML
    private void initialize() {

        resetPasswordSection.setVisible(false);
        resetPasswordSection.setManaged(false);

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

                    String result = formatted.toString();

                    if (!result.equals(newValue)) {
                        dateOfBirthField.setText(result);
                        dateOfBirthField.positionCaret(result.length());
                    }
                }
        );
    }

    @FXML
    protected void verifyIdentity() {

        String username = usernameField.getText().trim();
        String dateOfBirth = dateOfBirthField.getText().trim();

        showError("");

        if (username.isEmpty() || dateOfBirth.isEmpty()) {
            showError("Enter your username and date of birth.");
            return;
        }

        if (!isValidDate(dateOfBirth)) {
            showError("Enter a valid date using MM/DD/YYYY.");
            dateOfBirthField.requestFocus();
            return;
        }

        UserManager manager = new UserManager();

        if (!manager.verifyUser(username, dateOfBirth)) {
            showError("Username and date of birth do not match.");
            return;
        }

        verifiedUsername = username;

        messageLabel.setStyle("-fx-text-fill: #1D9A4A;");
        messageLabel.setText("Identity successfully verified.");

        usernameField.setDisable(true);
        dateOfBirthField.setDisable(true);
        verifyButton.setDisable(true);

        resetPasswordSection.setManaged(true);
        resetPasswordSection.setVisible(true);

        newPasswordField.requestFocus();
    }

    @FXML
    protected void resetPassword() {

        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        showError("");

        if (verifiedUsername == null) {
            showError("Verify your identity first.");
            return;
        }

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showError("Enter and confirm your new password.");
            return;
        }

        if (newPassword.length() < 6) {
            showError("Password must contain at least 6 characters.");
            newPasswordField.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showError("Passwords do not match.");
            confirmPasswordField.clear();
            confirmPasswordField.requestFocus();
            return;
        }

        UserManager manager = new UserManager();

        boolean updated =
                manager.updatePassword(verifiedUsername, newPassword);

        if (!updated) {
            showError("Password could not be updated.");
            return;
        }

        try {
            openLoginPage();
        } catch (Exception e) {
            showError("Password changed, but login page could not open.");
        }
    }

    @FXML
    protected void backToLogin() throws Exception {
        openLoginPage();
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

    private void openLoginPage() throws Exception {

        FXMLLoader loader = new FXMLLoader(
                BookingApplication.class.getResource("loginPage.fxml")
        );

        Scene scene = new Scene(loader.load(), 1080, 800);

        Stage stage =
                (Stage) backButton.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: #DD0808;");
        messageLabel.setText(message);
    }
}
