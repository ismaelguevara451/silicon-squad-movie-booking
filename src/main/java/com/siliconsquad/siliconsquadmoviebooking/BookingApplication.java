package com.siliconsquad.siliconsquadmoviebooking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BookingApplication extends Application {
    @Override
    // Stage stage = new Stage();
    public void start(Stage stage) throws IOException {

        // Create FXMLLoader and the scene
        FXMLLoader fxmlLoader = new FXMLLoader(BookingApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 800);

        //Create Application icon and set the icon
        Image icon = new Image(BookingApplication.class.getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);

        //Set the application title and scene
        stage.setTitle("Movie Ticket Booking");
        stage.setScene(scene);

        //Finally show the window
        stage.show();
    }
}
