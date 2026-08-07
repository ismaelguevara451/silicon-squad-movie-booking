package com.siliconsquad.siliconsquadmoviebooking.services;

// Import the User model used to store account information.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.siliconsquad.siliconsquadmoviebooking.models.User;

/**
 * Manages user account operations for the Movie Booking System.
 * This class provides methods for saving users, validating login
 * information, checking usernames, verifying users, and updating passwords.
 * @author I. Regalado
 * @since June 24, 2026
 */
public class UserManager {

    private static final String FILE_PATH = "users.txt";

    /**
     * Saves a new user account to the users file.
     * The user is added to the end of the file without deleting
     * existing user records.
     *
     * @param user the User object that will be saved
     * @throws IOException if the users file cannot be opened or written to
     */
    public static void saveUser(User user) throws IOException {

        // Open users.txt in append mode so existing accounts are not deleted.
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {

            // Convert the User object into its file-storage format and write it.
            writer.write(user.toFileString());

            // Move to a new line for the next user account.
            writer.write(System.lineSeparator());
        }
    }

    /**
     * Checks whether the provided username and password match
     * a stored user account.
     *
     * This method supports both current user records containing four
     * values and older user records containing three values.
     *
     * @param username the username entered by the user
     * @param password the password entered by the user
     * @return true if the username and password match, otherwise false
     */
    public static boolean validateLogin(
            String username,
            String password
    ) {

        // Open users.txt and automatically close it after reading.
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_PATH))) {

            // Store each line that is read from the file.
            String line;

            // Continue reading until there are no more user records.
            while ((line = reader.readLine()) != null) {

                // Split the user record into separate values.
                String[] data = line.split(",");

                // Handle records containing four stored values.
                if (data.length == 4) {

                    // Compare the stored username and password with the entered values.
                    if (data[1].trim().equals(username)
                            && data[3].trim().equals(password)) {

                        // Return true when both values match.
                        return true;
                    }

                // Handle older records containing three stored values.
                } else if (data.length == 3) {

                    // Compare the stored username and password with the entered values.
                    if (data[1].trim().equals(username)
                            && data[2].trim().equals(password)) {

                        // Return true when both values match.
                        return true;
                    }
                }
            }

        // Handle an error that occurs while reading users.txt.
        } catch (IOException e) {

            // Display an error message in the terminal.
            System.out.println("Could not read users.txt");
        }

        // Return false when no matching account was found.
        return false;
    }

    /**
     * Checks whether a username is already stored in the users file.
     * The username comparison is not case-sensitive.
     *
     * @param username the username that will be checked
     * @return true if the username already exists, otherwise false
     */
    public static boolean usernameExists(String username) {

        // Open users.txt and automatically close it after reading.
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_PATH))) {

            // Store each line that is read from the file.
            String line;

            // Continue reading until there are no more user records.
            while ((line = reader.readLine()) != null) {

                // Split the user record into separate values.
                String[] data = line.split(",");

                // Make sure a username exists and compare it without case sensitivity.
                if (data.length >= 2
                        && data[1].trim().equalsIgnoreCase(username)) {

                    // Return true when the username is already stored.
                    return true;
                }
            }

        // Ignore file-reading errors and continue to return false.
        } catch (IOException ignored) {
        }

        // Return false when the username was not found.
        return false;
    }

    /**
     * Verifies a user using their username and date of birth.
     *
     * @param username the username of the account
     * @param dateOfBirth the date of birth connected to the account
     * @return true if the username and date of birth match, otherwise false
     */
    public static boolean verifyUser(
            String username,
            String dateOfBirth
    ) {

        // Open users.txt and automatically close it after reading.
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_PATH))) {

            // Store each line that is read from the file.
            String line;

            // Continue reading until there are no more user records.
            while ((line = reader.readLine()) != null) {

                // Split the user record into separate values.
                String[] data = line.split(",");

                // Only check records containing four values.
                if (data.length == 4) {

                    // Compare the stored username and date of birth.
                    if (data[1].trim().equals(username)
                            && data[2].trim().equals(dateOfBirth)) {

                        // Return true when both values match.
                        return true;
                    }
                }
            }

        // Ignore file-reading errors and continue to return false.
        } catch (IOException ignored) {
        }

        // Return false when the user's information was not verified.
        return false;
    }

    /**
     * Replaces the password of a stored user account.
     * The method reads all user records, changes the matching user's
     * password, and rewrites the users file.
     *
     * @param username the username of the account being updated
     * @param newPassword the new password that will replace the old password
     * @return true if the password was updated, otherwise false
     */
    public static boolean updatePassword(
            String username,
            String newPassword
    ) {

        // Create a list that will temporarily store all user records.
        List<String> lines = new ArrayList<>();

        // Track whether the requested user's password was updated.
        boolean updated = false;

        // Open users.txt and automatically close it after reading.
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(FILE_PATH))) {

            // Store each line that is read from the file.
            String line;

            // Continue reading until there are no more user records.
            while ((line = reader.readLine()) != null) {

                // Split the user record into separate values.
                String[] data = line.split(",");

                // Check for a four-value record with the requested username.
                if (data.length == 4
                        && data[1].trim().equals(username)) {

                    // Replace the stored password with the new password.
                    data[3] = newPassword;

                    // Rebuild the modified user record as one comma-separated line.
                    line = String.join(",", data);

                    // Record that the password was successfully changed.
                    updated = true;
                }

                // Add the original or modified record to the temporary list.
                lines.add(line);
            }

        // Return false when users.txt could not be read.
        } catch (IOException e) {
            return false;
        }

        // Open users.txt for rewriting and automatically close it afterward.
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter(FILE_PATH))) {

            // Loop through all user records in the temporary list.
            for (String line : lines) {

                // Write each record back into users.txt.
                writer.println(line);
            }

        // Return false when users.txt could not be rewritten.
        } catch (IOException e) {
            return false;
        }

        // Return true if a password was changed, or false if no user matched.
        return updated;
    }
}