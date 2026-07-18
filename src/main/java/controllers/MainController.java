package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class MainController {

    @FXML
    private TilePane movieGrid;

    @FXML
    private TextField searchField;

    @FXML
    private Button logoutButton;

    private final List<Movie> movies = List.of(
            new Movie(
                    "Matrix",
                    "A computer hacker discovers that the world he knows is a simulated reality controlled by intelligent machines."
            ),
            new Movie(
                    "Avengers",
                    "Earth's greatest heroes unite to stop a powerful enemy whose plans threaten the future of the entire planet."
            ),
            new Movie(
                    "Inception",
                    "A skilled thief enters people's dreams to steal secrets and is offered a chance to erase his troubled past."
            ),
            new Movie(
                    "The Matrix Reloaded",
                    "Neo and his allies continue their fight as the machine army moves closer to humanity's final refuge."
            ),
            new Movie(
                    "Forrest Gump",
                    "A kindhearted man experiences several major moments in history while remaining devoted to the woman he loves."
            ),
            new Movie(
                    "Parasite",
                    "A struggling family slowly enters the lives of a wealthy household, leading to unexpected and dangerous consequences."
            ),
            new Movie(
                    "Terminator",
                    "A relentless machine is sent from the future to eliminate the woman whose son will lead humanity's resistance."
            ),
            new Movie(
                    "Gladiator",
                    "A betrayed Roman general becomes a gladiator and fights to avenge his family and restore honor to Rome."
            ),
            new Movie(
                    "Jurassic Park",
                    "Scientists bring dinosaurs back to life, but a failure in the park's security system creates a fight for survival."
            ),
            new Movie(
                    "Taxi Driver",
                    "A lonely New York taxi driver becomes increasingly disturbed by the violence and corruption surrounding him."
            ),
            new Movie(
                    "Tarzan",
                    "A man raised in the jungle must choose between the family that raised him and the human world he has discovered."
            ),
            new Movie(
                    "Rush Hour",
                    "A Hong Kong inspector and a fast-talking Los Angeles detective work together to solve a kidnapping."
            ),
            new Movie(
                    "Interstellar",
                    "A team of explorers travels through a wormhole in space to search for a new home for humanity."
            ),
            new Movie(
                    "The Dark Knight",
                    "Batman faces a criminal mastermind whose campaign of chaos pushes Gotham City and its heroes to their limits."
            ),
            new Movie(
                    "Titanic",
                    "Two passengers from different social classes fall in love aboard the famously doomed ocean liner."
            ),
            new Movie(
                    "Pulp Fiction",
                    "Several interconnected stories of crime, loyalty, and redemption unfold across the streets of Los Angeles."
            ),
            new Movie(
                    "The Godfather",
                    "The youngest son of a powerful crime family is gradually drawn into the family's dangerous business."
            ),
            new Movie(
                    "Goodfellas",
                    "A young man enters organized crime and experiences its wealth, loyalty, violence, and eventual collapse."
            )
    );

    @FXML
    public void initialize() throws IOException {
        showMovies(movies);
    }

    private void showMovies(List<Movie> movieList) throws IOException {
        movieGrid.getChildren().clear();

        for (Movie movie : movieList) {
            FXMLLoader loader = new FXMLLoader(
                    BookingApplication.class.getResource("movieCard.fxml")
            );

            StackPane card = loader.load();

            MovieCardController controller = loader.getController();
            controller.setMovie(movie);

            movieGrid.getChildren().add(card);
        }
    }

    @FXML
    private void logoutUser() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    BookingApplication.class.getResource("loginPage.fxml")
            );

            Parent loginPage = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(loginPage));
            stage.setTitle("Movie Ticket Booking");
            stage.show();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Logout Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully logged out.");
            alert.showAndWait();

        } catch (IOException exception) {
            exception.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Logout Error");
            alert.setHeaderText(null);
            alert.setContentText(
                    "Unable to return to the login page."
            );
            alert.showAndWait();
        }
    }
}
