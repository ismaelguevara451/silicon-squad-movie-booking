package com.siliconsquad.siliconsquadmoviebooking.services;

import com.siliconsquad.siliconsquadmoviebooking.models.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserManager {

    private static final String FILE_PATH = "users.txt";

    public void saveUser(User user) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH, true)) {
            writer.write(user.toFileString());
            writer.write(System.lineSeparator());
        }
    }

    public boolean validateLogin(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length >= 3) {
                    String storedUsername = data[1].trim();
                    String storedPassword = data[2].trim();

                    if (storedUsername.equals(username) && storedPassword.equals(password)) {
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Could not read users.txt");
        }

        return false;
    }
}
