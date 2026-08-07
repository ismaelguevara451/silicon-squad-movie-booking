package com.siliconsquad.siliconsquadmoviebooking.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a movie in the movie booking system.
 * Keeps track of the movie's title, description, and scheduled showtimes.
 */
public class Movie {

    /** The title of the movie. */
    private String title;

    /** The description of the movie. */
    private String description;

    /** The list of showtimes of the movie.  */
    private List<Showtime> showtimes;


    /**
     * Constructs a Movie with a title.
     * Sets a default placeholder message for the description.
     * @param title the title of the movie
     */
    public Movie(String title) {
        this(title, "Movie details are currently unavailable.");
    }

    /**
     * Constructs a Movie with a title and a description.
     * Initializes an empty list for showtimes.
     * @param title the title of the movie
     * @param description a short summary of the movie
     */
    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
        this.showtimes = new ArrayList<>() {
        };
    }

    /**
     * Constructs a Movie with a title, description, and a provided list of showtimes.
     * @param title the title of the movie
     * @param description the description of the movie
     * @param showtimes the list of showtimes for the movie
     */
    public Movie(String title, String description, List<Showtime> showtimes) {
        this.title = title;
        this.description = description;
        this.showtimes = showtimes;
    }

    /**
     * Gets the title of the movie.
     * @return the movie title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets or updates the title of the movie.
     * @param title the new movie title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the movie.
     * @return the movie description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the movie.
     * @param description the new description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds a showtime to the movie's list of showtimes.
     * @param showtimes the showtime to add
     */
    public void setShowtimes(Showtime showtimes){ this.showtimes.add(showtimes);}

    /**
     * Gets the list of showtimes for this movie.
     * @return a list of showtimes
     */
    public List<Showtime> getShowtimes(){return showtimes;}

    /**
     * Returns a string representation of the movie.
     * @return the title of the movie
     */
    @Override
    public String toString() {
        return title;
    }
}
