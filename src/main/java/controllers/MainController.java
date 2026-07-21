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
import com.siliconsquad.siliconsquadmoviebooking.services.MovieManager;


public class MainController {

    @FXML
    private TilePane movieGrid;

    @FXML
    private TextField searchField;

    @FXML
    private Button logoutButton;

    private List<Movie> movies;

    private final MovieManager movieManager = new MovieManager();

    @FXML
        public void initialize() throws IOException {
        movies = movieManager.loadMovies();
        showMovies(movies);
    }

    @FXML
    private void searchMovies() {
        String keyword = searchField.getText().trim();

        try {
            if (keyword.isEmpty()) {
                showMovies(movies);
            } else {
                List<Movie> results =
                    movieManager.searchMovies(movies, keyword);

                showMovies(results);
            }
        } catch (IOException exception) {
            exception.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setHeaderText(null);
            alert.setContentText("Unable to display the search results.");
            alert.showAndWait();
        }
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
