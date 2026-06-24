package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button forgotPasswordButton;


    @FXML
    protected void moveToRegister() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("registerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage) registerButton.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    // Just testing for now
    @FXML
    protected void moveToHome() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("mainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }
}
