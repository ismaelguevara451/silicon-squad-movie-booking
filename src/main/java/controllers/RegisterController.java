package controllers;

import com.siliconsquad.siliconsquadmoviebooking.models.User;
import com.siliconsquad.siliconsquadmoviebooking.services.UserManager;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class RegisterController {

    // FXML components from register page
    @FXML
    private Button backbutton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private Button signupButton;

    // Methods down here
    @FXML
    protected void backToMain() throws Exception {
        //Create and load the fxmlLoader
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("loginPage.fxml"));

        //Create new scene
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        //Set the stage with current scene (window)
        Stage stage = (Stage)backbutton.getScene().getWindow();

        //Set the stage with the newly created scene
        stage.setScene(scene);

        //Display the new page
        stage.show();
    }

    @FXML
    protected void signupUser() throws Exception {

        // If one of the field is empty then show an error message
        if(nameField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()){
            messageLabel.setText("Please enter name, username and password.");
            return;
        }

        // Create a new user using the information entered in the form
        User user = new User(
            nameField.getText(),
            usernameField.getText(),
            passwordField.getText()
        );

        // Save the user information to the text file
        UserManager manager = new UserManager();
        manager.saveUser(user);

        //Create and load the fxmlLoader
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("loginPage.fxml"));

        //Create new scene
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        //Set the stage with current scene (window)
        Stage stage = (Stage)signupButton.getScene().getWindow();

        //Set the stage with the newly created scene
        stage.setScene(scene);

        //Display the new page
        stage.show();
    }
}
