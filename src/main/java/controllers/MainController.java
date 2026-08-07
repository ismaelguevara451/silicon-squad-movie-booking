package controllers;

import com.siliconsquad.siliconsquadmoviebooking.BookingApplication;
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import com.siliconsquad.siliconsquadmoviebooking.models.Showtime;
import com.siliconsquad.siliconsquadmoviebooking.services.MovieManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {

    @FXML
    private TilePane movieGrid;

    @FXML
    private VBox showtimesPanel;

    @FXML
    private TextField searchField;

    @FXML
    private Button logoutButton;

    private List<Movie> movies;
    private List<Showtime> showtimes;

    private StackPane selectedMovieCard;

    private static final Map<String, String> POSTERS =
            createPosterMap();

    @FXML
    public void initialize() throws IOException {

        movies = MovieManager.loadMovies();
        showtimes = MovieManager.loadShowtimes();

        MovieManager.assignShowtimes(movies, showtimes);

        addDefaultShowtimesToMovies();

        showMovies(movies);
        showShowtimePlaceholder();

        // Pressing Enter in the search field also performs a search.
        searchField.setOnAction(event -> searchMovies());
    }

    private static Map<String, String> createPosterMap() {

        Map<String, String> posters = new HashMap<>();

        posters.put("Matrix", "matrix.jpg");
        posters.put("Avengers", "avengers.jpg");
        posters.put("Inception", "inception.jpg");
        posters.put("The Matrix Reloaded", "matrix-reloaded.jpg");
        posters.put("Forrest Gump", "forrest-gump.jpg");
        posters.put("Parasite", "parasite.jpg");
        posters.put("Terminator", "terminator.jpg");
        posters.put("Gladiator", "gladiator.jpg");
        posters.put("Jurassic Park", "jurassic-park.jpg");
        posters.put("Taxi Driver", "taxi-driver.jpg");
        posters.put("Tarzan", "tarzan.jpg");
        posters.put("Rush Hour", "rush-hour.jpg");
        posters.put("Interstellar", "interstellar.jpg");
        posters.put("The Dark Knight", "dark-knight.jpg");
        posters.put("Titanic", "titanic.jpg");
        posters.put("Pulp Fiction", "pulp-fiction.jpg");
        posters.put("The Godfather", "godfather.jpg");
        posters.put("Goodfellas", "goodfellas.jpg");

        return posters;
    }

    private void addDefaultShowtimesToMovies() {

        LocalDateTime baseDate =
                LocalDateTime.of(2026, 9, 20, 11, 0);

        int showtimeId = 1000;

        for (int movieIndex = 0;
             movieIndex < movies.size();
             movieIndex++) {

            Movie movie = movies.get(movieIndex);

            if (!movie.getShowtimes().isEmpty()) {
                continue;
            }

            for (int roomNumber = 1;
                 roomNumber <= 4;
                 roomNumber++) {

                for (int timeIndex = 0;
                     timeIndex < 4;
                     timeIndex++) {

                    LocalDateTime time =
                            baseDate
                                    .plusMinutes(movieIndex * 10L)
                                    .plusHours(timeIndex * 3L)
                                    .plusMinutes((roomNumber - 1L) * 30L);

                    Showtime generatedShowtime =
                            new Showtime(
                                    showtimeId++,
                                    movie.getTitle(),
                                    MovieManager.createAuditorium(
                                            "Room " + roomNumber
                                    ),
                                    time,
                                    20.00
                            );

                    movie.setShowtimes(generatedShowtime);
                }
            }
        }
    }

    @FXML
    private void searchMovies() {

        String keyword = searchField.getText().trim();

        try {

            if (keyword.isEmpty()) {

                showMovies(movies);

            } else {

                List<Movie> searchResults =
                        MovieManager.searchMovies(
                                movies,
                                keyword
                        );

                showMovies(searchResults);
            }

            selectedMovieCard = null;
            showShowtimePlaceholder();

        } catch (IOException exception) {

            exception.printStackTrace();

            showError(
                    "Search Error",
                    "Unable to display the search results.",
                    exception
            );
        }
    }

    private void showMovies(List<Movie> movieList)
            throws IOException {

        movieGrid.getChildren().clear();

        for (Movie movie : movieList) {

            FXMLLoader loader =
                    new FXMLLoader(
                            BookingApplication.class.getResource(
                                    "movieCard.fxml"
                            )
                    );

            StackPane movieCard = loader.load();

            MovieCardController controller =
                    loader.getController();

            controller.setMovie(movie);

            movieCard.setOnMouseClicked(event -> {

                selectMovieCard(movieCard);
                showShowtimes(movie);
            });

            movieGrid.getChildren().add(movieCard);
        }
    }

    private void selectMovieCard(StackPane movieCard) {

        if (selectedMovieCard != null) {

            selectedMovieCard
                    .getStyleClass()
                    .remove("selected-movie-card");
        }

        selectedMovieCard = movieCard;

        if (!movieCard
                .getStyleClass()
                .contains("selected-movie-card")) {

            movieCard
                    .getStyleClass()
                    .add("selected-movie-card");
        }
    }

    private void showShowtimePlaceholder() {

        showtimesPanel.getChildren().clear();

        HBox header = createShowtimeHeader();

        VBox placeholder = new VBox(8);
        placeholder.setAlignment(Pos.CENTER);
        placeholder.getStyleClass().add("showtime-placeholder-box");

        Label messageTitle =
                new Label("Select a Movie");

        messageTitle.getStyleClass()
                .add("showtime-empty-title");

        Label message =
                new Label(
                        "Select a movie above to view available rooms and times."
                );

        message.getStyleClass()
                .add("showtime-empty-message");

        placeholder.getChildren().addAll(
                messageTitle,
                message
        );

        showtimesPanel.getChildren().addAll(
                header,
                placeholder
        );
    }

    private HBox createShowtimeHeader() {

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("showtime-section-header");

        Label calendarIcon = new Label("▦");
        calendarIcon.getStyleClass().add("showtime-header-icon");

        Label heading =
                new Label("AVAILABLE SHOWTIMES");

        heading.getStyleClass()
                .add("showtime-header-title");

        header.getChildren().addAll(
                calendarIcon,
                heading
        );

        return header;
    }

    private void showShowtimes(Movie movie) {

        showtimesPanel.getChildren().clear();

        HBox header = createShowtimeHeader();

        HBox content = new HBox(25);
        content.setAlignment(Pos.CENTER_LEFT);
        content.getStyleClass().add("showtime-content");

        VBox movieInformation =
                createMovieInformation(movie);

        Region divider = new Region();
        divider.getStyleClass()
                .add("showtime-divider");

        HBox roomColumns = new HBox(25);
        roomColumns.setAlignment(Pos.TOP_CENTER);
        roomColumns.getStyleClass()
                .add("room-columns");

        for (int roomNumber = 1;
             roomNumber <= 4;
             roomNumber++) {

            VBox roomColumn =
                    createRoomColumn(
                            movie,
                            "Room " + roomNumber
                    );

            roomColumns.getChildren()
                    .add(roomColumn);
        }

        content.getChildren().addAll(
                movieInformation,
                divider,
                roomColumns
        );

        Label footer =
                new Label(
                        "All showtimes are subject to availability"
                );

        footer.getStyleClass()
                .add("showtime-footer");

        showtimesPanel.getChildren().addAll(
                header,
                content,
                footer
        );
    }

    private VBox createMovieInformation(Movie movie) {

        VBox movieInfo = new VBox(7);
        movieInfo.setAlignment(Pos.CENTER_LEFT);
        movieInfo.getStyleClass().add("selected-movie-info");

        HBox movieDetails = new HBox(15);
        movieDetails.setAlignment(Pos.CENTER_LEFT);

        ImageView poster = new ImageView();
        poster.setFitWidth(82);
        poster.setFitHeight(115);
        poster.setPreserveRatio(false);
        poster.setSmooth(true);
        poster.getStyleClass().add("showtime-movie-poster");

        loadPoster(movie.getTitle(), poster);

        VBox textInformation = new VBox(7);
        textInformation.setAlignment(Pos.CENTER_LEFT);

        Label movieTitle =
                new Label(movie.getTitle());

        movieTitle.getStyleClass()
                .add("selected-showtime-movie-title");

        Label description =
                new Label(movie.getDescription());

        description.setWrapText(true);
        description.setMaxWidth(185);
        description.setMaxHeight(42);

        description.getStyleClass()
                .add("selected-showtime-description");

        Label instruction =
                new Label(
                        "◷  Select a time and room\nto continue"
                );

        instruction.getStyleClass()
                .add("showtime-instruction");

        textInformation.getChildren().addAll(
                movieTitle,
                description,
                instruction
        );

        movieDetails.getChildren().addAll(
                poster,
                textInformation
        );

        movieInfo.getChildren().add(movieDetails);

        return movieInfo;
    }

    private VBox createRoomColumn(
            Movie movie,
            String roomName
    ) {

        VBox roomColumn = new VBox(7);
        roomColumn.setAlignment(Pos.TOP_CENTER);
        roomColumn.getStyleClass().add("room-column");

        HBox roomHeading = new HBox(7);
        roomHeading.setAlignment(Pos.CENTER);

        Label roomIcon = new Label("▣");
        roomIcon.getStyleClass().add("room-icon");

        Label roomTitle =
                new Label(roomName.toUpperCase());

        roomTitle.getStyleClass()
                .add("room-title");

        roomHeading.getChildren().addAll(
                roomIcon,
                roomTitle
        );

        roomColumn.getChildren().add(roomHeading);

        DateTimeFormatter timeFormatter =
                DateTimeFormatter.ofPattern("h:mm a");

        boolean foundRoomShowtime = false;

        for (Showtime showtime : movie.getShowtimes()) {

            if (showtime.getAuditorium() == null
                    || !showtime.getAuditorium()
                    .getName()
                    .equalsIgnoreCase(roomName)) {

                continue;
            }

            foundRoomShowtime = true;

            Button timeButton =
                    new Button(
                            showtime
                                    .getStartTime()
                                    .format(timeFormatter)
                    );

            timeButton.getStyleClass()
                    .add("room-time-button");

            timeButton.setMaxWidth(Double.MAX_VALUE);

            timeButton.setOnAction(event ->
                    openBookingPage(
                            showtime,
                            timeButton
                    )
            );

            roomColumn.getChildren()
                    .add(timeButton);
        }

        if (!foundRoomShowtime) {

            Label unavailable =
                    new Label("No times");

            unavailable.getStyleClass()
                    .add("no-room-times");

            roomColumn.getChildren()
                    .add(unavailable);
        }

        return roomColumn;
    }

    private void loadPoster(
            String movieTitle,
            ImageView poster
    ) {

        String posterFile =
                POSTERS.get(movieTitle);

        URL posterUrl = null;

        if (posterFile != null) {

            posterUrl =
                    MainController.class.getResource(
                            "/com/siliconsquad/"
                                    + "siliconsquadmoviebooking/"
                                    + "posters/"
                                    + posterFile
                    );
        }

        if (posterUrl == null) {

            posterUrl =
                    MainController.class.getResource(
                            "/com/siliconsquad/"
                                    + "siliconsquadmoviebooking/"
                                    + "icon.png"
                    );
        }

        if (posterUrl != null) {

            poster.setImage(
                    new Image(
                            posterUrl.toExternalForm(),
                            false
                    )
            );
        }
    }

    private void openBookingPage(
            Showtime selectedShowtime,
            Button sourceButton
    ) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            BookingApplication.class.getResource(
                                    "bookingPage.fxml"
                            )
                    );

            Parent root = loader.load();

            BookingController controller =
                    loader.getController();

            controller.initData(selectedShowtime);

            Stage stage =
                    (Stage) sourceButton
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(root, 1080, 800)
            );

            stage.setTitle("Select Your Seats");
            stage.centerOnScreen();
            stage.show();

        } catch (Exception exception) {

            exception.printStackTrace();

            showError(
                    "Booking Page Error",
                    "The seat-selection page could not be opened.",
                    exception
            );
        }
    }

    @FXML
    private void logoutUser() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            BookingApplication.class.getResource(
                                    "loginPage.fxml"
                            )
                    );

            Parent loginPage = loader.load();

            Stage stage =
                    (Stage) logoutButton
                            .getScene()
                            .getWindow();

            stage.setScene(
                    new Scene(loginPage, 1080, 800)
            );

            stage.setTitle("Movie Ticket Booking");
            stage.centerOnScreen();
            stage.show();

        } catch (IOException exception) {

            exception.printStackTrace();

            showError(
                    "Logout Error",
                    "Unable to return to the login page.",
                    exception
            );
        }
    }

    private void showError(
            String title,
            String header,
            Exception exception
    ) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(header);

        alert.setContentText(
                exception.getMessage() == null
                        ? exception.toString()
                        : exception.getMessage()
        );

        alert.showAndWait();
    }
}