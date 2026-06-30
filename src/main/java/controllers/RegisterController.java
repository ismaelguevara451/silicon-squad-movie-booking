package controllers;

import com.siliconsquad.siliconsquadmoviebooking.models.User;
import com.siliconsquad.siliconsquadmoviebooking.services.UserManager;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Button backbutton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    protected void backToMain() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        Stage stage = (Stage)backbutton.getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }

    @FXML
    protected void signupUser() throws Exception {

        // Create a new user using the information entered in the form
        User user = new User(
            nameField.getText(),
            usernameField.getText(),
            passwordField.getText()
        );

        // Save the user information to the text file
        UserManager manager = new UserManager();
        manager.saveUser(user);

        System.out.println("User registered successfully!");
    }
}
