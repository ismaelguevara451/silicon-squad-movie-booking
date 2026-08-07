package com.siliconsquad.siliconsquadmoviebooking.models;

/**
 * Represents a registered user account.
 * The User class stores the user's name, username, date of birth and password.
 * Also has methods to access user's information and formatting the information for storing.
 * @author I. Regalado
 * @since July 6, 2026
 */
public class User {

    private final String name;
    private final String username;
    private final String dateOfBirth;
    private final String password;

    /**
     * Construct an account with a name, username, date of birth and password.
     * @param name the name of the user.
     * @param username the username of the user's account.
     * @param dateOfBirth the date of birth of the user.
     *@param password the password of the user's account.
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
     * This one constructs a user object without date of birth.
     * @param name the name of the user.
     * @param username the username of the user's account.
     * @param password the password of the user's account.
     */
    public User(
            String name,
            String username,
            String password
    ) {
        this(name, username, "", password);
    }

    /**
     * Returns user's name.
     * @return the user's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns user's username.
     * @return username of the user account.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns user's date of birth.
     * @return user's date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }


    /**
     * Returns user's password.
     * @return user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Convert's the user information into a one string of text separated by commas.
     * This is then used to be stored into text file.
     * New users are saved as:
     * name,username,dateOfBirth,password
     * @return the user's information formatted in one string of text.
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


    /**
     * Cleans the text in string of leading and trailing spaces.
     * Also removes commas.
     * @param value the string that needs to cleaned.
     * @return cleaned string or and empty string if the input value is null.
     */
    private String sanitize(String value) {
        if (value == null) {
            return "";
        }

        return value
                .trim()
                .replace(",", "");
    }
}
