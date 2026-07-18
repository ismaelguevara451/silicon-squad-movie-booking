package com.siliconsquad.siliconsquadmoviebooking.models;

public class Movie {

    private String title;
    private String description;

    public Movie(String title) {
        this(title, "Movie details are currently unavailable.");
    }

    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
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

    @Override
    public String toString() {
        return title;
    }
}
