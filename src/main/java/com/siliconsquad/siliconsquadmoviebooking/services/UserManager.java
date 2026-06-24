package com.siliconsquad.siliconsquadmoviebooking.services;

import com.siliconsquad.siliconsquadmoviebooking.models.User;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles user-related operations such as
 * saving user information to a file.
 */
public class UserManager {

    // File used to store registered users
    private static final String FILE_PATH = "users.txt";

    /**
     * Saves a user to the users.txt file.
     * New users are appended to the end of the file.
     *
     * @param user User object to save
     * @throws IOException if file writing fails
     */
    public void saveUser(User user) throws IOException {

        // Append user information to file
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {

            // Write user data in CSV format
            writer.write(user.toFileString());

            // Move to next line for the next user
            writer.write(System.lineSeparator());
        }
    }
}