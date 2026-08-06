package com.siliconsquad.siliconsquadmoviebooking.services;

import com.siliconsquad.siliconsquadmoviebooking.models.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static final String FILE_PATH = "users.txt";

    public static void saveUser(User user) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(user.toFileString());
            writer.write(System.lineSeparator());
        }
    }

    public static boolean validateLogin(String username, String password) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {

                    if (data[1].trim().equals(username)
                            && data[3].trim().equals(password)) {
                        return true;
                    }

                } else if (data.length == 3) {

                    if (data[1].trim().equals(username)
                            && data[2].trim().equals(password)) {
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read users.txt");
        }

        return false;
    }

    public static boolean usernameExists(String username) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length >= 2 &&
                        data[1].trim().equalsIgnoreCase(username)) {
                    return true;
                }
            }

        } catch (IOException ignored) {
        }

        return false;
    }

    public static boolean verifyUser(String username, String dateOfBirth) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4) {

                    if (data[1].trim().equals(username)
                            && data[2].trim().equals(dateOfBirth)) {
                        return true;
                    }
                }
            }

        } catch (IOException ignored) {
        }

        return false;
    }

    public static boolean updatePassword(String username, String newPassword) {

        List<String> lines = new ArrayList<>();

        boolean updated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 4 &&
                        data[1].trim().equals(username)) {

                    data[3] = newPassword;

                    line = String.join(",", data);

                    updated = true;
                }

                lines.add(line);
            }

        } catch (IOException e) {
            return false;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {

            for (String line : lines) {
                writer.println(line);
            }

        } catch (IOException e) {
            return false;
        }

        return updated;
    }
}
