package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MovieCardController {

    @FXML

    private Label cardLabel;

    public void setMovieName(String name){
        cardLabel.setText(name);
    }
}
