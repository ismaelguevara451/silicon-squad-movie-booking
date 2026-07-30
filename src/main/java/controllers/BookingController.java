package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import com.siliconsquad.siliconsquadmoviebooking.models.Showtime;
import com.siliconsquad.siliconsquadmoviebooking.models.Auditorium;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BookingController {

    @FXML
    private GridPane frontSectionGridPane;
    @FXML
    private GridPane backSectionGridPane;
    @FXML
    private int rows = 0;
    @FXML
    private int seatsPerRow = 0;
    @FXML
    private Label movieLabel;
    @FXML
    private Label theaterLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private Label timeLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button checkoutButton;
    @FXML
    private int seatQuantity;
    @FXML
    private Label priceLabel;
    @FXML
    private Label seatLabel;
    @FXML
    private Double ticketPrice;
    @FXML
    private Double customerTotal;
    @FXML
    private boolean takenSeat; //pull from theater seat map
    @FXML
    private List<String> totalSelectedSeats = new ArrayList<>();

    @FXML
    public void initData(Showtime showtimeSelected) { //set values
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        movieLabel.setText(String.valueOf(showtimeSelected.getShowtimeMovie()));
        theaterLabel.setText(String.valueOf(showtimeSelected.getFormattedStartTime()));
        ticketPrice = showtimeSelected.getTicketPrice();
        rows = showtimeSelected.getAuditorium().getNumberOfRows();
        seatsPerRow = showtimeSelected.getAuditorium().getSeatsPerRow();
        setTilePanes();
    }

    private void setTilePanes() {
        int asciiConverter = 65;

        String seatNumber = "";
        for (int i = 0; i < rows; i++) {
            int asciiCode = asciiConverter + i;
            for (int j = 0; j < seatsPerRow; j++) {
                seatNumber = Character.toString((char) asciiCode);
                seatNumber = (seatNumber + String.valueOf(j + 1));
                Tooltip tooltip = new Tooltip(seatNumber);
                tooltip.setShowDelay(Duration.ZERO);
                ToggleButton tb = new ToggleButton(); //create Toggle button
                tb.setUserData(seatNumber); //assign seat number
                tb.getStyleClass().add("seat-button"); //add CSS class
                tb.setOnAction(this::handleSeatSelection); //call method
                tb.setTooltip(tooltip);
                if(i < 3) {
                    frontSectionGridPane.add(tb, j, i);
                }
                else{
                    backSectionGridPane.add(tb, j, i);
                }
            }
        }
    }

    @FXML
    private void handleSeatSelection(ActionEvent event){
        ToggleButton clickedToggleButton = (ToggleButton) event.getSource(); //gets clicked Toggle Button
        String seatSelected = clickedToggleButton.getUserData().toString(); //get selected seat
        String seatLabelText = "";

        if (clickedToggleButton.isSelected()) { //if seat is selected
            seatSelected(seatSelected, seatLabelText);
        }
        else{ //if seat is deselected
            seatDeselected(seatSelected, seatLabelText);
        }
    }

    @FXML
    private void seatSelected(String seatSelected, String seatLabelText){
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(); //used to format price
        seatQuantity++; //update seat quantity
        customerTotal = ticketPrice * seatQuantity; //update customer price in FXML
        priceLabel.setText(currencyFormatter.format(customerTotal)); //update price label in FXML
        totalSelectedSeats.add(seatSelected); //add current selection to selected seats list

        if(totalSelectedSeats.size() == 1) {//if only one item in list, print recently added item
            seatLabelText = seatSelected;
            seatLabel.setText(seatLabelText);
        }
        else {
            int count = 0; //used to check for initial seat
            for(String seat : totalSelectedSeats) {
                if (count == 0) {
                    seatLabelText = seat;
                    count++;
                }
                else {
                    seatLabelText = seatLabelText + ", " + seat;
                }
            }
        }
        seatLabel.setText(seatLabelText);
    }

    @FXML
    private void seatDeselected(String seatSelected, String seatLabelText){
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(); //used to format price
        seatQuantity--;
        customerTotal = ticketPrice * seatQuantity;
        priceLabel.setText(currencyFormatter.format(customerTotal));
        totalSelectedSeats.remove(seatSelected);
        if(totalSelectedSeats.isEmpty()) { //if only one item in list, print recently added item
            seatLabel.setText("");
        }
        else if(totalSelectedSeats.size() == 1){
            seatLabel.setText(seatSelected);
        }
        else {
            int count = 0; //used to check for initial seat
            for(String seat : totalSelectedSeats) {
                if (count == 0) {
                    seatLabelText = seat;
                    count++;
                }
                else {
                    seatLabelText = seatLabelText + ", " + seat;
                }
            }
        }
        seatLabel.setText(seatLabelText);
    }

    private void openPage(String pageName, Button sourceButton)
            throws Exception {

        FXMLLoader loader = new FXMLLoader(
                BookingApplication.class.getResource(pageName)
        );

        Scene scene = new Scene(loader.load(), 1080, 800);

        Stage stage =
                (Stage) sourceButton.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void backToMain() throws Exception {
        openPage("mainPage.fxml", backButton);
    }

    @FXML
    private void checkTakenSeat(){
        //logic needed
    }


}
