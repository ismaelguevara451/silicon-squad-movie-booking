package com.siliconsquad.siliconsquadmoviebooking.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Auditorium {

    private int auditoriumId;
    private String name;
    private int numberOfRows;
    private int seatsPerRow;
    private final List<Seat> seats;

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

    private String convertRowNumberToLetters(int rowIndex) {
        StringBuilder label = new StringBuilder();
        int number = rowIndex;

        do {
            label.insert(0, (char) ('A' + number % 26));
            number = number / 26 - 1;
        } while (number >= 0);

        return label.toString();
    }

    public int getAuditoriumId() {
        return auditoriumId;
    }

    public void setAuditoriumId(int auditoriumId) {
        this.auditoriumId = auditoriumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public int getCapacity() {
        return numberOfRows * seatsPerRow;
    }

    public List<Seat> getSeats() {
        return Collections.unmodifiableList(seats);
    }

    public Seat findSeat(String rowLabel, int seatNumber) {
        for (Seat seat : seats) {
            if (seat.getRowLabel().equalsIgnoreCase(rowLabel)
                    && seat.getSeatNumber() == seatNumber) {
                return seat;
            }
        }

        return null;
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> availableSeats = new ArrayList<>();

        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                availableSeats.add(seat);
            }
        }

        return availableSeats;
    }

    public boolean hasAvailableSeats() {
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                return true;
            }
        }

        return false;
    }

    public int getAvailableSeatCount() {
        return getAvailableSeats().size();
    }

    public void resetAllSeats() {
        for (Seat seat : seats) {
            seat.release();
        }
    }

    @Override
    public String toString() {
        return name
                + " ("
                + getCapacity()
                + " seats)";
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(auditoriumId);
    }
}
