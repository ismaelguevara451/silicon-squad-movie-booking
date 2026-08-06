package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.services.UserManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private Button registerButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button forgotPasswordButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    protected void moveToRegister() throws Exception {
        openPage("registerPage.fxml", registerButton);
    }

    @FXML
    protected void moveToForgotPassword() throws Exception {
        openPage("forgotPasswordPage.fxml", forgotPasswordButton);
    }

    @FXML
    protected void moveToHome() throws Exception {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter your username and password.");
            return;
        }


        if (UserManager.validateLogin(username, password)) {
            openPage("mainPage.fxml", loginButton);
        } else {
            showError("Invalid username or password.");
            passwordField.clear();
            passwordField.requestFocus();
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

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
