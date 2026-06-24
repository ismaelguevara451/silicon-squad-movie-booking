package com.siliconsquad.siliconsquadmoviebooking.models;

/**
 * Represents a user account in the movie booking system.
 * Stores the username and password entered during registration.
 */
public class User {

    // Username chosen by the user
    private String username;

    // Password associated with the account
    private String password;

    /**
     * Creates a new User object.
     *
     * @param username User's username
     * @param password User's password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the user's username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Converts the user object into a format suitable
     * for saving into a text file.
     *
     * Example:
     * john,password123
     *
     * @return formatted string representation of user
     */
    public String toFileString() {
        return username + "," + password;
    }
}