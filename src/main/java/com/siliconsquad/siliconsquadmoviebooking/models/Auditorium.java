package com.siliconsquad.siliconsquadmoviebooking.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * Represents an auditorium inside a movie theater, holds seat configurations
 * and layout information.
 * @author G. Mkrtchyan
 * @since July 6, 2026
 */
public class Auditorium {

    /** The unique identifier for the auditorium. */
    private int auditoriumId;

    /** The name or number of the auditorium. */
    private String name;

    /** The total count of rows in the auditorium. */
    private int numberOfRows;

    /** The count of seats available in each row. */
    private int seatsPerRow;

    /** The list of all seats present in this auditorium. */
    private final List<Seat> seats;

    /**
     * Creates a new Auditorium with specified dimensions and initializes its seats.
     *
     * @param auditoriumId the unique ID of the auditorium
     * @param name the name of the auditorium
     * @param numberOfRows the number of rows (must be greater than zero)
     * @param seatsPerRow the number of seats per row (must be greater than zero)
     * @throws IllegalArgumentException if rows or seats per row are less than or equal to zero
     */
    public Auditorium(
            int auditoriumId,
            String name,
            int numberOfRows,
            int seatsPerRow
    ) {
        if (numberOfRows <= 0 || seatsPerRow <= 0) {
            throw new IllegalArgumentException(
                    "Rows and seats per row must be greater than zero."
            );
        }

        this.auditoriumId = auditoriumId;
        this.name = name;
        this.numberOfRows = numberOfRows;
        this.seatsPerRow = seatsPerRow;
        this.seats = new ArrayList<>();

        createSeats();
    }

    /**
     * Creates the grid of standard seats based on row and seat counts.
     */
    private void createSeats() {
        seats.clear();

        int seatId = 1;

        for (int rowIndex = 0; rowIndex < numberOfRows; rowIndex++) {
            String rowLabel = convertRowNumberToLetters(rowIndex);

            for (int seatNumber = 1;
                 seatNumber <= seatsPerRow;
                 seatNumber++) {

                seats.add(
                        new Seat(
                                seatId,
                                rowLabel,
                                seatNumber,
                                Seat.SeatType.STANDARD
                        )
                );

                seatId++;
            }
        }
    }

    /**
     * Converts a numeric row index into an alphabetical label (e.g., 0 to "A", 25 to "Z", 26 to "AA").
     *
     * @param rowIndex the zero-based index of the row
     * @return the alphabetical row label string
     */
    private String convertRowNumberToLetters(int rowIndex) {
        StringBuilder label = new StringBuilder();
        int number = rowIndex;

        do {
            label.insert(0, (char) ('A' + number % 26));
            number = number / 26 - 1;
        } while (number >= 0);

        return label.toString();
    }

    /**
     * Returns the unique identifier of the auditorium.
     * @return the auditorium ID
     */
    public int getAuditoriumId() {
        return auditoriumId;
    }

    /**
     * Sets the unique identifier of the auditorium.
     * @param auditoriumId the new auditorium ID to set.
    */
    public void setAuditoriumId(int auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    /**
     * Gets the name of the auditorium.
     * @return the auditorium name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the auditorium.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the auditorium.
     * @return numbers of rows in the auditorium.
     */
    public int getNumberOfRows() {
        return numberOfRows;
    }

    /**
     * Gets the number of seats in each row.
     * @return seats per row
     */
    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    /**
     * Calculates the total capacity of the auditorium.
     * @return total number of seats
     */
    public int getCapacity() {
        return numberOfRows * seatsPerRow;
    }

    /**
     * Gets a list of all seats in the auditorium.
     * @return list of seats
     */
    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    /**
     * Finds a specific seat by its row label and seat number.
     * @param rowLabel the row label of the seat
     * @param seatNumber the seat number
     * @return the matching Seat object, or null if not found
     */
    public Seat findSeat(String rowLabel, int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getRowLabel().equalsIgnoreCase(rowLabel)
                    && seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }

        return null;
    }

    /**
     * Gets a list of all currently available seats.
     * @return list of available seats
     */
    public List<Seat> getAvailableSeats() {
        List<Seat> availableSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                availableSeats.add(seat);
            }
        }

        return availableSeats;
    }

    /**
     * Checks if there are any available seats left.
     * @return true if at least one seat is available, false otherwise
     */
    public boolean hasAvailableSeats() {
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Counts the total number of available seats.
     * @return count of available seats
     */
    public int getAvailableSeatCount() {
        return getAvailableSeats().size();
    }

    /**
     * Resets all seats in the auditorium to an available state.
     */
    public void resetAllSeats() {
        for (Seat seat : seats) {
            seat.release();
        }
    }

    /**
     * Returns the capacity of the auditorium in a string format.
     * @return a string format of the name and seat capacity
     */

    @Override
    public String toString() {
        return name
                + " ("
                + getCapacity()
                + " seats)";
    }

    /**
     * Compares this auditorium to another object for equality.
     * @param object the reference object with which to compare
     * @return true if this object is the same as the object argument; false if otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Auditorium auditorium)) {
            return false;
        }

        return auditoriumId == auditorium.auditoriumId;
    }

    /**
     * Returns a hash code value for the auditorium.
     * @return a hash code value based on the auditorium ID
     */
    @Override
    public int hashCode() {
        return Objects.hash(auditoriumId);
    }
}
