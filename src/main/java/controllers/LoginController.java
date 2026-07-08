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
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("registerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void moveToHome() throws Exception {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        UserManager manager = new UserManager();

        if (manager.validateLogin(username, password)) {
            FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("mainPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password.");
            alert.showAndWait();

            passwordField.clear();
        }
    }
}
