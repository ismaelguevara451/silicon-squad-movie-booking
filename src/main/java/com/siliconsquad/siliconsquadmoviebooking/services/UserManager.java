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

// This class manages user registration, login, and password operations.
public class UserManager {

    // Store the location of the file that contains user account information.
    private static final String FILE_PATH = "users.txt";

    // This method saves a new user account to users.txt.
    public static void saveUser(User user) throws IOException {

        // Open users.txt in append mode so existing accounts are not deleted.
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {

            // Convert the User object into its file-storage format and write it.
            writer.write(user.toFileString());

            // Move to a new line for the next user account.
            writer.write(System.lineSeparator());
        }
    }

    // This method checks whether a username and password are valid.
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

    // This method checks whether a username is already registered.
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

    // This method verifies a user using a username and date of birth.
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

    // This method replaces a user's current password with a new password.
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