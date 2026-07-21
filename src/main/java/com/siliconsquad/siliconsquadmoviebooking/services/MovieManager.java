package com.siliconsquad.siliconsquadmoviebooking.services;

import com.siliconsquad.siliconsquadmoviebooking.models.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    public List<Movie> loadMovies() throws IOException {

        List<Movie> movies = new ArrayList<>();

        InputStream inputStream =
                getClass().getResourceAsStream("/movies.txt");

        if (inputStream == null) {
            throw new IOException("movies.txt not found.");
        }

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(inputStream)
                );

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",", 2);

            if (data.length == 2) {

                Movie movie = new Movie(
                        data[0],
                        data[1]
                );

                movies.add(movie);
            }
        }

        reader.close();

        return movies;
    }

    public List<Movie> searchMovies(
            List<Movie> movies,
            String keyword
    ) {

        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {

            if (movie.getTitle()
                    .toLowerCase()
                    .contains(keyword.toLowerCase())) {

                results.add(movie);
            }
        }

        return results;
    }
}