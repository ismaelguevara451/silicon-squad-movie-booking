package com.siliconsquad.siliconsquadmoviebooking.models;

/**
 * Represents a registered user account.
 */
public class User {

    private final String name;
    private final String username;
    private final String dateOfBirth;
    private final String password;

    /**
     * Creates a new user with a date of birth.
     */
    public User(
            String name,
            String username,
            String dateOfBirth,
            String password
    ) {
        this.name = name;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
    }

    /**
     * Compatibility constructor for older code and older users.
     */
    public User(
            String name,
            String username,
            String password
    ) {
        this(name, username, "", password);
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    /**
     * New users are saved as:
     * name,username,dateOfBirth,password
     */
    public String toFileString() {
        return sanitize(name)
                + ","
                + sanitize(username)
                + ","
                + sanitize(dateOfBirth)
                + ","
                + sanitize(password);
    }

    private String sanitize(String value) {
        if (value == null) {
            return "";
        }

        return value
                .trim()
                .replace(",", "");
    }
}
