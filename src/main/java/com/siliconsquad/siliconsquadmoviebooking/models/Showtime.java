package com.siliconsquad.siliconsquadmoviebooking.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a movie showtime.
 * Stores information about the movie, auditorium, start time, ticket price and availability of seats.
 * Also has methods to access information of the showtime.
 * @author G. Mkrtchyan
 * @since July 6, 2026
 */
public class Showtime {

    private int showtimeId;
    private String movie;
    private Auditorium auditorium;
    private LocalDateTime startTime;
    private double ticketPrice;


    /**
     * Construct a new showtime
     * @param showtimeId ID number for the showtime
     * @param movie The name of the movie
     * @param auditorium The auditorium where the movie is shown
     * @param startTime The starting time of the showtime
     * @param ticketPrice The price of the ticket
     */
    public Showtime(
            int showtimeId,
            String movie,
            Auditorium auditorium,
            LocalDateTime startTime,
            double ticketPrice
    ) {
        this.showtimeId = showtimeId;
        this.movie = movie;
        this.auditorium = auditorium;
        this.startTime = startTime;
        setTicketPrice(ticketPrice);
    }

    /**
     * Returns the ID number of the showtime
     * @return ID number of the showtime
     */

    public int getShowtimeId() {
        return showtimeId;
    }

    /**
     * Returns the name of the movie
     * @return The name of the movie
     */

    public String getShowtimeMovie() {
        return movie;
    }

    /**
     * Sets the ID number of the showtime
     * @param showtimeId The ID number of the showtime
     */

    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }

    /**
     * Returns the auditorium room of the showtime
     * @return The auditorium of the showtime
     */

    public Auditorium getAuditorium() {
        return auditorium;
    }

    /**
     * Sets the auditorium room of the showtime
     * @param auditorium The auditorium of the showtime
     */

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    /**
     * Returns the starting time of the showtime
     * @return The starting time of the showtime
     */

    public LocalDateTime getStartTime() {
        return startTime;
    }


    /**
     * Sets the starting time of the showtime
     * @param startTime The starting time of the showtime
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Returns the ticket price of the showtime
     * @return Ticket price of the showtime
     */

    public double getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the ticket price of the showtime
     * @param ticketPrice Ticket price of the showtime
     */

    public void setTicketPrice(double ticketPrice) {
        if (ticketPrice < 0) {
            throw new IllegalArgumentException(
                    "Ticket price cannot be negative."
            );
        }

        this.ticketPrice = ticketPrice;
    }

    /**
     * Formats the showtime's starting time and returns it
     * @return Showtime's formatted starting time
     */

    public String getFormattedStartTime() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("EEE h:mm a - MM/dd/yyyy");

        return auditorium.getName().toUpperCase()
            + " - "
            + startTime.format(formatter).toUpperCase();

    }

    /**
     * Compares two showtimes using their showtime ID.
     *
     * @param object the object to compare
     * @return true if the showtimes have the same ID, otherwise false
     */

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Showtime showtime)) {
            return false;
        }

        return showtimeId == showtime.showtimeId;
    }

    /**
     * Returns the hash code of the showtime based on its ID.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(showtimeId);
    }
}
