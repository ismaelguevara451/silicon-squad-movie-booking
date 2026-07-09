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

    // FXML components from login page
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

    // Methods down here
    @FXML
    protected void moveToRegister() throws Exception {

        //Create FXMLLoader and load the page
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("registerPage.fxml"));

        //We set the scene or page with the loaded FXMLLoader
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        //We choose the current stage from the registerbutton (can be taken from other parts of the stage)
        Stage stage = (Stage) registerButton.getScene().getWindow();

        //We replace the stage with our current scene or page
        stage.setScene(scene);

        //Display the scene or page
        stage.show();
    }

    // Just testing for now
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
