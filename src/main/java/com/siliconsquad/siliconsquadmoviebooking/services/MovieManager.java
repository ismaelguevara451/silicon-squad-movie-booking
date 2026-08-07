package com.siliconsquad.siliconsquadmoviebooking.services;

// Import the Auditorium model used to create theater rooms.
import com.siliconsquad.siliconsquadmoviebooking.models.Auditorium;

// Import the Movie model used to store movie information.
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;

// Import the Showtime model used to store showing information.
import com.siliconsquad.siliconsquadmoviebooking.models.Showtime;

// Import classes used to read files.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

// Import LocalDateTime for storing showtime dates and times.
import java.time.LocalDateTime;

// Import classes used to create and manage lists.
import java.util.ArrayList;
import java.util.List;

/**
 * Manages movie, showtime, and auditorium information.
 * This class provides methods for loading movies and showtimes from files,
 * creating auditoriums, assigning showtimes to movies, and searching movies.
 * @author I. Regalado
 * @since July 21, 2026
 */
public class MovieManager {

    /**
     * Loads movie information from the movies.txt file.
     * Each line must contain a movie title and description separated
     * by a comma.
     *
     * @return a list containing all movies loaded from the file
     * @throws IOException if movies.txt cannot be found or read
     */
    public static List<Movie> loadMovies() throws IOException {

        // Create an empty list that will store the movies.
        List<Movie> movies = new ArrayList<>();

        // Locate and open the movies.txt file from the resources folder.
        InputStream inputStream =
                MovieManager.class.getClassLoader().getResourceAsStream("movies.txt");

        // Check whether the movies.txt file was found.
        if (inputStream == null) {

            // Stop the method and report that the file could not be found.
            throw new IOException("movies.txt not found.");
        }

        // Create a reader that can read text from the input stream.
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(inputStream)
                );

        // Store each line that is read from the file.
        String line;

        // Continue reading until there are no more lines in the file.
        while ((line = reader.readLine()) != null) {

            // Split the line into two parts using the first comma.
            String[] data = line.split(",", 2);

            // Make sure the line contains the expected two values.
            if (data.length == 2) {

                // Create a Movie object using the title and movie information.
                Movie movie = new Movie(
                        data[0],
                        data[1]
                );

                // Add the created movie to the movie list.
                movies.add(movie);
            }
        }

        // Close the file reader after all movie information has been read.
        reader.close();

        // Return the completed list of movies.
        return movies;
    }

    /**
     * Loads showtime information from the showtimes.txt file.
     * Each line must contain the showtime ID, movie title, room name,
     * starting date and time, and ticket price.
     *
     * @return a list containing all showtimes loaded from the file
     * @throws IOException if showtimes.txt cannot be found or read
     * @throws NumberFormatException if the ID or ticket price is not valid
     * @throws java.time.format.DateTimeParseException if the starting
     *         date and time is not valid
     */
    public static List<Showtime> loadShowtimes() throws IOException {

        // Create an empty list that will store the showtimes.
        List<Showtime> showtimes = new ArrayList<>();

        // Locate and open the showtimes.txt file from the resources folder.
        InputStream inputStream =
                MovieManager.class.getClassLoader().getResourceAsStream("showtimes.txt");

        // Check whether the showtimes.txt file was found.
        if (inputStream == null) {

            // Stop the method and report that the file could not be found.
            throw new IOException("showtimes.txt not found.");
        }

        // Create a reader that can read text from the input stream.
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(inputStream)
                );

        // Store each line that is read from the file.
        String line;

        // Continue reading until there are no more lines in the file.
        while ((line = reader.readLine()) != null) {

            // Split the line into five values separated by commas.
            String[] data = line.split(",", 5);

            // Make sure the line contains all five expected values.
            if (data.length == 5) {

                // Create a Showtime object using the values from the file.
                Showtime showtime = new Showtime(

                        // Convert the showtime ID from text into an integer.
                        Integer.parseInt(data[0]),

                        // Store the movie title associated with the showtime.
                        data[1],

                        // Create the correct auditorium using the room name.
                        createAuditorium(data[2]),

                        // Convert the date and time text into LocalDateTime.
                        LocalDateTime.parse(data[3]),

                        // Convert the ticket price from text into a decimal value.
                        Double.parseDouble(data[4])
                );

                // Add the created showtime to the showtime list.
                showtimes.add(showtime);
            }
        }

        // Close the file reader after all showtimes have been read.
        reader.close();

        // Return the completed list of showtimes.
        return showtimes;
    }


    /**
     * Creates an auditorium based on the provided room name.
     *
     * @param room the name of the auditorium
     * @return the matching Auditorium object, or null if the room
     *         name is not recognized
     */
    public static Auditorium createAuditorium(String room) {

        // Check which room name was provided.
        switch (room) {

            // Create Room 1 with 10 rows and 10 seats per row.
            case "Room 1":
                return new Auditorium(1, "Room 1", 10, 10);

            // Create Room 2 with 10 rows and 15 seats per row.
            case "Room 2":
                return new Auditorium(2, "Room 2", 10, 15);

            // Create Room 3 with 10 rows and 20 seats per row.
            case "Room 3":
                return new Auditorium(3, "Room 3", 10, 20);

            // Create Room 4 with 10 rows and 25 seats per row.
            case "Room 4":
                return new Auditorium(4, "Room 4", 10, 25);

            // Return null when the room name does not match a known room.
            default:
                return null;
        }
    }


    /**
     * Assigns each showtime to the movie with the matching title.
     *
     * @param movies the list of movies that will receive showtimes
     * @param showtimes the list of showtimes that will be assigned
     */
    public static void assignShowtimes(
            List<Movie> movies,
            List<Showtime> showtimes
    ) {

        // Loop through every movie in the movie list.
        for (Movie movie : movies) {

            // Loop through every showtime in the showtime list.
            for (Showtime showtime : showtimes) {

                // Compare the movie title with the movie title stored in the showtime.
                if (movie.getTitle().equals(showtime.getShowtimeMovie())) {

                    // Add the matching showtime to the movie.
                    movie.setShowtimes(showtime);
                }
            }
        }
    }

    /**
     * Searches for movies whose titles contain the provided keyword.
     * The search is not case-sensitive.
     *
     * @param movies the list of movies that will be searched
     * @param keyword the title keyword used for the search
     * @return a list containing all matching movies
     */
    public static List<Movie> searchMovies(
            List<Movie> movies,
            String keyword
    ) {

        // Create an empty list that will store matching movies.
        List<Movie> results = new ArrayList<>();

        // Loop through every movie in the provided movie list.
        for (Movie movie : movies) {

            // Convert both values to lowercase and check for a partial title match.
            if (movie.getTitle()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                // Add the matching movie to the search results.
                results.add(movie);
            }
        }

        // Return all movies that matched the keyword.
        return results;
    }
}