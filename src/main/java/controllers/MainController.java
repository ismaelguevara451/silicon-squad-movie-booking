package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MainController {

    @FXML
    private Button registerbutton;

    @FXML
    private Button loginButton;

    // Methods

    @FXML
    protected void loginButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage)loginButton.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void registerButtonClick() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("registerPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage)registerbutton.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void exitButtonClick() {
        Platform.exit();
    }

}
