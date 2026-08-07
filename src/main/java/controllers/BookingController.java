package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.models.Showtime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BookingController {

    @FXML
    private GridPane frontSectionGridPane;

    @FXML
    private GridPane backSectionGridPane;

    @FXML
    private Label movieLabel;

    @FXML
    private Label theaterLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label seatLabel;

    @FXML
    private Label seatCountLabel;

    @FXML
    private Button backButton;

    @FXML
    private Button checkoutButton;

    private int rows;
    private int seatsPerRow;
    private int seatQuantity;

    private double ticketPrice;
    private double customerTotal;

    private final List<String> totalSelectedSeats = new ArrayList<>();

    @FXML
    private void initialize() {
        seatLabel.setText("No seats selected");
        priceLabel.setText("$0.00");
        seatCountLabel.setText("0 seats");
    }

    public void initData(Showtime showtimeSelected) {

        movieLabel.setText(showtimeSelected.getShowtimeMovie());
        theaterLabel.setText(showtimeSelected.getFormattedStartTime());

        ticketPrice = showtimeSelected.getTicketPrice();
        rows = showtimeSelected.getAuditorium().getNumberOfRows();
        seatsPerRow = showtimeSelected.getAuditorium().getSeatsPerRow();

        frontSectionGridPane.getChildren().clear();
        backSectionGridPane.getChildren().clear();

        createSeatLayout();
    }

    private void createSeatLayout() {

        createSeatNumberHeader();

        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {

            String rowLetter =
                    Character.toString((char) ('A' + rowIndex));

            GridPane targetGrid =
                    rowIndex < 3
                            ? frontSectionGridPane
                            : backSectionGridPane;

            int targetRow =
                    rowIndex < 3
                            ? rowIndex + 1
                            : rowIndex - 3;

            Label rowLabel = new Label(rowLetter);
            rowLabel.getStyleClass().add("row-letter");
            rowLabel.setAlignment(Pos.CENTER);

            targetGrid.add(rowLabel, 0, targetRow);

            for (int seatIndex = 1;
                 seatIndex <= seatsPerRow;
                 seatIndex++) {

                String seatNumber = rowLetter + seatIndex;

                ToggleButton seatButton = createSeatButton(seatNumber);

                targetGrid.add(
                        seatButton,
                        seatIndex,
                        targetRow
                );
            }
        }
    }

    private void createSeatNumberHeader() {

        Label emptyCorner = new Label("");
        emptyCorner.getStyleClass().add("seat-number-header");

        frontSectionGridPane.add(emptyCorner, 0, 0);

        for (int seatIndex = 1;
             seatIndex <= seatsPerRow;
             seatIndex++) {

            Label numberLabel =
                    new Label(String.valueOf(seatIndex));

            numberLabel.getStyleClass().add("seat-number-header");
            numberLabel.setAlignment(Pos.CENTER);

            frontSectionGridPane.add(
                    numberLabel,
                    seatIndex,
                    0
            );
        }
    }

    private ToggleButton createSeatButton(String seatNumber) {

        ToggleButton seatButton = new ToggleButton();

        seatButton.setUserData(seatNumber);
        seatButton.getStyleClass().add("seat-button");

        Tooltip tooltip = new Tooltip("Seat " + seatNumber);
        tooltip.setShowDelay(Duration.ZERO);

        seatButton.setTooltip(tooltip);
        seatButton.setOnAction(this::handleSeatSelection);

        return seatButton;
    }

    @FXML
    private void handleSeatSelection(ActionEvent event) {

        ToggleButton selectedButton =
                (ToggleButton) event.getSource();

        String selectedSeat =
                selectedButton.getUserData().toString();

        if (selectedButton.isSelected()) {
            addSeat(selectedSeat);
        } else {
            removeSeat(selectedSeat);
        }

        updateCheckoutSummary();
    }

    private void addSeat(String selectedSeat) {

        if (!totalSelectedSeats.contains(selectedSeat)) {
            totalSelectedSeats.add(selectedSeat);
        }
    }

    private void removeSeat(String selectedSeat) {
        totalSelectedSeats.remove(selectedSeat);
    }

    private void updateCheckoutSummary() {

        seatQuantity = totalSelectedSeats.size();
        customerTotal = ticketPrice * seatQuantity;

        NumberFormat currencyFormatter =
                NumberFormat.getCurrencyInstance();

        priceLabel.setText(
                currencyFormatter.format(customerTotal)
        );

        seatCountLabel.setText(
                seatQuantity == 1
                        ? "1 seat"
                        : seatQuantity + " seats"
        );

        if (totalSelectedSeats.isEmpty()) {
            seatLabel.setText("No seats selected");
        } else {
            seatLabel.setText(
                    String.join(", ", totalSelectedSeats)
            );
        }
    }

    @FXML
    private void reviewAndPay() {

        if (totalSelectedSeats.isEmpty()) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setTitle("No Seats Selected");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Please select at least one seat before continuing."
            );

            alert.showAndWait();
            return;
        }

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Booking Summary");
        alert.setHeaderText("Your selected seats are ready.");

        alert.setContentText(
                "Seats: "
                        + String.join(", ", totalSelectedSeats)
                        + "\nTotal: "
                        + priceLabel.getText()
        );

        alert.showAndWait();
    }

    private void openPage(
            String pageName,
            Button sourceButton
    ) throws Exception {

        FXMLLoader loader =
                new FXMLLoader(
                        BookingApplication.class.getResource(pageName)
                );

        Scene scene =
                new Scene(loader.load(), 1080, 800);

        Stage stage =
                (Stage) sourceButton
                        .getScene()
                        .getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void backToMain() throws Exception {
        openPage("mainPage.fxml", backButton);
    }
}
