package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private Button backbutton;
    private Button loginButton;
    private Button forgotPasswordButton;


    @FXML
    protected void backToMain() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("mainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage) backbutton.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }
}
