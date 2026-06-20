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
    private Label welcomeText;

    @FXML
    private Button registerbutton;


    // Methods

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Still work in progress!");
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
