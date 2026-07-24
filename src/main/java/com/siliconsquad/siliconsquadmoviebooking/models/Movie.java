package com.siliconsquad.siliconsquadmoviebooking.models;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String title;
    private String description;
    private List<Showtime> showtimes;

    public Movie(String title) {
        this(title, "Movie details are currently unavailable.");
    }

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
        this.showtimes = new ArrayList<>() {
        };
    }

    public Movie(String title, String description, List<Showtime> showtimes) {
        this.title = title;
        this.description = description;
        this.showtimes = showtimes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShowtimes(Showtime showtimes){ this.showtimes.add(showtimes);}

    public List<Showtime> getShowtimes(){return showtimes;}

    @Override
    public String toString() {
        return title;
    }
}
