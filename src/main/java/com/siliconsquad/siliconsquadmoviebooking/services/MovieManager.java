package com.siliconsquad.siliconsquadmoviebooking.services;

import com.siliconsquad.siliconsquadmoviebooking.models.Auditorium;
import com.siliconsquad.siliconsquadmoviebooking.models.Movie;
import com.siliconsquad.siliconsquadmoviebooking.models.Showtime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MovieManager {

    public static List<Movie> loadMovies() throws IOException {

        List<Movie> movies = new ArrayList<>();

        InputStream inputStream =
                MovieManager.class.getClassLoader().getResourceAsStream("movies.txt");

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

    public static List<Showtime> loadShowtimes() throws IOException {

        List<Showtime> showtimes = new ArrayList<>();

        InputStream inputStream = MovieManager.class.getClassLoader().getResourceAsStream("showtimes.txt");

        if (inputStream == null) {
            throw new IOException("showtimes.txt not found.");
        }

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(inputStream)
                );

        String line;

        while ((line = reader.readLine()) != null) {

            String[] data = line.split(",", 5);

            if (data.length == 5) {

                Showtime showtime = new Showtime(
                        Integer.parseInt(data[0]),
                        data[1],
                        createAuditorium(data[2]),
                        LocalDateTime.parse(data[3]),
                        Double.parseDouble(data[4])
                );

                showtimes.add(showtime);
            }
        }
        reader.close();

        return showtimes;
    }

    public static Auditorium createAuditorium(String room){

        switch(room){

            case "Room 1":
                return new Auditorium(1,"Room 1", 10, 10);

            case "Room 2":
                return new Auditorium(2,"Room 2", 10, 15);

            case "Room 3":
                return new Auditorium(3,"Room 3", 10, 20);

            case "Room 4":
                return new Auditorium(4,"Room 4", 10, 25);

            default:
                return null;
        }
    }

    public static void assignShowtimes(List<Movie> movies, List<Showtime> showtimes){

        for(Movie movie : movies){
            for(Showtime showtime: showtimes){
                if(movie.getTitle().equals(showtime.getShowtimeMovie())){
                    movie.setShowtimes(showtime);
                }
            }
        }
    }

    public static List<Movie> searchMovies(
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