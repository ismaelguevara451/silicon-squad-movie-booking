package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.util.List;




public class MainController {

    // FXML components from register page
    @FXML
    private TilePane movieGrid;

    @FXML
    private TextField searchField;

    // Create list of movie objects
    private List<Movie> movies = List.of(
            new Movie("Matrix"),
            new Movie("Avengers"),
            new Movie("Inception"),
            new Movie("Matrix"),
            new Movie("Forrest Gump"),
            new Movie("Parasite"),
            new Movie("Terminator"),
            new Movie("Gladiator"),
            new Movie("Jurassic Park"),
            new Movie("Taxi Driver"),
            new Movie("Tarzan"),
            new Movie("Rush Hour"),
            new Movie("Test 1"),
            new Movie("Test 2"),
            new Movie("Test 3"),
            new Movie("Test 4"),
            new Movie("Test 5"),
            new Movie("Test 6")
    );

    // Initialize upon loading the page
    public void initialize() throws IOException{
        showMovies(movies);
    }

    // Method for showing list of movie objects in a tilepane
    private void showMovies(List<Movie> movieList) throws IOException {

        //Remove all movie cards from the tilepane
        movieGrid.getChildren().clear();

        //Loop through every movie object in the list
        for (Movie movie: movieList) {

            //Load the movie card fxml file into new FXMLloader
            FXMLLoader loader = new FXMLLoader(
                    BookingApplication.class.getResource("movieCard.fxml"));

            //This is to create movie card as an anchorpane
            AnchorPane card = loader.load();

            //We use the controller that is associated with movie card fxml file
            MovieCardController controller = loader.getController();

            //Set the name of the movie card
            controller.setMovieName(movie.getTitle());

            //Add the card into the tile pane
            movieGrid.getChildren().add(card);

        }
    }
}