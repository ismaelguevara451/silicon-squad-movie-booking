package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import com.siliconsquad.siliconsquadmoviebooking.services.MovieManager;

public class BookingController {

    @FXML
    private Button checkoutButton;

    @FXML
    private int seatQuantity;

    @FXML
    private Label priceLabel;

    @FXML
    private Label seatLabel;

    @FXML
    private Double ticketPrice = 9.98;

    @FXML
    private Double customerPrice;

    @FXML
    private boolean takenSeat; //pull from theater seat map

    @FXML
    private List<String> totalSelectedSeats = new ArrayList<>();


    @FXML
    private void handleSeatSelection(ActionEvent event){
        ToggleButton clickedToggleButton = (ToggleButton) event.getSource(); //gets clicked Toggle Button
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(); //used to format price
        String seatSelected = clickedToggleButton.getText(); //get selected seat
        String seatLabelText = "";


        if (clickedToggleButton.isSelected()) { //if seat is selected
            seatQuantity++; //update seat quantity
            customerPrice = ticketPrice * seatQuantity; //update customer price in FXML
            priceLabel.setText(currencyFormatter.format(customerPrice)); //update price label in FXML
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
        else{ //if seat is deselected
            seatQuantity--;
            customerPrice = ticketPrice * seatQuantity;
            priceLabel.setText(currencyFormatter.format(customerPrice));
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
    }

    @FXML
    private void checkTakenSeat(){
        //logic needed
    }

    

}
