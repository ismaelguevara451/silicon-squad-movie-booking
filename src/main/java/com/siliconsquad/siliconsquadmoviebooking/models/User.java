package com.siliconsquad.siliconsquadmoviebooking.models;

/**
 * Represents a user account in the movie booking system.
 * Stores the user's name, username, and password entered during registration.
 */
public class User {

    // User's full name
    private String name;

    // Username chosen by the user
    private String username;

    // Password associated with the account
    private String password;

    /**
     * Creates a new User object.
     *
     * @param name User's full name
     * @param username User's username
     * @param password User's password
     */
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns the user's full name.
     *
     * @return name
     */
    public String getName() {
        return name;
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
     * John Doe,johndoe,password123
     *
     * @return formatted string representation of user
     */
    public String toFileString() {
        return name + "," + username + "," + password;
    }
}